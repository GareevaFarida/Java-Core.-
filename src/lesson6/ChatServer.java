package lesson6;

import lesson6.auth.AuthService;
import lesson6.auth.AuthServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class ChatServer {
    private AuthService authService = new AuthServiceImpl();

    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start(7777);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                System.out.println("New client connected!");

                try {
                    String authMessage = inp.readUTF();
                    Matcher matcher = Constants.AUTH_PATTERN_FOR_READ.matcher(authMessage);
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        String password = matcher.group(2);
                        if (authService.authUser(username, password)) {
                            //сообщим всем пользователям о появлении нового клиента online
                            broadcastUserConnected(username);
                            ClientHandler newUserClientHandler = new ClientHandler(username, socket, this);
                            clientHandlerMap.put(username, newUserClientHandler);
                            out.writeUTF("/auth successful");
                            out.flush();
                            System.out.printf("Authorization for user %s successful%n", username);
                            //сообщим новому клиенту список online-пользователей
                            sendOnlineUsers(newUserClientHandler);
                        } else {
                            System.out.printf("Authorization for user %s failed%n", username);
                            out.writeUTF("/auth fails");
                            out.flush();
                            socket.close();
                        }
                    } else {
                        System.out.printf("Incorrect authorization message %s%n", authMessage);
                        out.writeUTF("/auth fails");
                        out.flush();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendOnlineUsers(ClientHandler newUserClientHandler) throws IOException {
        if (clientHandlerMap.size() == 1) {
            //единственный подключенный пользователь - это текущий пользователь, список будет пустым
            return;
        }
        String listOnlineUsers = "";
        for (Map.Entry<String, ClientHandler> pair : clientHandlerMap.entrySet()) {
            if (pair.getKey() != newUserClientHandler.getUsername()) {
                listOnlineUsers = listOnlineUsers + " " + pair.getKey();
            }
        }
        //System.out.println("Список online пользователей: " + listOnlineUsers);
        newUserClientHandler.sendMessage(String.format(Constants.USERLIST_PATTERN, listOnlineUsers));

    }

    public void sendMessage(String sender, String adresat, String msg) throws IOException {
        //прилепим отправителя по шаблону, чтобы впоследствии можно было расчленить сообщение
        msg = String.format(Constants.MESSAGE_PATTERN, msg, sender);

        ClientHandler handler = clientHandlerMap.get(adresat);
        if (handler == null) {
            System.out.printf("User %s is offline", adresat);
            //информируем отправителя, что адресат offline
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
            String dataFormatted = "[" + (LocalDateTime.now()).format(formatter) + "] ";
            sendMessage("server", sender, dataFormatted + "User " + adresat + " is offline");
        } else {
            handler.sendMessage(msg);
        }

    }

    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

    public void unsubscribeClient(ClientHandler clientHandler) {
        String username = clientHandler.getUsername();
        clientHandlerMap.remove(username);
        try {
            broadcastUserDisconnected(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastUserConnected(String username) throws IOException {
        // оообщаем о том, что конкретный пользователь подключился
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
        String dataFormatted = "[" + (LocalDateTime.now()).format(formatter) + "] ";
        for (Map.Entry<String, ClientHandler> pair : clientHandlerMap.entrySet()) {
            //отправляем сообщение для перерисовки userlist при добавлении нового пользователя
            pair.getValue().sendMessage(String.format(Constants.USER_ONLINE_PATTERN, username));
            //сообщение о подключении нового пользователя
            sendMessage("server", pair.getKey(), dataFormatted + "User " + username + " is online.");
        }
    }

    public void broadcastUserDisconnected(String username) throws IOException {
        // сообщаем о том, что конкретный пользователь отключился
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
        String dataFormatted = "[" + (LocalDateTime.now()).format(formatter) + "] ";
        for (Map.Entry<String, ClientHandler> pair : clientHandlerMap.entrySet()) {
            //отправляем сообщение для перерисовки userlist при отключении пользователя
            pair.getValue().sendMessage(String.format(Constants.USER_OFFLINE_PATTERN, username));
            //сообщение о выходе пользователя из чата
            sendMessage("server", pair.getKey(), dataFormatted + "User " + username + " is disconnected.");
        }
    }
}
