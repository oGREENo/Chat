package ua.edu.sumdu.greenberg.client.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.sumdu.greenberg.client.controller.ClientController;
import ua.edu.sumdu.greenberg.client.controller.ClientMessageThread;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Document createXML(String nick, String to_nick, String action, String text) {
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
        if (to_nick != null) {
            Element NameElementToNick = doc.createElement("to_nick");
            NameElementToNick.appendChild(doc.createTextNode(to_nick));
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

    public void readMessage(Document doc, ClientMessageThread clientThread) {
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String toNick = doc.getElementsByTagName("to_nick").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        System.out.println("Client Message : nick - " + nick + ", toNick - " + toNick + ", action - " + action +", text - " + text + ".");
        if (action.equals("GET_USER_LIST")) {
            usersList.add(text);
        }
    }
    
}
