package ui;

import model.game.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


// Represents a menu of the chess game, user can either create a new game to play or load already existing one
public class GameMenu {

    private static final String JSON_STORE = "./data/gameNameStorage.json"; // Save file of game names.
    private static final String JSON_DIRECTORY = "./data/"; // General directory of all save files
    private final Scanner input; // Scanner that parses input of the user

    private List<String> gameNames; // Names of the all current games
    private JsonWriter jsonWriter; // JSON writer
    private JsonReader jsonReader; // JSON Reader

    // EFFECTS: Constructs a new game
    public GameMenu() {
        gameNames = new ArrayList<>();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        try {
            loadGameNames();
        } catch (IOException e) {
            saveGameName();
        }
        instantiateMenu();
    }

    // EFFECTS: The options for user to choose from are displayed, if player chooses to quit the game is halted
    private void instantiateMenu() {
        while (true) {
            printInstructions();
            String command = input.nextLine();
            if (command.equals("q")) {
                break;
            }
            try {
                checkInstructions(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // EFFECTS: Checks the user's input, and after that, either creates a new game or opens already existing one
    private void checkInstructions(String command) throws IOException {
        if (command.equals("e")) {
            System.out.println("Here is a list of games that you already created:");
            printNames();
            System.out.println("Enter name of game that you wanna play:");
            command = input.nextLine();
            while (!gameNames.contains(command)) {
                System.out.println("You have not created game with this name yet.");
                System.out.println("Enter a name of already existing game:");
                command = input.nextLine();
            }
            Game g = loadGame(command);
            GameRunner gr = new GameRunner(g);
            g = gr.getGame();
            saveGame(g);
        } else if (command.equals("n")) {
            createNewGame();
        } else {
            System.out.println("Not a valid input, try again!");
        }
    }

    // EFFECTS: Prints names of the current games
    private void printNames() {
        for (String str : gameNames) {
            System.out.print(str + ", ");
        }
        System.out.println();
    }

    // EFFECTS: Saves the game provided
    private void saveGame(Game g) {
        System.out.println("Do you want to save this game?");
        System.out.println("\tyes -- enter y");
        System.out.println("\tno  -- enter n");
        String command = input.nextLine();
        if (command.toLowerCase(Locale.ROOT).equals("y")) {
            String fileName = g.getName();
            if (!gameNames.contains(fileName)) {
                gameNames.add(fileName);
            }
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
    }

    // EFFECTS : Saves names of the current games
    private void saveGameName() {
        try {
            jsonWriter.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        jsonWriter.writeNames(gameNames);
        jsonWriter.close();
    }


    // EFFECTS : Loads the names of existing games
    private void loadGameNames() throws IOException {
        gameNames = jsonReader.readNames();
    }

    // EFFECTS: Prints instructions for the user
    private void printInstructions() {
        System.out.println("Welcome to Chess!!!");
        System.out.println("\tIf you want to play already existing game enter   -> e");
        System.out.println("\tIf you want to create a new game enter            -> n");
        System.out.println("\tIf you want to quit enter                         -> q");
    }


    //MODIFIES: This
    //EFFECTS: Creates a new game with name specified by the user
    private void createNewGame() {
        System.out.println("Enter how do you want to name your game:");
        String command = input.nextLine();
        while (gameNames.contains(command)) {
            System.out.println("Game with given name already exists! Enter another name for your game:");
            command = input.nextLine();
        }
        GameRunner gr = new GameRunner(command);
        Game g = gr.getGame();
        saveGame(g);
    }

    // EFFECTS: Loads a game with a given name
    private Game loadGame(String command) throws IOException {
        String dir = JSON_DIRECTORY + command + ".json";
        Game g;
        jsonReader = new JsonReader(dir);
        g = jsonReader.read();
        return g;
    }


}
