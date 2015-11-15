package tetrisGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class BoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1705626207127777875L;
	private TilePanel[][] tileMatrix;
	// private GameManager gameManager;

	public BoardPanel() {
		initializeVariables();
		createGUI();

		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("inside keytyped");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("inside keypressed");
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_UP:
					System.out.println("up key pressed");
					break;
				case KeyEvent.VK_DOWN:
					System.out.println("down key pressed");
					break;
				case KeyEvent.VK_LEFT:
					System.out.println("left key pressed");
					break;
				case KeyEvent.VK_RIGHT:
					System.out.println("right key pressed");
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("inside keyreleased");

			}

		});
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
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkLeft);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkUp);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkDown);
		
		actionMap.put(vkLeft, new KeyAction(vkLeft));
		actionMap.put(vkRight, new KeyAction(vkRight));
		actionMap.put(vkLeft, new KeyAction(vkUp));
		actionMap.put(vkRight, new KeyAction(vkDown));

	}

	private class KeyAction extends AbstractAction {
		public KeyAction(String actionCommand) {
			putValue(ACTION_COMMAND_KEY, actionCommand);
		}

		@Override
		public void actionPerformed(ActionEvent actionEvt) {
			System.out.println(actionEvt.getActionCommand() + " pressed");
		}
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

}
