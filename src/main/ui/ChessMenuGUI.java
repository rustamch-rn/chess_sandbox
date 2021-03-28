package ui;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.actionhandlers.LoadGameAction;
import ui.actionhandlers.NewGameAction;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Graphical user interface of the game menu
 */
public class ChessMenuGUI extends JFrame {

    private static final int FRAME_WIDTH = 600; // Width of the frame
    private static final int FRAME_HEIGHT = 600; // Height of the frame
    private static final String JSON_STORE = "./data/gameNameStorage.json"; // Save file of game names.

    private JsonReader jsonReader; // JSON reader
    private List<String> gameNames; // Names of already existing games


    // EFFECTS: Constructs a new game menu
    public ChessMenuGUI() {
        initialiseFields();
        JPanel contentPanel = createContentPanel();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(contentPanel);
        this.pack();
        this.setVisible(true);
    }

    // Creates a panel with all the contents of the game menu
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 200, 100));
        JLabel label = new JLabel("<html><H1>Chess</H1></html>");
        contentPanel.setLayout(new GridLayout(0, 1, 0, 50));
        contentPanel.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        JButton newGame = new JButton("Create a new game");
        JButton loadGame = new JButton("Load game");
        newGame.addActionListener(new NewGameAction(this, gameNames));
        loadGame.addActionListener(new LoadGameAction(this, gameNames));
        contentPanel.add(newGame);
        contentPanel.add(loadGame);
        return contentPanel;
    }

    // EFFECTS : Initialises all the fields
    private void initialiseFields() {
        jsonReader = new JsonReader(JSON_STORE);
        gameNames = new ArrayList<>();
        try {
            loadGameNames();
        } catch (IOException e) {
            addGameNamesFile();
        }
    }

    // EFFECTS : Creates a file with the names of existing games
    private void addGameNamesFile() {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.writeNames(gameNames);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS : Loads the names of existing games
    private void loadGameNames() throws IOException {
        gameNames = jsonReader.readNames();
    }


}
