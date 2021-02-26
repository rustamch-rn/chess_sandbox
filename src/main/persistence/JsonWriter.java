package persistence;

import model.game.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs a new JSON writer
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // EFFECTS: opens a file at given path;
    // throws a FileNotFoundException if it fails to create a file at a given path
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // EFFECTS: Converts game to JSON and writes it to specified file
    public void write(Game g) {
        JSONObject json = g.toJson();
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: Converts names of the games to JSON and writes them to specified file
    public void writeNames(List<String> strArray) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("gameNames", jsonArray);
        for (String str : strArray) {
            jsonArray.put(str);
        }
        saveToFile(jsonObject.toString(TAB));
    }


    // EFFECTS: Closes the writer
    public void close() {
        writer.close();
    }

    // EFFECTS: Writes given string to the file specified
    private void saveToFile(String json) {
        writer.print(json);
    }
}
