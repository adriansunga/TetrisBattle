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
		setKeyBindings();

	}

	private void setKeyBindings() {
		ActionMap actionMap = getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);

		String vkLeft = "VK_LEFT";
		String vkRight = "VK_RIGHT";
		String vkUp = "VK_UP";
		String vkDown = "VK_DOWN";
		String space = "space";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), vkRight);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "PlayerDownRelease");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), space);

		actionMap.put(vkLeft, new KeyAction(vkLeft));
		actionMap.put(vkRight, new KeyAction(vkRight));
		actionMap.put(vkUp, new KeyAction(vkUp));
		actionMap.put(vkDown, new KeyAction(vkDown));
		actionMap.put(space, new KeyAction(space));
		actionMap.put("PlayerDownRelease", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("down release");
				gm.zoomDown(1000);
            }
        });


	}

	private class KeyAction extends AbstractAction {
		public KeyAction(String actionCommand) {
			putValue(ACTION_COMMAND_KEY, actionCommand);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvt) {
			//System.out.println("key action performed..");
			String keyCode = actionEvt.getActionCommand();
			switch (keyCode) {
			case "VK_UP":
				System.out.println("up key pressed");
				gm.rotatePiece();
				break;
			case "VK_DOWN":
				System.out.println("down key pressed");
				gm.zoomDown(50);
				break;
			case "VK_LEFT":
				System.out.println("left key pressed");
				gm.move("left");
				break;
			case "VK_RIGHT":
				System.out.println("right key pressed");
				gm.move("right");
				break;
			case "PlayerRightRelease":
				System.out.println("Key released");
				gm.zoomDown(1000);
				break;
			case "space":
				System.out.println("space key pressed");
				gm.zoomDown(0); //TODO: should i make this instantaneous?
				break;
			}
		}
//		setKeyBindings();
//
	}
//
//	private void setKeyBindings() {
//		ActionMap actionMap = getActionMap();
//		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
//		InputMap inputMap = getInputMap(condition);
//
//		String vkLeft = "VK_LEFT";
//		String vkRight = "VK_RIGHT";
//		String vkUp = "VK_UP";
//		String vkDown = "VK_DOWN";
//		String space = "space";
//		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
//		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), vkRight);
//		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
//		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);
//
//		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "PlayerDownRelease");
//		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), space);
//
//		actionMap.put(vkLeft, new KeyAction(vkLeft));
//		actionMap.put(vkRight, new KeyAction(vkRight));
//		actionMap.put(vkUp, new KeyAction(vkUp));
//		actionMap.put(vkDown, new KeyAction(vkDown));
//		actionMap.put(space, new KeyAction(space));
//		actionMap.put("PlayerDownRelease", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("down release");
//				gm.zoomDown(1000);
//            }
//        });
//
//
//	}
//
//	private class KeyAction extends AbstractAction {
//		public KeyAction(String actionCommand) {
//			putValue(ACTION_COMMAND_KEY, actionCommand);
//		}
//
//		@Override
//		public void actionPerformed(ActionEvent actionEvt) {
//			System.out.println("key action performed..");
//			String keyCode = actionEvt.getActionCommand();
//			switch (keyCode) {
//			case "VK_UP":
//				System.out.println("up key pressed");
//				// gm.testFunction();
//				break;
//			case "VK_DOWN":
//				System.out.println("down key pressed");
//				gm.zoomDown(50);
//				break;
//			case "VK_LEFT":
//				System.out.println("left key pressed");
//				gm.move("left");
//				break;
//			case "VK_RIGHT":
//				System.out.println("right key pressed");
//				gm.move("right");
//				break;
//			case "PlayerRightRelease":
//				System.out.println("Key released");
//				gm.zoomDown(1000);
//				break;
//			case "space":
//				System.out.println("space key pressed");
//				gm.zoomDown(0); //TODO: should i make this instantaneous?
//				break;
//			}
//		}
//	}

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
