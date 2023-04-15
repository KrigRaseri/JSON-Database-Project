package client;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JCommanderClient implements IStringConverter<JsonElement> {

    @Parameter(
        names = "-t",
            description = "set, get, delete type.",
            required = false
    )
    String type;

    @Parameter(
            names = "-k",
            description = "index of database",
            required = false
    )
    String key;

    @Parameter(
            names = "-v",
            description = "message to save to server",
            required = false
    )
    String value;

    @Parameter(
            names = "-in",
            description = "JSON file from user chosen file.",
            required = false
    )
    String input;


    @Override
    public JsonElement convert(String value) {
        return JsonParser.parseString(value);
    }
}
