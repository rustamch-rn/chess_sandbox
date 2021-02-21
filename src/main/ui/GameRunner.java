package ui;

import model.game.Board;
import model.game.Game;
import model.game.Player;

import java.util.Scanner;

// Represents a chess game, which has 2 players and a board on which they are playing
public class GameRunner {

    private Scanner input; // User input
    private Game game; // Game this GameRunner runs


    public GameRunner(String gameName) {
        input = new Scanner(System.in);
        initializeGame(gameName);
    }

    public GameRunner(Game game) {
        input = new Scanner(System.in);
        this.game = game;
        launchTheGame();
    }

    // MODIFIES: This
    // EFFECTS: Initialises a new chess game
    private void initializeGame(String gameName) {
        String command;
        Board bd = new Board();
        System.out.println("Player1 enter your name:");
        command = input.nextLine();
        Player pl1 = new Player(true, bd, command);
        System.out.println("Player2 enter your name:");
        command = input.nextLine();
        Player pl2 = new Player(false, bd, command);
        System.out.println("Welcome to Chess!!!");
        Game g = new Game(gameName,bd,pl1,pl2);
        this.game = g;
        launchTheGame();
    }

    // REQUIRES: User input should be a square within the board.
    // EFFECTS: The moves are casted on the board, until one player loses.
    public void launchTheGame() {
        Board bd = game.getBoard();
        Player pl1 = game.getPlayer1();
        Player pl2 = game.getPlayer2();
        bd.printBoard();
        playTheGame(bd,pl1,pl2);
    }

    public void playTheGame(Board bd, Player pl1, Player pl2) {
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

    public Game getGame() {
        return game;
    }
}
