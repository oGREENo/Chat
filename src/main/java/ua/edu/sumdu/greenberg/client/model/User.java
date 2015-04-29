package ua.edu.sumdu.greenberg.client.model;

/**
 * This class is the client user.
 */
public class User {
    private String name;
    private String url;
    private int port;

    /**
     * This is the constructor.
     * @param name - name.
     * @param url - address url.
     * @param port - number port.
     */
    public User(String name, String url, int port) {
        if (name != null || url != null || port < 0) {
            setName(name);
            setUrl(url);
            setPort(port);
        } else throw new IllegalArgumentException("Name or URL = null. Or port < 0");

    }

    /**
     * This method sets a name.
     * @param name - name.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * This method gets a name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets a URL.
     * @param url - URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method gets a URL.
     * @return URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method sets a port.
     * @param port - port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * This method gets a port.
     * @return port.
     */
    public int getPort() {
        return port;
    }
}
