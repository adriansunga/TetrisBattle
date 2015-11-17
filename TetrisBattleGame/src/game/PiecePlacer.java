package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PiecePlacer
{
	private Piece[] pieceTypes;
	private ArrayList<Piece> pieces;
	
	private final int numPieces = 70;
	
	private int index;
	private int seed;
	
	private Piece nextNextPiece;
	private Piece currentPiece;
		
	public PiecePlacer()
	{		
		index = 0;
		seed = 0;
		
		currentPiece = null;
		nextNextPiece = null;
		
		
		pieceTypes = new Piece[7];
		pieceTypes[0] = new IPiece();
		pieceTypes[1] = new JPiece();
		pieceTypes[2] = new LPiece();
		pieceTypes[3] = new OPiece();
		pieceTypes[4] = new SPiece();
		pieceTypes[5] = new TPiece();
		pieceTypes[6] = new ZPiece();
		
		pieces = new ArrayList<Piece>();
				
		int type = 0;
		for(int i = 0; i < numPieces; i++)
		{			
			pieces.add(pieceTypes[type]);
			
			if(i%10 == 9)
			{
				type++;
			}
		}	
		
		Collections.shuffle(pieces);
	}
	
	public Piece nextPiece()
	{
		if(index == numPieces-1)
		{
			index = 0;
			Collections.shuffle(pieces, new Random(seed));
			seed++;

			currentPiece = nextNextPiece;
			nextNextPiece = pieces.get(0);
			
			return currentPiece;
		}

		nextNextPiece = pieces.get(index+1);
		currentPiece = pieces.get(index++);
		
		return currentPiece;
	}
	
	public Piece nextNextPiece()
	{
		return nextNextPiece();
	}
}
