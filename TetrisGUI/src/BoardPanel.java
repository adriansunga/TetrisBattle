import java.awt.GridLayout;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1705626207127777875L;
	private TilePanel[][] tileMatrix;
	//private GameManager gameManager;
	
	public BoardPanel(){
		initializeVariables();
		createGUI();
	}
	
	private void initializeVariables(){
		tileMatrix = new TilePanel[20][10];
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 10; j++){
				tileMatrix[i][j] = new TilePanel();
			}
		}
	}
	
	private void createGUI(){
		
		setLayout(new GridLayout(20, 10));
		
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 10; j++){
				add(tileMatrix[i][j]);
			}
		}
	}
	
}
