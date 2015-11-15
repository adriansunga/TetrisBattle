package game;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;

public class GameManager {

	private Color[][] boardTiles;
	private final int matrixHeight = 20;
	private final int matrixWidth = 10;
	private Point[] currentPieceLocation;
	private Color backgroundColor = Color.GRAY;
	private int pieceSpeed = 1000; // in ms
	private PiecePlacer piecePlacer;

	public GameManager(PiecePlacer piecePlacer) {
		boardTiles = new Color[matrixHeight][matrixWidth];
		Arrays.fill(boardTiles, Color.GRAY);
		this.piecePlacer = piecePlacer;
	}

	public void dropPiece(Piece piece) {
		setCurrentPieceLocation(piece);
		boolean canDrop = true;
		while (canDrop) {
			try {
			    Thread.sleep(pieceSpeed); 
			    
			    if (canMoveDown()) {
			    	lowerPiece(); //deals with currentPosition array
			    	
			    } else {
			    	canDrop = false;
			    }
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}

	// TODO: WARNING: there may be some null pointer errors as we need to
	// find a way to handle when the piece is off of the board when it starts
	// (for now im neglecting that case. we could maybe avoid this by having the
	// first few indexes of the matrix be above the board? idk)
	private void setCurrentPieceLocation(Piece piece) {
		
	}

	// Check to see if you should send a line
	private boolean isLineFull() {
		for (int i = 0; i < matrixWidth; i++) {
			if (boardTiles[matrixHeight - 1][i].equals(backgroundColor)) {
				return false;
			}
		}
		return true;
	}

	// Check to see if the piece can move down further
	// if the spot is occupied and it's not my piece then false
	private boolean canMoveDown() {
		for (Point point : currentPieceLocation) {
			Point nextPoint = nextPoint(point);
			if (nextPoint.equals(null)) {
				return false;
			}
			// if there's someone else's piece blocking you
			if (!boardTiles[nextPoint.x][nextPoint.y].equals(backgroundColor) && !isMyPiece(nextPoint)) {
				return false;
			}
		}
		return true;
	}

	// if I occupy a certain given spot
	private boolean isMyPiece(Point thisSpot) {
		for (Point point : currentPieceLocation) {
			if (thisSpot.equals(point)) {
				System.out.println("isMyPiece is returning true");
				return true;
			}
		}
		System.out.println("isMyPiece is returning false");
		return false;
	}

	// Returns next lowest point (if null, then out of bounds)
	private Point nextPoint(Point p) {
		// Is at bottom?
		if (p.getY() == matrixHeight - 1) {
			return null;
		}
		return new Point((int) p.getX(), (int) p.getY() + 1);
	}

}
