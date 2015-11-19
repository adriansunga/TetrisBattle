package game;

public class OPiece extends Piece
{
	public OPiece(GameManager gm)
	{
		super(gm);
				
		initializeLocation();
		
		setUpOrientations();
	}
	
	protected void initializeLocation()
	{
		location.add(new Loc(-2, 4));
		location.add(new Loc(-2, 5));
		location.add(new Loc(-1, 4));
		location.add(new Loc(-1, 5));
	}
	
	protected void setUpOrientations()
	{
		for(int i = 0; i < 4; i++)
		{
			orientations[i][0] = new Loc(-2,4);
			orientations[i][1] = new Loc(-2,5);
			orientations[i][2] = new Loc(-1,4);
			orientations[i][3] = new Loc(-1,5);
		}
	}
}
