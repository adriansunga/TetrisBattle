package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import networking.TetrisClient;
import tetrisGUI.BoardPanel;

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
	private TetrisClient tc;

	public GameManager(PiecePlacer piecePlacer, TetrisClient tc) {
		this.tc = tc;
		boardTiles = new Color[matrixHeight][matrixWidth];
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				boardTiles[i][j] = backgroundColor;
			}
		}
		this.piecePlacer = piecePlacer;
		currentPiece = null;
	}

	public GameManager(PiecePlacer piecePlacer) {
		boardTiles = new Color[matrixHeight][matrixWidth];
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				boardTiles[i][j] = backgroundColor;
			}
		}
		this.piecePlacer = piecePlacer;
		currentPiece = null;
	}

	public void setBoardPanel(BoardPanel bp) {
		boardPanel = bp;
	}

	public void nextPiece() {
		currentPiece = piecePlacer.nextPiece();

		dropPiece();
	}

	public void dropPiece() {
		dropPieceTimer = new Timer(pieceSpeed, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (canMove("down")) {
					move("down");
				} else {
					dropPieceTimer.stop();
					nextPiece();
				}
			}
		});
		dropPieceTimer.start();
	}

	public void sendGarbageLine() {
		// Networking
		// tetrisClient.sendMessage("garbageline");
	}

	public void receiveGarbageLine() {
		// Determine location of the notch
		int notchLoc = (int) Math.random() * 10;

		// Populate row with vals accounting for notch
		boolean[] garbageRow = new boolean[10];
		for (int i = 0; i < garbageRow.length; i++) {
			if (i != notchLoc) {
				garbageRow[i] = true;
			} else {
				garbageRow[i] = false;
			}
		}

		// Change tile matrix accordingly

		// Push tiles up
		for (int r = 0; r < matrixHeight - 1; r++) {
			for (int c = 0; c < matrixWidth; c++) {
				boardTiles[r][c] = boardTiles[r + 1][c];
			}
		}
		// Add garbage row to the bottom
		for (int i = 0; i < garbageRow.length; i++) {
			if (garbageRow[i]) {
				boardTiles[matrixHeight - 1][i] = Color.GRAY;
			} else {
				boardTiles[matrixHeight - 1][i] = backgroundColor;
			}
		}

		// Refresh BoardPanel
		updateView();
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
		if (currentPiece.getLocation() == null) {
			return false;
		}
		for (Loc loc : currentPiece.getLocation()) {
			Loc nextPoint = nextPoint(loc, direction);
			if (nextPoint == null) {
				return false;
			}
			// if there's someone else's piece blocking you
			if (!boardTiles[nextPoint.row][nextPoint.col].equals(backgroundColor) && !isMyPiece(nextPoint)) {
				return false;
			}
		}

		return true;
	}

	// if I occupy a certain given spot
	private boolean isMyPiece(Loc thisSpot) {
		return currentPiece.getLocation().contains(thisSpot);
	}

	// Returns next lowest point (if null, then out of bounds)
	private Loc nextPoint(Loc l, String direction) {
		if (direction.equals("down")) {
			// Is at bottom?
			System.out.println("p.getx= " + l.row);
			if (l.row >= matrixHeight - 1) {
				return null;
			}
			return new Loc((int) l.row, (int) l.col + 1);
		} else if (direction.equals("left")) {
			if (l.row <= 0) {
				return null;
			}
			return new Loc((int) l.row - 1, (int) l.col);
		} else { // right
			if (l.col >= matrixWidth - 1) {
				return null;
			}
			return new Loc((int) l.row + 1, (int) l.col);
		}
	}

	public void move(String direction) {
		if (direction.equals("left") && canMove("left")) {
			setToBackground(backgroundColor);
			currentPiece.shiftLeft();
			setToBackground(currentPiece.getColor());
			updateView();
		} else if (direction.equals("right") && canMove("right")) {
			setToBackground(backgroundColor);
			currentPiece.shiftRight();
			setToBackground(currentPiece.getColor());
			updateView();
		} else if (direction.equals("down") && canMove("down")) {
			setToBackground(backgroundColor);
			currentPiece.dropDown();
			setToBackground(currentPiece.getColor());
			updateView();
		}
	}

	// sets points where piece is to black so you can redraw the new positions
	private void setToBackground(Color color) {
		for (Loc l : currentPiece.getLocation()) {
			boardTiles[l.row][l.col] = color;
		}
	}

	public void testFunction() {
		currentPiece = new OPiece();
		currentPiece.setColor(Color.RED);
		System.out.println("current piece: " + currentPiece);
		ArrayList<Loc> location = new ArrayList<Loc>();
		location.add(new Loc(0, 0));
		location.add(new Loc(0, 1));
		location.add(new Loc(1, 1));
		location.add(new Loc(1, 0));

		currentPiece.setLocation(location);
		setToBackground(currentPiece.getColor());
		System.out.println("current piece color: " + currentPiece.getColor());
		updateView();
		// boardTiles[5][5] = Color.red;
		// updateView();
	}

	private void updateView() {
		// TilePanel[][] tileMatrix = boardPanel.getTileMatrix();
		// for (int i = 0; i < matrixHeight; i++) {
		// for (int j = 0; j < matrixWidth; j++) {
		// tileMatrix[i][j].setColor(boardTiles[i][j]);
		// }
		// }
		boardPanel.setTileMatrix(boardTiles);
	}
}
