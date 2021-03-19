package model;

import model.game.Board;
import model.game.Player;
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
}
