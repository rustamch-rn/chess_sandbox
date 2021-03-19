package model;

import model.game.Board;
import model.pieces.*;
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
                bd.removePiece(j, i);
            }
        }
    }

    public void clearBoard(Board bd) {
        Piece[][] tiles = bd.getTiles();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                bd.removePiece(j, i);
            }
        }
    }

    @Test
    public void rookTest() {
        p = new Rook(true, 0, 0, bd);
        bd.setTile(p, 0, 0);
        assertEquals('R', p.getIdentifier());
        assertFalse(p.makeMove(7, 7));
        assertTrue(p.makeMove(0, 7));
        assertTrue(p.makeMove(7, 0));
        assertFalse(p.makeMove(7, 7));
        assertTrue(p.makeMove(0, 7));
    }

    @Test
    public void queenTest() {
        p = new Queen(true, 0, 0, bd);
        bd.setTile(p, 0, 0);
        assertEquals('Q', p.getIdentifier());
        assertTrue(p.makeMove(0, 7));
        assertTrue(p.makeMove(7, 0));
        assertTrue(p.makeMove(7, 7));
    }

    @Test
    public void bishopTest() {
        p = new Bishop(true, 4, 4, bd);
        bd.setTile(p, 4, 4);
        assertEquals('B', p.getIdentifier());
        assertFalse(p.makeMove(6, 7));
        assertFalse(p.makeMove(7, 6));
        assertTrue(p.makeMove(5, 5));
        assertTrue(p.makeMove(3, 3));
        assertTrue(p.makeMove(5, 3));
        assertTrue(p.makeMove(3, 5));
    }

    @Test
    public void kingTest() {
        p = new King(true, 4, 4, bd);
        bd.setTile(p, 4, 4);
        assertEquals('K', p.getIdentifier());
        p.setFirstMove(false);
        assertFalse(p.makeMove(6, 4));
        assertFalse(p.makeMove(4, 6));
        assertTrue(p.makeMove(5, 4));
        assertTrue(p.makeMove(4, 3));
        assertFalse(p.makeMove(4, 4));
        assertFalse(p.makeMove(6, 6));
        assertTrue(p.makeMove(3, 3));
        assertTrue(p.makeMove(5, 3));
        assertTrue(p.makeMove(3, 5));
    }

    @Test
    public void shortCastleTestSucc() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertTrue(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF1() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertTrue(p.makeMove(5, 0));
    }

    @Test
    public void shortCastleTestF2() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertFalse(p.makeMove(6, 1));
    }

    @Test
    public void shortCastleTestF3() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        Piece b = new Bishop(true, 5, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        bd.setTile(b, 5, 0);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF4() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        Piece n = new Knight(true, 6, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        bd.setTile(n, 6, 0);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF5() {
        p = new King(true, 4, 0, bd);
        bd.setTile(p, 4, 0);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF6() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertTrue(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF7() {
        p = new King(true, 4, 0, bd);
        Piece r = new Pawn(true, 7, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF8() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(false, 7, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF9() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 7, 0, bd);
        r.setFirstMove(false);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 7, 0);
        assertFalse(p.makeMove(6, 0));
    }


    @Test
    public void shortCastleTestF10() {
        p = new King(true, 4, 0, bd);
        Piece rw = new Rook(true, 7, 0, bd);
        Piece rb = new Rook(false, 6, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(rw, 7, 0);
        bd.setTile(rb, 6, 7);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF11() {
        p = new King(true, 4, 0, bd);
        Piece rw = new Rook(true, 7, 0, bd);
        Piece rb = new Rook(false, 5, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(rw, 7, 0);
        bd.setTile(rb, 5, 7);
        assertFalse(p.makeMove(6, 0));
    }

    @Test
    public void shortCastleTestF12() {
        p = new King(true, 4, 0, bd);
        Piece rw = new Rook(true, 7, 0, bd);
        Piece rb1 = new Rook(false, 5, 7, bd);
        Piece rb2 = new Rook(false, 4, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(rw, 7, 0);
        bd.setTile(rb1, 5, 7);
        bd.setTile(rb2, 4, 7);
        assertFalse(p.makeMove(6, 0));
    }


    @Test
    public void longCastleTestSucc() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        assertTrue(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF1() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(1, 0));
    }

    @Test
    public void longCastleTestF2() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(1, 1));
    }

    @Test
    public void longCastleTestF3() {
        p = new King(true, 4, 0, bd);
        bd.setTile(p, 4, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF4() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(false, 0, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF5() {
        p = new King(true, 4, 0, bd);
        Piece r = new Pawn(true, 0, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF6() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        r.setFirstMove(false);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF7() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        Piece q = new Knight(true, 3, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(q, 3, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF8() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        Piece b = new Knight(true, 2, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(b, 2, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF9() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        Piece n = new Knight(true, 1, 0, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(n, 1, 0);
        bd.setTile(r, 0, 0);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF10() {
        p = new King(true, 4, 0, bd);
        Piece wr = new Rook(true, 0, 0, bd);
        Piece br = new Rook(false, 4, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(wr, 1, 0);
        bd.setTile(br, 4, 7);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF11() {
        p = new King(true, 4, 0, bd);
        Piece wr = new Rook(true, 0, 0, bd);
        Piece br = new Rook(false, 3, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(wr, 0, 0);
        bd.setTile(br, 3, 7);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF12() {
        p = new King(true, 4, 0, bd);
        Piece wr = new Rook(true, 0, 0, bd);
        Piece br = new Rook(false, 2, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(wr, 0, 0);
        bd.setTile(br, 2, 7);
        assertFalse(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF13() {
        p = new King(true, 4, 0, bd);
        Piece wr = new Rook(true, 0, 0, bd);
        Piece br = new Rook(false, 1, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(wr, 0, 0);
        bd.setTile(br, 1, 7);
        assertTrue(p.makeMove(2, 0));
    }

    @Test
    public void longCastleTestF14() {
        p = new King(true, 4, 0, bd);
        Piece r = new Rook(true, 0, 0, bd);
        Piece rb1 = new Rook(false, 3, 7, bd);
        Piece rb2 = new Rook(false, 2, 7, bd);
        Piece rb3 = new Rook(false, 4, 7, bd);
        bd.setTile(p, 4, 0);
        bd.setTile(r, 0, 0);
        bd.setTile(rb1, 3, 7);
        bd.setTile(rb2, 2, 7);
        bd.setTile(rb3, 4, 7);
        assertFalse(p.makeMove(2, 0));
    }


    @Test
    public void knightTest() {
        p = new Knight(true, 4, 4, bd);
        bd.setTile(p, 4, 4);
        assertEquals('N', p.getIdentifier());
        assertFalse(p.makeMove(7, 5));
        assertFalse(p.makeMove(5, 7));
        assertFalse(p.makeMove(6, 6));
        assertFalse(p.makeMove(2, 2));
        assertFalse(p.makeMove(6, 2));
        assertFalse(p.makeMove(2, 6));
        assertTrue(p.makeMove(6, 5));
        assertTrue(p.makeMove(7, 7));
    }

    @Test
    public void whitePawnTest() {
        p = new Pawn(true, 3, 2, bd);
        bd.setTile(p, 3, 2);
        assertEquals('P', p.getIdentifier());
        // first move
        assertFalse(p.makeMove(3, 5));
        assertFalse(p.makeMove(3, 1));
        assertFalse(p.makeMove(4, 5));
        assertTrue(p.makeMove(3, 4));
        assertTrue(p.makeMove(3, 3));
        // regular move
        p.setFirstMove(false);
        assertFalse(p.makeMove(3, 4));
        assertFalse(p.makeMove(4, 4));
        assertTrue(p.makeMove(3, 3));

    }

    @Test
    public void blackPawnTest() {
        p = new Pawn(false, 5, 5, bd);
        bd.setTile(p, 5, 5);
        assertEquals('P', p.getIdentifier());
        // first move
        assertFalse(p.makeMove(5, 2));
        assertFalse(p.makeMove(5, 6));
        assertFalse(p.makeMove(4, 4));
        assertTrue(p.makeMove(5, 4));
        assertTrue(p.makeMove(5, 3));
        // regular move
        p.setFirstMove(false);
        assertFalse(p.makeMove(5, 3));
        assertFalse(p.makeMove(6, 6));
        assertTrue(p.makeMove(5, 4));
        // pawn takes
        Pawn pt = new Pawn(true,4,4);
        bd.setTile(pt,4,4);
        assertTrue(p.makeMove(4,4));
        // piece in the way
        Pawn pw = new Pawn(true,5,4);
        bd.setTile(pw,5,4);
        assertFalse(p.makeMove(5,4));
        

    }

    @Test
    public void promotionTest() {
        Pawn p1 = new Pawn(true, 0, 6, bd);
        Pawn p2 = new Pawn(false, 1, 1, bd);
        Pawn p3 = new Pawn(true, 3, 6, bd);
        Pawn p4 = new Pawn(false, 4, 1, bd);
        bd.setTile(p1, 0, 6);
        bd.setTile(p2, 1, 1);
        bd.setTile(p3, 3, 6);
        bd.setTile(p4, 4, 1);
        //promotion white straight line move
        assertTrue(p1.makeMove(0, 7));
        assertEquals('Q', bd.getTile(0, 7).getIdentifier());
        assertEquals(true, bd.getTile(0, 7).getPieceColor());
        //promotion black straight line move
        assertTrue(p2.makeMove(1, 0));
        assertEquals('Q', bd.getTile(1, 0).getIdentifier());
        assertEquals(false, bd.getTile(1, 0).getPieceColor());
        //promotion white take move
        Pawn p1t = new Pawn(false, 4, 6, bd);
        bd.setTile(p1t, 4, 7);
        assertTrue(p3.makeMove(4, 7));
        assertEquals('Q', bd.getTile(4, 7).getIdentifier());
        assertEquals(true, bd.getTile(4, 7).getPieceColor());
        //promotion black take move
        Pawn p2t = new Pawn(false, 3, 0, bd);
        bd.setTile(p2t, 3, 0);
        assertTrue(p4.makeMove(3, 0));
        assertEquals('Q', bd.getTile(3, 0).getIdentifier());
        assertEquals(false, bd.getTile(3, 0).getPieceColor());

    }

}

