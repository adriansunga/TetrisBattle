package tetrisGUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import game.GameManager;
import game.PiecePlacer;
import networking.TetrisClient;

public class TetrisBattlePanel extends JPanel {

	private static final long serialVersionUID = -3365559486379271363L;

	Font font = new Font("Tetris Mania Type", Font.BOLD, 20);

	// left Side
	private JPanel LeftPanel;
	private String username;
	private JLabel tetrisTitle;
	private JPanel sideBarPanel;
	private JPanel scorePanel;
	private JLabel scoreLabel;
	private JLabel scoreTextLabel;
	private int score = 0;
	private JPanel nextPeicePanel;
	private JLabel nextPieceTextLabel;
	private NextPiecePanel nextImage;
	private JPanel jp;
	private JPanel centerPanel;
	private BoardPanel boardPanel;
	private JPanel linesSentPanel;
	private JLabel linesSentLabel;
	private JLabel linesSentTextLabel;
	private int linesSent = 0;

	// right side
	private JPanel RightPanel;
	private String oppUsername;
	private JLabel oppTetrisTitle;
	private JPanel oppSideBarPanel;
	private JPanel oppScorePanel;
	private JLabel oppScoreLabel;
	private JLabel oppScoreTextLabel;
	private int oppScore = 0;
	private JPanel oppNextPeicePanel;
	private JLabel oppNextPieceTextLabel;
	private NextPiecePanel oppNextImage;
	private JPanel oppjp;
	private JPanel oppCenterPanel;
	private BoardPanel oppBoardPanel;
	private JPanel oppLinesSentPanel;
	private JLabel oppLinesSentLabel;
	private JLabel oppLinesSentTextLabel;
	private int oppLinesSent = 0;
	private int delay = 500;
	private Image bg;
	private ActionListener scoreLevelUpdater;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	private TetrisClient tc;
	private GameManager gameManager;
	private PiecePlacer piecePlacer;

	// constructor
	public TetrisBattlePanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, TetrisClient tc) {
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		this.username = tc.getUserName();
		this.tc = tc;
		tc.sendMessage("name:" + tc.getUserName());
		initializeVariables();
		createGUI();
		addActionAdapters();
		gameManager.startGame();

		setKeyBindings();
		new Timer(delay, scoreLevelUpdater).start();

	}

	private void setKeyBindings() {
		ActionMap actionMap = getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);

		String vkLeft = "VK_LEFT";
		String vkRight = "VK_RIGHT";
		String vkUp = "VK_UP";
		String vkDown = "VK_DOWN";
		String drop = "drop";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), vkRight);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "PlayerDownRelease");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), drop);

		actionMap.put(vkLeft, new KeyAction(vkLeft));
		actionMap.put(vkRight, new KeyAction(vkRight));
		actionMap.put(vkUp, new KeyAction(vkUp));
		actionMap.put(vkDown, new KeyAction(vkDown));
		actionMap.put(drop, new KeyAction(drop));
		actionMap.put("PlayerDownRelease", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("down release");
				gameManager.zoomDown(1000);
			}
		});

	}

	private class KeyAction extends AbstractAction {
		public KeyAction(String actionCommand) {
			putValue(ACTION_COMMAND_KEY, actionCommand);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvt) {
			System.out.println("key action performed..");
			String keyCode = actionEvt.getActionCommand();
			switch (keyCode) {
			case "VK_UP":
				System.out.println("up key pressed");
				gameManager.rotatePiece();
				break;
			case "VK_DOWN":
				System.out.println("down key pressed");
				gameManager.zoomDown(50);
				break;
			case "VK_LEFT":
				System.out.println("left key pressed");
				gameManager.move("left");
				break;
			case "VK_RIGHT":
				System.out.println("right key pressed");
				gameManager.move("right");
				break;
			case "PlayerRightRelease":
				System.out.println("Key released");
				gameManager.zoomDown(1000);
				break;
			case "drop":
				System.out.println("space key pressed");
				gameManager.zoomDown(0); // TODO: should i make this instantaneous?
				break;
			}
		}
	}

	private void initializeVariables() {

		jp = new JPanel();
		jp.setPreferredSize(new Dimension(20, 20));

		oppjp = new JPanel();
		oppjp.setPreferredSize(new Dimension(20, 20));

		ImageIcon image2 = new ImageIcon("images/backgrounds/GuestBackground.jpg");
		bg = image2.getImage();

		LeftPanel = new JPanel();
		LeftPanel.setLayout(new BorderLayout());

		piecePlacer = new PiecePlacer();
		nextImage = new NextPiecePanel(piecePlacer);
		gameManager = new GameManager(piecePlacer, tc, nextImage);
		gameManager.setTwoPlayer(true);
		piecePlacer.setGameManager(gameManager);
		piecePlacer.initializeNextPiece();
		tc.setGameManager(gameManager);

		// north
		tetrisTitle = new JLabel("User: " + username, JLabel.CENTER);
		tetrisTitle.setFont(font);

		// center
		centerPanel = new JPanel();
		boardPanel = new BoardPanel(gameManager);

		// west
		sideBarPanel = new JPanel();

		sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));

		scorePanel = new JPanel();

		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel("Score: ");
		scoreLabel.setFont(font);
		// TODO make it get score from game manager
		scoreTextLabel = new JLabel(Integer.toString(score));
		scoreTextLabel.setFont(font);
		scoreTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		linesSentPanel = new JPanel();
		linesSentPanel.setLayout(new BoxLayout(linesSentPanel, BoxLayout.Y_AXIS));
		linesSentLabel = new JLabel("Lines Sent:");
		linesSentLabel.setFont(font);
		linesSentTextLabel = new JLabel(Integer.toString(linesSent));
		linesSentTextLabel.setFont(font);
		linesSentLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nextPeicePanel = new JPanel();
		nextPeicePanel.setLayout(new BoxLayout(nextPeicePanel, BoxLayout.Y_AXIS));
		nextPieceTextLabel = new JLabel("Next Piece");
		nextPieceTextLabel.setFont(font);
		nextPieceTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		RightPanel = new JPanel();

		RightPanel.setLayout(new BorderLayout());

		// north
		oppUsername = tc.getOpponentName();
		oppTetrisTitle = new JLabel("Opponent: " + oppUsername, JLabel.CENTER);
		oppTetrisTitle.setFont(font);

		// center
		oppCenterPanel = new JPanel();

		// TODO
		// delete this, this needs to be passed in by networking
		PiecePlacer DELETEpiecePlacer = new PiecePlacer();
		NextPiecePanel DELETEnextPiecePanel = new NextPiecePanel(DELETEpiecePlacer);
		GameManager DELETEgameManager = new GameManager(DELETEpiecePlacer, DELETEnextPiecePanel);
		oppBoardPanel = new BoardPanel(DELETEgameManager);
		gameManager.setOppBoardPanel(oppBoardPanel);
		// TODO

		// west
		oppSideBarPanel = new JPanel();

		oppSideBarPanel.setLayout(new BoxLayout(oppSideBarPanel, BoxLayout.Y_AXIS));

		oppScorePanel = new JPanel();
		oppScorePanel.setLayout(new BoxLayout(oppScorePanel, BoxLayout.Y_AXIS));
		oppScoreLabel = new JLabel("Score: ");
		oppScoreLabel.setFont(font);
		// TODO make it get score from game manager
		oppScoreTextLabel = new JLabel(Integer.toString(oppScore));
		oppScoreTextLabel.setFont(font);
		oppScoreTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		oppLinesSentPanel = new JPanel();
		oppLinesSentPanel.setLayout(new BoxLayout(oppLinesSentPanel, BoxLayout.Y_AXIS));
		oppLinesSentLabel = new JLabel("Lines Sent:");
		oppLinesSentLabel.setFont(font);
		oppLinesSentTextLabel = new JLabel(Integer.toString(oppLinesSent));
		oppLinesSentTextLabel.setFont(font);

		oppNextPeicePanel = new JPanel();
		oppNextPeicePanel.setLayout(new BoxLayout(oppNextPeicePanel, BoxLayout.Y_AXIS));
		oppNextPieceTextLabel = new JLabel("Next Piece");
		oppNextPieceTextLabel.setFont(font);
		oppNextImage = new NextPiecePanel(piecePlacer);
		oppNextPieceTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

	}

	private void createGUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		LeftPanel.setLayout(new BorderLayout());

		LeftPanel.setOpaque(false);

		// north
		LeftPanel.add(tetrisTitle, BorderLayout.NORTH);

		JPanel boxLayoutForLeftPanel = new JPanel();
		boxLayoutForLeftPanel.setLayout(new BoxLayout(boxLayoutForLeftPanel, BoxLayout.X_AXIS));
		boxLayoutForLeftPanel.setOpaque(false);
		// boxLayoutForLeftPanel.setBorder(BorderFactory.createMatteBorder(5, 5,
		// 5, 5, Color.MAGENTA));
		// west
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.setOpaque(false);
		sideBarPanel.setPreferredSize(new Dimension(200, 600));
		scorePanel.setOpaque(false);
		JPanel jpp = new JPanel();
		jpp.setLayout(new BoxLayout(jpp, BoxLayout.X_AXIS));
		jpp.add(scoreLabel);
		jpp.add(scoreTextLabel);
		jpp.setOpaque(false);
		jpp.setAlignmentY(CENTER_ALIGNMENT);
		JLabel jt = new JLabel(" ");
		jt.setFont(font);
		scorePanel.add(jt);
		scorePanel.add(jpp);
		scorePanel.setPreferredSize(new Dimension(200, 75));
		scorePanel.setMaximumSize(new Dimension(200, 75));
		scorePanel.setMinimumSize(new Dimension(200, 75));
		scorePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		scorePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.PINK));
		sideBarPanel.add(scorePanel);
		sideBarPanel.add(Box.createGlue());

		//
		nextPeicePanel.setOpaque(false);
		nextPeicePanel.add(nextPieceTextLabel);
		jp.add(nextImage);
		jp.setOpaque(false);
		nextPeicePanel.add(jp);
		nextPeicePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextPeicePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.MAGENTA));
		nextPeicePanel.setPreferredSize(new Dimension(200, 150));
		nextPeicePanel.setMaximumSize(new Dimension(200, 150));
		nextPeicePanel.setMinimumSize(new Dimension(200, 150));
		sideBarPanel.add(nextPeicePanel);

		//
		sideBarPanel.add(Box.createGlue());
		linesSentPanel.setOpaque(false);
		JPanel jppp = new JPanel();
		jppp.setOpaque(false);
		jppp.setLayout(new BoxLayout(jppp, BoxLayout.X_AXIS));
		jppp.add(linesSentLabel);
		jppp.add(linesSentTextLabel);
		JLabel jt4 = new JLabel(" ");
		jt4.setFont(font);
		linesSentPanel.add(jt4);
		linesSentPanel.add(jppp);
		linesSentPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		linesSentPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
		linesSentPanel.setPreferredSize(new Dimension(200, 75));
		linesSentPanel.setMaximumSize(new Dimension(200, 75));
		linesSentPanel.setMinimumSize(new Dimension(200, 75));
		sideBarPanel.add(linesSentPanel);

		// sideBarPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
		// Color.MAGENTA));
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());

		// LeftPanel.add(sideBarPanel, BorderLayout.WEST);
		boxLayoutForLeftPanel.add(sideBarPanel);

		// center
		// centerPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
		// Color.MAGENTA));
		centerPanel.add(boardPanel);
		centerPanel.setOpaque(false);
		// LeftPanel.add(centerPanel, BorderLayout.CENTER);
		boxLayoutForLeftPanel.add(centerPanel);

		LeftPanel.add(boxLayoutForLeftPanel, BorderLayout.CENTER);

		add(LeftPanel);

		JPanel boxLayoutForRightPanel = new JPanel();
		boxLayoutForRightPanel.setLayout(new BoxLayout(boxLayoutForRightPanel, BoxLayout.X_AXIS));
		boxLayoutForRightPanel.setOpaque(false);

		RightPanel.setLayout(new BorderLayout());
		RightPanel.setOpaque(false);
		RightPanel.add(oppTetrisTitle, BorderLayout.NORTH);
		oppSideBarPanel.setOpaque(false);
		oppSideBarPanel.setPreferredSize(new Dimension(200, 600));
		oppSideBarPanel.add(Box.createGlue());

		oppScorePanel.setOpaque(false);

		JPanel jpp1 = new JPanel();
		jpp1.setLayout(new BoxLayout(jpp1, BoxLayout.X_AXIS));
		jpp1.setOpaque(false);
		jpp1.add(oppScoreLabel);
		jpp1.add(oppScoreTextLabel);
		JLabel jt2 = new JLabel(" ");
		jt2.setFont(font);
		oppScorePanel.add(jt2);

		oppScorePanel.add(jpp1);
		oppScorePanel.setPreferredSize(new Dimension(200, 75));
		oppScorePanel.setMaximumSize(new Dimension(200, 75));
		oppScorePanel.setMinimumSize(new Dimension(200, 75));
		oppScorePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.PINK));
		oppScorePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		oppSideBarPanel.add(oppScorePanel);
		oppSideBarPanel.add(Box.createGlue());

		oppNextPeicePanel.setOpaque(false);
		oppNextPeicePanel.add(oppNextPieceTextLabel);
		oppjp.add(oppNextImage);
		oppjp.setOpaque(false);
		oppNextPeicePanel.add(oppjp);
		oppNextPeicePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.MAGENTA));
		oppNextPeicePanel.setPreferredSize(new Dimension(200, 150));
		oppNextPeicePanel.setMaximumSize(new Dimension(200, 150));
		oppNextPeicePanel.setMinimumSize(new Dimension(200, 150));
		oppNextPeicePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		oppSideBarPanel.add(oppNextPeicePanel);
		oppSideBarPanel.add(Box.createGlue());

		oppLinesSentPanel.setOpaque(false);
		JPanel jppp1 = new JPanel();
		jppp1.setOpaque(false);
		jppp1.setLayout(new BoxLayout(jppp1, BoxLayout.X_AXIS));
		jppp1.add(oppLinesSentLabel);
		jppp1.add(oppLinesSentTextLabel);

		JLabel jt3 = new JLabel(" ");
		jt3.setFont(font);
		oppLinesSentPanel.add(jt3);

		oppLinesSentPanel.add(jppp1);
		oppLinesSentPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
		oppLinesSentPanel.setPreferredSize(new Dimension(200, 75));
		oppLinesSentPanel.setMaximumSize(new Dimension(200, 75));
		oppLinesSentPanel.setMinimumSize(new Dimension(200, 75));
		oppLinesSentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		oppSideBarPanel.add(oppLinesSentPanel);
		// oppSideBarPanel.add(Box.createGlue());
		//
		//
		oppSideBarPanel.add(Box.createGlue());
		oppSideBarPanel.add(Box.createGlue());
		oppSideBarPanel.add(Box.createGlue());

		// RightPanel.add(oppSideBarPanel, BorderLayout.EAST);

		// center
		oppCenterPanel.setOpaque(false);
		oppCenterPanel.add(oppBoardPanel);
		// RightPanel.add(oppCenterPanel, BorderLayout.CENTER);

		boxLayoutForRightPanel.add(oppCenterPanel);
		boxLayoutForRightPanel.add(oppSideBarPanel);

		RightPanel.add(boxLayoutForRightPanel);

		add(RightPanel);
		try {
			new PlayMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addActionAdapters() {
		scoreLevelUpdater = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	              scoreTextLabel.setText("" + (gameManager.getLinesCleared() - gameManager.getGarbageLinesReceived()));
	              linesSentTextLabel.setText("" + gameManager.getLinesCleared());
	              oppScoreTextLabel.setText("" + (gameManager.getGarbageLinesReceived() - gameManager.getGarbageLinesSent()));
	              oppLinesSentTextLabel.setText("" + gameManager.getGarbageLinesReceived());
	          }
	      };

	}
	
	public void endGame() {
		cardLayout.show(outerPanelForCardLayout, "loginPanel");
		//pm.stop();
		//notify others?
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(480, 0, 480, 960);
	}

}