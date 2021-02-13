package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    Player pl1;
    Player pl2;
    Board bd;

    @BeforeEach
    public void setup(){
        bd = new Board();
        pl1 = new Player(false,bd,"TestPlayer1");
        pl2 = new Player(true,bd,"TestPlayer2");
    }

    @Test
    public void playerConstructorTest(){
        assertEquals("TestPlayer1", pl1.getName());
        assertEquals("TestPlayer2",pl2.getName());
        assertTrue(pl2.getPieceColor());
        assertFalse(pl1.getPieceColor());
    }

    @Test
    public void testCheckPieceSelection(){
        assertTrue(pl1.checkPieceSelection("H8"));
        assertFalse(pl1.checkPieceSelection("D4"));
        assertTrue(pl2.checkPieceSelection("D2"));
        assertFalse(pl2.checkPieceSelection("D4"));
    }

    @Test
    public void makeMove(){

        String origSquare1 = "A2"; // leftmost white pawn
        String destSquare1 = "A4";
        assertTrue(pl2.makeMove(origSquare1,destSquare1));
        assertFalse(pl2.checkPieceSelection(origSquare1));

        String origSquare2 = "A8"; // leftmost black rook
        String destSquare2 = "A6";
        assertFalse(pl1.makeMove(destSquare1,destSquare2));
        assertTrue(pl1.checkPieceSelection(origSquare2));
    }

    @Test
    public void testletterToPos(){
        assertEquals(0,pl1.letterToPos("A"));
        assertEquals(1,pl1.letterToPos("B"));
        assertEquals(2,pl1.letterToPos("C"));
        assertEquals(3,pl1.letterToPos("D"));
        assertEquals(4,pl1.letterToPos("E"));
        assertEquals(5,pl1.letterToPos("F"));
        assertEquals(6,pl1.letterToPos("G"));
        assertEquals(7,pl1.letterToPos("H"));
        assertEquals(-1,pl1.letterToPos("Z"));
    }

}
