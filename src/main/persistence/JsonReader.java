package persistence;

import model.game.Board;
import model.game.Game;
import model.game.Player;
import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS : reads a list with names of games and returns it;
    // throws IOException if an error occurs reading data from file
    public List<String> readNames() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameNames(jsonObject);
    }

    // EFFECTS : parses a list with name of the games from JSON object and returns it
    private List<String> parseGameNames(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameNames");
        List<String> names = new ArrayList<>();
        for (Object j : jsonArray) {
            names.add((String) j);
        }
        return names;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        String name = jsonObject.getString("gameName");
        JSONObject bd = jsonObject.getJSONObject("board");
        JSONObject player1 = jsonObject.getJSONObject("player1");
        JSONObject player2 = jsonObject.getJSONObject("player2");
        Board board = parseBoard(bd);
        board.piecesSetBoard();
        Player pl1 = parsePlayer(player1,board);
        Player pl2 = parsePlayer(player2,board);
        boolean playerToMove = jsonObject.getBoolean("playerToMove");
        Game g = new Game(name,board, pl1, pl2, playerToMove);
        return g;
    }

    // EFFECTS: parses the player from JSON object and returns it
    private Player parsePlayer(JSONObject player, Board board) {
        String name = player.getString("name");
        boolean pieceColor = player.getBoolean("pieceColor");
        Player pl = new Player(pieceColor,board,name);
        return pl;
    }

    // EFFECTS: parses the board from the JSON object and returns it
    private Board parseBoard(JSONObject bd) {
        boolean st = bd.getBoolean("status");
        JSONArray tiles = bd.getJSONArray("pieces");
        Piece[][] result = new Piece[8][8];
        int count = 0;
        for (Object j : tiles) {
            int x = count % 8;
            int y = count / 8;
            if (j.equals("null")) {
                result[y][x] = null;
            } else {
                JSONObject jsonObject = (JSONObject) j;
                result[y][x] = addPiece(jsonObject);
            }
            count++;
        }
        Board board = new Board(result,st);
        return board;
    }

    // EFFECTS: parses a piece from JSON object and adds it to the board
    private Piece addPiece(JSONObject j) {
        boolean pieceColor = j.getBoolean("pieceColor");
        int x = j.getInt("posX");
        int y = j.getInt("posY");
        char id = (char) j.getInt("identifier");
        Piece p = pieceCreator(pieceColor,x,y,id);
        return p;
    }

    // EFFECTS: determines which piece should be created based on piece identifier
    private Piece pieceCreator(boolean pieceColor, int x, int y, char id) {
        Piece p;
        switch (id) {
            case 'K':
                p = new King(pieceColor,x,y);
                return p;
            case 'Q':
                p = new Queen(pieceColor,x,y);
                return p;
            case 'N':
                p = new Knight(pieceColor,x,y);
                return p;
            case 'B':
                p = new Bishop(pieceColor,x,y);
                return p;
            case 'R':
                p = new Rook(pieceColor,x,y);
                return p;
            default:
                p = new Pawn(pieceColor,x,y);
                return p;
        }
    }

}
