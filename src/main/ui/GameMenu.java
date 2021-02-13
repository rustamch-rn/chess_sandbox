package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMenu {

    private Scanner input;
    private List<Game> games;
    private List<String> gameNames;

    public GameMenu() {
        input = new Scanner(System.in);
        games = new ArrayList<>();
        gameNames = new ArrayList<>();
        instantiateMenu();
    }

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

    private void checkInstructions(String command) {
        if (command.equals("e")) {
            if (games.size() == 0) {
                System.out.println("Hmm... It looks like you haven't created any games yet");
                createNewGame();
            }
            System.out.println("Enter name of your game");
            command = input.nextLine();
            if (gameNames.contains(command)) {
                Game g = findGame(command);
                g.playTheGame();
            }
            System.out.println("Game with given name doesn't exist, try again!");
        } else if (command.equals("n")) {
            createNewGame();
        } else {
            System.out.println("Not a valid input, try again!");
        }
    }

    private void printInstructions() {
        System.out.println("Welcome to Chess!!!");
        System.out.println("    If you want to play already existing game enter e");
        System.out.println("    If you want to create a new game enter n");
        System.out.println("    If you want to quit enter q");
    }


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

    private void createNewGame() {
        System.out.println("Enter how do you want to name your game");
        String command = input.nextLine();
        while (gameNames.contains(command)) {
            System.out.println("Game with given name already exists! Enter another name for your game:");
            command = input.nextLine();
        }
        Game g = new Game();
        games.add(g);
        gameNames.add(command);
    }
}
