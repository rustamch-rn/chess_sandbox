package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    // delete or rename this class!
    Board b1;
    Board b2;


    @BeforeEach
    public void setup() {
        b1 = new Board();
        b2 = new Board();
        Piece[][] tiles = b2.getTiles();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b2.removePiece(j,i);
            }
        }
    }

    @Test
    public void testConstructor(){
        b1 = new Board();
        Piece[][] tiles1 = b1.getTiles();
        int rowCount = 0;
        int squareCount = 0;
        int pieceCount = 0;
        for (Piece[] row : tiles1){
            rowCount++;
            for (Piece p : row){
                squareCount++;
                if (p != null) {
                    pieceCount++;
                }
            }
        }
        assertEquals(8,rowCount);
        assertEquals(64,squareCount);
        assertEquals(32,pieceCount);
    }

    @Test
    public void testCheckTile() {
        assertEquals(false,b1.checkTile(true,2,6));
        for (int i = 0; i < 16; i++){
            int x = i % 8;
            int y = i / 8;
            assertTrue(b1.checkTile(true,x,y));
        }
        for (int i = 48; i < 64; i++){
            int x = i % 8;
            int y = i / 8;
            assertTrue(b1.checkTile(false,x,y));
        }
        for (int i = 16; i < 48; i++){
            int x = i % 8;
            int y = i / 8;
            assertFalse(b1.checkTile(false,x,y));
        }
        for (int i = 16; i < 48; i++){
            int x = i % 8;
            int y = i / 8;
            assertFalse(b1.checkTile(true,x,y));
        }

    }

    @Test
    public void testMakeMove() {
        assertTrue(b1.checkMoveRules(0,1,0,3));
        assertFalse(b1.checkMoveRules(0,0,1,0));
    }

    @Test
    public void testAddPiece() {
        Piece p1 = new Pawn(true,2,1,b2);
        Piece p2 = new King(false,3,6,b2);
        b2.setTile(p1,2,1);
        b2.setTile(p2,3,6);
        assertTrue(b2.checkTile(true,2,1));
        assertFalse(b2.checkTile(true,2,3));
        assertTrue(b2.checkTile(false,3,6));
        assertFalse(b2.checkTile(false,2,6));
    }
    @Test
    public void checkDestSquare() {
        Piece p1 = new Pawn(true,2,1,b2);
        Piece p2 = new Pawn(false,3,6,b2);
        b2.setTile(p1,2,1);
        b2.setTile(p2,3,6);
        assertTrue(b2.checkDestinationSquare(true,2,1,2,3));
        assertFalse(b2.checkDestinationSquare(true,2,3,2,1));
        assertTrue(b2.checkDestinationSquare(false,3,6,3,4));
        assertFalse(b2.checkDestinationSquare(false,3,4,3,6));
        assertTrue(b2.checkDestinationSquare(true,2,3,3,4));
    }

    @Test
    void verticalForwardMove() {
        Piece p1 = new Queen(true,1,2,b2);
        Piece p2 = new Pawn(false,1,5,b2);
        Piece p3 = new Pawn(true,1,6,b2);
        b2.setTile(p1,1,2);
        b2.setTile(p2,1,5);
        b2.setTile(p3,1,6);
        assertTrue(b2.verticalForwardMove(true,1,2,1,4));
        assertTrue(b2.verticalForwardMove(true,1,4,1,5));
        assertFalse(b2.verticalForwardMove(true,1,5,1,7));
        assertTrue(b2.verticalForwardMove(true,1,6,1,7));
        assertFalse(b2.verticalForwardMove(true,1,7,1,8));
    }

    @Test
    void verticalBackwardMove() {
        Piece p1 = new Queen(true,1,7,b2);
        Piece p2 = new Pawn(false,1,3,b2);
        Piece p3 = new Pawn(true,1,1,b2);
        b2.setTile(p1,1,7);
        b2.setTile(p2,1,3);
        b2.setTile(p3,1,1);
        assertTrue(b2.verticalBackwardMove(true,1,7,1,5));
        assertTrue(b2.verticalBackwardMove(true,1,5,1,3));
        assertFalse(b2.verticalBackwardMove(true,1,3,1,0));
        assertFalse(b2.verticalBackwardMove(true,1,1,1,-1));
    }

    @Test
    void horizontalRightwardMove() {
        Piece p1 = new Queen(true,2,1,b2);
        Piece p2 = new Pawn(false,5,1,b2);
        Piece p3 = new Pawn(true,6,1,b2);
        b2.setTile(p1,2,1);
        b2.setTile(p2,5,1);
        b2.setTile(p3,6,1);
        assertTrue(b2.horizontalRightwardMove(true,2,1,4,1));
        assertTrue(b2.horizontalRightwardMove(true,4,1,5,1));
        assertFalse(b2.horizontalRightwardMove(true,5,1,7,1));
        assertFalse(b2.horizontalRightwardMove(true,6,1,8,1));
    }

    @Test
    void horizontalLeftwardMove() {
        Piece p1 = new Queen(true,7,1,b2);
        Piece p2 = new Pawn(false,5,1,b2);
        Piece p3 = new Pawn(true,2,1,b2);
        b2.setTile(p1,7,1);
        b2.setTile(p2,5,1);
        b2.setTile(p3,2,1);
        assertTrue(b2.horizontalLeftwardMove(true,7,1,6,1));
        assertTrue(b2.horizontalLeftwardMove(true,6,1,5,1));
        assertFalse(b2.horizontalLeftwardMove(true,5,1,1,1));
        assertFalse(b2.horizontalLeftwardMove(true,2,1,-1,1));
    }

    @Test
    void diagonalUpRightMove() {
        Piece p1 = new Queen(true,2,1,b2);
        Piece p2 = new Pawn(false,4,3,b2);
        Piece p3 = new Pawn(true,6,5,b2);
        b2.setTile(p1,2,1);
        b2.setTile(p2,4,3);
        b2.setTile(p3,6,5);
        assertTrue(b2.diagonalUpRightMove(true,2,1,3,2));
        assertTrue(b2.diagonalUpRightMove(true,3,2,4,3));
        assertFalse(b2.diagonalUpRightMove(true,4,3,7,6));
        assertFalse(b2.diagonalUpRightMove(true,6,5,7,8));
        assertFalse(b2.diagonalUpRightMove(true,6,5,8,6));
        assertFalse(b2.diagonalUpRightMove(true,6,5,8,7));
        assertFalse(b2.diagonalUpRightMove(true,6,5,6,8));
        assertFalse(b2.diagonalUpRightMove(true,5,6,7,8));
        assertFalse(b2.diagonalUpRightMove(true,6,5,8,7));
        assertFalse(b2.diagonalUpRightMove(true,7,7,8,8));
    }

    @Test
    void diagonalUpLeftMove() {
        Piece p1 = new Queen(true,6,1,b2);
        Piece p2 = new Pawn(false,4,3,b2);
        Piece p3 = new Pawn(true,2,5,b2);
        b2.setTile(p1,6,1);
        b2.setTile(p2,4,3);
        b2.setTile(p3,2,5);
        assertTrue(b2.diagonalUpLeftMove(true,6,1,5,2));
        assertTrue(b2.diagonalUpLeftMove(true,5,2,4,3));
        assertFalse(b2.diagonalUpLeftMove(true,4,3,1,6));
        assertFalse(b2.diagonalUpLeftMove(true,2,5,7,8));
        assertFalse(b2.diagonalUpLeftMove(true,6,5,8,6));
        assertFalse(b2.diagonalUpLeftMove(true,6,5,8,7));
        assertFalse(b2.diagonalUpLeftMove(true,6,5,6,8));
        assertFalse(b2.diagonalUpLeftMove(true,1,7,0,8));
        assertFalse(b2.diagonalUpLeftMove(true,0,6,-1,7));
        assertFalse(b2.diagonalUpLeftMove(true,0,7,0,8));
    }

    @Test
    void diagonalDownRightMove() {
        Piece p1 = new Queen(true,0,7,b2);
        Piece p2 = new Pawn(false,2,5,b2);
        Piece p3 = new Pawn(true,4,3,b2);
        b2.setTile(p1,0,7);
        b2.setTile(p2,2,5);
        b2.setTile(p3,4,3);
        assertTrue(b2.diagonalDownRightMove(true,0,7,1,6));
        assertFalse(b2.diagonalDownRightMove(true,1,6,2,4));
        assertFalse(b2.diagonalDownRightMove(true,1,6,3,5));
        assertTrue(b2.diagonalDownRightMove(true,1,6,2,5));
        assertFalse(b2.diagonalDownRightMove(true,2,5,2,2));
        assertFalse(b2.diagonalDownRightMove(true,3,3,-1,-1));
        assertFalse(b2.diagonalDownRightMove(true,4,3,0,-1));
        assertFalse(b2.diagonalDownRightMove(true,3,4,-1,0));
        assertFalse(b2.diagonalDownRightMove(true,7,7,8,8));
    }

    @Test
    void diagonalDownLeftMove() {
        Piece p1 = new Queen(true,6,5,b2);
        Piece p2 = new Pawn(false,4,3,b2);
        Piece p3 = new Pawn(true,2,1,b2);
        b2.setTile(p1,6,5);
        b2.setTile(p2,4,3);
        b2.setTile(p3,2,1);
        assertTrue(b2.diagonalDownLeftMove(true,6,5,5,4));
        assertFalse(b2.diagonalDownLeftMove(true,6,5,4,4));
        assertFalse(b2.diagonalDownLeftMove(true,6,5,5,3));
        assertTrue(b2.diagonalDownLeftMove(true,5,4,4,3));
        assertFalse(b2.diagonalDownLeftMove(true,4,3,1,0));
        assertFalse(b2.diagonalDownLeftMove(true,2,1,0,-1));
        assertFalse(b2.diagonalDownLeftMove(true,1,2,-1,0));
        assertFalse(b2.diagonalDownLeftMove(true,1,1,-1,-1));
    }

    @Test
    void testStatusSetting(){
        b1.setOver();
        assertEquals(true,b1.getStatus());
    }
}