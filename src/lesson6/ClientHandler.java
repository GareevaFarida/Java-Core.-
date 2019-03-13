package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;

public class ClientHandler {

   // private final Thread handleThread;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final ChatServer server;
    private final String username;
    private final Socket socket;
    private final ExecutorService executorService;

    public ClientHandler(String username, Socket socket, ChatServer server, ExecutorService executorService) throws IOException {
        this.username = username;
        this.socket = socket;
        this.server = server;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.executorService = executorService;

     //   this.handleThread = new Thread (new Runnable() {
     executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String msg = inp.readUTF();
                        System.out.printf("Message from user %s: %s%n", username, msg);
                        // реализован прием сообщений от клиента и пересылка адресату через сервер
                        //////////////////////
                        Matcher matcher = Constants.MESSAGE_PATTERN_FOR_READ.matcher(msg);

                        if (matcher.matches()) {
                            String message = matcher.group(1);
                            String adresat = matcher.group(2);
                            System.out.println("Адресат: " + adresat);
                            System.out.println("Сообщение: " + message + " " + username);
                            server.sendMessage(username, adresat, message);

                        }
                        //////////////////////

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Client %s disconnected%n", username);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //удалим строку из hashMap и разошлем всем сообщения, что пользователь вышел
                    server.unsubscribeClient(ClientHandler.this);
                }
            }
        });
      //  handleThread.start();
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String msg) throws IOException {
        out.writeUTF(msg);
        out.flush();
    }
}
