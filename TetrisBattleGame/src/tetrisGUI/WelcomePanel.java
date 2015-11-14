package tetrisGUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	private JButton play;
	private JLabel welcome;
	private String username;

	public WelcomePanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.outerPanelForCardLayout = outerPanelForCardLayout; 
		this.cardLayout = cardLayout;
		this.username = username;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	public void initializeVariables() {
		play = new JButton("Play");
		welcome = new JLabel("Welcome " + username + "!");
	}
	
	public void createGUI() {
		add(play);
		add(welcome);
	}
	
	public void addActionAdapters() {
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				cardLayout.show(outerPanelForCardLayout, "hostJoinPanel");
			}
		});
	}

}
