package model;

import model.game.Board;
import model.pieces.*;
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
        b2.getTiles();
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
        assertFalse(b1.checkTile(true,2,6));
        assertFalse(b1.checkTile(true,2,5));
        assertTrue(b1.checkTile(false,2,6));
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
        assertTrue(b1.checkMoveRules(true, 0,1,0,3));
        assertFalse(b1.checkMoveRules(true, 0,0,1,0));
        Pawn pb = new Pawn(true,0,4);
        b1.setTile(pb,0,5);
        assertFalse(b1.checkMoveRules(true, 0,3,0,5));
        Pawn pt = new Pawn(false,1,4);
        b1.setTile(pt,1,4);
        assertTrue(b1.checkMoveRules(true, 0,3,1,4));

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
    public void placePieceOnNewSquareTest() {
        Piece p1 = new Pawn(true,2,1,b2);
        Piece p2 = new Pawn(false,3,6,b2);
        b2.setTile(p1,2,1);
        b2.setTile(p2,3,6);
        b2.placePieceOnNewSquare(2,1,2,3);
        assertEquals(b2.getTile(2,3),p1);
        b2.placePieceOnNewSquare(2,3,2,4);
        assertEquals(b2.getTile(2,4),p1);
        b2.placePieceOnNewSquare(2,4,2,5);
        assertEquals(b2.getTile(2,5),p1);
        b2.placePieceOnNewSquare(2,5,3,6);
        assertEquals(b2.getTile(3,6),p1);
    }

    @Test
    void verticalForwardMove() {
        Piece p1 = new Queen(true,1,2,b2);
        Piece p2 = new Pawn(false,1,5,b2);
        Piece p3 = new Pawn(true,1,6,b2);
        b2.setTile(p1,1,2);
        b2.setTile(p2,1,5);
        b2.setTile(p3,1,6);
        assertTrue(b2.verticalForwardMove(1,2,1,4));
        assertTrue(b2.verticalForwardMove(1,4,1,5));
        assertFalse(b2.verticalForwardMove(1,5,1,7));
        assertTrue(b2.verticalForwardMove(1,6,1,7));
        assertFalse(b2.verticalForwardMove(1,7,1,8));
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
        assertTrue(b2.horizontalRightwardMove(2,1,4,1));
        assertTrue(b2.horizontalRightwardMove(4,1,5,1));
        assertFalse(b2.horizontalRightwardMove(5,1,7,1));
        assertFalse(b2.horizontalRightwardMove(6,1,8,1));
    }

    @Test
    void horizontalLeftwardMove() {
        Piece p1 = new Queen(true,7,1,b2);
        Piece p2 = new Pawn(false,5,1,b2);
        Piece p3 = new Pawn(true,2,1,b2);
        b2.setTile(p1,7,1);
        b2.setTile(p2,5,1);
        b2.setTile(p3,2,1);
        assertTrue(b2.horizontalLeftwardMove(7,1,6,1));
        assertTrue(b2.horizontalLeftwardMove(6,1,5,1));
        assertFalse(b2.horizontalLeftwardMove(5,1,1,1));
        assertFalse(b2.horizontalLeftwardMove(2,1,-1,1));
    }

    @Test
    void diagonalUpRightMove() {
        Piece p1 = new Queen(true,2,1,b2);
        Piece p2 = new Pawn(false,4,3,b2);
        Piece p3 = new Pawn(true,6,5,b2);
        b2.setTile(p1,2,1);
        b2.setTile(p2,4,3);
        b2.setTile(p3,6,5);
        assertTrue(b2.diagonalUpRightMove(2,1,3,2));
        assertTrue(b2.diagonalUpRightMove(3,2,4,3));
        assertFalse(b2.diagonalUpRightMove(4,3,7,6));
        assertFalse(b2.diagonalUpRightMove(6,5,7,8));
        assertFalse(b2.diagonalUpRightMove(6,5,8,6));
        assertFalse(b2.diagonalUpRightMove(6,5,8,7));
        assertFalse(b2.diagonalUpRightMove(6,5,6,8));
        assertFalse(b2.diagonalUpRightMove(5,6,7,8));
        assertFalse(b2.diagonalUpRightMove(6,5,8,7));
        assertFalse(b2.diagonalUpRightMove(7,7,8,8));
    }

    @Test
    void diagonalUpLeftMove() {
        Piece p1 = new Queen(true,6,1,b2);
        Piece p2 = new Pawn(false,4,3,b2);
        Piece p3 = new Pawn(true,2,5,b2);
        b2.setTile(p1,6,1);
        b2.setTile(p2,4,3);
        b2.setTile(p3,2,5);
        assertTrue(b2.diagonalUpLeftMove(6,1,5,2));
        assertTrue(b2.diagonalUpLeftMove(5,2,4,3));
        assertFalse(b2.diagonalUpLeftMove(4,3,1,6));
        assertFalse(b2.diagonalUpLeftMove(2,5,7,8));
        assertFalse(b2.diagonalUpLeftMove(6,5,8,6));
        assertFalse(b2.diagonalUpLeftMove(6,5,8,7));
        assertFalse(b2.diagonalUpLeftMove(6,5,6,8));
        assertFalse(b2.diagonalUpLeftMove(1,7,0,8));
        assertFalse(b2.diagonalUpLeftMove(0,6,-1,7));
        assertFalse(b2.diagonalUpLeftMove(0,7,0,8));
    }

    @Test
    void diagonalDownRightMove() {
        Piece p1 = new Queen(true,0,7,b2);
        Piece p2 = new Pawn(false,2,5,b2);
        Piece p3 = new Pawn(true,4,3,b2);
        b2.setTile(p1,0,7);
        b2.setTile(p2,2,5);
        b2.setTile(p3,4,3);
        assertTrue(b2.diagonalDownRightMove(0,7,1,6));
        assertFalse(b2.diagonalDownRightMove(1,6,2,4));
        assertFalse(b2.diagonalDownRightMove(1,6,3,5));
        assertTrue(b2.diagonalDownRightMove(1,6,2,5));
        assertFalse(b2.diagonalDownRightMove(2,5,2,2));
        assertFalse(b2.diagonalDownRightMove(3,3,-1,-1));
        assertFalse(b2.diagonalDownRightMove(4,3,0,-1));
        assertFalse(b2.diagonalDownRightMove(3,4,-1,0));
        assertFalse(b2.diagonalDownRightMove(7,7,8,8));
    }

    @Test
    void diagonalDownLeftMove() {
        Piece p1 = new Queen(true,6,5,b2);
        Piece p2 = new Pawn(false,4,3,b2);
        Piece p3 = new Pawn(true,2,1,b2);
        b2.setTile(p1,6,5);
        b2.setTile(p2,4,3);
        b2.setTile(p3,2,1);
        assertTrue(b2.diagonalDownLeftMove(6,5,5,4));
        assertFalse(b2.diagonalDownLeftMove(6,5,4,4));
        assertFalse(b2.diagonalDownLeftMove(6,5,5,3));
        assertTrue(b2.diagonalDownLeftMove(5,4,4,3));
        assertFalse(b2.diagonalDownLeftMove(4,3,1,0));
        assertFalse(b2.diagonalDownLeftMove(2,1,0,-1));
        assertFalse(b2.diagonalDownLeftMove(1,2,-1,0));
        assertFalse(b2.diagonalDownLeftMove(1,1,-1,-1));
    }

    @Test
    void testStatusSetting(){
        b1.setStatus(true);
        assertTrue(b1.getStatus());
    }

    @Test
    void testTakePiece() {
        Piece p1 = new Queen(true,0,7,b2);
        Piece p2 = new King(false,0,0,b2);
        Piece p3 = new Queen(false,7,0,b2);
        b2.setTile(p1,0,7);
        b2.setTile(p2,0,0);
        b2.setTile(p3,7,0);
        b2.takePiece(0,7);
        assertNull(b2.getTile(0, 7));
        assertFalse(b2.getStatus());
        b2.takePiece(0,0);
        assertNull(b2.getTile(0, 0));
        assertTrue(b2.getStatus());
        b2.takePiece(7,0);
        assertNull(b2.getTile(7, 0));
        assertTrue(b2.getStatus());
    }

    @Test
    void  checkUnderAttackTest () {
        Piece p = new King(true, 4, 0, b1);
        Piece wr = new Rook(true, 0, 0, b1);
        Piece bk = new King(false, 1, 1, b1);
        b1.setTile(p, 4, 0);
        b1.setTile(wr, 0, 0);
        b1.setTile(bk, 1, 1);
        assertFalse(b1.checkUnderAttack(true,0,0));
        bk.setFirstMove(false);
        assertTrue(b1.checkUnderAttack(true,0,0));
    }
}