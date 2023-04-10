package client;

public class JsonMessageClient {
    String type;
    String key;
    String value;

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

    public JsonMessageClient(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public JsonMessageClient(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }
}
