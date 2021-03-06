package game;

import java.awt.Color;
import java.util.Random;

public class PiecePlacer {


	private int index;

	private Piece nextNextPiece;
	private Piece currentPiece;
	
	private GameManager gameManager;
		
	private final Color[] pieceColors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
			Color.MAGENTA };

	public PiecePlacer() {
		
		index = 0;

		currentPiece = null;
		nextNextPiece = null;
	}
	
	public void initializeNextPiece()
	{
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
	
	public void setGameManager(GameManager gm)
	{
		gameManager = gm;
	}
	
	private Color getColor() {
		int index = new Random().nextInt(pieceColors.length);
		return pieceColors[index];
	}

	public Piece getPiece(int index) {
		switch (index) {
		case 0:
			return new IPiece(gameManager);
		case 1:
			return new JPiece(gameManager);
		case 2:
			return new LPiece(gameManager);
		case 3:
			return new OPiece(gameManager);
		case 4:
			return new SPiece(gameManager);
		case 5:
			return new TPiece(gameManager);
		default:
			return new ZPiece(gameManager);
		}
	}
	
}
