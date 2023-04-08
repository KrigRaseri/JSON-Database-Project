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
            names = "-i",
            description = "index of database",
            required = false
    )
    int index;

    @Parameter(
            names = "-m",
            description = "message to save",
            required = false
    )
    String message;
}
