package ua.edu.sumdu.greenberg.client.view;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        JPanel sendPanel = new JPanel(new BorderLayout());
        createSmile(sendPanel);
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
     * @param panel - JPanel.
     */
    private void createSmile(JPanel panel) {
        JPanel smilePanel = new JPanel();
        JButton smile1 = new JButton(new ImageIcon("c:/images/smile1.png"));
        JButton smile2 = new JButton(new ImageIcon("c:/images/smile2.png"));
        JButton smile3 = new JButton(new ImageIcon("c:/images/smile3.png"));
        JButton smile4 = new JButton(new ImageIcon("c:/images/smile4.png"));
        JButton smile5 = new JButton(new ImageIcon("c:/images/smile5.png"));
        JButton smile6 = new JButton(new ImageIcon("c:/images/smile6.png"));
        JButton smile7 = new JButton(new ImageIcon("c:/images/smile7.png"));
        JButton smile8 = new JButton(new ImageIcon("c:/images/smile8.png"));
        JButton smile9 = new JButton(new ImageIcon("c:/images/smile9.png"));
        smile1.setPreferredSize(new Dimension(34, 34));
        smile2.setPreferredSize(new Dimension(34, 34));
        smile3.setPreferredSize(new Dimension(34, 34));
        smile4.setPreferredSize(new Dimension(34, 34));
        smile5.setPreferredSize(new Dimension(34, 34));
        smile6.setPreferredSize(new Dimension(34, 34));
        smile7.setPreferredSize(new Dimension(34, 34));
        smile8.setPreferredSize(new Dimension(34, 34));
        smile9.setPreferredSize(new Dimension(34, 34));
        smile1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" :-) ");
            }
        });
        smile2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" :-D ");
            }
        });
        smile3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" ;-) ");
            }
        });
        smile4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" x-D ");
            }
        });
        smile5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" ;-P ");
            }
        });
        smile6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" :-P ");
            }
        });
        smile7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" 8-) ");
            }
        });
        smile8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" B-) ");
            }
        });
        smile9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSmileToMessage(" :-( ");
            }
        });
        smilePanel.add(smile1, BorderLayout.CENTER);
        smilePanel.add(smile2, BorderLayout.CENTER);
        smilePanel.add(smile3, BorderLayout.CENTER);
        smilePanel.add(smile4, BorderLayout.CENTER);
        smilePanel.add(smile5, BorderLayout.CENTER);
        smilePanel.add(smile6, BorderLayout.CENTER);
        smilePanel.add(smile7, BorderLayout.CENTER);
        smilePanel.add(smile8, BorderLayout.CENTER);
        smilePanel.add(smile9, BorderLayout.CENTER);
        panel.add(smilePanel, BorderLayout.NORTH);
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

    /**
     * This method adds the smile in message.
     *
     * @param smile - smile.
     */
    private void addSmileToMessage(String smile) {
        message.setText(getMessage() + smile);
    }
}