package tetrisGUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TetrisBattlePanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;

	
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	//constructor
	public TetrisBattlePanel(CardLayout cardLayout, JPanel outerPanelForCardLayout){
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		
	}
	
	private void createGUI(){
		//TODO need to actually format, but button functionality is here

	}
	
	private void addActionAdapters(){
		
	}
}
