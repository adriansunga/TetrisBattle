package game;

public class IPiece extends Piece
{
	public IPiece()
	{
		super();
		
		setUpOrientations("ipiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(0,4));
		location.add(new Loc(1,4));
		location.add(new Loc(2,4));
		location.add(new Loc(3,4));
	}
}
