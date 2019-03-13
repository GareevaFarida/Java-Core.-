package lesson4.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NicknameChangeDialog extends JDialog {
    private JTextField tfNickname;
    private JLabel lbNickname;
    private JButton bnChange;
    private JButton bnCancel;
    private Network network;
    private JPanel panel;
    private JPanel bnpanel;

    public NicknameChangeDialog(Frame parent, Network network){
        super(parent,"Change nickname");
        this.network = network;
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        panel.setBorder(new LineBorder(Color.GRAY));

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbNickname = new JLabel("New nickname: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbNickname, cs);

        tfNickname = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfNickname, cs);

        bnpanel = new JPanel();
        bnChange = new JButton("Change");
        bnChange.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                network.changeNickname(tfNickname.getText());
                dispose();
            }
        });
        bnpanel.add(bnChange);
        bnCancel = new JButton("Cancel");
        bnCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bnpanel.add(bnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bnpanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}
