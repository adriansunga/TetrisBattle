package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.MySQLDriver;

public class LoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JLabel loginLabel;
	private JLabel passwordLabel;
	private JLabel titleLabel;
	private JTextField loginTF;
	private JPasswordField passwordTF;
	
	private JButton loginButton;
	private JButton createUserButton;
	private JButton guestButton;
	
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	private JPanel tetrisPanel, buttonPanel;
	private JPanel loginPanel, passwordPanel;
	private JPanel guestPanel;
	
	private Image bg;
	ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
	Image img = originalButton.getImage();
	Image newImage = img.getScaledInstance(originalButton.getIconWidth()/(7/2), originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage1 = new ImageIcon(newImage);
	Image newImage2 = img.getScaledInstance(originalButton.getIconWidth()/(5/2), originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage2 = new ImageIcon(newImage2);
	Font font1 = new Font("Tetris Mania Type", Font.BOLD, 25);
	Font font2 = new Font("Tetris Mania Type", Font.BOLD, 30);
	
	//constructor
	public LoginPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout){
		this.outerPanelForCardLayout = outerPanelForCardLayout; 
		this.cardLayout = cardLayout;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		ImageIcon image = new ImageIcon("images/tetrisbattle.png");
		titleLabel = new JLabel(image);
		tetrisPanel = new JPanel();
		buttonPanel = new JPanel();
		loginPanel = new JPanel();
		guestPanel = new JPanel();
		passwordPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		loginLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		loginTF = new JTextField(20);
		passwordTF = new JPasswordField(20);
		loginTF.setFont(font1);
		passwordTF.setFont(font1);
		
		loginButton = new JButton("Login");
		createUserButton = new JButton("Create User");
		guestButton = new JButton("Play Classic Tetris");
		loginButton.setIcon(ButtonImage1);
		loginButton.setHorizontalTextPosition(SwingConstants.CENTER);
		createUserButton.setIcon(ButtonImage1);
		createUserButton.setHorizontalTextPosition(SwingConstants.CENTER);
		guestButton.setIcon(ButtonImage2);
		guestButton.setHorizontalTextPosition(SwingConstants.CENTER);
		guestButton.setPreferredSize(new Dimension(ButtonImage2.getIconWidth(), ButtonImage2.getIconHeight()));
		loginButton.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		createUserButton.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		guestButton.setMaximumSize(new Dimension(ButtonImage2.getIconWidth(), ButtonImage2.getIconHeight()));
		loginButton.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		createUserButton.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		loginLabel.setFont(font1);
		passwordLabel.setFont(font1);
		createUserButton.setFont(font2);
		guestButton.setFont(font2);
		loginButton.setFont(font2);
		
		ImageIcon image2 = new ImageIcon("images/backgrounds/rainbow.png");
		bg = image2.getImage();
	}
	
	private void createGUI(){
		//TODO need to actually format, but button functionality is here
		tetrisPanel.add(titleLabel, BorderLayout.CENTER);
		loginPanel.add(loginLabel);
		loginPanel.add(loginTF);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTF);
		
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(createUserButton);
		buttonPanel.add(Box.createGlue());
		tetrisPanel.setOpaque(false);
		loginPanel.setOpaque(false);
		passwordPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		guestPanel.setOpaque(false);
		add(Box.createGlue());
		add(tetrisPanel);
		add(loginPanel);
		add(passwordPanel);
		add(buttonPanel);
		guestPanel.add(guestButton, BorderLayout.CENTER);
		add(guestPanel);
		add(Box.createGlue());
	}
	
	public String getUserName() {
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
					WelcomePanel welcomePanel = new WelcomePanel(cardLayout, outerPanelForCardLayout, getUserName());
					outerPanelForCardLayout.add(welcomePanel, "welcomePanel");
					cardLayout.show(outerPanelForCardLayout, "welcomePanel");
				}  else if (!msql.doesExist(loginTF.getText())) //username does not exist
					JOptionPane.showMessageDialog(null, "Username does not exist! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
				else if (loginTF.getText().equals("") || passwordTF.getPassword().length == 1) //username does not exist
					JOptionPane.showMessageDialog(null, "Username or password not entered! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
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
				} else if (loginTF.getText().equals("") || passwordTF.getPassword().length == 1) //username does not exist
					JOptionPane.showMessageDialog(null, "Username or password not entered! Try again!", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
				else { //adds the username and password to the database
					//TODO: hash the password that gets stored
					msql.add(loginTF.getText(), passwordTF.getPassword());
					System.out.println("user created with username: " + loginTF.getText() + " and password: " + new String(passwordTF.getPassword()));
					WelcomePanel welcomePanel = new WelcomePanel(cardLayout, outerPanelForCardLayout, getUserName());
					outerPanelForCardLayout.add(welcomePanel, "welcomePanel");
					cardLayout.show(outerPanelForCardLayout, "welcomePanel");
				}
				msql.stop();
			}
		});
		
		//guest button clicked
		guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				GuestTetrisPanel guestTetrisPanel = new GuestTetrisPanel(cardLayout, outerPanelForCardLayout);
				outerPanelForCardLayout.add(guestTetrisPanel, "guestTetrisPanel");
				cardLayout.show(outerPanelForCardLayout, "guestTetrisPanel");
			}
		});	
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}
