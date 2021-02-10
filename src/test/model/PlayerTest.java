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
        assertTrue(pl1.checkPieceSelection(63));
        assertFalse(pl1.checkPieceSelection(30));
        assertTrue(pl2.checkPieceSelection(0));
        assertFalse(pl2.checkPieceSelection(30));
    }

    @Test
    public void makeMove(){

        int origSquare1 = 8; // leftmost white pawn
        int x1 = origSquare1 % 8;
        int y1 = origSquare1 / 8;
        assertTrue(pl2.makeMove(8,16));
        assertFalse(pl2.checkPieceSelection(8));
        assertFalse(bd.checkTile(pl2.getPieceColor(),x1,y1));

        int origSquare2 = 56; // leftmost black rook
        int x2 = origSquare2 % 8;
        int y2 = origSquare2 / 8;
        assertFalse(pl1.makeMove(56,48));
        assertTrue(pl1.checkPieceSelection(56));
        assertTrue(bd.checkTile(pl1.getPieceColor(),x2,y2));


    }

}
