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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import library.FontLibrary;

public class HostJoinPanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;
	private JLabel titleLabel;
	private JButton hostButton;
	private JButton joinButton;
	private String username;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout, tetrisPanel, buttonPanel;
	private Image bg;
	//Font font = new Font("Tetris Mania Type", Font.BOLD, 30);
	Font font = FontLibrary.getFont("fonts/Tetris_Mania_Type.ttf", Font.BOLD, 30);
	ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
	Image img = originalButton.getImage();
	Image newImage = img.getScaledInstance(originalButton.getIconWidth()/4, originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage1 = new ImageIcon(newImage);
	ImageIcon originalButton1 = new ImageIcon("images/tetrisbattle.png");
	Image img1 = originalButton1.getImage();
	Image newImage1 = img1.getScaledInstance(originalButton1.getIconWidth()*2, originalButton1.getIconHeight()*2, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage11 = new ImageIcon(newImage1);
	
	//constructor
	public HostJoinPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.username = username;
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		titleLabel = new JLabel(ButtonImage11);
		tetrisPanel = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		hostButton = new JButton("Host");
		joinButton = new JButton("Join");
		hostButton.setFont(font);
		joinButton.setFont(font);
		hostButton.setIcon(ButtonImage1);
		hostButton.setHorizontalTextPosition(SwingConstants.CENTER);
		hostButton.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		hostButton.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		joinButton.setIcon(ButtonImage1);
		joinButton.setHorizontalTextPosition(SwingConstants.CENTER);
		joinButton.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		joinButton.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		ImageIcon image2 = new ImageIcon("images/backgrounds/vs1.jpg");
		bg = image2.getImage();
		buttonPanel.setOpaque(false);
	}
	
	private void createGUI(){
		//TODO need to actually format, but button functionality is here
		tetrisPanel.add(titleLabel, BorderLayout.CENTER);
		tetrisPanel.setOpaque(false);
		add(Box.createGlue());
		add(tetrisPanel);
		add(Box.createGlue());
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(hostButton);
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(joinButton);
		buttonPanel.add(Box.createGlue());
		add(buttonPanel);
		add(Box.createGlue());
	}
	
	private void addActionAdapters(){
		//host button clicked
		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				HostPanel hostPanel = new HostPanel(cardLayout, outerPanelForCardLayout, username);
				outerPanelForCardLayout.add(hostPanel, "hostPanel");
				cardLayout.show(outerPanelForCardLayout, "hostPanel");
			}
		});
		

		//join button clicked
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				JoinPanel joinPanel = new JoinPanel(cardLayout, outerPanelForCardLayout, username);
				outerPanelForCardLayout.add(joinPanel, "joinPanel");
				cardLayout.show(outerPanelForCardLayout, "joinPanel");
			}
		});
	}
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}