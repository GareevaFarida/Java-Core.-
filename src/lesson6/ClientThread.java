package lesson6;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private String msg;
    private DataOutputStream out;

    public ClientThread(Socket socket){
        this.socket = socket;
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {

    }

    public void printMessageFromServer(String message){
                System.out.println("Message: " + message);
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
