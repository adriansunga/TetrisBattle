package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.MySQLDriver;

public class LoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel loginLabel;
	JLabel passwordLabel;
	JTextField loginTF;
	JTextField passwordTF;
	
	//picture for tetris battle logo
	Image tetrisBattleImage;
	
	JButton loginButton;
	JLabel orLabel;
	JButton createUserButton;
	JButton guestButton;
	
	CardLayout cardLayout;
	JPanel outerPanelForCardLayout;
	
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
		passwordTF = new JTextField(20);
		
		//TODO
		Image tetrisBattleImage;
		
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
		add(createUserButton);
		add(guestButton);
		
	}
	
	private void addActionAdapters(){
		
		//login button clicked
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				//TODO check with data base, see if account exists
				//get information from pw and logintf
				//if its okay to log in, make an if statement so that I link them to following page
				MySQLDriver msql = new MySQLDriver();
				msql.connect();
				if(msql.doesExist(loginTF.getText()) && msql.passwordMatches(loginTF.getText(), passwordTF.getText())) {
					System.out.println("user logged in with username: " + loginTF.getText() + " and password: " + passwordTF.getText());
					cardLayout.show(outerPanelForCardLayout, "hostJoinPanel");
				}  else if (!msql.doesExist(loginTF.getText()))
					JOptionPane.showMessageDialog(null, "Username does not exist! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Password does not match username! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);

				msql.stop();
			}
		});
		
		//create user button clicked
		createUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				MySQLDriver msql = new MySQLDriver();
				msql.connect();
				if(msql.doesExist(loginTF.getText())) {
					JOptionPane.showMessageDialog(null, "Username already exists! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
				} else {
					msql.add(loginTF.getText(), passwordTF.getText());
					System.out.println("user created with username: " + loginTF.getText() + " and password: " + passwordTF.getText());
					cardLayout.show(outerPanelForCardLayout, "hostJoinPanel");
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
