package tetrisGUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HostJoinPanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;
	private JLabel tetrisBattleLabel;
	private JButton hostButton;
	private JButton joinButton;
	private String username;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
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
		tetrisBattleLabel = new JLabel("Tetris Battle");
		hostButton = new JButton("Host");
		joinButton = new JButton("Join");
	}
	
	private void createGUI(){
		//TODO need to actually format, but button functionality is here
		add(tetrisBattleLabel);
		add(hostButton);
		add(joinButton);
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
}
