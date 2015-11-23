package tetrisGUI;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class TetrisClientWindow extends JFrame {
	private static final long serialVersionUID = 123456789;
	// will be a set size for the entire window. Both mix and max are the same
	private final static Dimension minSize = new Dimension(960, 640);
	private final static Dimension maxSize = new Dimension(960, 640);
	private JMenuItem help, scores, report_bugs;
	private JPanel outerPanelForCardLayout;
	private CardLayout cardLayout;

	public TetrisClientWindow() {
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
		LoginPanel loginPanel = new LoginPanel(cardLayout, outerPanelForCardLayout);
		outerPanelForCardLayout.add(loginPanel, "loginPanel");
	}

	public void createHelpMenu() {
		JMenuBar mb = new JMenuBar();
		help = new JMenuItem("Help");
		scores = new JMenuItem("Top Scores");
		report_bugs = new JMenuItem("Report a Bug");

		mb.add(help);
		help.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.Event.CTRL_MASK));
		mb.add(scores);
		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mb.add(report_bugs);
		report_bugs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
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

		report_bugs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Desktop desktop;
				if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
					URI mailto = null;
					try {
						mailto = new URI(
								"mailto:zakeri@usc.edu,hmanuel@usc.edu,asunga@usc.edu,mackraz@usc.edu?subject=Bug%20Report");
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
					try {
						desktop.mail(mailto);
					} catch (IOException e) {

						e.printStackTrace();
					}
				} else {
					throw new RuntimeException("desktop doesn't support mailto");
				}
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
