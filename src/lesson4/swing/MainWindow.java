package lesson4.swing;

import lesson6.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
    private String filePathname;
    private File historyFile;
    private BufferedWriter writer;
    private BufferedReader reader;

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
                    writer.flush();
                    writer.close();
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
                        writer.flush();
                        writer.close();
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
        filePathname = "history_"+network.getUsername()+".txt";
        historyFile = new File(filePathname);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(historyFile,true)));
            readHistoryFromFile();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readHistoryFromFile() {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(historyFile)));
            Object[] arr = reader.lines().toArray();
            int firstLine = Math.max(0,arr.length- Constants.COUNT_ROWS);
            for (int i = firstLine;i<arr.length;i++){
                String line = (String) arr[i];
                displayMessage(line);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayMessage(String line) {
        if (line==null)return;
        String[] lines = line.split(Constants.INNER_SEPARATOR);
        if (lines.length<2)
            throw new RuntimeException("Неверный формат строки в файле истории "+filePathname);
        String message = "";
        for (int i = 1;i<lines.length;i++){
            message = message+lines[i]+(i==lines.length-1?"":Constants.INNER_SEPARATOR);
        }
        submitMessage(lines[0],message,false);

    }

    private void pressButtonSend() {
        String text = textField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
        String dataFormatted = "[" + (LocalDateTime.now()).format(formatter) + "] ";
        text = dataFormatted + text;

        if (!userList.isSelectionEmpty()) {
            submitMessage(network.getUsername(), text,true);//вывод сообщения в UI
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
    public void submitMessage(String user, String message, boolean toWrite) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Message msg = new Message(user, message);
        listModel.add(listModel.size(), msg);
        list.ensureIndexIsVisible(listModel.size() - 1);
        if (toWrite)
            writeMessageToFile(user,message);
    }

    private void writeMessageToFile(String user, String message) {
        try{
            writer.write(user+Constants.INNER_SEPARATOR+message+System.lineSeparator());
           // writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fillUserList(String[] arrUserList){
        userlistModel.clear();
        for (String user:arrUserList){
            userlistModel.add(userlistModel.size(),user);
        }
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
