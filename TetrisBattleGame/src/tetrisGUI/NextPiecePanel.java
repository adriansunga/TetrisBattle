package tetrisGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import game.Piece;
import game.PiecePlacer;

public class NextPiecePanel extends JPanel{

	private TilePanel[][] tileMatrix;
	private PiecePlacer piecePlacer;

	public NextPiecePanel(PiecePlacer piecePlacer){
		
		initializeVariables();
		createGUI();
		
	}
	
	public void initializeVariables(){
		tileMatrix = new TilePanel[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tileMatrix[i][j] = new TilePanel();
			}
		}
	}
	
	public void createGUI(){
		
		
		setLayout(new GridLayout(4, 4));

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				add(tileMatrix[i][j]);
			}
		}
	}
	
	public void setNextPiece(){
//		Piece nextPiece = piecePlacer.nextPiece();
//		boolean[][] peiceOrientations= nextPiece.getOrientation();
//		for(int i = 0; i < 4; i++){
//			for (int j = 0; j < 4; j++){
//				if(peiceOrientations[i][j] == false){
//					tileMatrix[i][j].setColor(Color.BLACK);
//				}
//				else{
//					tileMatrix[i][j].setColor(nextPiece.getColor());
//				}
//			}
//		}
	}
}
