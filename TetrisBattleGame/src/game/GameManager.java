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
		for (int i = 0; i < matrixWidth; i++) {
			if (boardTiles[matrixHeight - 1][i] == Color.GRAY) {
				return false;
			}
		}
		return true;
		//nat
	}

	// Check to see if the piece can move down further
	// if the spot is occupied and it's not my piece then false
	private boolean canMoveDown() {
<<<<<<< HEAD
		// if the spot is occupied and it's not my piece then false
		//hi iman
		//adrian
=======
		for (Point point : currentPieceLocation) {
			
		}
>>>>>>> 906d1317e217107dbdeb8785da266636174bab55
	}

	// if I occupy a certain given spot
	private boolean isMyPiece(int x, int y) {
		Point thisSpot = new Point(x, y);
		for (Point point : currentPieceLocation) {
			if (thisSpot.equals(point)) {
				System.out.println("isMyPiece is returning true");
				return true;
			}
		}
		System.out.println("isMyPiece is returning false");
		return false;
	}

}
