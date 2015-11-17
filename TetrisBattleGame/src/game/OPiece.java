package game;

public class OPiece extends Piece
{
	public OPiece()
	{
		super();
		
		setUpOrientations("opiece");
		
		initializeLocation();
	}
	
	public void initializeLocation()
	{
		location.add(new Loc(-2, 4));
		location.add(new Loc(-2, 5));
		location.add(new Loc(-1, 4));
		location.add(new Loc(-1, 5));
	}
}
