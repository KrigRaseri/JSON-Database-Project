package server;

import menu.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
             Socket socket = server.accept();
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream())) {

            String received = input.readUTF();
            System.out.println("Received: " + received);

            System.out.println("Sent: A record # 12 was sent!");
            output.writeUTF("A record # 12 was sent!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
⠀⠀⠀⠀⠀⠀⢀⣴⡻⠋⠁⠀⠀⠀⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣶⡀⠀⡿⠦⣤⣤⡈⠳⡙⢦⡀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣠⠛⢹⣅⣠⠤⠒⠒⠚⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣷⡀⡇⠀⠀⠙⠉⠛⠛⠀⠙⣄⠀⠀⠀⠀
⠀⠀⠀⠀⡼⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠺⠿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠸⢦⠀⠀⠀
⠀⠀⠀⡼⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣤⡤⠤⠤⠤⠤⠤⠤⢤⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢧⠀⠀
⠀⠀⡼⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠴⠚⣻⡟⠁⢰⠛⡇⠀⠀⠀⠀⠀⠀⠀⠀⠈⠹⣦⠀⠀⠀⠀⠀⠀⠀⠀⠈⡇⠀
⠀⢰⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠞⠉⠀⣀⡾⢽⠗⢲⠏⠘⠋⠉⢛⠉⠉⠉⠉⠑⠲⣄⡀⠈⢧⡀⠀⠀⠀⠀⠀⠀⠀⢸⡀
⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⠞⠉⣼⠤⠖⡻⠃⠀⢸⠀⡜⠀⠀⠀⠀⡘⠀⠀⠀⠀⠀⡄⠈⢳⡀⠀⢳⡀⠀⠀⠀⠀⠀⠀⠀⠁
⢸⠁⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣏⡤⠒⢩⠇⠀⡴⠁⠤⢀⣘⣸⡀⠀⢀⣧⢀⣿⣸⡀⠀⠀⢀⡇⠀⢀⠙⣆⠀⢻⡀⠀⠀⠀⠀⠀⠀⢀
⠈⠀⠀⠀⠀⠀⠀⠀⢀⣴⠿⠋⢡⠀⢠⠋⣀⠞⠀⠀⠀⠀⢸⡏⠀⠀⠀⢇⢸⠙⣿⠀⠀⠀⡸⡇⠀⣾⠀⠈⢷⡀⢳⡀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢀⣴⠿⡇⠀⠀⡇⠀⡏⢠⣏⠀⠀⠀⠀⠀⠘⠀⠀⠀⠀⢸⡇⠀⣿⠀⠀⢠⠃⡇⣰⠀⡇⠀⢸⢳⡈⣧⠀⠀⠀⠀⠀⢠
⠀⠀⠀⠀⠀⢀⡼⠋⠀⡇⠀⢸⣇⡸⠀⠈⠛⢿⣶⣤⣄⣐⠠⠤⡀⠀⠀⠘⠁⠀⠏⠀⢀⠎⠀⣿⠷⣄⣣⠀⡎⠀⠹⣼⡄⠀⠀⠀⠀⡏
⠀⠀⠀⠀⢠⠞⠀⠀⠀⠸⡄⢸⢻⡇⠀⠀⠀⢸⣇⠈⠉⠛⣿⡿⠇⠀⠀⠀⠀⠀⡇⣠⠋⠀⠘⠁⠀⠀⠙⢲⠇⠀⠀⢹⣧⠀⠀⠀⢠⠁
⠀⠀⠀⢠⣿⠀⠀⠀⠀⠀⣿⡼⢼⠇⠀⠀⠀⠘⠿⣶⣴⡾⠋⠀⠀⠀⠀⠀⠀⢸⠟⠁⣷⣦⣤⠀⠀⠀⢰⢻⠀⠀⣰⠀⣿⠀⠀⢀⡞⠀
⠀⠀⣰⣿⣿⠀⠀⠀⠀⠀⢸⣿⣼⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⠿⠷⣶⣦⣤⣼⡀⠀⡇⠀⣿⡀⠀⣼⠁⠀
⠀⢠⣿⣿⣿⠀⠀⠀⠀⠀⢸⣿⢻⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣤⡀⢀⣼⡿⠙⠀⣸⠃⢸⣿⠀⣼⠇⠀⠀
⠀⣼⣿⣿⣿⡆⠀⠀⠀⠀⠀⣿⠸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⠻⠛⠋⠀⡇⡰⠟⢀⡟⣿⣠⠏⠀⠀⠀
⢠⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⣿⢦⡁⠀⠀⠀⠀⠀⢀⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠞⢁⣧⠞⢀⣿⡏⠀⠀⠀⠀
⣾⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⣿⠀⣷⡄⠀⠀⠀⠀⢸⠛⠛⠲⢤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣷⢄⡾⢃⡴⣼⢻⠃⠀⠀⠀⠀
⣿⣿⣿⣿⣿⣿⠏⠀⠀⠀⠀⣿⡄⢿⠙⣦⡀⠀⠀⠈⠦⣀⡀⠀⢻⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⠟⣡⠏⢀⡞⣽⠃⣾⠀⠀⠀⠀⠀
⠻⣿⣿⣿⣿⡟⠀⠀⠀⠀⢀⣿⣷⣿⢠⠇⠙⢦⡀⠀⠀⠀⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⢿⣿⡄⠏⣠⣿⠞⠁⠀⣿⠀⠀⠀⠀⠀
⠀⠹⣿⣿⡟⠀⠀⠀⠀⠀⢸⣿⣿⡏⣸⠀⠀⠀⠉⠢⣄⣀⣀⣀⣀⣀⣀⣠⣤⣤⠴⠒⠚⢉⡴⠋⢸⣠⡾⠛⠁⠀⠀⠀⣿⠀⠀⠀⠀⠀
⡀⠀⢹⡿⠁⠀⠀⠀⠀⠀⣾⣿⣿⣧⡏⠀⠀⠀⠀⠀⠀⠀⢀⣠⣿⣥⣾⣿⡟⠀⠀⢀⡴⠋⠀⢀⣿⠋⠀⠀⣀⣀⣠⣤⡿⠀⠀⠀⠀⠀
⠙⢦⣴⠃⠀⠀⠀⠀⠀⢸⠋⠉⠻⣿⡀⠀⠀⠀⠀⢀⡤⠞⠋⠁⠀⠀⢈⣹⠇⢀⡴⠋⠀⠀⠀⣼⣷⣶⣾⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⠀
⠀⠀⣙⠀⠀⠀⠀⠀⠀⣏⣀⣀⡀⠙⠿⣦⣀⣤⢶⠋⠀⠀⣠⠶⠶⠚⠉⣹⣰⠞⠀⠀⠀⠀⢰⠏⠉⠉⠙⢻⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀

*/