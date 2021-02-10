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

    public void PieceTest() {
        p = new Queen(true,4,4,bd);
        bd.setTile(p,4,4);
        assertTrue(p.moveDiagonally(5,5));
        assertFalse(p.moveDiagonally(2,7));
        assertTrue(p.moveDiagonally(4,6));
        assertFalse(p.moveDiagonally(1,6));
        assertTrue(p.moveDiagonally(3,5));
        assertFalse(p.moveDiagonally(4,7));
    }
    @Test
    public void RookTest() {
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
    public void QueenTest() {
        p = new Queen(true,0,0,bd);
        bd.setTile(p,0,0);
        assertEquals('Q',p.getIdentifier());
        assertTrue(p.makeMove(7,7));
        assertTrue(p.makeMove(0,7));
        assertTrue(p.makeMove(7,0));
    }
}
