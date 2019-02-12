package lesson6;

import java.util.ArrayList;
import java.util.Scanner;

public class ThreadOfPublicMessageSending implements Runnable {
    private ArrayList<ClientThread> arrayClients;

    public ThreadOfPublicMessageSending(ArrayList<ClientThread> arrayClients) {
        this.arrayClients = arrayClients;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter a message, which you want to send to all users of chat. Use button 'ENTER' to send...");
        while (true) {
            if (sc.hasNext()) {
                String publicMessage = sc.nextLine();
                System.out.println(publicMessage);
                for (ClientThread clientThread : arrayClients) {
                //    if (!clientThread.isInterrupted()) {
                    //как отлавливать неактивные потоки пользователей, вышедших из чата, я не поняла :(
                        clientThread.printMessageFromServer(publicMessage);
                //    }
                }
            }
        }
    }
}

