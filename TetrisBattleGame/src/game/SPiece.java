package game;

public class SPiece extends Piece
{
	public SPiece()
	{
		super();
		
		setUpOrientations("spiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(0,6));
		location.add(new Loc(0,5));
		location.add(new Loc(1,5));
		location.add(new Loc(1, 4));
	}
}
