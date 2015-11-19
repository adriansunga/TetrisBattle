package tetrisGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import game.Loc;
import game.Piece;
import game.PiecePlacer;

public class NextPiecePanel extends JPanel{

	private TilePanel[][] tileMatrix;
	private PiecePlacer piecePlacer;

	public NextPiecePanel(PiecePlacer piecePlacer){
		this.piecePlacer = piecePlacer;
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

		Piece nextPiece = piecePlacer.nextNextPiece();
		for(int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				tileMatrix[i][j].setColor(Color.BLACK);
			}
		}
		
		Loc[] pieceOrientations = nextPiece.getOrientation();
		for(int i = 0; i < 4; i++)
		{
			Loc l = pieceOrientations[i];
			
			int row = l.row+4;
			int col = l.col-4;
			
			tileMatrix[row][col].setColor(nextPiece.getColor());
		}
		
		
		revalidate();
		repaint();
	}
}
