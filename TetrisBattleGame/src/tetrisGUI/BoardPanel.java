package tetrisGUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import game.GameManager;
import game.Loc;
import game.Piece;

public class BoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1705626207127777875L;
	private TilePanel[][] tileMatrix;
	// private GameManager gameManager;
	private GameManager gm;
	
	private JButton backToMenuButton;

	public BoardPanel(GameManager gm) {
		this.gm = gm;
		gm.setBoardPanel(this);
		initializeVariables();
		createGUI();
	}

	private void initializeVariables() {
		tileMatrix = new TilePanel[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				tileMatrix[i][j] = new TilePanel();
			}
		}
	}

	private void createGUI() {

		setLayout(new GridLayout(20, 10));

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				add(tileMatrix[i][j]);
			}
		}
	}

	public TilePanel[][] getTileMatrix() {
		return tileMatrix;
	}

	public void setTileMatrix(Color[][] boardColors) {
		TilePanel[][] fauxTileMatrix = new TilePanel[20][10];

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				fauxTileMatrix[i][j] = tileMatrix[i][j];
			}
		}
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				fauxTileMatrix[i][j].setColor(boardColors[i][j]);
			}
		}
		tileMatrix = fauxTileMatrix;
	}
	
	public void setBackToMenuButton(JButton jb)
	{
		backToMenuButton = jb;
	}
	
	public void clickBackToMenuButton()
	{
		backToMenuButton.doClick();
	}

}
