package game;

public class OPiece extends Piece
{
	public OPiece()
	{
		super();
		
		setUpOrientations("opiece");
		
		location.add(new Loc(0, 4));
		location.add(new Loc(0, 5));
		location.add(new Loc(1, 4));
		location.add(new Loc(1, 5));
	}
}
