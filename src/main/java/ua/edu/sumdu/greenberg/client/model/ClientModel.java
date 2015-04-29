package ua.edu.sumdu.greenberg.client.model;

import org.apache.log4j.Logger;

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
}
