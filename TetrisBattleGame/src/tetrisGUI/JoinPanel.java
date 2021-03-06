package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import library.FontLibrary;
import networking.TetrisClient;

public class JoinPanel extends JPanel{

	private static final long serialVersionUID = -3365559486379271363L;
	private JLabel ipLabel, portLabel, titleLabel;
	private JTextField ipTF, portTF;
	private JButton continueButton;
	private String username;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout, leftPanel, rightPanel, tetrisPanel, tfPanel, portPanel, buttonPanel, tf2Panel, ipPanel;
	Font font = FontLibrary.getFont("fonts/Tetris_Mania_Type.ttf", Font.BOLD, 30);
	private Image bg;
	ImageIcon originalButton1 = new ImageIcon("images/tetrisbattle.png");
	Image img1 = originalButton1.getImage();
	Image newImage1 = img1.getScaledInstance(originalButton1.getIconWidth()*5/4, originalButton1.getIconHeight()*5/4, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage11 = new ImageIcon(newImage1);
	ImageIcon originalButton = new ImageIcon("images/pieces/Tetris_I.svg.png");
	Image img = originalButton.getImage();
	Image newImage = img.getScaledInstance(originalButton.getIconWidth()/4, originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ButtonImage1 = new ImageIcon(newImage);
	
	//constructor
	public JoinPanel(CardLayout cardLayout, JPanel outerPanelForCardLayout, String username){
		this.username = username;
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		initializeVariables();
		createGUI();
		addActionAdapters();
	}
	
	private void initializeVariables(){
		ipLabel = new JLabel("IP Address:");
		ipTF = new JTextField(12);
		tf2Panel = new JPanel();
		ipPanel = new JPanel();
		titleLabel = new JLabel(ButtonImage11);
		tetrisPanel = new JPanel();
		leftPanel = new JPanel();
		tfPanel = new JPanel();
		portPanel = new JPanel();
		buttonPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		portLabel = new JLabel("Port:");
		portTF = new JTextField(6);
		continueButton = new JButton("Continue");
		continueButton.setIcon(ButtonImage1);
		continueButton.setHorizontalTextPosition(SwingConstants.CENTER);
		continueButton.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		continueButton.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		leftPanel.setOpaque(false);
		rightPanel.setOpaque(false);
		tetrisPanel.setOpaque(false);
		tfPanel.setOpaque(false);
		portPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		tf2Panel.setOpaque(false);
		ipPanel.setOpaque(false);
		portTF.setFont(font);
		portTF.setForeground(Color.BLACK);
		ipTF.setFont(font);
		ipTF.setForeground(Color.BLACK);
		continueButton.setFont(font);
		portLabel.setFont(font);
		portLabel.setForeground(Color.WHITE);
		portTF.setPreferredSize(new Dimension(20, 50));
		portTF.setMaximumSize(new Dimension(20, 50));
		portTF.setMinimumSize(new Dimension(20, 50));
		ipLabel.setFont(font);
		ipLabel.setForeground(Color.WHITE);
		//.setPreferredSize(new Dimension(200, 50));
//		ipTF.setMinimumSize(new Dimension(200, 50));
		//tfPanel.setPreferredSize(new Dimension(200, 50));
		tfPanel.setMaximumSize(new Dimension(200, 50));
		tfPanel.setMinimumSize(new Dimension(200, 50));
		portPanel.setPreferredSize(new Dimension(200, 50));
		portPanel.setMaximumSize(new Dimension(200, 50));
		portPanel.setMinimumSize(new Dimension(200, 50));
		//tf2Panel.setPreferredSize(new Dimension(200, 50));
		//tf2Panel.setMaximumSize(new Dimension(200, 50));
		//tf2Panel.setMinimumSize(new Dimension(200, 50));
		ipPanel.setPreferredSize(new Dimension(250, 50));
		ipPanel.setMaximumSize(new Dimension(250, 50));
		ipPanel.setMinimumSize(new Dimension(250, 50));
		buttonPanel.setPreferredSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		buttonPanel.setMaximumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		buttonPanel.setMinimumSize(new Dimension(ButtonImage1.getIconWidth(), ButtonImage1.getIconHeight()));
		leftPanel.setPreferredSize(new Dimension(450, 620));
		leftPanel.setMaximumSize(new Dimension(450, 620));
		leftPanel.setMinimumSize(new Dimension(450, 620));
		rightPanel.setPreferredSize(new Dimension(450, 620));
		rightPanel.setMaximumSize(new Dimension(450, 620));
		rightPanel.setMinimumSize(new Dimension(450, 620));
	}
	
	private void createGUI(){
		tetrisPanel.add(titleLabel, BorderLayout.CENTER);
		leftPanel.add(tetrisPanel);
		tf2Panel.add(ipTF, BorderLayout.CENTER);
		tfPanel.add(portTF, BorderLayout.CENTER);
		portPanel.add(portLabel, BorderLayout.WEST);
		ipPanel.add(ipLabel, BorderLayout.WEST);
		buttonPanel.add(continueButton, BorderLayout.EAST);
		leftPanel.add(Box.createGlue());
		leftPanel.add(Box.createGlue());
		leftPanel.add(Box.createGlue());
		rightPanel.add(Box.createGlue());
		rightPanel.add(Box.createGlue());
		rightPanel.add(Box.createGlue());
		rightPanel.add(Box.createGlue());
		rightPanel.add(Box.createGlue());
		rightPanel.add(ipPanel);
		rightPanel.add(tf2Panel);
		rightPanel.add(portPanel);
		rightPanel.add(tfPanel);
		rightPanel.add(Box.createGlue());
		rightPanel.add(buttonPanel);
		rightPanel.add(Box.createGlue());
		add(leftPanel);
		add(rightPanel);
		
		ImageIcon image2 = new ImageIcon("images/backgrounds/s.png");
		bg = image2.getImage();
	}
	
	private void addActionAdapters(){	
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				if(portTF.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please select a valid port within the range 1024-65535",
							"Port Problem", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				int portNum = -1;
				
				try {
					portNum = Integer.parseInt(portTF.getText());
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Please select a valid port within the range 1024-65535",
							"Port Problem", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if(portNum < 1024 || portNum > 65535)
				{
					JOptionPane.showMessageDialog(null, "Please select a valid port within the range 1024-65535",
							"Port Problem", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				TetrisClient tc = new TetrisClient(ipTF.getText(), portNum, username, outerPanelForCardLayout, cardLayout);
				TetrisBattlePanel tetrisBattlePanel = new TetrisBattlePanel(cardLayout, outerPanelForCardLayout, tc);
				outerPanelForCardLayout.add(tetrisBattlePanel, "tetrisBattlePanel");
				cardLayout.show(outerPanelForCardLayout, "tetrisBattlePanel");
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}