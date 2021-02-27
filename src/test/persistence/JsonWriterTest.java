package persistence;

import model.game.Board;
import model.game.Game;
import model.game.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterNewGame() {
        try {
            Board bd = new Board();
            Player testPl1 = new Player(true,bd,"TestPlayer1");
            Player testPl2 = new Player(false,bd,"TestPlayer2");
            Game game = new Game("GameTest",bd,testPl1,testPl2);
            JsonWriter writer = new JsonWriter("./data/testGame.json");
            writer.open();
            writer.write(game);
            writer.close();
            JsonReader reader = new JsonReader("./data/testGame.json");
            game = reader.read();
            assertEquals("GameTest", game.getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGameNames() {
        try {
            List<String> gameNames = new ArrayList<>();
            gameNames.add("Test1");
            gameNames.add("Test2");
            gameNames.add("Test3");
            JsonWriter writer = new JsonWriter("./data/testGameNames.json");
            writer.open();
            writer.writeNames(gameNames);
            writer.close();
            JsonReader reader = new JsonReader("./data/testGameNames.json");
            List<String> gameNamesReader = reader.readNames();
            for (int i = 0; i < gameNames.size(); i++){
                String currElt = gameNames.get(i);
                String currEltRead = gameNamesReader.get(i);
                assertTrue(currElt.equals(currEltRead));
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
