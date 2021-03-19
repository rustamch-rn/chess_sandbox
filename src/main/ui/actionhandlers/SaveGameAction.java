package ui.actionhandlers;

import model.game.Game;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * Action listener for save game button
 */
public class SaveGameAction implements ActionListener {

    private static final String JSON_STORE = "./data/gameNameStorage.json"; // Save file of game names.
    private static final String JSON_DIRECTORY = "./data/"; // General directory of all save files

    private final Game game; // The game that needs to be saved
    private final List<String> gameNames; // Names of already saved games
    private JsonWriter jsonWriter; // JSON writer


    // EFFECTS : Constructs a new save game action listener
    public SaveGameAction(Game g, List<String> gameNames) {
        this.game = g;
        this.gameNames = gameNames;
    }

    // EFFECTS : On activation saves the game
    @Override
    public void actionPerformed(ActionEvent e) {
        saveGame(game);
    }

    // EFFECTS: Saves the game provided and add its name to the list of already existing games
    private void saveGame(Game g) {
        String fileName = game.getName();
        saveGameName();
        jsonWriter = new JsonWriter(JSON_DIRECTORY + fileName + ".json");
        try {
            jsonWriter.open();
            jsonWriter.write(g);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS : Saves names of the current games
    private void saveGameName() {
        jsonWriter = new JsonWriter(JSON_STORE);
        String currGameName = game.getName();
        if (!gameNames.contains(currGameName)) {
            gameNames.add(currGameName);
        }
        try {
            jsonWriter.open();
            jsonWriter.writeNames(gameNames);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
