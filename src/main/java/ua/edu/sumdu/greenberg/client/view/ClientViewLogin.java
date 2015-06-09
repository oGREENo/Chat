package ua.edu.sumdu.greenberg.client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * This class is created login frame.
 */
public class ClientViewLogin extends JFrame {
    private static final long serialVersionUID = 101L;
    private JTextField textLogin;
    private JTextField textURL;
    private JTextField textPort;
    private JButton jLoginButton;

    /**
     * This is constructor a ClientViewLogin
     */
    public ClientViewLogin() {
        super("Connect to Server");
        setLocationRelativeTo(null);
        createClientName();
        this.setSize(220, 180);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * This method adds elements to the form.
     */
    private void createClientName() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        JPanel clientLoginPanel = new JPanel();
        this.add(clientLoginPanel, new GridBagConstraints(0, 0, 3, 2, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        inLoginComponents(clientLoginPanel);
    }

    /**
     * This method adds elements to the form.
     *
     * @param panel - JPanel
     */
    private void inLoginComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        JLabel nickName = new JLabel("Nick name:");
        panel.add(nickName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        textLogin = new JTextField();
        panel.add(textLogin, new GridBagConstraints(1, 0, 3, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));
        textLogin.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (textLogin.getText().length() >= 12) {
                    e.consume();
                }
            }
            public void keyPressed(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (vChar == KeyEvent.VK_ENTER) {
                    jLoginButton.doClick();
                }
            }
        });
        JLabel url = new JLabel("URL:");
        panel.add(url, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        textURL = new JTextField("127.0.0.1");
        panel.add(textURL, new GridBagConstraints(1, 1, 3, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));
        textURL.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (textURL.getText().length() >= 15) {
                    e.consume();
                }
            }
            public void keyPressed(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (vChar == KeyEvent.VK_ENTER) {
                    jLoginButton.doClick();
                }
            }
        });
        JLabel port = new JLabel("Port:");
        panel.add(port, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        textPort = new JTextField("12345");
        textPort.setEnabled(false);
        panel.add(textPort, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));

        jLoginButton = new JButton("Login");
        panel.add(jLoginButton, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 0, 0));
        JButton jCancel = new JButton("Cancel");
        panel.add(jCancel, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.EAST,
                GridBagConstraints.HORIZONTAL, new Insets(10, 35, 0, 0), 0, 0));
        jCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * This method gets a name.
     *
     * @return name.
     */
    public String getName() {
        return textLogin.getText();
    }

    /**
     * This method gets a URL.
     *
     * @return URL.
     */
    public String getUrl() {
        return textURL.getText();
    }

    /**
     * This method gets a port.
     *
     * @return port.
     */
    public int getPort() {
        return Integer.valueOf(textPort.getText());
    }

    public void clickLogin(ActionListener listener) {
        jLoginButton.addActionListener(listener);
    }

    /**
     * This method shows an informational message.
     */
    public void loginBusyMessage() {
        JOptionPane.showMessageDialog(null, "This login is already in use.");
    }
}