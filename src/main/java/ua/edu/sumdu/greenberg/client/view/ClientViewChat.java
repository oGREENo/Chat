package ua.edu.sumdu.greenberg.client.view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class creates a chat window.
 */
public class ClientViewChat extends JFrame {
    private JList listMessage;
    private JList userList;
    private JTextField message;
    private JButton sendButton;
    private JButton privateButton;
    private String selectedUser;
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
        listMessage = new JList(new String[]{"Message 1", "Message 2", "Message 3", "Message 4"});
        JScrollPane scroolChat = new JScrollPane(listMessage);
        panel.add(scroolChat, BorderLayout.CENTER);
    }

    /**
     * This method adds elements.
     * @param panel - JPanel.
     */
    private void userList(JPanel panel) {
        userList = new JList(new String[]{"Иванов", "Петров", "Сидоров", "Очень большое имя"});
        userList.addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        Object element = userList.getSelectedValue();
                        setSelectedUser(element.toString());
                    }
                });
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
     * This method gets the name of the selected user.
     * @return name user.
     */
    public String getSelectedUser() {
        return selectedUser;
    }

    /**
     * This method sets the name of the selected user.
     * @param selectedUser - name user.
     */
    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
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
}