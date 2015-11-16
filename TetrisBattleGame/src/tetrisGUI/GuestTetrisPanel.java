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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameManager;
import game.PiecePlacer;

public class GuestTetrisPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -585498416770344734L;
	
	Font titleFont = new Font("Tetris Mania Type", Font.BOLD, 40);
	Font font = new Font("Tetris Mania Type", Font.BOLD, 20);
	
	private JLabel tetrisTitle;
	private JPanel titlePanel;
	private JPanel sideBarPanel;
	private JPanel scorePanel;
	private JPanel leftPanel;
	private JLabel scoreLabel;
	private JLabel scoreTextLabel;
	private int score = 0;
	private JPanel nextPeicePanel;
	private JLabel nextPieceTextLabel;
	private NextPiecePanel;
	private JPanel jp;
	
	private JPanel centerPanel;
	private BoardPanel boardPanel;
	
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
	}
	
	private void initializeVariables(){
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
		sideBarPanel = new JPanel();
		sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel("Lines Cleared:");
		scoreLabel.setFont(font);
		//TODO make it get score from game manager
		scoreTextLabel = new JLabel(Integer.toString(score));
		
		nextPeicePanel = new JPanel();
		nextPeicePanel.setLayout(new BoxLayout(nextPeicePanel, BoxLayout.Y_AXIS));
		nextPieceTextLabel = new JLabel("Next Piece:");
		nextPieceTextLabel.setFont(font);
		nextImage = new NextPiecePanel(piecePlacer);
		
	}
	
	private void createGUI(){
		//west
		sideBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		sideBarPanel.add(Box.createGlue());
		
		nextPeicePanel.setOpaque(false);
		nextPeicePanel.add(nextPieceTextLabel);
		jp.add(nextImage);
		jp.setOpaque(false);
		nextPeicePanel.setPreferredSize(new Dimension(40,150));
		nextPeicePanel.add(jp);
		nextPeicePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(nextPeicePanel);
		
		scorePanel.setOpaque(false);
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreTextLabel);
		scorePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		sideBarPanel.add(scorePanel);
		sideBarPanel.add(Box.createGlue());
		
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		
		titlePanel.add(tetrisTitle, BorderLayout.CENTER);
		titlePanel.setOpaque(false);
		
		//add(sideBarPanel, BorderLayout.WEST);
		sideBarPanel.setOpaque(false);
		boardPanel.setOpaque(false);
		//add(sideBarPanel);
		
		leftPanel.setOpaque(false);
		leftPanel.add(titlePanel);
		leftPanel.add(sideBarPanel);
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
