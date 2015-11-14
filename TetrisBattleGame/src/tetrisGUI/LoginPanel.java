package tetrisGUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.MySQLDriver;

public class LoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JLabel loginLabel;
	private JLabel passwordLabel;
	private JTextField loginTF;
	private JPasswordField passwordTF;
	
	private JButton loginButton;
	private JLabel orLabel;
	private JButton createUserButton;
	private JButton guestButton;
	
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	//constructor
	public LoginPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout){
		this.outerPanelForCardLayout = outerPanelForCardLayout; 
		this.cardLayout = cardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		loginLabel = new JLabel("Login: ");
		passwordLabel = new JLabel("Password: ");
		loginTF = new JTextField(20);
		passwordTF = new JPasswordField(20);
		
		loginButton = new JButton("Login");
		orLabel = new JLabel("or");
		createUserButton = new JButton("Create User");
		guestButton = new JButton("Guest");
	}
	
	private void createGUI(){
		
		//TODO need to actually format, but button functionality is here
		add(loginLabel);
		add(loginTF);
		add(passwordLabel);
		add(passwordTF);
		
		add(loginButton);
		add(orLabel);
		add(createUserButton);
		add(guestButton);
		
		
	}
	
	public String getUser() {
		return loginTF.getText();
	}
	
	private void addActionAdapters(){
		
		//login button clicked
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				MySQLDriver msql = new MySQLDriver();
				msql.connect();
				if(msql.doesExist(loginTF.getText()) && msql.passwordMatches(loginTF.getText(), passwordTF.getPassword())) { //it is okay to login
					System.out.println("user logged in with username: " + loginTF.getText() + " and password: " + new String(passwordTF.getPassword()));
					cardLayout.show(outerPanelForCardLayout, "welcomePanel");
				}  else if (!msql.doesExist(loginTF.getText())) //username does not exist
					JOptionPane.showMessageDialog(null, "Username does not exist! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
				else //password does not match username
					JOptionPane.showMessageDialog(null, "Password does not match username! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);

				msql.stop();
			}
		});
		
		//create user button clicked
		createUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				MySQLDriver msql = new MySQLDriver();
				msql.connect();
				if(msql.doesExist(loginTF.getText())) { //username already exists
					JOptionPane.showMessageDialog(null, "Username already exists! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
				} else { //adds the username and password to the database
					//TODO: hash the password that gets stored
					msql.add(loginTF.getText(), passwordTF.getPassword());
					System.out.println("user created with username: " + loginTF.getText() + " and password: " + new String(passwordTF.getPassword()));
					cardLayout.show(outerPanelForCardLayout, "welcomePanel");
				}
				msql.stop();
			}
		});
		
		//guest button clicked
		guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				
				cardLayout.show(outerPanelForCardLayout, "guestTetrisPanel");
			}
		});
		
	}
	
}
