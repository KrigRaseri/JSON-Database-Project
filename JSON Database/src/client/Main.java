package client;

import menu.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        System.out.println("Client started!");

        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Sent: Give me a record # 12");
            output.writeUTF("Give me a record # 12");

            String received = input.readUTF();
            System.out.println("Received: " + received);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
