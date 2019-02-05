import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {

    public MyWindow() {

        setTitle("Lesson-4");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 400, 400);

        //основная панель
        JPanel jPanel = new JPanel();
        add(jPanel);
        jPanel.setLayout(new BorderLayout());
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);//делаем нередактируемой
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jPanel.add(jScrollPane, BorderLayout.CENTER);

        //дополнительная "южная" панель для размещения на ней поля ввода и кнопки
        JPanel jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new BorderLayout());

        JTextField jTextField = new JTextField();
        //реакция текстового поля на ввод Enter
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(jTextArea, jTextField);
            }
        });

        //размещаем на южной панели поле ввода и кнопку
        jPanelSouth.add(jTextField, BorderLayout.CENTER);
        JButton buttonSend = new JButton("Send");
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(jTextArea, jTextField);
            }
        });
        jPanelSouth.add(buttonSend, BorderLayout.EAST);

        //размещаем южную панель на основную панель
        jPanel.add(jPanelSouth, BorderLayout.SOUTH);

        //никак не получается установить фокус на поле ввода
        //jTextField.setRequestFocusEnabled(true);
        //jTextField.requestFocus();
        setVisible(true);
    }

    void sendMessage(JTextArea jTextArea, JTextField jTextField) {
        jTextArea.append((jTextArea.getDocument().getLength()==0?"":"\n") + jTextField.getText());
        jTextField.setText("");//очищаем поле после отправки сообщения
    }
}

