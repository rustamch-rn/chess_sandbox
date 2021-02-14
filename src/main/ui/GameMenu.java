package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Represents a menu of the chess game, user can either create a new game to play or load already existing one
public class GameMenu {

    private final Scanner input; // Scanner that parses input of the user
    private final List<Game> games; // List of the games that user already created
    private final List<String> gameNames; // Names of the games that user has created

    public GameMenu() {
        input = new Scanner(System.in);
        games = new ArrayList<>();
        gameNames = new ArrayList<>();
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
            checkInstructions(command);
        }
    }

    // EFFECTS: Checks the user's input, and after that, either creates a new game or opens already existing one
    private void checkInstructions(String command) {
        if (command.equals("e")) {
            if (games.size() == 0) {
                System.out.println("Hmm... It looks like you haven't created any games yet");
                createNewGame();
            } else {
                System.out.println("Here is a list of games that you already created:");
                printGameNames();
                System.out.println("Enter name of game that you wanna play:");
                command = input.nextLine();
                if (gameNames.contains(command)) {
                    Game g = findGame(command);
                    assert g != null;
                    g.playTheGame();
                }
                System.out.println("Game with given name doesn't exist, try again!");
            }
        } else if (command.equals("n")) {
            createNewGame();
        } else {
            System.out.println("Not a valid input, try again!");
        }
    }

    // EFFECTS: Prints instructions for the user
    private void printInstructions() {
        System.out.println("Welcome to Chess!!!");
        System.out.println("    If you want to play already existing game enter - e");
        System.out.println("    If you want to create a new game enter - n");
        System.out.println("    If you want to quit enter -  q");
    }


    //REQUIRES: The game with given name must exist
    //EFFECTS: Returns a game with given name
    private Game findGame(String command) {
        int count = 0;
        while (gameNames.size() > count) {
            if (command.equals(gameNames.get(count))) {
                return games.get(count);
            }
            count++;
        }
        return null;
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
        Game g = new Game();
        games.add(g);
        gameNames.add(command);
    }

    //EFFECTS: Prints names of all the current games
    private void printGameNames() {
        for (String str : gameNames) {
            System.out.print(str + ", ");
        }
        System.out.println();
    }
}
