package model.game;

import org.json.JSONObject;
import persistence.Writable;

// Represents a player, who is playing chess with either black or white pieces
public class Player implements Writable {

    private final boolean pieceColor; // Color of pieces which this player controls
    private final Board playingBoard; // Board on which this player plays
    private final String playerName; // Name of this player

    // EFFECTS: Constructs a new Player
    public Player(boolean pieceColor,Board playingBoard,String playerName) {
        this.pieceColor = pieceColor;
        this.playingBoard = playingBoard;
        this.playerName = playerName;

    }

    //EFFECTS: Returns name of the given player
    public String getName() {
        return playerName;
    }

    //EFFECTS: Returns the color of the player's pieces
    public boolean getPieceColor() {
        return pieceColor;
    }


    //EFFECTS: Checks if there is a piece on a given square
    public boolean checkPieceSelection(String origSquare) {
        int y = Integer.parseInt(origSquare.substring(1,2)) - 1;
        int x = letterToPos(origSquare.substring(0,1));
        return playingBoard.checkTile(pieceColor,x,y);

    }

    //EFFECTS: Converts user input coordinate into actual board coordinate
    public int letterToPos(String substring) {
        switch (substring) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "G":
                return 6;
            case "H":
                return 7;
            default:
                return -1;
        }
    }

    //MODIFIES: Board playingBoard
    //EFFECTS: Returns a true if player's move is legal, if it is legal casts given move on a board,
    //         returns false and throws an exception
    public boolean makeMove(String origSquare,String destSquare) throws Exception {
        int origY = Integer.parseInt(origSquare.substring(1,2)) - 1;
        int origX = letterToPos(origSquare.substring(0,1));
        int destY = Integer.parseInt(destSquare.substring(1,2)) - 1;
        int destX = letterToPos(destSquare.substring(0,1));
        if (playingBoard.checkMoveRules(origX,origY,destX,destY)) {
            playingBoard.printBoard();
            return true;
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieceColor",pieceColor);
        jsonObject.put("name",playerName);
        return jsonObject;
    }
}
