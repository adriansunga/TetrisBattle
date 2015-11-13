import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuestTetrisPanel extends JPanel{
	private JLabel tetrisLabel;
	JPanel sideBarPanel;
	JPanel scorePanel;
	JPanel nextPeicePanel;
	JLabel scoreLabel;
	JLabel scoreTextLabel;
	JLabel nextPieceTextLabel;
	Image nextPieceImage;
	BoardPanel boardPanel;
	
	CardLayout cardLayout;
	JPanel outerPanelForCardLayout;
	
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
