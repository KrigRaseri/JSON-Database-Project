package client;

import com.beust.jcommander.Parameter;

public class JCommanderClient {

    @Parameter(
        names = "-t",
            description = "set, get, delete type.",
            required = true
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
}
