package model.game;

import org.json.JSONObject;
import persistence.Writable;

// Represents a chess game, which has 2 players and a board on which they are playing
public class Game implements Writable {

    private String gameName;
    private Board bd; // Board on which this game is played
    private Player pl1; // Player that controls white pieces
    private Player pl2; // Player that controls black pieces

    // EFFECTS: Initialises the scanner and starts the game
    public Game(String gameName,Board bd,Player pl1,Player pl2) {
        this.gameName = gameName;
        this.bd = bd;
        this.pl1 = pl1;
        this.pl2 = pl2;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameName",gameName);
        json.put("board",bd.toJson());
        json.put("player1",pl1.toJson());
        json.put("player2",pl2.toJson());
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
}
