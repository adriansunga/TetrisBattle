package game;

public class ZPiece extends Piece
{
	public ZPiece()
	{
		super();
		
		setUpOrientations("zpiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(0,4));
		location.add(new Loc(0,5));
		location.add(new Loc(1,5));
		location.add(new Loc(1, 6));
	}
}
