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
}