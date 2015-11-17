package game;

public class LPiece extends Piece
{
	public LPiece()
	{
		super();	
		
		setUpOrientations("lpiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(0,4));
		location.add(new Loc(1,4));
		location.add(new Loc(2,4));
		location.add(new Loc(2,5));
	}
}