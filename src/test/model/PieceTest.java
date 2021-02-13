package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    Board bd;
    Piece p;

    @BeforeEach
    public void setup() {
        bd = new Board();
        Piece[][] tiles = bd.getTiles();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                bd.removePiece(j,i);
            }
        }
        bd.printBoard();
    }

    @Test
    public void diagonalMovesTest() {
        p = new Queen(true,4,4,bd);
        bd.setTile(p,4,4);
        assertTrue(p.moveDiagonally(5,5));
        assertFalse(p.moveDiagonally(2,7));
        assertTrue(p.moveDiagonally(4,6));
        assertFalse(p.moveDiagonally(1,6));
        assertTrue(p.moveDiagonally(3,5));
        assertFalse(p.moveDiagonally(4,7));
        assertTrue(p.moveDiagonally(2,4));
        assertFalse(p.moveDiagonally(3,4));
        assertFalse(p.moveDiagonally(2,3));
        assertTrue(p.moveDiagonally(3,3));

    }

    @Test
    public void staightLineMovesTest() {
        p = new Queen(true,4,4,bd);
        bd.setTile(p,4,4);
        assertTrue(p.moveInStraightLine(7,4));
        assertFalse(p.moveInStraightLine(5,5));
        assertTrue(p.moveInStraightLine(7,7));
        assertFalse(p.moveInStraightLine(4,6));
        assertTrue(p.moveInStraightLine(0,7));
        assertFalse(p.moveInStraightLine(6,1));
    }
    @Test
    public void rookTest() {
        p = new Rook(true,0,0,bd);
        bd.setTile(p,0,0);
        assertEquals('R',p.getIdentifier());
        assertFalse(p.makeMove(7,7));
        assertTrue(p.makeMove(0,7));
        assertFalse(p.makeMove(7,0));
        assertTrue(p.makeMove(7,7));
        assertFalse(p.makeMove(0,7));
        assertTrue(p.makeMove(7,0));
    }

    @Test
    public void queenTest() {
        p = new Queen(true,0,0,bd);
        bd.setTile(p,0,0);
        assertEquals('Q',p.getIdentifier());
        assertTrue(p.makeMove(7,7));
        assertFalse(p.makeMove(5,4));
        assertTrue(p.makeMove(0,7));
        assertTrue(p.makeMove(0,6));
    }

    @Test
    public void bishopTest() {
        p = new Bishop(true,0,0,bd);
        bd.setTile(p,0,0);
        assertEquals('B',p.getIdentifier());
        assertTrue(p.makeMove(4,4));
        assertFalse(p.makeMove(0,7));
        assertFalse(p.makeMove(7,0));
        assertTrue(p.makeMove(7,7));
        assertFalse(p.makeMove(0,7));
        assertTrue(p.makeMove(6,6));
    }

    @Test
    public void kingTest() {
        p = new King(true,4,4,bd);
        bd.setTile(p,4,4);
        assertEquals('K',p.getIdentifier());
        assertFalse(p.makeMove(6,4));
        assertFalse(p.makeMove(4,6));
        assertFalse(p.makeMove(2,4));
        assertFalse(p.makeMove(4,2));
        assertTrue(p.makeMove(4,5));
        assertTrue(p.makeMove(5,6));
        assertTrue(p.makeMove(6,6));
        assertTrue(p.makeMove(7,5));
        assertTrue(p.makeMove(7,4));
        assertTrue(p.makeMove(6,3));
        assertTrue(p.makeMove(5,3));
        assertTrue(p.makeMove(5,2));
        assertTrue(p.makeMove(4,3));

    }

    @Test
    public void knightTest() {
        p = new Knight(true,4,4,bd);
        bd.setTile(p,4,4);
        assertEquals('N',p.getIdentifier());
        assertFalse(p.makeMove(7,5));
        assertFalse(p.makeMove(5,7));
        assertFalse(p.makeMove(6,6));
        assertFalse(p.makeMove(2,2));
        assertFalse(p.makeMove(6,2));
        assertFalse(p.makeMove(2,6));
        assertTrue(p.makeMove(6,5));
        assertTrue(p.makeMove(7,7));
    }

    @Test
    public void whitePawnTest() {
        p = new Pawn(true,3,2,bd);
        bd.setTile(p,3,2);
        assertEquals('P',p.getIdentifier());
        // first move
        assertFalse(p.makeMove(4,3));
        assertFalse(p.makeMove(3,1));
        assertFalse(p.makeMove(3,5));
        assertFalse(p.makeMove(4,0));
        assertTrue(p.makeMove(3,4));
        //second move
        assertFalse(p.makeMove(3,7));
        assertTrue(p.makeMove(3,5));
        //move obstruction
        Piece po = new Pawn(true,3,6,bd);
        bd.setTile(po,3,6);
        assertFalse(p.makeMove(3,6));
        //takes move
        Piece pt = new Pawn(false,4,6,bd);
        bd.setTile(pt,4,6);
        assertTrue(p.makeMove(4,6));
    }

    @Test
    public void blackPawnTest() {
        p = new Pawn(false,5,5,bd);
        bd.setTile(p,5,5);
        assertEquals('P',p.getIdentifier());
        // first move
        assertFalse(p.makeMove(6,6));
        assertFalse(p.makeMove(5,6));
        assertFalse(p.makeMove(5,2));
        assertFalse(p.makeMove(4,6));
        assertTrue(p.makeMove(5,3));
        //second move
        assertFalse(p.makeMove(5,1));
        assertTrue(p.makeMove(5,2));
        //move obstruction
        Piece po = new Pawn(false,5,1,bd);
        bd.setTile(po,5,1);
        assertFalse(p.makeMove(5,1));
        //takes move
        Piece pt = new Pawn(true,4,1,bd);
        bd.setTile(pt,4,1);
        assertTrue(p.makeMove(4,1));
    }

    @Test
    public void promotionTest() {
        Pawn p1 = new Pawn(true,0,6,bd);
        Pawn p2 = new Pawn(false,1,1,bd);
        Pawn p3 = new Pawn(true,3,6,bd);
        Pawn p4 = new Pawn(false,4,1,bd);
        bd.setTile(p1,0,6);
        bd.setTile(p2,1,1);
        bd.setTile(p3,3,6);
        bd.setTile(p4,4,1);
        //promotion white straight line move
        assertTrue(p1.makeMove(0,7));
        assertEquals('Q',bd.getTile(0,7).getIdentifier());
        assertEquals(true,bd.getTile(0,7).getPieceColor());
        //promotion black straight line move
        assertTrue(p2.makeMove(1,0));
        assertEquals('Q',bd.getTile(1,0).getIdentifier());
        assertEquals(false,bd.getTile(1,0).getPieceColor());
        //promotion white take move
        Pawn p1t = new Pawn(false,4,7,bd);
        bd.setTile(p1t,4,7);
        assertTrue(p3.makeMove(4,7));
        assertEquals('Q',bd.getTile(4,7).getIdentifier());
        assertEquals(true,bd.getTile(4,7).getPieceColor());
        //promotion black take move
        Pawn p2t = new Pawn(false,3,0,bd);
        bd.setTile(p2t,3,0);
        assertTrue(p4.makeMove(3,0));
        assertEquals('Q',bd.getTile(3,0).getIdentifier());
        assertEquals(false,bd.getTile(3,0).getPieceColor());

    }

}

