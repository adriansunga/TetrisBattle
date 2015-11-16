package tetrisGUI;

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

public class WelcomePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout, playPanel;
	
	private Image bg;
	private JButton play;
	private JLabel welcome;
	private String username;
	Font font1 = new Font("Tetris Mania Type", Font.BOLD, 80);
	Font font2 = new Font("Tetris Mania Type", Font.BOLD, 30);
	ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
	Image img = originalButton.getImage();
	Image newImage = img.getScaledInstance(originalButton.getIconWidth()/(7/2), originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage1 = new ImageIcon(newImage);

	public WelcomePanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.outerPanelForCardLayout = outerPanelForCardLayout; 
		this.username = username;
		this.cardLayout = cardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void initializeVariables() {
		play = new JButton("Play");
		playPanel = new JPanel();
		playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.X_AXIS));
		welcome = new JLabel("Welcome " + username + "!");
	}
	
	public void createGUI() {
		play.setFont(font2);
		play.setIcon(ButtonImage1);
		play.setHorizontalTextPosition(SwingConstants.CENTER);
		play.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		play.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		
		welcome.setFont(font1);
		playPanel.setOpaque(false);
		playPanel.add(Box.createGlue());
		playPanel.add(Box.createGlue());
		playPanel.add(play);
		playPanel.add(Box.createGlue());
		add(Box.createGlue());
		add(welcome);
		add(playPanel);
		add(Box.createGlue());
		ImageIcon image2 = new ImageIcon("images/backgrounds/rainbow.png");
		bg = image2.getImage();
	}
	
	public void addActionAdapters() {
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				HostJoinPanel hostJoinPanel = new HostJoinPanel(cardLayout, outerPanelForCardLayout, username);
				outerPanelForCardLayout.add(hostJoinPanel, "hostJoinPanel");
				cardLayout.show(outerPanelForCardLayout, "hostJoinPanel");
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}