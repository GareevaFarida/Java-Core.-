package lesson4.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainWindow extends JFrame implements MessageSender {

    private JTextField textField;
    private JButton button;
    private JScrollPane scrollPane;
    private JList<Message> list;
    private DefaultListModel<Message> listModel;
    private DefaultListModel<String> userlistModel;
    private JList<String> userList;
    private JPanel panel;
    private JMenu mainMenu;
    private Network network;

    public MainWindow() {
        setTitle("Сетевой чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 500);

        setLayout(new BorderLayout());   // выбор компоновщика элементов

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new MessageCellRenderer());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(list, BorderLayout.SOUTH);
        panel.setBackground(list.getBackground());
        scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        userlistModel = new DefaultListModel<>();
        userList = new JList<>(userlistModel);
        userList.setPreferredSize(new Dimension(100, 0));
        add(userList, BorderLayout.WEST);

        textField = new JTextField();
        //реакция текстового поля на ввод Enter
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButtonSend();
            }
        });

        button = new JButton("Send");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButtonSend();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                list.ensureIndexIsVisible(listModel.size() - 1);
            }
        });

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.EAST);
        panel.add(textField, BorderLayout.CENTER);

        add(panel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (network != null) {
                        network.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.windowClosing(e);
            }
        });
        JFrame thisWindow = this;
        JMenuBar menuBar = new JMenuBar();
        mainMenu = new JMenu("Options");
        JMenuItem changeNickItem = new JMenuItem("Change nickname");
        mainMenu.add(changeNickItem);
        changeNickItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка смены ника");
                JDialog dialogChangeNickname = new NicknameChangeDialog(thisWindow,network);
                dialogChangeNickname.setVisible(true);
            }
        });
        mainMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Close");
        mainMenu.add(exitItem);
        exitItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (network != null) {
                        network.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });
        menuBar.add(mainMenu);
        setJMenuBar(menuBar);
        setVisible(true);
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);

        if (!loginDialog.isAuthSuccessful()) {
            System.exit(0);
        }
        network = loginDialog.getNetwork();
        setTitle("Сетевой чат. Пользователь " + network.getUsername());
    }

    private void pressButtonSend() {
        String text = textField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
        String dataFormatted = "[" + (LocalDateTime.now()).format(formatter) + "] ";
        text = dataFormatted + text;

        if (!userList.isSelectionEmpty()) {
            submitMessage(network.getUsername(), text);//вывод сообщения в UI
            textField.setText(null);
            textField.requestFocus();
            network.sendMessage(text, userList.getSelectedValue());//отправка сообщения через сеть
        } else {
            //вывод сообщения о невыбранном адресате
            JOptionPane.showMessageDialog(MainWindow.this,
                    "Не выбран адресат.",
                    "Сообщение",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void submitMessage(String user, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Message msg = new Message(user, message);
        listModel.add(listModel.size(), msg);
        list.ensureIndexIsVisible(listModel.size() - 1);
    }

    @Override
    public void fillUserList(String[] arrUserList){
        userlistModel.clear();
        for (String user:arrUserList){
            userlistModel.add(userlistModel.size(),user);
        }
     //   userList.setListData(arrUserList);
    }

    @Override
    public void removeUserFromUserList(String username){
        int index = userlistModel.indexOf(username);
       if (index!=-1){
           userlistModel.remove(index);
       }
    }

    @Override
    public void addUserAtUserList(String username){
        userlistModel.add(userlistModel.size(),username);
    }
}
