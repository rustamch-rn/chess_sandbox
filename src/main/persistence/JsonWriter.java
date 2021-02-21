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


    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(Game g) {
        JSONObject json = g.toJson();
        saveToFile(json.toString(TAB));
    }

    public void writeNames(List<String> strArray) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("gameNames", jsonArray);
        for (String str : strArray) {
            jsonArray.put(str);
        }
        saveToFile(jsonObject.toString(TAB));
    }


    public void close() {
        writer.close();
    }

    private void saveToFile(String json) {
        writer.print(json);
    }
}
