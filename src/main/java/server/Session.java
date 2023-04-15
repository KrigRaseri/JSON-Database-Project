package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {
    private final Socket socket;
    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            ServerMenu.serverMenu(input, output);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
