package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import library.FontLibrary;
import game.GameManager;
import game.PiecePlacer;

public class GuestTetrisPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -585498416770344734L;

	private TitledBorder titledBorder;

//	Font font = new Font("Tetris Mania Type", Font.BOLD, 30);
//	Font font1 = new Font("Tetris Mania Type", Font.BOLD, 20);
	
	Font font = FontLibrary.getFont("fonts/Tetris_Mania_Type.ttf", Font.BOLD, 30);
	Font font1 = FontLibrary.getFont("fonts/Tetris_Mania_Type.ttf", Font.BOLD, 50);
	
	private JLabel tetrisTitle;
	private JPanel titlePanel;
	private JPanel leftPanel;
	private JLabel scoreLabel;
	private JLabel scoreTextLabel, levelNumberLabel, levelLabel;
	private JPanel nextPiecePanel;
	private JLabel nextPieceTextLabel;
	private NextPiecePanel nextImage;
	private JPanel jp;
	private JPanel nextPanel, scoresPanel, levelPanel, scorePanel, adLeftPanel;
	private JPanel scoresTextLabelPanel, nextPieceTextLabelPanel, scoresLabelPanel;
	private JButton backToMenu, mute;
	private JPanel backToMenuPanel;

	private BoardPanel boardPanel;

	private JPanel levelsLabelPanel, levelsTextLabelPanel;

	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;

	private PlayMusic playMusic;
	private JPanel totalAdsPanel;
	private GameManager gameManager;
	private PiecePlacer piecePlacer;
	private int delay = 500;
	private boolean isMuted = false;
	private ActionListener scoreLevelUpdater;
	private JPanel advertisementPanel1, advertisementPanel2, advertisementPanel3;
	private Advertisements ad1, ad2, ad3;
	private JButton adPicture1, adPicture2, adPicture3;
	private JLabel adText1, adText2, adText3;

	ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
	Image img = originalButton.getImage();
	Image newImage = img.getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage1 = new ImageIcon(newImage);

	ImageIcon originalButton1 = new ImageIcon("images/mute/on.png");
	Image img1 = originalButton1.getImage();
	Image newImage1 = img1.getScaledInstance(75, 50, java.awt.Image.SCALE_SMOOTH);
	ImageIcon on = new ImageIcon(newImage1);

	ImageIcon originalButton3 = new ImageIcon("images/mute/off.png");
	Image img3 = originalButton3.getImage();
	Image newImage3 = img3.getScaledInstance(75, 50, java.awt.Image.SCALE_SMOOTH);
	ImageIcon off = new ImageIcon(newImage3);

	private Image bg;

	public GuestTetrisPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout) {
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
		try {
			playMusic = new PlayMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			private static final long serialVersionUID = 2088417225735086214L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("down release");
				gameManager.zoomDown(1000);
			}
		});

	}

	private class KeyAction extends AbstractAction {
		private static final long serialVersionUID = 2511872532302802502L;

		public KeyAction(String actionCommand) {
			putValue(ACTION_COMMAND_KEY, actionCommand);
		}
		
		@Override
		public void actionPerformed(ActionEvent actionEvt) {
			System.out.println("key action performed..");
			String keyCode = actionEvt.getActionCommand();
			switch (keyCode) {
			case "VK_UP":
				//System.out.println("up key pressed");
				gameManager.rotatePiece();
				break;
			case "VK_DOWN":
				//System.out.println("down key pressed");
				gameManager.zoomDown(50);
				break;
			case "VK_LEFT":
				//System.out.println("left key pressed");
				gameManager.move("left");
				break;
			case "VK_RIGHT":
				//System.out.println("right key pressed");
				gameManager.move("right");
				break;
			case "PlayerRightRelease":
				//System.out.println("Key released");
				gameManager.zoomDown(1000);
				break;
			case "drop":
				//System.out.println("shift key pressed");
				gameManager.zoomDown(0); // TODO: should i make this instantaneous?
				break;
			}
		}
	}

	private void initializeVariables() {

		// TODO
		// initialized advertisements
		adLeftPanel = new JPanel();
		adLeftPanel.setLayout(new BoxLayout(adLeftPanel, BoxLayout.X_AXIS));
		totalAdsPanel = new JPanel();
		totalAdsPanel.setLayout(new BoxLayout(totalAdsPanel, BoxLayout.Y_AXIS));
		
		adPicture1 = new JButton();
		adPicture1.setOpaque(false);
		adPicture1.setEnabled(true);
		adText1 = new JLabel();
		adText1.setFont(new Font("Helvetica", Font.PLAIN, 15));
		adPicture1.setHorizontalAlignment(SwingConstants.CENTER);
		adText1.setHorizontalAlignment(SwingConstants.CENTER);
		ad1 = new Advertisements(adPicture1, adText1, 0);
		ad1.start();
		advertisementPanel1 = new JPanel();
		advertisementPanel1.setPreferredSize(new Dimension(300, 190));
		advertisementPanel1.setMinimumSize(new Dimension(300, 190));
		advertisementPanel1.setMaximumSize(new Dimension(300, 190));
		adPicture2 = new JButton();
		adPicture2.setOpaque(false);
		adPicture2.setEnabled(true);
		adText2 = new JLabel();
		adText2.setFont(new Font("Helvetica", Font.PLAIN, 15));
		adPicture2.setHorizontalAlignment(SwingConstants.CENTER);
		adText2.setHorizontalAlignment(SwingConstants.CENTER);
		ad2 = new Advertisements(adPicture2, adText2, 1);
		ad2.start();
		advertisementPanel2 = new JPanel();
		advertisementPanel2.setPreferredSize(new Dimension(300, 190));
		advertisementPanel2.setMinimumSize(new Dimension(300, 190));
		advertisementPanel2.setMaximumSize(new Dimension(300, 190));
		adPicture3 = new JButton();
		adPicture3.setOpaque(false);
		adPicture3.setEnabled(true);
		adText3 = new JLabel();
		adText3.setFont(new Font("Helvetica", Font.PLAIN ,15));
		adPicture3.setHorizontalAlignment(SwingConstants.CENTER);
		adText3.setHorizontalAlignment(SwingConstants.CENTER);
		ad3 = new Advertisements(adPicture3, adText3, 2);
		ad3.start();
		advertisementPanel3 = new JPanel();
		advertisementPanel3.setPreferredSize(new Dimension(300, 190));
		advertisementPanel3.setMinimumSize(new Dimension(300, 190));
		advertisementPanel3.setMaximumSize(new Dimension(300, 190));

		nextPanel = new JPanel();
		levelPanel = new JPanel();
		scoresPanel = new JPanel();
		levelLabel = new JLabel("level = ");
		levelLabel.setFont(font);
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
		nextImage = new NextPiecePanel(piecePlacer);
		gameManager = new GameManager(piecePlacer, nextImage);
		gameManager.setTwoPlayer(false);
		piecePlacer.setGameManager(gameManager);
		piecePlacer.initializeNextPiece();
		mute = new JButton();

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		titlePanel = new JPanel();

		// north
		ImageIcon image1 = new ImageIcon("images/Tetris_Title.jpg");
		Image img1 = image1.getImage();
		Image newImage1 = img1.getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH);
		ImageIcon ButtonImage3 = new ImageIcon(newImage1);
		tetrisTitle = new JLabel(ButtonImage3);

		// center
		boardPanel = new BoardPanel(gameManager);
		gameManager.setBoardPanel(boardPanel);

		// west
		scorePanel = new JPanel();
		scoresTextLabelPanel = new JPanel();
		nextPieceTextLabelPanel = new JPanel();
		scoresLabelPanel = new JPanel();
		scoreLabel = new JLabel("Lines Cleared");
		scoreLabel.setFont(font);
		scoreTextLabel = new JLabel("0");
		scoreTextLabel.setFont(font);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nextPiecePanel = new JPanel();
		nextPieceTextLabel = new JLabel("Next Piece");
		nextPieceTextLabel.setFont(font);
		nextPieceTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		backToMenuPanel = new JPanel();
		backToMenu = new JButton("main menu");
		backToMenu.setFont(font);
		backToMenu.setIcon(ButtonImage1);
		backToMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		backToMenu.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		backToMenu.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));

		mute.setIcon(off);
		mute.setPreferredSize(new Dimension(on.getIconWidth(), on.getIconHeight()));
		mute.setMaximumSize(new Dimension(on.getIconWidth(), on.getIconHeight()));
	}

	private void createGUI() {
		// west
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
		levelPanel.setPreferredSize(new Dimension(300, 50));
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

		backToMenuPanel.setOpaque(false);
		backToMenuPanel.setLayout(new BoxLayout(backToMenuPanel, BoxLayout.X_AXIS));
		backToMenuPanel.add(backToMenu);
		backToMenuPanel.setPreferredSize(new Dimension(300, 50));
		backToMenuPanel.setMaximumSize(new Dimension(300, 50));
		backToMenuPanel.setMinimumSize(new Dimension(300, 50));
		backToMenuPanel.add(Box.createGlue());
		backToMenuPanel.add(mute);
		mute.setOpaque(false);

		// add(sideBarPanel, BorderLayout.WEST);
		boardPanel.setOpaque(false);
		// add(sideBarPanel);
		nextPanel.add(nextPiecePanel, BorderLayout.CENTER);
		scoresPanel.add(scorePanel, BorderLayout.CENTER);
		leftPanel.setOpaque(false);
		levelPanel.setOpaque(false);
		titlePanel.setOpaque(false);
		scoresPanel.setOpaque(false);
		nextPanel.setOpaque(false);
		adLeftPanel.setOpaque(false);

		advertisementPanel1.setOpaque(false);
		//advertisementPanel1.setLayout(new BoxLayout(advertisementPanel1, BoxLayout.Y_AXIS));
		titledBorder = new TitledBorder(null, "Advertisements", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.BLACK);
		titledBorder.setBorder(new LineBorder(Color.orange, 5));
		titledBorder.setTitleFont(font1);
		advertisementPanel1.setBorder(titledBorder);
		advertisementPanel1.add(adPicture1, BorderLayout.CENTER);
		advertisementPanel1.add(adText1, BorderLayout.SOUTH);
		advertisementPanel2.setOpaque(false);
		//advertisementPanel2.setLayout(new BoxLayout(advertisementPanel2, BoxLayout.Y_AXIS));
		titledBorder = new TitledBorder(null, "Advertisements", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.BLACK);
		titledBorder.setBorder(new LineBorder(Color.MAGENTA, 5));
		titledBorder.setTitleFont(font1);
		advertisementPanel2.setBorder(titledBorder);
		advertisementPanel2.add(adPicture2,BorderLayout.CENTER);
		advertisementPanel2.add(adText2, BorderLayout.SOUTH);
		advertisementPanel3.setOpaque(false);
		//advertisementPanel3.setLayout(new BoxLayout(advertisementPanel3, BoxLayout.Y_AXIS));
		titledBorder = new TitledBorder(null, "Advertisements", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.BLACK);
		titledBorder.setBorder(new LineBorder(Color.WHITE, 5));
		titledBorder.setTitleFont(font1);
		advertisementPanel3.setBorder(titledBorder);
		advertisementPanel3.add(adPicture3, BorderLayout.CENTER);
		advertisementPanel3.add(adText3, BorderLayout.SOUTH);

		leftPanel.add(titlePanel);
		totalAdsPanel.setOpaque(false);
		totalAdsPanel.add(advertisementPanel1);
		totalAdsPanel.add(advertisementPanel2);
		totalAdsPanel.add(advertisementPanel3);
		leftPanel.add(nextPanel);
		leftPanel.add(levelPanel);
		leftPanel.add(scoresPanel);
		leftPanel.add(backToMenuPanel);
		leftPanel.add(Box.createGlue());
		leftPanel.add(Box.createGlue());
		adLeftPanel.add(totalAdsPanel);
		adLeftPanel.add(leftPanel);

		// center
		add(Box.createGlue());
		add(adLeftPanel);
		add(Box.createGlue());
		add(boardPanel);
		add(Box.createGlue());

	}

	private void addActionAdapters() {
		
		//website integration for ads
		
		adPicture1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(Desktop.isDesktopSupported())
				{
				  try {
					  
					Desktop.getDesktop().browse(new URI( ad1.getWebsite() ));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
		
		adPicture2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(Desktop.isDesktopSupported())
				{
				  try {
					  
					Desktop.getDesktop().browse(new URI( ad2.getWebsite() ));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
		
		
		adPicture3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(Desktop.isDesktopSupported())
				{
				  try {
					  
					Desktop.getDesktop().browse(new URI( ad3.getWebsite() ));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});

		
		
		
		
		
		
		
		
		
		
		
		
		
		scoreLevelUpdater = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	              scoreTextLabel.setText("" + gameManager.getLinesCleared());
	              levelNumberLabel.setText("" + gameManager.getLevel());
	          }
	      };

		backToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("BACK TO MAIN MENU CALLED");
				gameManager.stopDropTimer();
				cardLayout.show(outerPanelForCardLayout, "loginPanel");
				playMusic.stop();
			}
		});

		boardPanel.setBackToMenuButton(backToMenu);

		// InputMap im = (InputMap)UIManager.get("mute.focusInputMap");
		// im.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
		// im.put(KeyStroke.getKeyStroke("released SPACE"), "none");

		mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isMuted) {
					playMusic.unpause();
					mute.setIcon(off);
					isMuted = false;
				} else {
					playMusic.pause();
					mute.setIcon(on);
					isMuted = true;
				}
			}
		});
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}
