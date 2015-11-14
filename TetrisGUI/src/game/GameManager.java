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
	}
	
	// Check to see if the piece can move down further
//	private boolean canMoveDown() {
//		
//	}
	
	
	
	
}
