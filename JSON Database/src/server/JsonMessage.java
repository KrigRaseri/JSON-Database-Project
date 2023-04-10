package server;

import java.util.HashMap;

public class JsonMessage {
    static HashMap<String, String> jsonDbMap = new HashMap<>();
    String type;
    String key;
    String value;
    String response;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        response = response;
    }

    public JsonMessage(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public JsonMessage(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public JsonMessage(String response) {
        this.response = response;
    }
}