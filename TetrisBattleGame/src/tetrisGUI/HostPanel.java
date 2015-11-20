package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import networking.TetrisServer;

public class HostPanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;
	private JLabel portLabel;
	private JTextField portTF;
	private JButton continueButton;
	private String username;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	private Image bg;
	
	//constructor
	public HostPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.username = username;
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		portLabel = new JLabel("Port:");
		portTF = new JTextField(5);
		continueButton = new JButton("Continue");
	}
	
	private void createGUI(){
		//TODO need to actually format, but button functionality is here
		add(portLabel);
		add(portTF);
		add(continueButton);
		
		ImageIcon image2 = new ImageIcon("images/backgrounds/s.png");
		bg = image2.getImage();
	}
	
	private void addActionAdapters(){
		
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				//TODO: go to a waiting for clients page (pass in as parameters the port)
				TetrisServer ts = new TetrisServer(Integer.parseInt(portTF.getText()), username, outerPanelForCardLayout, cardLayout);
				TetrisBattlePanel tetrisBattlePanel = new TetrisBattlePanel(cardLayout, outerPanelForCardLayout, ts.getTC());
				outerPanelForCardLayout.add(tetrisBattlePanel, "tetrisBattlePanel");
				cardLayout.show(outerPanelForCardLayout, "tetrisBattlePanel");		
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}