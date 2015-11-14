package game;
import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;

public class GameManager {

	private Color[][] boardTiles;
	private final int matrixHeight = 20;
	private final int matrixWidth = 10;
	private Point[] currentPieceLocation;
	
	public GameManager() {
		boardTiles = new Color[matrixHeight][matrixWidth];
		Arrays.fill(boardTiles, Color.GRAY);

	}
	
	// Check to see if you should send a line
	private boolean isLineFull() {
		for (int i =0; i< matrixWidth; i++) {
			if (boardTiles[matrixHeight -1][i] == Color.GRAY) {
				return false;
			}
		}
		return true;
		//iman
	}
	
	// Check to see if the piece can move down further
	private boolean canMoveDown() {
		// if the spot is occupied and it's not my piece then false
		//hi iman
		
	}
	
	// if I occupy a certain given spot
	private boolean isMyPiece(int x, int y) {
		Point thisSpot = new Point(x, y);
		for (Point point : currentPieceLocation) {
			//HEY ADRIAN ;) ;P
		}
	}
	
	
	
	
}
