package model.game;

import org.json.JSONObject;
import persistence.Writable;

// Represents a player, who is playing chess with either black or white pieces
public class Player implements Writable {

    private final boolean pieceColor; // Color of pieces which this player controls
    private final Board playingBoard; // Board on which this player plays
    private final String playerName; // Name of this player

    // EFFECTS: Constructs a new Player
    public Player(boolean pieceColor, Board playingBoard, String playerName) {
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
    public boolean checkPieceSelection(int x, int y) {
        return playingBoard.checkTile(pieceColor, x, y);
    }

    //MODIFIES: Board playingBoard
    //EFFECTS: Returns a true if player's move is legal, if it is legal casts given move on a board,
    //         returns false and throws an exception
    public boolean makeMove(int origX, int origY, int destX, int destY) {
        return playingBoard.checkMoveRules(pieceColor, origX, origY, destX, destY);
    }

    // EFFECTS : Creates a json object from this player
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieceColor", pieceColor);
        jsonObject.put("name", playerName);
        return jsonObject;
    }

    // EFFECTS : Returns name of this player
    public String getPlayerName() {
        return playerName;
    }
}
