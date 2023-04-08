package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public interface Receiver {
    static void receiver(DataInputStream dis, DataOutputStream dos, Server s , ArrayList<String> db) {
        try {
            String input = dis.readUTF();
            String[] commands = input.split(" ", 3);
            //System.out.println(input);
            //System.out.println(Arrays.toString(commands));

            switch (commands[0]) {
                case "set":
                    try {
                        db.set(Integer.parseInt(commands[1]) - 1, commands[2]);
                        dos.writeUTF("OK");
                        System.out.println(db.get(Integer.parseInt(commands[1]) - 1));

                    } catch (IndexOutOfBoundsException e) {
                        dos.writeUTF("ERROR");
                    }
                    break;

                case "get":
                    try {
                        if (db.get(Integer.parseInt(commands[1]) - 1).equals("")) {
                            dos.writeUTF("ERROR");
                        }

                        dos.writeUTF(db.get(Integer.parseInt(commands[1]) - 1));
                        //System.out.println(db.get(Integer.parseInt(commands[1]) - 1));

                    } catch (IndexOutOfBoundsException e) {
                        dos.writeUTF("ERROR");
                    }
                    break;

                case "delete":
                    try {
                        db.set(Integer.parseInt(commands[1]) - 1, "");
                        dos.writeUTF("OK");

                    } catch (IndexOutOfBoundsException e) {
                        dos.writeUTF("ERROR");
                    }
                    break;

                case "exit":
                    s.isRunning=false;
                    break;

                default:
                    System.out.println("Wrong input");
                }
            } catch (IOException e) {


        } catch (IndexOutOfBoundsException oob) {
            System.out.println("ERROR");
        }
    }
}
