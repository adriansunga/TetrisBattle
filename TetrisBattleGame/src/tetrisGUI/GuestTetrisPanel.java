package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

public class GuestTetrisPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -585498416770344734L;
	
	Font font = new Font("Tetris Mania Type", Font.BOLD, 30);
	
	private JLabel tetrisTitle;
	private JPanel titlePanel;
	private JPanel leftPanel;
	private JLabel scoreLabel;
	private JLabel scoreTextLabel, levelNumberLabel, levelLabel;
	private int score = 0;
	private JPanel nextPiecePanel;
	private JLabel nextPieceTextLabel;
	private NextPiecePanel nextImage;
	private JPanel jp;
	private JPanel nextPanel, scoresPanel, levelPanel, scorePanel;
	private JPanel scoresTextLabelPanel, nextPieceTextLabelPanel, scoresLabelPanel;
	
	private BoardPanel boardPanel;
	
	private JPanel levelsLabelPanel, levelsTextLabelPanel;
	
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	private GameManager gameManager;
	private PiecePlacer piecePlacer;
	
	private Image bg;
	
	public GuestTetrisPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout){
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
		try {
			new PlayMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initializeVariables(){
		nextPanel = new JPanel();
		levelPanel = new JPanel();
		scoresPanel = new JPanel();
		levelLabel = new JLabel("level = ");
		levelLabel.setFont(font);
		//TODO: get level from game manager
		levelNumberLabel = new JLabel("0");
		levelNumberLabel.setFont(font);
		levelPanel = new JPanel();
		levelsLabelPanel = new JPanel();
		levelsTextLabelPanel = new JPanel();
		jp = new JPanel();
		jp.setPreferredSize(new Dimension(20, 20));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		ImageIcon image2 = new ImageIcon("images/backgrounds/guestbg.jpg");
		bg = image2.getImage();
		
		piecePlacer = new PiecePlacer();
		gameManager = new GameManager(piecePlacer);
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		titlePanel = new JPanel();

		//north
		ImageIcon image1 = new ImageIcon("images/Tetris_Title.jpg");
		Image img1 = image1.getImage();
		Image newImage1 = img1.getScaledInstance(image1.getIconWidth()/2, image1.getIconHeight()/2, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ButtonImage3 = new ImageIcon(newImage1);
		tetrisTitle = new JLabel(ButtonImage3);

		//center 
		boardPanel = new BoardPanel(gameManager);
		gameManager.setBoardPanel(boardPanel);
		
		//west
		scorePanel = new JPanel();
		scoresTextLabelPanel = new JPanel();
		nextPieceTextLabelPanel = new JPanel();
		scoresLabelPanel = new JPanel();
		scoreLabel = new JLabel("Lines Cleared");
		scoreLabel.setFont(font);
		//TODO make it get score from game manager
		scoreTextLabel = new JLabel(Integer.toString(score));
		scoreTextLabel.setFont(font);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nextPiecePanel = new JPanel();
		nextPieceTextLabel = new JLabel("Next Piece");
		nextPieceTextLabel.setFont(font);
		nextImage = new NextPiecePanel(piecePlacer);
		nextPieceTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		
	}
	
	private void createGUI(){
		//west
		nextPiecePanel.setOpaque(false);
		nextPieceTextLabelPanel.add(nextPieceTextLabel, BorderLayout.CENTER);
		jp.add(nextImage);
		jp.setOpaque(false);
		nextPiecePanel.setLayout(new BoxLayout(nextPiecePanel, BoxLayout.Y_AXIS));
		nextPiecePanel.add(nextPieceTextLabel);
		nextPieceTextLabelPanel.setOpaque(false);
		nextPiecePanel.add(jp);
		nextPiecePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GREEN));
		nextPiecePanel.setPreferredSize(new Dimension(300, 150));
		nextPiecePanel.setMaximumSize(new Dimension(300, 150));
		nextPiecePanel.setMinimumSize(new Dimension(300, 150));
		
		levelPanel.setOpaque(false);
		levelsLabelPanel.setOpaque(false);
		levelsTextLabelPanel.setOpaque(false);
		levelsLabelPanel.add(levelLabel, BorderLayout.CENTER);
		levelsTextLabelPanel.add(levelNumberLabel, BorderLayout.SOUTH);
		levelPanel.add(levelsLabelPanel, BorderLayout.NORTH);
		levelPanel.add(levelsTextLabelPanel, BorderLayout.SOUTH);
		levelPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.YELLOW));
		levelPanel.setPreferredSize(new Dimension(300,50));
		levelPanel.setMaximumSize(new Dimension(300, 50));
		levelPanel.setMinimumSize(new Dimension(300, 50));
		
		scorePanel.setOpaque(false);
		scoresLabelPanel.setOpaque(false);
		scoresTextLabelPanel.setOpaque(false);
		scoresLabelPanel.add(scoreLabel, BorderLayout.CENTER);
		scoresTextLabelPanel.add(scoreTextLabel, BorderLayout.SOUTH);
		scorePanel.add(scoresLabelPanel, BorderLayout.NORTH);
		scorePanel.add(scoresTextLabelPanel, BorderLayout.SOUTH);
		scorePanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
		scorePanel.setPreferredSize(new Dimension(300, 90));
		scorePanel.setMaximumSize(new Dimension(300, 90));
		scorePanel.setMinimumSize(new Dimension(300, 90));

		titlePanel.add(tetrisTitle, BorderLayout.NORTH);
		titlePanel.setOpaque(false);
		
		//add(sideBarPanel, BorderLayout.WEST);
		boardPanel.setOpaque(false);
		//add(sideBarPanel);
		nextPanel.add(nextPiecePanel, BorderLayout.CENTER);
		scoresPanel.add(scorePanel, BorderLayout.CENTER);
		leftPanel.setOpaque(false);
		levelPanel.setOpaque(false);
		titlePanel.setOpaque(false);
		scoresPanel.setOpaque(false);
		nextPanel.setOpaque(false);
		leftPanel.add(titlePanel);
		leftPanel.add(nextPanel);
		leftPanel.add(levelPanel);
		leftPanel.add(scoresPanel);
		leftPanel.add(Box.createGlue());
		leftPanel.add(Box.createGlue());

		
				
		//center
		add(Box.createGlue());
		add(leftPanel);
		add(Box.createGlue());
		add(boardPanel);
		add(Box.createGlue());
		
	}
	
	private void addActionAdapters(){
		
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
}
