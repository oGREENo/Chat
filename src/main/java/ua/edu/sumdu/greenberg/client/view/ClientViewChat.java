package ua.edu.sumdu.greenberg.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private String mess;

    /**
     * This is the class constructor.
     */
    public ClientViewChat() {
        super("Instant messenger");
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
     * @param panel - JPanel.
     */
	private void messageList(JPanel panel) {
        listMessage = new JList();
        JScrollPane scroolChat = new JScrollPane(listMessage);
        panel.add(scroolChat, BorderLayout.CENTER);
    }

    /**
     * This method adds elements.
     * @param panel - JPanel.
     */
    private void userList(JPanel panel) {
        userList = new JList();
        JScrollPane scroolUser = new JScrollPane(userList);
        panel.add(scroolUser, BorderLayout.EAST);
    }

    /**
     * This method adds elements.
     * @param sendPanel - JPanel.
     */
    private void createMessageSend(JPanel sendPanel) {
        JLabel textMessage = new JLabel("Your message: ");
        message = new JTextField(40);
        message.setSize(500, 50);
        sendButton = new JButton("Send");
        privateButton = new JButton("Private");

        sendPanel.add(textMessage, BorderLayout.WEST);
        sendPanel.add(message, BorderLayout.CENTER);
        JPanel panelSendButton = new JPanel();
        panelSendButton.setLayout(new BoxLayout(panelSendButton, BoxLayout.Y_AXIS));
        panelSendButton.add(sendButton);
        panelSendButton.add(privateButton);
        sendPanel.add(panelSendButton, BorderLayout.EAST);
    }

    /**
     * This method returns true if the user has selected.
     * @return true or false.
     */
    public boolean selectedUser() {
        return (userList.getSelectedValue() != null)? true : false;
    }

    /**
     * This method gets the name of the selected user.
     * @return name user.
     */
    public String getSelectedUser() {
        return userList.getSelectedValue().toString();
    }

    /**
     * This method gets a message.
     * @return name.
     */
    public String getMessage() {
        mess = null;
        mess = message.getText();
        return mess;
    }

    /**
     * This actionListener for button with name a "Send".
     * @param listener this listener.
     */
    public void clickSend(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    /**
     * This actionListener for button with name a "Private".
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
        message.setText("");
    }

    /**
     * This method add user to list.
     * @param usersList - user list.
     */
    public void addUsersToList(ArrayList usersList) {
        userList.setListData(usersList.toArray());
    }

    /**
     * This method add messages to list.
     * @param chatList - array message.
     */
    public void addMessageToChat(ArrayList chatList) {
        listMessage.setListData(chatList.toArray());
    }
}