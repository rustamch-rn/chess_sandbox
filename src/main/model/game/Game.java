package model.game;

import com.sun.org.apache.xerces.internal.dom.AbortException;
import model.pieces.Piece;
import org.json.JSONObject;
import persistence.Writable;

// Represents a chess game, which has 2 players and a board on which they are playing
public class Game implements Writable {

    private String gameName; // Name of the game
    private Board bd; // Board on which this game is played
    private Player pl1; // Player that controls white pieces
    private Player pl2; // Player that controls black pieces
    private boolean playerToMove; // Represents piece color of the player who is to move

    // EFFECTS : Constructs a new game
    public Game(String gameName, String playerName1, String playerName2) {
        this.gameName = gameName;
        this.playerToMove = true;
        this.bd = new Board();
        this.pl1 = new Player(true, this.bd, playerName1);
        this.pl2 = new Player(false, this.bd, playerName2);
    }

    // EFFECTS: Initialises the scanner and starts the game
    public Game(String gameName, Board bd, Player pl1, Player pl2,boolean playerToMove) {
        this.gameName = gameName;
        this.bd = bd;
        this.pl1 = pl1;
        this.pl2 = pl2;
        this.playerToMove = playerToMove;
    }


    // EFFECTS: Creates a new JSON object from this game
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameName", gameName);
        json.put("board", bd.toJson());
        json.put("player1", pl1.toJson());
        json.put("player2", pl2.toJson());
        json.put("playerToMove",playerToMove);
        return json;
    }

    public String getName() {
        return gameName;
    }

    public Board getBoard() {
        return bd;
    }

    public Player getPlayer1() {
        return pl1;
    }

    public Player getPlayer2() {
        return pl2;
    }


    // EFFECTS : Converts user input into board coordinate and returns a piece on a given square
    public Piece getPiece(int i) {
        int posX = i % 8;
        int posY = i / 8;
        return bd.getTile(posX, posY);
    }

    // EFFECTS : Checks if the given x and y, coordinates are within the board's bounds, if they are checks if player
    //          to move can select piece on a given square
    public boolean checkPieceSelection(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false;
        } else {
            if (playerToMove) {
                return pl1.checkPieceSelection(x, y);
            } else {
                return pl2.checkPieceSelection(x, y);
            }
        }
    }

    // REQUIRES : The original coordinates should be coordinates of the piece
    // EFFECTS : Checks if the given x and y, coordinates are within the board's bounds, if they are checks if move
    //           can be played on the board
    public boolean makeMove(int origX, int origY, int destX, int destY) {
        if (destX >= 0 && destX <= 7 && destY >= 0 && destY <= 7) {
            if (origX == destX && origY == destY) {
                return false;
            } else if (playerToMove) {
                if (pl1.makeMove(origX, origY, destX, destY)) {
                    this.playerToMove = false;
                    return true;
                }
            } else {
                if (pl2.makeMove(origX, origY, destX, destY)) {
                    this.playerToMove = true;
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS : Returns whether the game is finished or not
    public boolean getBoardStatus() {
        return bd.getStatus();
    }

    // EFFECTS : Produces the congratulation message for a player who just won
    public String producePlayerWonMessage() {
        if (playerToMove == true) {
            return "Checkmate! Good job " + pl2.getName();
        } else {
            return "Checkmate! Good job " + pl1.getName();
        }
    }

    // EFFECTS : Sets which player is to move
    public void setPlayerToMove(boolean b) {
        this.playerToMove = b;
    }

    public boolean getPlayerToMove() {
        return playerToMove;
    }

    public void setStatus(boolean b) {
        this.bd.setStatus(b);
    }
}
