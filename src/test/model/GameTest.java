package model;

import model.game.Board;
import model.game.Game;
import model.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        g = new model.game.Game("TestGame",bd,pl1,pl2);
    }

    @Test
    public void testConstructor() {
        assertEquals("TestGame",g.getName());
        assertEquals(pl1,g.getPlayer1());
        assertEquals(pl2,g.getPlayer2());
        assertEquals(bd,g.getBoard());
    }
}
