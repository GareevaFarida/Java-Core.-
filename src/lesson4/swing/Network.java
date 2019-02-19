package lesson4.swing;

import lesson6.Constants;

import javax.swing.*;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;

public class Network implements Closeable {

    private Socket socket;
    private int port;
    private String hostName;
    private DataOutputStream out;
    private DataInputStream in;
    private MessageSender messageSender;
    private Thread receiver;

    private String username;

    public Network(String hostName, int port, MessageSender messageSender) {
        this.hostName = hostName;
        this.port = port;
        this.messageSender = messageSender;
        this.receiver = createReciverThread();
    }

    public Thread createReciverThread() {

        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    if (socket.isClosed()) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    try {
                        String msg = in.readUTF();

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                String message = "";
                                String userSender = "";
                                //распарсим сообщение, чтобы выделить отправителя
                                Matcher matcher = Constants.MESSAGE_PATTERN_FOR_READ.matcher(msg);

                                if (matcher.matches()) {
                                    message = matcher.group(1);
                                    userSender = matcher.group(2);
                                }
                                System.out.println("New message " + message);
                                messageSender.submitMessage(userSender, message);//вывод в UI сообщения, прочитанного из входящего потока

                                if (isUserOnlineMessage(msg)) {
                                    return;
                                }
                                if (isUserOfflineMessage(msg)) {
                                    return;
                                }
                                if (isListUsersOnlineMessage(msg)) {
                                    return;
                                }
                            }

                            //  проверяем, это пришло сообщение со списком пользователей в online?
                            private boolean isListUsersOnlineMessage(String msg) {
                                Matcher matcher = Constants.USERLIST_PATTERN_FOR_READ.matcher(msg);
                                if (!matcher.matches()) {
                                    return false;
                                }
                                String strUserlist = matcher.group(1);
                                String[] arrUserList = strUserlist.split(" ");
                                messageSender.fillUserList(arrUserList);
                                return true;
                            }

                            //проверяем, это пришло сообщение об отключении пользователя?
                            private boolean isUserOfflineMessage(String msg) {
                                Matcher matcher = Constants.USER_OFFLINE_PATTERN_FOR_READ.matcher(msg);
                                if (!matcher.matches()) {
                                    return false;
                                }
                                messageSender.removeUserFromUserList(matcher.group(1));
                                return true;
                            }

                            //Проверяем, это пришло сообщение о подключении нового пользователя?
                            private boolean isUserOnlineMessage(String msg) {
                                Matcher matcher = Constants.USER_ONLINE_PATTERN_FOR_READ.matcher(msg);
                                if (!matcher.matches()) {
                                    return false;
                                }
                                messageSender.addUserAtUserList(matcher.group(1));
                                return true;
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void sendMessage(String msg, String adresat) {
        msg = String.format(Constants.MESSAGE_PATTERN, msg, adresat);
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorize(String username, String password) throws IOException {
        socket = new Socket(hostName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        out.writeUTF(String.format(Constants.AUTH_PATTERN, username, password));
        out.flush();
        String response = in.readUTF();
        if (response.equals("/auth successful")) {
            this.username = username;
            receiver.start();
        } else throw new AuthException("");


    }

    public String getUsername() {
        return username;
    }

    @Override
    public void close() throws IOException {
        socket.close();
        receiver.interrupt();
        try {
            receiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}