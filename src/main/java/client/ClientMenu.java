package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public interface ClientMenu {
    static void clientMenu(DataInputStream dis, DataOutputStream dos, String[] args) {
        JCommanderClient jcc = new JCommanderClient();
        JCommander jc = JCommander.newBuilder().addObject(jcc).build();
        jc.parse(args);
        Gson gson = new Gson();

        String inp = fileCommand(jcc);

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
                        String g = new String(Files.readAllBytes(Paths.get(inp)));
                        System.out.println(g);
                        System.out.printf("Sent: " + g);
                        System.out.println();
                        dos.writeUTF(g);
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
            inp = "src/client/data/" + jcc.input;
            jcc.type = "fileCommand";
        }
        return inp;
    }
}