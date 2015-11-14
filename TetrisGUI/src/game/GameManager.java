package game;


import java.awt.Color;
import java.util.Arrays;


public class GameManager {

	private Color[][] boardTiles;
	private final int matrixHeight = 20;
	private final int matrixWidth = 10;
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
	
	
}
