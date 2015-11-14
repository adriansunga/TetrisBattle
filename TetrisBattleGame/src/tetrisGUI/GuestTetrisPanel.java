package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuestTetrisPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -585498416770344734L;
	private JLabel tetrisLabel;
	private JPanel sideBarPanel;
	private JPanel scorePanel;
	private JPanel nextPeicePanel;
	private JLabel scoreLabel;
	private JLabel scoreTextLabel;
	private JLabel nextPieceTextLabel;
	private Image nextPieceImage;
	private BoardPanel boardPanel;
	
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	public GuestTetrisPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout){
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		boardPanel = new BoardPanel();
	}
	
	private void createGUI(){
		add(new JLabel("Top"));
		add(boardPanel);
		add(new JLabel("Bottom"));
	}
	
	private void addActionAdapters(){
		
	}
	
}
