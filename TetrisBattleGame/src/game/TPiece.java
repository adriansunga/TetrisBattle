package game;

public class TPiece extends Piece
{
	public TPiece()
	{
		super();
		
		setUpOrientations("tpiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(-2,5));
		location.add(new Loc(-1,4));
		location.add(new Loc(-1,5));
		location.add(new Loc(-1,6));
	}
}
