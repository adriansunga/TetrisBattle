package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import tetrisGUI.HelpMenu;
import tetrisGUI.ScoresList;

public class TetrisClientWindow extends JFrame{
	private static final long serialVersionUID = 123456789;
	//will be a set size for the entire window. Both mix and max are the same
	private final static Dimension minSize = new Dimension(960,640);
	private final static Dimension maxSize = new Dimension(960,640);
	private JMenuItem help, scores;
	private JPanel outerPanelForCardLayout;
	private CardLayout cardLayout;
	
	public TetrisClientWindow(){
		super("Tetris Battle");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		setLocationRelativeTo(null);
		createHelpMenu();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		createOuterPanel();
		add(outerPanelForCardLayout);
	}
	
	public void createOuterPanel() {
		outerPanelForCardLayout = new JPanel();
		cardLayout = new CardLayout();
		outerPanelForCardLayout.setLayout(cardLayout);

		HostJoinPanel hostJoinPanel = new HostJoinPanel(cardLayout, outerPanelForCardLayout);
		LoginPanel loginPanel = new LoginPanel(cardLayout, outerPanelForCardLayout);
		WelcomePanel welcomePanel = new WelcomePanel(cardLayout, outerPanelForCardLayout, loginPanel.getUser());
		HostPanel hostPanel = new HostPanel(cardLayout, outerPanelForCardLayout);
		JoinPanel joinPanel = new JoinPanel(cardLayout, outerPanelForCardLayout);
		TetrisBattlePanel tetrisBattlePanel = new TetrisBattlePanel(cardLayout, outerPanelForCardLayout);
		GuestTetrisPanel guestTetrisPanel = new GuestTetrisPanel(cardLayout, outerPanelForCardLayout);
		
		outerPanelForCardLayout.add(loginPanel, "loginPanel");
		outerPanelForCardLayout.add(welcomePanel, "welcomePanel");
		outerPanelForCardLayout.add(hostJoinPanel, "hostJoinPanel");
		outerPanelForCardLayout.add(hostPanel, "hostPanel");
		outerPanelForCardLayout.add(joinPanel, "joinPanel");
		outerPanelForCardLayout.add(tetrisBattlePanel, "tetrisBattlePanel");
		outerPanelForCardLayout.add(guestTetrisPanel, "guestTetrisPanel");
	}
	
	public void createHelpMenu(){
		JMenuBar mb = new JMenuBar();
		help = new JMenuItem("Help");
		scores = new JMenuItem("Top Scores");
		mb.add(help);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		mb.add(scores);
		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		setJMenuBar(mb);
		addMenuEvents();
	}
	
	public void addMenuEvents() {
		scores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new ScoresList().setVisible(true);
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new HelpMenu().setVisible(true);
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	System.out.println("Main program started in TetrisClientWindow");
				new TetrisClientWindow();
		    }
		});
	}
	
}
