package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    boolean isRunning = true;
    ArrayList<String> serverDB = new ArrayList<>(100);

    private String address = "127.0.0.1";
    private int port = 23456;

    public void serverRun(Server s) {
        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            while (isRunning) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output  = new DataOutputStream(socket.getOutputStream())) {

                    Receiver.receiver(input, output, s , serverDB);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
