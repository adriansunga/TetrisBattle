package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuestTetrisPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -585498416770344734L;
	
	Font titleFont = new Font("Tetris Mania Type", Font.BOLD, 40);
	Font font = new Font("Tetris Mania Type", Font.BOLD, 20);
	
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
		
		//north
		tetrisTitle = new JLabel("Tetris",JLabel.CENTER);
		tetrisTitle.setFont(titleFont);
		
		//center 
		centerPanel = new JPanel();
		boardPanel = new BoardPanel();
		
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
		nextPieceImageButton = new JButton();
		
		ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
		Image img = originalButton.getImage();
		Image newImage = img.getScaledInstance(originalButton.getIconWidth()/(10), originalButton.getIconHeight()/10, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ButtonImage1 = new ImageIcon(newImage);
		nextPieceImageButton.setBorderPainted( false );
		nextPieceImageButton.setFocusPainted( false );
		nextPieceImageButton.setIcon(ButtonImage1);
		
	}
	
	private void createGUI(){
		
		setLayout(new BorderLayout());
		
		//north
		add(tetrisTitle, BorderLayout.NORTH);
		
		//west
		sideBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		sideBarPanel.add(Box.createGlue());
		
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreTextLabel);
		scorePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		sideBarPanel.add(scorePanel);
		sideBarPanel.add(Box.createGlue());
		
		nextPeicePanel.add(nextPieceTextLabel);
		nextPeicePanel.add(nextPieceImageButton);
		nextPeicePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		sideBarPanel.add(nextPeicePanel);
		
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		sideBarPanel.add(Box.createGlue());
		
		add(sideBarPanel, BorderLayout.WEST);
		
		//center
		centerPanel.add(boardPanel);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private void addActionAdapters(){
		
	}
	
}
