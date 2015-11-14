package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	
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
			
				cardLayout.show(outerPanelForCardLayout, "hostJoinPanel");
				//TODO check with data base, see if account exists
				//get information from pw and logintf
				//if its okay to log in, make an if statement so that I link them to following page
			}
		});
		
		//create user button clicked
		createUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				
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
