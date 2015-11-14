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
		
	public PiecePlacer()
	{		
		index = 0;
		seed = 0;
		
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
		if(index == numPieces)
		{
			index = 0;
			Collections.shuffle(pieces, new Random(seed));
			seed++;
		}
		
		return pieces.get(index++);
	}
}
