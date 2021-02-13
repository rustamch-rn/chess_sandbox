package ui;

import model.Board;
import model.Player;

import java.util.PrimitiveIterator;
import java.util.Scanner;

public class Game {

    private Scanner input;
    private Board bd;
    private Player pl1;
    private Player pl2;

    // EFFECTS: Initialises the scanner and starts the game
    public Game() {
        input = new Scanner(System.in);
        initializeGame();
    }

    // EFFECTS : Initialises a new chess game
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

    // REQUIRES: User input should be between 0-63
    // EFFECTS: Game, it is played until one player loses.
    public void playTheGame() {
        bd.printBoard();
        while (true) {
            System.out.println("If you want to close this game enter q, press enter otherwise");
            String command = input.nextLine();
            if (command.equals("q")) {
                break;
            }
            while (playerMove(pl1) == false) {
                System.out.println("Okay choose another piece then");
            }
            if (bd.getStatus() == true) {
                System.out.println("Checkmate! Good job " + pl1.getName() + "!");
                break;
            }
            while (playerMove(pl2) == false) {
                System.out.println("Okay choose another piece then.");
            }
            if (bd.getStatus() == true) {
                System.out.println("Checkmate! Good job " + pl2.getName() + "!");
                break;
            }

        }
    }

    // EFFECTS: Implementation of player move
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
        System.out.println("If you want to choose another piece enter q, otherwise press c to continue");
        command = input.nextLine();
        if (command.equals("q")) {
            return false;
        }
        System.out.println("Enter where you want to move your piece");
        command = input.nextLine();
        String destSquare = command;
        while (!pl.makeMove(origSquare,destSquare)) {
            System.out.println("Please check your input,\n Enter a new tile position");
            command = input.nextLine();
            destSquare = command;
        }
        return true;
    }

}
