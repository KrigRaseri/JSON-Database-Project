package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Sender {
    static void sender(DataInputStream dis, DataOutputStream dos, String[] args) {
        JCommanderClient jcc = new JCommanderClient();
        JCommander jc = JCommander.newBuilder().addObject(jcc).build();
        jc.parse(args);

        try {
            switch (jcc.type) {
                case "set":
                    try {
                        System.out.printf("Sent: %s %s %s\n", jcc.type, jcc.index, jcc.message);
                        dos.writeUTF(String.format("%s %s %s", jcc.type, jcc.index, jcc.message));
                        System.out.println("Received: " + dis.readUTF());

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERROR");
                    }
                    break;

                case "get":
                    try {
                        System.out.printf("Sent: %s %s\n", jcc.type, jcc.index);
                        dos.writeUTF(String.format("%s %s", jcc.type, jcc.index));
                        System.out.println("Received: " + dis.readUTF());


                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERROR");
                    }
                    break;

                case "delete":
                    try {
                        System.out.printf("Sent: %s %s\n", jcc.type, jcc.index);
                        dos.writeUTF(String.format("%s %s", jcc.type, jcc.index));
                        System.out.println("Received: " + dis.readUTF());

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ERROR");
                    }
                    break;

                case "exit":
                    System.out.printf("Sent: %s\n", jcc.type);
                    dos.writeUTF(jcc.type);
                    break;

                default:
                    System.out.println("Wrong input");
                    break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException oob) {
            System.out.println("ERROR");
        }
    }
}
