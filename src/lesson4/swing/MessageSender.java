package lesson4.swing;


public interface MessageSender {

    void submitMessage(String user, String message);

    void fillUserList(String[] arrUserList);

    void removeUserFromUserList(String username);

    void addUserAtUserList(String username);
}
