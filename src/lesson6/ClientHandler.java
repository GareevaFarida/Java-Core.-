package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientHandler {

    private static final Pattern MESSAGE_PATTERN_FOR_READ = Pattern.compile("^/w (.+) (.+)$");
   // private static final String MESSAGE_PATTERN = "/w %s %s";
    private final Thread handleThread;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final ChatServer server;
    private final String username;
    private final Socket socket;

    public ClientHandler(String username, Socket socket, ChatServer server) throws IOException {
        this.username = username;
        this.socket = socket;
        this.server = server;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.handleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String msg = inp.readUTF();
                        System.out.printf("Message from user %s: %s%n", username, msg);
                        // TODO реализовать прием сообщений от клиента и пересылку адресату через сервер
                        //////////////////////
                        Matcher matcher = MESSAGE_PATTERN_FOR_READ.matcher(msg);

                        if (matcher.matches()) {
                            String message = matcher.group(1);
                            String adresat = matcher.group(2);
                            System.out.println("Адресат: " + adresat);
                            System.out.println("Сообщение: " + message + " " + username);
                            server.sendMessage(username,adresat, message);

                        }
                        //////////////////////

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Client %s disconnected%n", username);
                    /////////////////////////////////
                    //удалим строку из hashMap и разошлем всем сообщения, что пользователь вышел
                    Map<String, ClientHandler> clientmap = server.getClientHandlerMap();
                    clientmap.remove(username);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
                    String dataFormatted = "[" + (LocalDateTime.now()).format(formatter) + "] ";
                    for (Map.Entry<String, ClientHandler> pair : clientmap.entrySet()) {
                        if (pair.getKey() !=username){
                            server.sendMessage("server",pair.getKey(),dataFormatted+"User "+username+" is disconnected.");
                        }
                    }
                    /////////////////////////////////
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        handleThread.start();
    }

    public DataOutputStream getOut() {
        return out;
    }
}
