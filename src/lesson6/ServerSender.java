package lesson6;

import java.util.ArrayList;

public class ServerSender {

    private static ArrayList<ClientThread> arrayClients = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        arrayClients = new ArrayList<>();
        Thread thread1 = new Thread(new ThreadOfConnections(arrayClients));
        thread1.start();
        Thread thread2 = new Thread(new ThreadOfPublicMessageSending(arrayClients));
        thread2.start();
    }

}
