package com.csumb.cst438.a1;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test of Game class
 * @author david wisneski
 * @veraion 1.0
 */
public class GameTest {
    
    public GameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getState method, of class Game.
     * at start of game, state should be 1.
     * a correct guess will not change the state
     * an incorrect guess will increase state by 1
     */
    @org.junit.Test
    public void testGetState() {
        System.out.println("getState");
        Game instance = new Game();
        int expResult = 1;
        int result = instance.getState();
        assertEquals(expResult, result);
        instance.playGame('c');
        result = instance.getState();
        assertEquals(expResult, result);
        instance.playGame('d');
        result = instance.getState();
        assertEquals(expResult+1, result);
    }

    /**
     * Test of getWord method, of class Game.
     */
    @org.junit.Test
    public void testGetWord() {
        System.out.println("getWord");
        Game instance = new Game();
        String expResult = "computer";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDisplayWord method, of class Game.
     */
    @org.junit.Test
    public void testGetDisplayWord() {
        System.out.println("getDisplayWord");
        Game instance = new Game();
        String expResult = "_ _ _ _ _ _ _ _";
        String result = instance.getDisplayWord();
        assertEquals(expResult, result);
        instance.playGame('r');
        result = instance.getDisplayWord();
        assertEquals("_ _ _ _ _ _ _ r", result);

    }

    /**
     * Test of startNewGame method, of class Game.
     */
    @org.junit.Test
    public void testStartNewGame() {
        System.out.println("startNewGame");
        Game instance = new Game();
        instance.startNewGame();
        instance.playGame('c');
        instance.playGame('d');
        instance.startNewGame();
        int result = instance.getState();
        assertEquals(1,result);
 
    }

    /**
     * Test of playGame method, of class Game.
     *   correct guess should return 0 , or 1 when game is won
     *   incorrect guess should return 2, or 3 when game is lost
     */
    @org.junit.Test
    public void testPlayGame() {
        System.out.println("playGame");
        String word = "";
        char guess = 'c';
        Game instance = new Game();
        int expResult = 0;
        int result = instance.playGame(guess);
        assertEquals(expResult, result);
        result = instance.playGame('d');
        assertEquals(2, result);
        result = instance.playGame('f');
        assertEquals(2, result);
        result = instance.playGame('g');
        assertEquals(2, result);
        result = instance.playGame('H');
        assertEquals(2,result);
        result = instance.playGame('j');
        assertEquals(2,result);
        result = instance.playGame('k');
        assertEquals(3,result);
 
        instance.startNewGame();
        word = instance.getWord();
        System.out.println(word);
        ArrayList<Character> wordChars = new ArrayList<>();
        for ( int i = 0; i < word.length(); i++ )
        {
            char test = word.charAt(i);
            if ( !wordChars.contains(test) )
            {
                wordChars.add(test);
            }
        }
        System.out.println(wordChars);
        for ( int i = 0; i < wordChars.size(); i++)
        {
            guess = wordChars.get(i);
            if ( i == 3 )
            {
                guess = Character.toUpperCase(guess);
            }
            
            result = instance.playGame( guess );
            if ( i == wordChars.size()-1 )
            {
                assertEquals(1,result);
            }
            else
            {
                assertEquals(0,result);
            }            
        }
    }
    
}
