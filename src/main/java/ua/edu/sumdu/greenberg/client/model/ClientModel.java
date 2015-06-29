package ua.edu.sumdu.greenberg.client.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.sumdu.greenberg.client.controller.ClientController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This class is model.
 */
public class ClientModel {
    private static final Logger log = Logger.getLogger(ClientModel.class);
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private static final String LOCALHOST_PATTERN =
            "localhost";
    private DocumentBuilder builder;
    private ClientController clientController;
    private ArrayList<String> usersList = new ArrayList<String>();
    private ArrayList<String> chatList = new ArrayList<String>();
    private ArrayList<String> arrChat = new ArrayList<String>();

    /**
     * This method added ClientController.
     *
     * @param clientController - ClientController.
     */
    public void addClientModel(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * This method checks the entered data.
     *
     * @return boolean.
     */
    public boolean validData(String login, String url, int port) {
        return (login.length() > 0 && url.length() > 0 && port > 0
                && validIP(url) && validPort(port));
    }

    /**
     * This method checks the entered IP.
     *
     * @return boolean.
     */
    public boolean validIP(String url) {
        return (Pattern.compile(IPADDRESS_PATTERN).matcher(url).matches()
                || Pattern.compile(LOCALHOST_PATTERN).matcher(url).matches());
    }

    /**
     * This method checks the entered port.
     *
     * @return boolean.
     */
    public boolean validPort(int port) {
        return ((port >= 1) && (port <= 65355) && (port != 80)) ? true : false;
    }

    /**
     * This method create new XML.
     *
     * @param nick   - nick.
     * @param toNick - toNick.
     * @param action - action.
     * @param text   - message.
     * @return Document.
     */
    public Document createXML(String nick, String toNick, String action, String text) {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e);
        }
        Document doc = builder.newDocument();
        Element RootElement = doc.createElement("message");

        Element NameElementNick = doc.createElement("nick");
        if (nick != null) {
            NameElementNick.appendChild(doc.createTextNode(nick));
        } else NameElementNick.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementNick);

        Element NameElementToNick = doc.createElement("toNick");
        if (toNick != null) {
            NameElementToNick.appendChild(doc.createTextNode(toNick));
        } else NameElementToNick.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementToNick);

        Element NameElementAction = doc.createElement("action");
        if (action != null) {
            NameElementAction.appendChild(doc.createTextNode(action));
        } else NameElementAction.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementAction);

        Element NameElementText = doc.createElement("text");
        if (text != null) {
            NameElementText.appendChild(doc.createTextNode(text));
        } else NameElementText.appendChild(doc.createTextNode(""));
        RootElement.appendChild(NameElementText);

        doc.appendChild(RootElement);
        return doc;
    }

    /**
     * This method read the XML.
     *
     * @param doc - xml.
     */
    public void readMessage(Document doc) {
        String nick = doc.getElementsByTagName("nick").item(0).getTextContent();
        String toNick = doc.getElementsByTagName("toNick").item(0).getTextContent();
        String action = doc.getElementsByTagName("action").item(0).getTextContent();
        String text = doc.getElementsByTagName("text").item(0).getTextContent();
        if (action.equals("CHECK_LOGIN") && text.equals("OK")) {
            clientController.openChatFrame(nick);
        } else if (action.equals("CHECK_LOGIN") && text.equals("BUSY")) {
            clientController.startLoginBusyMessage();
        }
        if (action.equals("GET_USER_LIST")) {
            usersList.add(text);
        }
        if (action.equals("ADDED_USER")) {
            usersList.add(text);
        }
        if (action.equals("REMOVE_USER")) {
            usersList.remove(text);
        }
        if (action.equals("WELCOME") || action.equals("BYE")) {
            createMessage(text, "red");
        }
        if (action.isEmpty()) {
            if (toNick.isEmpty()) {
                if (nick.equals(clientController.getName())) {
                    createMessage(text, "blue");
                } else {
                    createMessage(text, "black");
                }
            } else {
                createMessage(text, "green");
            }
        }
        clientController.addUserListToModel();
    }

    /**
     * This method creates message.
     *
     * @param text  - text message.
     * @param color - color message.
     */
    private void createMessage(String text, String color) {
        int count = 0;
        StringBuilder str = new StringBuilder("<html><font color=");
        str.append(color);
        str.append(">");
        str.append(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        str.append(" ");
        if (findSmile(text)) {
            while ((text.length() - count) >= 3) {
                if (findSmile(text.substring(count, count + 3))) {
                    str.append("<img src = \"");
                    str.append(getAddressSmile(readSmile(text.substring(count, count + 3))));
                    str.append("\" width = \"20\" height = \"20\">");
                    count += 3;
                } else {
                    str.append(text.substring(count, count + 1));
                    count += 1;
                }
            }
            str.append(text.substring(count));
        } else {
            str.append(text);
        }
        str.append("</font></html>");
        chatList.add(str.toString());
        clientController.addMessageToChat();
    }

    /**
     * This method finds smile in text.
     *
     * @param text - text message.
     * @return true or false.
     */
    private boolean findSmile(String text) {
        return (text.contains(":-)") || text.contains(":-D")
                || text.contains(";-)") || text.contains("x-D")
                || text.contains(";-p") || text.contains(";-P")
                || text.contains(":-p") || text.contains(":-P")
                || text.contains("8-)") || text.contains("B-)")
                || text.contains(":-(")) ? true : false;
    }

    /**
     * This method reads smile in text.
     *
     * @param text - text message.
     * @return smile text.
     */
    private String readSmile(String text) {
        String smile = null;
        if (text.contains(":-)")) {
            smile = ":-)";
        } else if (text.contains(":-D")) {
            smile = ":-D";
        } else if (text.contains(";-)")) {
            smile = ";-)";
        } else if (text.contains("x-D")) {
            smile = "x-D";
        } else if (text.contains(";-P")) {
            smile = ";-P";
        } else if (text.contains(";-p")) {
            smile = ";-p";
        } else if (text.contains(":-P")) {
            smile = ":-P";
        } else if (text.contains(":-p")) {
            smile = ":-p";
        } else if (text.contains("8-)")) {
            smile = "8-)";
        } else if (text.contains("B-)")) {
            smile = "B-)";
        } else if (text.contains(":-(")) {
            smile = ":-(";
        }
        return smile;
    }

    /**
     * This method returns address the smiles.
     *
     * @param smile - smile message.
     * @return address smiles.
     */
    private String getAddressSmile(String smile) {
        String smileAddress = null;
        if (smile.equals(":-)")) {
            smileAddress = "file:///c:/images/smile1.png";
        } else if (smile.equals(":-D")) {
            smileAddress = "file:///c:/images/smile2.png";
        } else if (smile.equals(";-)")) {
            smileAddress = "file:///c:/images/smile3.png";
        } else if (smile.equals("x-D")) {
            smileAddress = "file:///c:/images/smile4.png";
        } else if (smile.equals(";-P")) {
            smileAddress = "file:///c:/images/smile5.png";
        } else if (smile.equals(";-p")) {
            smileAddress = "file:///c:/images/smile5.png";
        } else if (smile.equals(":-P")) {
            smileAddress = "file:///c:/images/smile6.png";
        } else if (smile.equals(":-p")) {
            smileAddress = "file:///c:/images/smile6.png";
        } else if (smile.equals("8-)")) {
            smileAddress = "file:///c:/images/smile7.png";
        } else if (smile.equals("B-)")) {
            smileAddress = "file:///c:/images/smile8.png";
        } else if (smile.equals(":-(")) {
            smileAddress = "file:///c:/images/smile9.png";
        }
        return smileAddress;
    }

    /**
     * This method returned array a users.
     *
     * @return ArrayList.
     */
    public ArrayList<String> getUsersList() {
        return usersList;
    }

    /**
     * This method returned array a messages.
     *
     * @return ArrayList.
     */
    public ArrayList<String> getChatList() {
        arrChat.clear();
        for (int i = chatList.size() - 1; i >= 0; i--) {
            arrChat.add(chatList.get(i));
        }
        return arrChat;
    }
}