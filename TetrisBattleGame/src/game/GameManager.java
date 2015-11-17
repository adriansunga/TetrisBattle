package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import networking.TetrisClient;
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
	private TetrisClient tc;

	// TODO: if time, add more cute colors #thrive
	private final Color[] pieceColors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
			Color.MAGENTA };

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
		int index = new Random().nextInt(pieceColors.length);
		currentPiece.setColor(pieceColors[index]);
		System.out.println("in nextPiece");
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
			if (boardTiles[nextPoint.row][nextPoint.col] != backgroundColor && !isMyPiece(nextPoint)) {
				return false;
			}
		}

		return true;
	}

	// if I occupy a certain given spot
	private boolean isMyPiece(Loc thisSpot) {
		for (Loc loc : currentPiece.getLocation()) {
			if (loc.row == thisSpot.row && loc.col == thisSpot.col) {
				return true;
			}
		}
		return false;
	}

	// Returns next lowest point (if null, then out of bounds)
	private Loc nextPoint(Loc l, String direction) {
		if (direction.equals("down")) {
			// Is at bottom?
			System.out.println("p.getx= " + l.row);
			if (l.row >= matrixHeight - 1) {
				return null;
			}
			return new Loc(l.row + 1, l.col);
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
			// if want to stop them from changing colors every move: remove
			// following two lines
			int index = new Random().nextInt(pieceColors.length);
			currentPiece.setColor(pieceColors[index]);
			System.out.println("AFTER DROPDOWN....");
			setToBackground(currentPiece.getColor());
			updateView();
		}
	}

	// sets points where piece is to black so you can redraw the new positions
	private void setToBackground(Color color) {
		for (Loc l : currentPiece.getLocation())
		{
			if(l.isOnBoard())
			{
				boardTiles[l.row][l.col] = color;
			}
		}
	}

	public void testFunction() {
		currentPiece = new OPiece();
		int index = new Random().nextInt(pieceColors.length);
		currentPiece.setColor(pieceColors[index]);
		System.out.println("current piece: " + currentPiece);
		System.out.println("location arr size in testfunction: " + currentPiece.getLocation().size());
		setToBackground(currentPiece.getColor());
		System.out.println("current piece color: " + currentPiece.getColor());
		updateView();
	}

	private void updateView() {
		TilePanel[][] tileMatrix = boardPanel.getTileMatrix();
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (boardTiles[i][j] != backgroundColor) {
					System.out.println("adding to board row, col: " + i + ", " + j);
				}
				tileMatrix[i][j].setColor(boardTiles[i][j]);
			}
		}

	}
}
