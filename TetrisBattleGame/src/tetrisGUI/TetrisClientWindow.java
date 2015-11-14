package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetrisClientWindow extends JFrame{
	private static final long serialVersionUID = 123456789;
	//will be a set size for the entire window. Both mix and max are the same
	private final static Dimension minSize = new Dimension(960,640);
	private final static Dimension maxSize = new Dimension(960,640);
	
	CardLayout cardLayout;
	JPanel outerPanelForCardLayout;
	
	public TetrisClientWindow(){
		super("Tetris Battle");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
		outerPanelForCardLayout = new JPanel();
		outerPanelForCardLayout.setLayout(cardLayout);
		
		//create all panels to add to cardLayout
		//give them all a name
		
		HostJoinPanel hostJoinPanel = new HostJoinPanel(cardLayout, outerPanelForCardLayout);
		LoginPanel loginPanel = new LoginPanel(cardLayout, outerPanelForCardLayout);
		HostPanel hostPanel = new HostPanel(cardLayout, outerPanelForCardLayout);
		JoinPanel joinPanel = new JoinPanel(cardLayout, outerPanelForCardLayout);
		TetrisBattlePanel tetrisBattlePanel = new TetrisBattlePanel(cardLayout, outerPanelForCardLayout);
		GuestTetrisPanel guestTetrisPanel = new GuestTetrisPanel(cardLayout, outerPanelForCardLayout);
		
		outerPanelForCardLayout.add(loginPanel, "loginPanel");
		outerPanelForCardLayout.add(hostJoinPanel, "hostJoinPanel");
		outerPanelForCardLayout.add(hostPanel, "hostPanel");
		outerPanelForCardLayout.add(joinPanel, "joinPanel");
		outerPanelForCardLayout.add(tetrisBattlePanel, "tetrisBattlePanel");
		outerPanelForCardLayout.add(guestTetrisPanel, "guestTetrisPanel");
		
		

		add(outerPanelForCardLayout);
		
		
	
		
		setVisible(true);
		
		//TODO
		//set default operation close on exit
	}
	
	public static void main(String[] args) {
		System.out.println("Main program started in TetrisClientWindow");
		TetrisClientWindow TCW = new TetrisClientWindow();
	}
	
}
