package ui;

import model.Board;
import model.Player;

import java.util.Scanner;

// Represents a chess game, which has 2 players and a board on which they are playing
public class Game {

    private Scanner input; // User input
    private Board bd; // Board on which this game is played
    private Player pl1; // Player that controls white pieces
    private Player pl2; // Player that controls black pieces

    // EFFECTS: Initialises the scanner and starts the game
    public Game() {
        input = new Scanner(System.in);
        initializeGame();
    }

    // MODIFIES: This
    // EFFECTS: Initialises a new chess game
    private void initializeGame() {
        String command;
        bd = new Board();
        System.out.println("Player1 enter your name:");
        command = input.nextLine();
        pl1 = new Player(true, bd, command);
        System.out.println("Player2 enter your name:");
        command = input.nextLine();
        pl2 = new Player(false, bd, command);
        System.out.println("Welcome to Chess!!!");
        playTheGame();
    }

    // REQUIRES: User input should be a square within the board.
    // EFFECTS: The moves are casted on the board, until one player loses.
    public void playTheGame() {
        bd.printBoard();
        while (true) {
            System.out.println("If you want to close this game enter q, press enter otherwise");
            String command = input.nextLine();
            if (command.equals("q")) {
                break;
            }
            while (playerMove(pl1)) {
                System.out.println("Okay choose another piece then");
            }
            if (bd.getStatus()) {
                System.out.println("Checkmate! Good job " + pl1.getName() + "!");
                break;
            }
            while (playerMove(pl2)) {
                System.out.println("Okay choose another piece then.");
            }
            if (bd.getStatus()) {
                System.out.println("Checkmate! Good job " + pl2.getName() + "!");
                break;
            }

        }
    }

    // MODIFIES: Board bd
    // EFFECTS: Player casts move on the board
    public boolean playerMove(Player pl) {
        System.out.println(pl.getName() + " enter a square on which your piece resides");
        String command;
        command = input.nextLine();
        String origSquare = command;
        while (!pl.checkPieceSelection(origSquare)) {
            System.out.println("Please check your input,\n Enter a new tile position");
            command = input.nextLine();
            origSquare = command;
        }
        System.out.println("Enter where you want to move your piece");
        command = input.nextLine();
        String destSquare = command;
        while (!pl.makeMove(origSquare,destSquare)) {
            System.out.println("Please check your input,\n Enter a new tile position");
            System.out.println("If you want to choose another piece enter q");
            command = input.nextLine();
            if (command.equals("q")) {
                return true;
            }
            destSquare = command;
        }
        return false;
    }

}
