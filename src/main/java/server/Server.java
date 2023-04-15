package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    static boolean isRunning = true;
    private final String address = "127.0.0.1";
    private final int port = 23456;

    public void serverRun() {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            while (isRunning) {
                try {
                    Session session = new Session(server.accept());
                    executor.submit(session::start);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Kills server on command, only for the purposes of the tests for the project.
    static void serverKill() {
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
            socket.close();
        } catch (Exception e) {

        }
    }
}
