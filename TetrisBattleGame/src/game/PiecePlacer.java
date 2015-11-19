package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class PiecePlacer {
	private ArrayList<Piece> pieceTypes;
	private ArrayList<Piece> pieces;

	private final int numPieces = 70;

	private int index;
	private int seed;

	private Piece nextNextPiece;
	private Piece currentPiece;
	
	private final Color[] pieceColors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
			Color.MAGENTA };

	public PiecePlacer() {
		index = 0;
		seed = 0;

		currentPiece = null;
		nextNextPiece = null;

		Random rand = new Random();
		int randomNum = rand.nextInt((7 - 0) + 1);
		nextNextPiece = getPiece(randomNum);
		
		nextNextPiece.setColor(getColor());
		
	}

	public Piece nextPiece() {
		System.out.println("in nextPiece() with index: " + index);

		currentPiece = nextNextPiece;
		Random rand = new Random();

		int randomNum = rand.nextInt((7 - 0) + 1);
		nextNextPiece = getPiece(randomNum);
		nextNextPiece.setColor(getColor());

		return currentPiece;
	}

	public Piece nextNextPiece() {
		return nextNextPiece;
	}
	
	private Color getColor() {
		int index = new Random().nextInt(pieceColors.length);
		return pieceColors[index];
	}

	public Piece getPiece(int index) {
		switch (index) {
		case 0:
			return new IPiece();
		case 1:
			return new JPiece();
		case 2:
			return new LPiece();
		case 3:
			return new OPiece();
		case 4:
			return new SPiece();
		case 5:
			return new TPiece();
		default:
			return new ZPiece();
		}
	}
}
