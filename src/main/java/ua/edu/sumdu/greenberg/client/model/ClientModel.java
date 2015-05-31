package ua.edu.sumdu.greenberg.client.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.sumdu.greenberg.client.controller.ClientController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        return (login.length() > 0 && url.length() > 0 && port > 0
                && validIP(url) && validPort(port));
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
     * @return Document.
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

        Element NameElementNick = doc.createElement("nick");
        if (nick != null) NameElementNick.appendChild(doc.createTextNode(nick));
        else NameElementNick.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementNick);

        Element NameElementToNick = doc.createElement("to_nick");
        if (toNick != null) NameElementToNick.appendChild(doc.createTextNode(toNick));
        else NameElementToNick.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementToNick);

        Element NameElementAction = doc.createElement("action");
        if (action != null) NameElementAction.appendChild(doc.createTextNode(action));
        else NameElementAction.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementAction);

        Element NameElementText = doc.createElement("text");
        if (text != null) NameElementText.appendChild(doc.createTextNode(text));
        else NameElementText.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementText);

        doc.appendChild(RootElement);
        return doc;
    }

    /**
     * This method read the XML.
     * @param doc - xml.
     */
    public void readMessage(Document doc) {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        if (action.equals("GET_USER_LIST")) usersList.add(text);
        if (action.equals("ADDED_USER")) usersList.add(text);
        if (action.equals("REMOVE_USER")) usersList.remove(text);
        if (action.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            chatList.add(dateFormat.format(date) + " " + text);
            clientController.addMessageToChat();
        }
        clientController.addUserListToModel();
    }

    /**
     * This method returned array a users.
     * @return ArrayList.
     */
    public ArrayList getUsersList() {
        return usersList;
    }

    /**
     * This method returned array a messages.
     * @return ArrayList.
     */
    public ArrayList getChatList() {
        return chatList;
    }
}