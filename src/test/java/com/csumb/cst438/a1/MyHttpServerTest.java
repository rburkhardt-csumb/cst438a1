/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csumb.cst438.a1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.sun.net.httpserver.Headers;

/**
 *
 * @author david wisneski
 * @version 1.0
 * last update 3-21-2017
 */
public class MyHttpServerTest
{

    public MyHttpServerTest ()
    {
    }

    @BeforeClass
    public static void setUpClass ()
    {
    }

    @AfterClass
    public static void tearDownClass ()
    {
    }

    @Before
    public void setUp ()
    {
    }

    @After
    public void tearDown ()
    {
    }

    /**
     * Test of main method, of class MyHttpServer.
     */
    @Test
    public void testHandle ()
    {
        String expectedBody = "<!DOCTYPE html><html><head><title>MyHttpServer</title></head><body><h2>Hangman</h2>"
                            + "<img src=\"h1.gif\">"
                            + "<h2 style=\"font-family:'Lucida Console', monospace\">_ _ _ _ _ _ _ _</h2>"
                            + "<form action=\"/\" method=\"get\"> "
                            + "Guess a character <input type=\"text\" name=\"guess\"><br>"
                            + "<input type=\"submit\" value=\"Submit\">" + "</form></body></html>";

        Headers header = new Headers();
        String cookie1 = "";
        MyHttpServer.MyHandler handler = new MyHttpServer.MyHandler();
        try
        {
            TestHttpExchange t = new TestHttpExchange("/", header);
            handler.handle(t);
            // check response for cookie returned, response code=200, and expected response body 
            Headers response = t.getResponseHeaders();
            cookie1 = response.getFirst("Set-cookie");
            assertEquals("Bad content type", "text/html", response.getFirst("Content-type"));
            assertNotNull("No cookie returned", cookie1);
            assertEquals("Bad response code.", 200, t.getResponseCode());
            assertEquals("Bad response body.", expectedBody, t.getOstream().toString());
        }
        catch ( Exception e )
        {
            fail("unexpected exception in testHandle " + e.getMessage());
        }
        
        // The following loop tests for correct response from the MyHttpServer
        // when invalid inputs are submited, characters that are not alphabetic.
        // This test works only when Game.startNewGame sets the variable word to
        // computer, if it is set to random the test will fail do to a random
        // word being set to the variable word.
        String testString = " 1#";
        String testUri = "";
        int state = 1;
        
        for ( char testChar : testString.toCharArray() )
        {
            expectedBody = "<!DOCTYPE html><html><head><title>MyHttpServer</title></head>"
                    + "<body><h2>Hangman</h2><img src=\"h" + state + ".gif" + "\">"
                    + "<h2 style=\"font-family:'Lucida Console', monospace\">_ _ _ _ _ _ _ _</h2>"
                    + "<h2 style=\"font-family:'Lucida Console', monospace; color:red;\">Invalid input try again!</h2>"
                    + "<form action=\"/\" method=\"get\"> Guess a character <input type=\"text\" name=\"guess\">"
                    + "<br><input type=\"submit\" value=\"Submit\"></form></body></html>";
            
            try
            {
                if ( testChar == ' ' )
                    testUri = "/?guess=";
                else
                    testUri = "/?guess=" + testChar;
                header = new Headers();
                header.add("Cookie", cookie1);  // cookie from previous response
                TestHttpExchange t = new TestHttpExchange(testUri, header);
                handler.handle(t);
                // check response for response code, content-type and response body 
                Headers response = t.getResponseHeaders();
                cookie1 = response.getFirst("Set-cookie");
                assertEquals("Bad content type", "text/html", response.getFirst("Content-type"));
                assertEquals("Bad response code.", 200, t.getResponseCode());
                assertEquals("Bad response body.", expectedBody, t.getOstream().toString());
            }
            catch ( Exception e )
            {
                fail("unexpected exception in testHandle " + e.getMessage());
            }
        }
        
        // The following tests for the request to download h1.gif
        try
        {
            header = new Headers();
            TestHttpExchange t = new TestHttpExchange("/h1.gif", header);
            handler.handle(t);
            Headers response = t.getResponseHeaders();
            assertEquals("Bad content type", "image/gif", response.getFirst("Content-type"));
            assertEquals("Bad response code.", 200 , t.getResponseCode());
            assertEquals("Bad response length", "8581", response.getFirst("Content-length"));
        }
        catch ( Exception e )
        {
            fail("unexpected exception in testHandle " + e.getMessage());
        }
        
        // The following tests for the request to download a image that does not
        // exist, h9.gif
        try
        {
            header = new Headers();
            TestHttpExchange t = new TestHttpExchange("/h9.gif", header);
            handler.handle(t);
            Headers response = t.getResponseHeaders();
            // checks if the response from t is empty or populated
            assertEquals(true, response.isEmpty());
        }
        catch ( Exception e )
        {
            fail("unexpected exception in testHandle " + e.getMessage());
        }

    }

    /**
     * Test of main method, of class MyHttpServer.
     */
    @Test
    public void testMain ()
    {
        System.out.println("main");
        String[] args = null;
        MyHttpServer.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
