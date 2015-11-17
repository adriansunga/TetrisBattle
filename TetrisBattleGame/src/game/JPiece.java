package game;

public class JPiece extends Piece
{
	public JPiece()
	{
		super();
		
		setUpOrientations("jpiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(0,5));
		location.add(new Loc(1,5));
		location.add(new Loc(2,5));
		location.add(new Loc(2,4));
	}
}
