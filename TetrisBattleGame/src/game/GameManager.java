package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import tetrisGUI.BoardPanel;
import tetrisGUI.TilePanel;

public class GameManager {

	private Color[][] boardTiles;
	private final int matrixHeight = 20;
	private final int matrixWidth = 10;
	private Color backgroundColor = Color.BLACK;
	private int pieceSpeed = 1000; // in ms

	private BoardPanel boardPanel;

	private PiecePlacer piecePlacer;
	private Piece currentPiece;
	private Timer dropPieceTimer;
	private boolean canDrop;

	public GameManager(PiecePlacer piecePlacer) {
		boardTiles = new Color[matrixHeight][matrixWidth];
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				boardTiles[i][j] = backgroundColor;
			}
		}
		this.piecePlacer = piecePlacer;
		currentPiece = null;
		canDrop = true;
	}

	public void setBoardPanel(BoardPanel bp) {
		boardPanel = bp;
	}

	public void nextPiece() {
		currentPiece = piecePlacer.nextPiece();

		dropPiece(currentPiece);
	}

	public void dropPiece(Piece piece) {
		currentPiece = piece;

		dropPieceTimer = new Timer(pieceSpeed, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentPiece.dropDown();
				boardPanel.revalidate();
				boardPanel.repaint();

				canDrop = canMove("down");

				if (!canDrop) {
					dropPieceTimer.stop();
					nextPiece();
				}
			}
		});
		dropPieceTimer.start();
	}

	// TODO: WARNING: there may be some null pointer errors as we need to
	// find a way to handle when the piece is off of the board when it starts
	// (for now im neglecting that case. we could maybe avoid this by having the
	// first few indexes of the matrix be above the board? idk)
	private void setCurrentPieceLocation(Piece piece) {

	}

	// Check to see if you should send a line
	private boolean isLineFull(int rowNumber) {
		for (int i = 0; i < matrixWidth; i++) {
			if (boardTiles[rowNumber][i].equals(backgroundColor)) {
				return false;
			}
		}
		return true;
	}

	// Check to see if the piece can move down further
	// if the spot is occupied and it's not my piece then false
	private boolean canMove(String direction) {
		for (Point point : currentPiece.getLocation()) {
			Point nextPoint = nextPoint(point, direction);
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
		return currentPiece.getLocation().contains(thisSpot);
	}

	// Returns next lowest point (if null, then out of bounds)
	private Point nextPoint(Point p, String direction) {
		if (direction.equals("down")) {
			// Is at bottom?
			if (p.getY() == matrixHeight - 1) {
				return null;
			}
			return new Point((int) p.getX(), (int) p.getY() + 1);
		} else if (direction.equals("left")) {
			if (p.getX() == 0) {
				return null;
			}
			return new Point((int) p.getX() - 1, (int) p.getY());
		} else { // right
			if (p.getY() == matrixWidth - 1) {
				return null;
			}
			return new Point((int) p.getX() + 1, (int) p.getY());
		}
	}

	public void move(String direction) {
		if (direction.equals("left") && canMove("left")) {
			setToBackground(backgroundColor);
			currentPiece.shiftLeft();
			setToBackground(currentPiece.getColor());
		} else if (direction.equals("right") && canMove("right")) {
			setToBackground(backgroundColor);
			currentPiece.shiftRight();
			setToBackground(currentPiece.getColor());
		}
	}

	// sets points where piece is to black so you can redraw the new positions
	private void setToBackground(Color color) {
		for (Point point : currentPiece.getLocation()) {
			boardTiles[point.x][point.y] = color;
		}
	}

	public void testFunction() {
		boardTiles[5][5] = Color.red;
		updateView();
	}

	private void updateView() {
		TilePanel[][] tileMatrix = boardPanel.getTileMatrix();
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				tileMatrix[i][j].setColor(boardTiles[i][j]);
			}
		}
	}
}
