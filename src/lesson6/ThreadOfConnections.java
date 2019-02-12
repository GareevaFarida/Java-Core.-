package lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadOfConnections implements Runnable {

    private ArrayList<ClientThread> arrayClients;
    public ThreadOfConnections(ArrayList<ClientThread> arrayClients) {
      this.arrayClients = arrayClients;
    }

    @Override
    public void run() {
     try (ServerSocket serverSocket = new ServerSocket(7777)) {
        System.out.println("Server started!");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
            arrayClients.add(clientThread);
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}}
