package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public interface ClientMenu {
    static void clientMenu(DataInputStream dis, DataOutputStream dos, String[] args) {
        JCommanderClient jcc = new JCommanderClient();
        JCommander jc = JCommander.newBuilder().addObject(jcc).build();
        jc.parse(args);
        Gson gson = new Gson();

        String input = fileCommand(jcc);

        try {
            switch (jcc.type) {
                case "set" -> {
                    try {
                        String responseJSON = gson.toJson(Map.of("type", jcc.type, "key", jcc.key, "value", jcc.value));
                        System.out.printf("Sent: " + responseJSON);
                        System.out.println();
                        dos.writeUTF(responseJSON);
                        System.out.println("Received: " + dis.readUTF());

                    } catch (EOFException e) {
                        System.out.println("ERROR");
                    }
                }

                case "get", "delete" -> {
                    try {
                        String responseJSON = gson.toJson(Map.of("type", jcc.type, "key", jcc.key));
                        System.out.printf("Sent: " + responseJSON);
                        System.out.println();
                        dos.writeUTF(responseJSON);
                        System.out.println("Received: " + dis.readUTF());

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERROR");
                    }
                }

                case "exit" -> {
                    String responseJSON = gson.toJson(Map.of("type", jcc.type));
                    System.out.printf("Sent: " + responseJSON);
                    System.out.println();
                    dos.writeUTF(responseJSON);
                    System.out.println("Received: " + dis.readUTF());
                }

                case "fileCommand" -> {
                    try {
                        String inputFile = new String(Files.readAllBytes(Paths.get(input)));
                        System.out.printf("Sent: " + inputFile);
                        System.out.println();
                        dos.writeUTF(inputFile);
                        System.out.println("Received: " + dis.readUTF());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                default -> System.out.println("Wrong input");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static String fileCommand(JCommanderClient jcc) {
        String inp = null;
        if (jcc.input != null) {
            inp = "src/main/java/client/data/" + jcc.input;
            jcc.type = "fileCommand";
        }
        return inp;
    }
}
