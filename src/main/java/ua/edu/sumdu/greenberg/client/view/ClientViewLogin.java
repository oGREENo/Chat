package ua.edu.sumdu.greenberg.client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientViewLogin extends JFrame {
	private static final long serialVersionUID = 101L;
	private JTextField textLogin;
	private JTextField textURL;
	private JTextField textPort;
	private JButton jLoginButton;
	private JButton jCancel;
	private String name;
	private String url;
	private int port;
	
	public ClientViewLogin() {
		super("Connect to Server");
		setLocationRelativeTo(null);
		createClientName();
		this.setSize(220,180);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void createClientName() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		JPanel clientLoginPanel = new JPanel();
		this.add(clientLoginPanel, new GridBagConstraints(0, 0, 3, 2, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		inLoginComponents(clientLoginPanel);
	}

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
		});
		
		JLabel port = new JLabel("Port:");
		panel.add(port, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		
		textPort = new JTextField("12345");
		panel.add(textPort, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));
		textPort.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if (textPort.getText().length() >= 5 
						|| !(Character.isDigit(vChar))
						|| (vChar == KeyEvent.VK_BACK_SPACE)
						|| (vChar == KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
		});
		
		jLoginButton = new JButton("Login");
		panel.add(jLoginButton, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 0, 0));
		

		jCancel = new JButton("Cancel");
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
	 * @return name.
	 */
	public String getName() {
		name = textLogin.getText();
		return name;
	}

	/**
	 * This method gets a URL.
	 * @return URL.
	 */
	public String getUrl() {
		url = textURL.getText();
		return url;
	}

	/**
	 * This method gets a port.
	 * @return port.
	 */
	public int getPort() {
		port = Integer.valueOf(textPort.getText());
		return port;
	}
	
	public void clickLogin(ActionListener listener) {
		jLoginButton.addActionListener(listener);
	}

}
