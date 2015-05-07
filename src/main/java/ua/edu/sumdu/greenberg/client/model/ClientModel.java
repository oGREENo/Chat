package ua.edu.sumdu.greenberg.client.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.sumdu.greenberg.client.controller.ClientController;
import ua.edu.sumdu.greenberg.client.controller.ClientMessageThread;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is model.
 */
public class ClientModel {
    private static final Logger log = Logger.getLogger(ClientModel.class);
    private Pattern pattern;
    private Pattern pattern2;
    private Matcher matcher;
    private Matcher matcher2;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private static final String LOCALHOST_PATTERN =
            "localhost";
    private DocumentBuilder builder;
    private ClientController clientController;
    private ArrayList usersList = new ArrayList<String>();
    private ArrayList chatList = new ArrayList<String>();

    /**
     * This method added ClientController.
     * @param clientController - ClientController.
     */
    public void addClientModel(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * This method checks the entered data.
     * @return boolean.
     */
    public boolean validData(String login, String url, int port) {
        if (login.length() > 0
                && url.length() > 0
                && port > 0 ) {
            if (validIP(url) && validPort(port)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks the entered IP.
     * @return boolean.
     */
    public boolean validIP(String url) {
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        pattern2 = Pattern.compile(LOCALHOST_PATTERN);
        matcher = pattern.matcher(url);
        matcher2 = pattern2.matcher(url);
        return (matcher.matches() || matcher2.matches());
    }

    /**
     * This method checks the entered port.
     * @return boolean.
     */
    public boolean validPort(int port) {
        return ((port >= 1) && (port <= 65355) && (port != 80))? true : false;
    }

    /**
     * This method create new XML.
     * @param nick - nick.
     * @param toNick - toNick.
     * @param action - action.
     * @param text - message.
     * @return
     */
    public Document createXML(String nick, String toNick, String action, String text) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e);
        }
        Document doc=builder.newDocument();
        Element RootElement=doc.createElement("message");
        if (nick != null) {
            Element NameElementNick = doc.createElement("nick");
            NameElementNick.appendChild(doc.createTextNode(nick));
            RootElement.appendChild(NameElementNick);
        } else {
            Element NameElementNick = doc.createElement("nick");
            NameElementNick.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementNick);
        }
        if (toNick != null) {
            Element NameElementToNick = doc.createElement("to_nick");
            NameElementToNick.appendChild(doc.createTextNode(toNick));
            RootElement.appendChild(NameElementToNick);
        } else {
            Element NameElementToNick = doc.createElement("to_nick");
            NameElementToNick.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementToNick);
        }
        if (action != null) {
            Element NameElementAction = doc.createElement("action");
            NameElementAction.appendChild(doc.createTextNode(action));
            RootElement.appendChild(NameElementAction);
        } else {
            Element NameElementAction = doc.createElement("action");
            NameElementAction.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementAction);
        }
        if (text != null) {
            Element NameElementText = doc.createElement("text");
            NameElementText.appendChild(doc.createTextNode(text));
            RootElement.appendChild(NameElementText);
        } else {
            Element NameElementText = doc.createElement("text");
            NameElementText.appendChild(doc.createTextNode(""));
            RootElement.appendChild(NameElementText);
        }
        doc.appendChild(RootElement);
        return doc;
    }

    /**
     * This method read the XML.
     * @param doc - xml.
     */
    public void readMessage(Document doc) {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String toNick = doc.getElementsByTagName("to_nick").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        System.out.println("Client Message : nick - " + nick + ", toNick - " + toNick + ", action - " + action +", text - " + text + ".");
        if (action.equals("GET_USER_LIST")) {
            usersList.add(text);
            clientController.addUserListToModel();
        } else if (action.equals("ADDED_USER")) {
            usersList.add(text);
            clientController.addUserListToModel();
        } else if (action.equals("REMOVE_USER")) {
            usersList.remove(text);
            clientController.addUserListToModel();
        } else if (action.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            chatList.add(dateFormat.format(date) + " " + text);
            clientController.addMessageToChat();
        }
    }

    /**
     * This method returned array a users.
     * @return ArrayList.
     */
    public ArrayList getUsersList() {
        System.out.println("GetUsersList: " + usersList);
        return usersList;
    }

    public ArrayList getChatList() {
        return chatList;
    }
}
