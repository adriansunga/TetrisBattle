package tetrisGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HelpMenu extends JFrame{

	private static final long serialVersionUID = 1L;
	private JScrollPane jsp;
	private JPanel infoPanel;
	private String[] words = {"Tetris Battle Help Menu",};
	
	HelpMenu() {
		super("Help Menu");
		setSize(300, 200);
		setMinimumSize(new Dimension(200, 350));
		setMaximumSize(new Dimension(200, 350));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < words.length; i++) {
			JLabel hi = new JLabel(words[i]);
			infoPanel.add(hi);
		}
		infoPanel.setOpaque(false);
		jsp = new JScrollPane(infoPanel);
		jsp.getViewport().setBackground(new Color(211,191,143));
		add(jsp);
		setLocationRelativeTo(null);
	}
}
