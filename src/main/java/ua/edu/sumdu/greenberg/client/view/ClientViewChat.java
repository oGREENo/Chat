package ua.edu.sumdu.greenberg.client.view;

import javax.swing.*;
import java.awt.*;

public class ClientViewChat extends JFrame {
    private JList listMessage;
    private JList userList;

    public ClientViewChat() {
        super("Instant messenger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        messageList(panel);
        userList(panel);
        getContentPane().add(panel);
    }

	private void messageList(JPanel panel) {
        listMessage = new JList();
        panel.add(listMessage, BorderLayout.CENTER);
    }

    private void userList(JPanel panel) {
        userList = new JList();
        panel.add(userList, BorderLayout.EAST);
    }

}
