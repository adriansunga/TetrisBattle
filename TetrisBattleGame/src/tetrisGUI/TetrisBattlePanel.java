package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JButton nextPieceImageButton;
	private JPanel centerPanel;
	private BoardPanel boardPanel;
	
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
	private JButton oppNextPieceImageButton;
	private JPanel oppCenterPanel;
	private BoardPanel oppBoardPanel;
	
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
		
		ImageIcon image2 = new ImageIcon("images/backgrounds/GuestBackground.jpg");
		bg = image2.getImage();
		
		LeftPanel = new JPanel();
		LeftPanel.setLayout(new BorderLayout());

		
		piecePlacer = new PiecePlacer();
		gameManager = new GameManager(piecePlacer, tc);
		tc.setGameManager(gameManager);

		//north
		tetrisTitle = new JLabel(username,JLabel.CENTER);
		tetrisTitle.setFont(font);

		//center 
		centerPanel = new JPanel();
		boardPanel = new BoardPanel(gameManager);
		
		//west
		sideBarPanel = new JPanel();
		
		sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
		
		scorePanel = new JPanel();
		
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel("Lines Sent:");
		scoreLabel.setFont(font);
		//TODO make it get score from game manager
		scoreTextLabel = new JLabel(Integer.toString(score));
		
		nextPeicePanel = new JPanel();
		
		nextPeicePanel.setLayout(new BoxLayout(nextPeicePanel, BoxLayout.Y_AXIS));
		nextPieceTextLabel = new JLabel("Next Piece:");
		nextPieceTextLabel.setFont(font);
		nextPieceImageButton = new JButton();
		
		//TODO make it get next peice from manager
		ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
		Image img = originalButton.getImage();
		Image newImage = img.getScaledInstance(originalButton.getIconWidth()/(10), originalButton.getIconHeight()/10, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ButtonImage1 = new ImageIcon(newImage);
		nextPieceImageButton.setBorderPainted( false );
		nextPieceImageButton.setFocusPainted( false );
		nextPieceImageButton.setIcon(ButtonImage1);
		
		
		
		RightPanel = new JPanel();
		
		RightPanel.setLayout(new BorderLayout());
		
		//north
		//TODO delete this line
		oppUsername = tc.getOpponentName();
		oppTetrisTitle = new JLabel(oppUsername,JLabel.CENTER);
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
		oppScoreLabel = new JLabel("Lines Sent:");
		oppScoreLabel.setFont(font);
		//TODO make it get score from game manager
		oppScoreTextLabel = new JLabel(Integer.toString(oppScore));
		
		oppNextPeicePanel = new JPanel();

		oppNextPeicePanel.setLayout(new BoxLayout(oppNextPeicePanel, BoxLayout.Y_AXIS));
		oppNextPieceTextLabel = new JLabel("Next Piece:");
		oppNextPieceTextLabel.setFont(font);
		oppNextPieceImageButton = new JButton();
		
		//TODO make it get next peice from manager
		ImageIcon originalButton1 = new ImageIcon("images/pieces/Tetris_I.svg.png");
		Image img1 = originalButton1.getImage();
		Image newImage1 = img1.getScaledInstance(originalButton1.getIconWidth()/(10), originalButton1.getIconHeight()/10, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ButtonImage11 = new ImageIcon(newImage1);
		oppNextPieceImageButton.setBorderPainted( false );
		oppNextPieceImageButton.setFocusPainted( false );
		oppNextPieceImageButton.setIcon(ButtonImage11);
		
	}
	
	private void createGUI(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		LeftPanel.setLayout(new BorderLayout());
		
		LeftPanel.setOpaque(false);
		
		//north
		LeftPanel.add(tetrisTitle, BorderLayout.NORTH);
		//LeftPanel.add(tetrisTitle);
		
		
		//west
		sideBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.setOpaque(false);
		nextPeicePanel.setOpaque(false);
		nextPeicePanel.add(nextPieceTextLabel);
		nextPeicePanel.add(nextPieceImageButton);
		nextPeicePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		sideBarPanel.add(nextPeicePanel);
		sideBarPanel.add(Box.createGlue());
		
		scorePanel.setOpaque(false);
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreTextLabel);
		scorePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		sideBarPanel.add(scorePanel);
		sideBarPanel.add(Box.createGlue());
		
		
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		
		LeftPanel.add(sideBarPanel, BorderLayout.WEST);
		//LeftPanel.add(sideBarPanel);
				
		//center
		centerPanel.add(boardPanel);
		centerPanel.setOpaque(false);
		LeftPanel.add(centerPanel, BorderLayout.CENTER);
		//LeftPanel.add(centerPanel);
		
		add(LeftPanel);
		
	
		
		
		
		
		
		
		
		RightPanel.setLayout(new BorderLayout());
		RightPanel.setOpaque(false);
		//north
		RightPanel.add(oppTetrisTitle, BorderLayout.NORTH);
		//RightPanel.add(oppTetrisTitle);
		
		
		//west
		oppSideBarPanel.setOpaque(false);
		oppSideBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		oppSideBarPanel.add(Box.createGlue());
		
		oppNextPeicePanel.setOpaque(false);
		oppNextPeicePanel.add(oppNextPieceTextLabel);
		oppNextPeicePanel.add(oppNextPieceImageButton);
		oppNextPeicePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		oppSideBarPanel.add(oppNextPeicePanel);
		oppSideBarPanel.add(Box.createGlue());
		
		oppScorePanel.setOpaque(false);
		oppScorePanel.add(oppScoreLabel);
		oppScorePanel.add(oppScoreTextLabel);
		oppScorePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		oppSideBarPanel.add(oppScorePanel);
		
		
		oppSideBarPanel.add(Box.createGlue());
		oppSideBarPanel.add(Box.createGlue());
		oppSideBarPanel.add(Box.createGlue());
		
		RightPanel.add(oppSideBarPanel, BorderLayout.EAST);
		//RightPanel.add(oppSideBarPanel);
				
		//center
		oppCenterPanel.setOpaque(false);
		oppCenterPanel.add(oppBoardPanel);
		RightPanel.add(oppCenterPanel, BorderLayout.CENTER);
		//RightPanel.add(oppCenterPanel);
		
		add(RightPanel);
	}
	
	private void addActionAdapters(){
		
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	

}