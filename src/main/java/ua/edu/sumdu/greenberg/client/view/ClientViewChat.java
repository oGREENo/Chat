package ua.edu.sumdu.greenberg.client.view;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This class creates a chat window.
 */
public class ClientViewChat extends JFrame {
    private JList listMessage;
    private JList userList;
    private JTextField message;
    private JButton sendButton;
    private JButton privateButton;

    /**
     * This is the class constructor.
     */
    public ClientViewChat() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640, 480);
        this.setMinimumSize(new Dimension(640, 480));
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        messageList(panel);
        userList(panel);
        JPanel sendPanel = new JPanel();
        createMessageSend(sendPanel);
        getContentPane().add(panel);
        getContentPane().add(sendPanel, BorderLayout.SOUTH);
    }

    /**
     * This method adds elements.
     *
     * @param panel - JPanel.
     */
    private void messageList(JPanel panel) {
        listMessage = new JList();
        JScrollPane scrollPane = new JScrollPane(listMessage);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This method adds elements.
     *
     * @param panel - JPanel.
     */
    private void userList(JPanel panel) {
        JPanel userPanel = new JPanel();
        userPanel.setMinimumSize(new Dimension(100, 100));
        userPanel.setPreferredSize(new Dimension(100, 500));
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel uList = new JLabel("List of users : ");
        userList = new JList();
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                privateButton.setEnabled(true);
            }
        });
        JScrollPane scrollUser = new JScrollPane(userList);
        userPanel.add(uList);
        userPanel.add(scrollUser);
        panel.add(userPanel, BorderLayout.EAST);
    }

    /**
     * This method adds elements.
     *
     * @param sendPanel - JPanel.
     */
    private void createMessageSend(JPanel sendPanel) {
        JLabel textMessage = new JLabel("Your message: ");
        message = new JTextField(30);
        message.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (vChar == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }
        });
        sendButton = new JButton("Send");
        privateButton = new JButton("Private");
        privateButton.setEnabled(false);

        sendPanel.add(textMessage, BorderLayout.WEST);
        sendPanel.add(message, BorderLayout.CENTER);
        JPanel panelSendButton = new JPanel();
        panelSendButton.setLayout(new BoxLayout(panelSendButton, BoxLayout.X_AXIS));
        panelSendButton.add(sendButton);
        panelSendButton.add(privateButton);
        sendPanel.add(panelSendButton, BorderLayout.EAST);
    }

    /**
     * This method returns true if the user has selected.
     *
     * @return true or false.
     */
    public boolean selectedUser() {
        return (userList.getSelectedValue() != null);
    }

    /**
     * This method gets the name of the selected user.
     *
     * @return name user.
     */
    public String getSelectedUser() {
        return userList.getSelectedValue().toString();
    }

    /**
     * This method gets a message.
     *
     * @return name.
     */
    public String getMessage() {
        return message.getText();
    }

    /**
     * This actionListener for button with name a "Send".
     *
     * @param listener this listener.
     */
    public void clickSend(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    /**
     * This actionListener for button with name a "Private".
     *
     * @param listener this listener.
     */
    public void clickPrivate(ActionListener listener) {
        privateButton.addActionListener(listener);
    }

    /**
     * This method cleans the chat window.
     */
    public void clearSelectedUser() {
        listMessage.clearSelection();
        userList.clearSelection();
        message.setText("");
        privateButton.setEnabled(false);
    }

    /**
     * This method add user to list.
     *
     * @param usersList - user list.
     */
    public void addUsersToList(ArrayList<String> usersList) {
        userList.setListData(usersList.toArray());
    }

    /**
     * This method add messages to list.
     *
     * @param chatList - array message.
     */
    public void addMessageToChat(ArrayList<String> chatList) {
        listMessage.setListData(chatList.toArray());
    }
}