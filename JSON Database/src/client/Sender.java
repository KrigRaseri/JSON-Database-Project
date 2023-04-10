package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Map;

public interface Sender {
    static void sender(DataInputStream dis, DataOutputStream dos, String[] args) {
        JCommanderClient jcc = new JCommanderClient();
        JCommander jc = JCommander.newBuilder().addObject(jcc).build();
        jc.parse(args);
        Gson gson = new Gson();

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
                case "get" -> {
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
                case "delete" -> {
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
                    System.out.printf("Sent: %s\n", jcc.type);
                    dos.writeUTF(jcc.type);
                }
                default -> System.out.println("Wrong input");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
