package tetrisGUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	private JButton play;

	public WelcomePanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.outerPanelForCardLayout = outerPanelForCardLayout; 
		this.cardLayout = cardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	public void initializeVariables() {
		play = new JButton("Play");
	}
	
	public void createGUI() {
		add(play);
	}
	
	public void addActionAdapters() {
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				cardLayout.show(outerPanelForCardLayout, "hostJoinPanel");
			}
		});
	}

}
