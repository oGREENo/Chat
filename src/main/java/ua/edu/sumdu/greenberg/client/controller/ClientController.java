package ua.edu.sumdu.greenberg.client.controller;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import ua.edu.sumdu.greenberg.client.model.ClientModel;
import ua.edu.sumdu.greenberg.client.view.ClientViewChat;
import ua.edu.sumdu.greenberg.client.view.ClientViewLogin;
import ua.edu.sumdu.greenberg.client.model.User;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class is the controller.
 */
public class ClientController {
    private static final Logger log = Logger.getLogger(ClientController.class);
    private ClientViewLogin clientViewLogin;
    private ClientViewChat clientViewChat;
    private ClientModel clientModel;
    private User user;
    private Socket socket;

    /**
     * This is the class constructor.
     *
     * @param clientViewLogin - clientViewLogin.
     * @param clientViewChat  - clientViewChat.
     * @param clientModel     - clientModel.
     */
    public ClientController(ClientViewLogin clientViewLogin,
                            ClientViewChat clientViewChat, ClientModel clientModel) {
        this.clientModel = clientModel;
        clientModel.addClientModel(this);
        this.clientViewLogin = clientViewLogin;
        clientViewLogin.setVisible(true);
        this.clientViewChat = clientViewChat;
        this.clientViewLogin.clickLogin(new ClickLogin());
        this.clientViewChat.clickSend(new ClickSend());
        this.clientViewChat.clickPrivate(new ClickPrivate());
        this.clientViewChat.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    writeInSocket(user.getName(), null, "REMOVE_USER", "Bye");
                } catch (IOException e1) {
                    log.error(e1);
                }
                log.info("The user has left.");
                System.exit(0);
            }
        });
    }

    /**
     * This class is responsible for the connection to the server.
     */
    class ClickLogin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (clientModel.validData(clientViewLogin.getName(), clientViewLogin.getUrl(), clientViewLogin.getPort())) {
                createConnection();
                comCheckLogin(clientViewLogin.getName());
            }
        }
    }

    /**
     * This method start a chat window.
     *
     * @param name - login.
     */
    public void openChatFrame(String name) {
        createConnection();
        createNewUser(name, clientViewLogin.getUrl(), clientViewLogin.getPort());
        comAddUser(name);
        clientViewLogin.setVisible(false);
        clientViewChat.setTitle("Welcome " + name);
        clientViewChat.setVisible(true);
        comGetUserList(name);
    }

    /**
     * This method launches an informational message.
     */
    public void startLoginBusyMessage() {
        clientViewLogin.loginBusyMessage();
    }

    /**
     * This method creates new user.
     *
     * @param name - name.
     * @param url  - url.
     * @param port - port.
     */
    private void createNewUser(String name, String url, int port) {
        user = new User(name, url, port);
    }

    /**
     * This method gives a command to the server.
     *
     * @param name - login.
     */
    private void comCheckLogin(String name) {
        try {
            writeInSocket(name, null, "CHECK_LOGIN", null);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * This method gives a command to the server.
     *
     * @param name - name.
     */
    private void comAddUser(String name) {
        try {
            writeInSocket(name, null, "ADD_USER", "Hello server");
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * This method gives a command to the server.
     *
     * @param name - name.
     */
    private void comGetUserList(String name) {
        try {
            writeInSocket(name, null, "GET_USER_LIST", null);
        } catch (IOException e) {
            log.error(e);
        }
    }


    /**
     * This class is responsible for sending messages.
     */
    class ClickSend implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (!clientViewChat.getMessage().isEmpty()) {
                try {
                    writeInSocket(user.getName(), null, null, clientViewChat.getMessage());
                    clientViewChat.clearSelectedUser();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * This class is responsible for sending private messages.
     */
    class ClickPrivate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (!clientViewChat.getMessage().isEmpty()) {
                if (clientViewChat.selectedUser() && !clientViewChat.getSelectedUser().isEmpty()) {
                    try {
                        writeInSocket(user.getName(), clientViewChat.getSelectedUser(), null, clientViewChat.getMessage());
                        clientViewChat.clearSelectedUser();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
            }
        }

    }


    /**
     * This method create a connection.
     */
    public void createConnection() {
        log.info("ClientUser Name - " + clientViewLogin.getName());
        try {
            socket = new Socket(clientViewLogin.getUrl(), clientViewLogin.getPort());
        } catch (IOException e) {
            log.error(e);
        }
        new Thread(new ClientMessageThread(socket, clientModel)).start();
        log.info("Connect to URL - " + clientViewLogin.getUrl() + " and PORT - " + clientViewLogin.getPort());
    }

    /**
     * This method returns name client.
     *
     * @return name client.
     */
    public String getName() {
        return user.getName();
    }

    /**
     * This method writes in socket a message.
     *
     * @param nick    - name
     * @param to_nick - name
     * @param action  - command
     * @param text    - message
     * @throws IOException
     */
    public void writeInSocket(String nick, String to_nick, String action, String text) throws IOException {
        Document doc = clientModel.createXML(nick, to_nick, action, text);
        try {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc),
                    new StreamResult(socket.getOutputStream()));
            socket.getOutputStream().write('\n');
        } catch (TransformerException e) {
            log.error(e);
        }
    }

    /**
     * This method sends a list of users clientViewChat.
     */
    public void addUserListToModel() {
        clientViewChat.addUsersToList(clientModel.getUsersList());
    }

    /**
     * This method send a list of messages to clientViewChat.
     */
    public void addMessageToChat() {
        clientViewChat.addMessageToChat(clientModel.getChatList());
    }
}