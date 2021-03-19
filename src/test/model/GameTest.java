package model;

import model.game.Board;
import model.game.Game;
import model.game.Player;
import model.pieces.Pawn;
import model.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    Board bd;
    Player pl1;
    Player pl2;
    model.game.Game g;

    @BeforeEach
    public void setup() {
        bd = new Board();
        pl1 = new Player(true,bd,"TestPlayer1");
        pl2 = new Player(false,bd,"TestPlayer2");
        g = new Game("TestGame",bd,pl1,pl2,true);
    }

    @Test
    public void testConstructorExistingGame() {
        assertEquals("TestGame",g.getName());
        assertEquals(pl1,g.getPlayer1());
        assertEquals(pl2,g.getPlayer2());
        assertEquals(bd,g.getBoard());
    }

    @Test
    public void testConstructorNewGame() {
        Game g = new Game("TestGameName","TestName1","TestName2");
        assertEquals("TestGameName",g.getName());
        assertEquals("TestName1", g.getPlayer1().getName());
        assertEquals("TestName2", g.getPlayer2().getName());
    }

    @Test
    public void testGetPiece() {
        assertTrue(g.getPiece(0) instanceof Rook);
        assertTrue(g.getPiece(63) instanceof Rook);
        assertTrue(g.getPiece(8) instanceof Pawn);
        assertTrue(g.getPiece (9) instanceof Pawn);
    }

    @Test
    public void testCheckPieceSelection() {
        assertFalse(g.checkPieceSelection(-1,0));
        assertFalse(g.checkPieceSelection(0,-1));
        assertFalse(g.checkPieceSelection(8,0));
        assertFalse(g.checkPieceSelection(0,8));
        g.setPlayerToMove(true);
        assertFalse(g.checkPieceSelection(7,7));
        assertTrue(g.checkPieceSelection(0,0));
        g.setPlayerToMove(false);
        assertTrue(g.checkPieceSelection(7,7));
        assertFalse(g.checkPieceSelection(0,0));
    }

    @Test
    public void testMakeMove() {
        assertFalse(g.makeMove(0,0,8,0));
        assertFalse(g.makeMove(0,0,0,8));
        assertFalse(g.makeMove(0,0,-1,0));
        assertFalse(g.makeMove(0,0,0,-1));
        assertFalse(g.makeMove(0,1,0,1));
        assertTrue(g.makeMove(0,1,0,3));
        assertFalse(g.makeMove(1,1,2,2));
        assertFalse(g.getPlayerToMove());
        assertTrue(g.makeMove(0,6,0,5));
        assertTrue(g.getPlayerToMove());
        assertFalse(g.makeMove(0,0,3,3));
        assertTrue(g.getPlayerToMove());
    }

    @Test
    public void testGetters() {
        assertFalse(g.getBoardStatus());
        g.setPlayerToMove(true);
        g.setStatus(false);
        assertTrue(g.getPlayerToMove());
        assertEquals("Checkmate! Good job " + g.getPlayer2().getPlayerName(),g.producePlayerWonMessage());
        g.setPlayerToMove(false);
        assertEquals("Checkmate! Good job " + g.getPlayer1().getPlayerName(),g.producePlayerWonMessage());
    }
}

