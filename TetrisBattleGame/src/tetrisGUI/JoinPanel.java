package tetrisGUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import networking.TetrisClient;

public class JoinPanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;
	private JLabel ipLabel;
	private JTextField ipTF;
	private JLabel portLabel;
	private JTextField portTF;
	private JButton continueButton;
	private String username;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	//constructor
	public JoinPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.username = username;
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		ipLabel = new JLabel("IP:");
		ipTF = new JTextField(20);
		portLabel = new JLabel("Port:");
		portTF = new JTextField(5);
		continueButton = new JButton("Continue");
	}
	
	private void createGUI(){
		//TODO need to actually format, but button functionality is here
		add(ipLabel);
		add(ipTF);
		add(portLabel);
		add(portTF);
		add(continueButton);
	}
	
	private void addActionAdapters(){	
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				TetrisClient tc = new TetrisClient(ipTF.getText(), Integer.parseInt(portTF.getText()), username, outerPanelForCardLayout, cardLayout);
				TetrisBattlePanel tetrisBattlePanel = new TetrisBattlePanel(cardLayout, outerPanelForCardLayout, tc);
				outerPanelForCardLayout.add(tetrisBattlePanel, "tetrisBattlePanel");
				cardLayout.show(outerPanelForCardLayout, "tetrisBattlePanel");
			}
		});
	}
}