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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.GameManager;
import game.PiecePlacer;
import networking.TetrisClient;

public class TetrisBattlePanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;

	Font font = new Font("Tetris Mania Type", Font.BOLD, 20);
	
	//left Side
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
	
	//right side
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
	
	
	private Image bg;
	
	
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	private TetrisClient tc;
	private GameManager gameManager;
	private PiecePlacer piecePlacer;
	
	//constructor
	public TetrisBattlePanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, TetrisClient tc){
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		this.username = tc.getUserName();
		this.tc = tc;
		tc.sendMessage("name:" + tc.getUserName());
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		
		jp = new JPanel();
		jp.setPreferredSize(new Dimension(20, 20));
		
		oppjp = new JPanel();
		oppjp.setPreferredSize(new Dimension(20, 20));
		
		ImageIcon image2 = new ImageIcon("images/backgrounds/GuestBackground.jpg");
		bg = image2.getImage();
		
		LeftPanel = new JPanel();
		LeftPanel.setLayout(new BorderLayout());

		
		piecePlacer = new PiecePlacer();
		gameManager = new GameManager(piecePlacer, tc);
		tc.setGameManager(gameManager);

		//north
		tetrisTitle = new JLabel("User: " + username,JLabel.CENTER);
		tetrisTitle.setFont(font);

		//center 
		centerPanel = new JPanel();
		boardPanel = new BoardPanel(gameManager);
		
		//west
		sideBarPanel = new JPanel();
		
		sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
		
		scorePanel = new JPanel();
		
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel("Score:   ");
		scoreLabel.setFont(font);
		//TODO make it get score from game manager
		scoreTextLabel = new JLabel(Integer.toString(score));
		scoreTextLabel.setFont(font);
		scoreTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		linesSentPanel = new JPanel();
		linesSentPanel.setLayout(new BoxLayout(linesSentPanel, BoxLayout.Y_AXIS));
		linesSentLabel = new JLabel("Lines Sent:   ");
		linesSentLabel.setFont(font);
		linesSentTextLabel = new JLabel(Integer.toString(linesSent));
		linesSentTextLabel.setFont(font);
		linesSentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		nextPeicePanel = new JPanel();
		nextPeicePanel.setLayout(new BoxLayout(nextPeicePanel, BoxLayout.Y_AXIS));
		nextPieceTextLabel = new JLabel("Next Piece");
		nextPieceTextLabel.setFont(font);
		nextImage = new NextPiecePanel(piecePlacer);
		nextPieceTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		RightPanel = new JPanel();
		
		RightPanel.setLayout(new BorderLayout());
		
		//north
		oppUsername = tc.getOpponentName();
		oppTetrisTitle = new JLabel("Opponent: " + oppUsername,JLabel.CENTER);
		oppTetrisTitle.setFont(font);
		
		//center 
		oppCenterPanel = new JPanel();
		
		//TODO
		//delete this, this needs to be passed in by networking
		PiecePlacer DELETEpiecePlacer = new PiecePlacer();
		GameManager DELETEgameManager = new GameManager(DELETEpiecePlacer);
		oppBoardPanel = new BoardPanel(DELETEgameManager);
		//TODO
		
		//west
		oppSideBarPanel = new JPanel();
	
		oppSideBarPanel.setLayout(new BoxLayout(oppSideBarPanel, BoxLayout.Y_AXIS));
		
		oppScorePanel = new JPanel();
		oppScorePanel.setLayout(new BoxLayout(oppScorePanel, BoxLayout.Y_AXIS));
		oppScoreLabel = new JLabel("Score:   ");
		oppScoreLabel.setFont(font);
		//TODO make it get score from game manager
		oppScoreTextLabel = new JLabel(Integer.toString(oppScore));
		oppScoreTextLabel.setFont(font);
		oppScoreTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		oppLinesSentPanel = new JPanel();
		oppLinesSentPanel.setLayout(new BoxLayout(oppLinesSentPanel, BoxLayout.Y_AXIS));
		oppLinesSentLabel = new JLabel("Lines Sent:   ");
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
	
	private void createGUI(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		LeftPanel.setLayout(new BorderLayout());
		
		LeftPanel.setOpaque(false);
		
		//north
		LeftPanel.add(tetrisTitle, BorderLayout.NORTH);
		
		JPanel boxLayoutForLeftPanel = new JPanel();
		boxLayoutForLeftPanel.setLayout(new BoxLayout(boxLayoutForLeftPanel, BoxLayout.X_AXIS));
		boxLayoutForLeftPanel.setOpaque(false);
	//	boxLayoutForLeftPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.MAGENTA));
		//west
		//sideBarPanel.add(Box.createGlue());
		sideBarPanel.setOpaque(false);
		sideBarPanel.setPreferredSize(new Dimension(200, 600));
		scorePanel.setOpaque(false);
		JPanel jpp = new JPanel();
		jpp.setLayout(new BoxLayout(jpp, BoxLayout.X_AXIS));
		jpp.add(scoreLabel);
		jpp.add(scoreTextLabel);
		jpp.setOpaque(false);
		scorePanel.add(jpp);
		scorePanel.setPreferredSize(new Dimension(200, 60));
		scorePanel.setMaximumSize(new Dimension(200, 60));
		scorePanel.setMinimumSize(new Dimension(200, 60));
		scorePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scorePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.PINK));
		sideBarPanel.add(scorePanel);
		
		
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
		
		linesSentPanel.setOpaque(false);
		JPanel jppp = new JPanel();
		jppp.setOpaque(false);
		jppp.setLayout(new BoxLayout(jppp, BoxLayout.X_AXIS));
		jppp.add(linesSentLabel);
		jppp.add(linesSentTextLabel);
		linesSentPanel.add(jppp);
		linesSentPanel.add(linesSentLabel);
		linesSentPanel.add(linesSentTextLabel);
		linesSentPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		linesSentPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
		linesSentPanel.setPreferredSize(new Dimension(200, 45));
		linesSentPanel.setMaximumSize(new Dimension(200, 45));
		linesSentPanel.setMinimumSize(new Dimension(200, 45));
		sideBarPanel.add(linesSentPanel);
		
		//sideBarPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.MAGENTA));
//		sideBarPanel.add(Box.createGlue());
//		sideBarPanel.add(Box.createGlue());
//		sideBarPanel.add(Box.createGlue());
		
		//LeftPanel.add(sideBarPanel, BorderLayout.WEST);
		boxLayoutForLeftPanel.add(sideBarPanel);
		
		//center
		//centerPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.MAGENTA));
		centerPanel.add(boardPanel);
		centerPanel.setOpaque(false);
		//LeftPanel.add(centerPanel, BorderLayout.CENTER);
		boxLayoutForLeftPanel.add(centerPanel);
		
		LeftPanel.add(boxLayoutForLeftPanel, BorderLayout.CENTER);
		
		add(LeftPanel);
		
		RightPanel.setLayout(new BorderLayout());
		RightPanel.setOpaque(false);
		RightPanel.add(oppTetrisTitle, BorderLayout.NORTH);
		oppSideBarPanel.setOpaque(false);
		oppSideBarPanel.setPreferredSize(new Dimension(175, 600));
		//oppSideBarPanel.add(Box.createGlue());
		
		
		JPanel boxLayoutForRightPanel = new JPanel();
		boxLayoutForRightPanel.setLayout(new BoxLayout(boxLayoutForRightPanel, BoxLayout.X_AXIS));
		boxLayoutForRightPanel.setOpaque(false);
		
		
		oppScorePanel.setOpaque(false);
		
		JPanel jpp1 = new JPanel();
		jpp1.setLayout(new BoxLayout(jpp1, BoxLayout.X_AXIS));
		jpp1.setOpaque(false);
		jpp1.add(oppScoreLabel);
		jpp1.add(oppScoreTextLabel);
		oppScorePanel.add(jpp1);
		
		oppScorePanel.add(jpp1);
		oppScorePanel.setPreferredSize(new Dimension(175, 45));
		oppScorePanel.setMaximumSize(new Dimension(175, 45));
		oppScorePanel.setMinimumSize(new Dimension(175, 45));
		oppScorePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.PINK));
		oppSideBarPanel.add(oppScorePanel);
		//oppSideBarPanel.add(Box.createGlue());
		
		oppNextPeicePanel.setOpaque(false);
		oppNextPeicePanel.add(oppNextPieceTextLabel);
		oppjp.add(oppNextImage);
		oppjp.setOpaque(false);
		oppNextPeicePanel.add(oppjp);
		oppNextPeicePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.MAGENTA));
		oppNextPeicePanel.setPreferredSize(new Dimension(175, 150));
		oppNextPeicePanel.setMaximumSize(new Dimension(175, 150));
		oppNextPeicePanel.setMinimumSize(new Dimension(175, 150));
		oppSideBarPanel.add(oppNextPeicePanel);
		//oppSideBarPanel.add(Box.createGlue());
		
		oppLinesSentPanel.setOpaque(false);
		JPanel jppp1 = new JPanel();
		jppp1.setOpaque(false);
		jppp1.setLayout(new BoxLayout(jppp1, BoxLayout.X_AXIS));
		jppp1.add(oppLinesSentLabel);
		jppp1.add(oppLinesSentTextLabel);
		oppLinesSentPanel.add(jppp1);
		oppLinesSentPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
		oppLinesSentPanel.setPreferredSize(new Dimension(175, 45));
		oppLinesSentPanel.setMaximumSize(new Dimension(175, 45));
		oppLinesSentPanel.setMinimumSize(new Dimension(175, 45));
		oppSideBarPanel.add(oppLinesSentPanel);
//		oppSideBarPanel.add(Box.createGlue());
//		
//		
//		oppSideBarPanel.add(Box.createGlue());
//		oppSideBarPanel.add(Box.createGlue());
//		oppSideBarPanel.add(Box.createGlue());
		
		
		//RightPanel.add(oppSideBarPanel, BorderLayout.EAST);
				
		//center
		oppCenterPanel.setOpaque(false);
		oppCenterPanel.add(oppBoardPanel);
		//RightPanel.add(oppCenterPanel, BorderLayout.CENTER);
		
		
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
	
	private void addActionAdapters(){
		
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(3));
		g2.drawLine(485, 0, 485, 960);
	}
	

}