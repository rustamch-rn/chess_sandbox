package persistence;

import org.json.JSONObject;

/**
 * Represents a object that can be written to JSON
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
