package lesson4.swing;

import lesson6.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import j3_lesson2.Exceptions.*;


public class LoginDialog extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JButton bnRegistration;
    private Network network;
    private boolean connected;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        network = null;
        connected = false;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");
        bnRegistration = new JButton("Registration");
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    network = new Network("localhost", 7777, (MessageSender) parent);
                    network.authorize(tfUsername.getText(), String.valueOf(pfPassword.getPassword()));
                    connected = true;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Ошибка сети",
                            "Авторизация",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (AuthException ex) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Ошибка авторизации",
                            "Авторизация",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });

        /////////////////////////////////////////////////
        //запустим новый поток, подождем занное время и прервем аутетификацию
        class WaitingForAuthorization extends Thread {
            @Override
            public void run() {
                try {
                    //даем время на авторизацию
                    sleep(Constants.AUTORIZATION_TIMEOUT);
                    if (!connected) {
                        //значит, пользователь не успел аутентифицироваться
                        System.out.printf("Time for authorization is over.");
                        JOptionPane.showMessageDialog(LoginDialog.this,
                                "Время авторизации вышло.",
                                "Авторизация",
                                JOptionPane.ERROR_MESSAGE);
                        LoginDialog.this.dispose();
                        Thread.currentThread().interrupt();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        WaitingForAuthorization waitingForAuthorization = new WaitingForAuthorization();
        waitingForAuthorization.start();

        //////////////////////////////////////////////////

        bp.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        bp.add(bnRegistration);
        bnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPasswordCorrect(String.valueOf(pfPassword.getPassword()))) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Пароль не должен быть пустым!",
                            "Регистрация",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    network = new Network("localhost", 7777, (MessageSender) parent);
                    network.registrateUser(tfUsername.getText(), String.valueOf(pfPassword.getPassword()));
                    connected = true;
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Регистрация прошла успешно!",
                            "Регистрация",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Ошибка сети",
                            "Регистрация",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (NonuniqueUserRegistrationException ex){
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Пользователь с именем "+tfUsername.getText()+" уже зарегистрирован.",
                            "Регистрация",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (RegistrationException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Ошибка регистрации.",
                            "Регистрация",
                            JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private boolean isPasswordCorrect(String password) {
        return  !password.isEmpty();
    }

    public Network getNetwork() {
        return network;
    }

    public boolean isAuthSuccessful() {
        return connected;//network != null;
    }
}
