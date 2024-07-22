package persistence;

import org.json.JSONObject;

// Interface for classes that can be converted to and from JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
