package game;

public class TPiece extends Piece
{
	public TPiece(GameManager gm)
	{
		super(gm);
		
		//setUpOrientations("tpiece");
		
		initializeLocation();
		
		setUpOrientations();
	}
	
	protected void initializeLocation()
	{
		location.add(new Loc(-2,5));
		location.add(new Loc(-1,4));
		location.add(new Loc(-1,5));
		location.add(new Loc(-1,6));
	}
	
	protected void setUpOrientations()
	{
		//First Orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[0][i] = new Loc(-2, i+4);
		}
		orientations[0][3] = new Loc(-3, 5);
		
		//Second Orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[1][i] = new Loc((i+1)*(-1), 5);
		}
		orientations[1][3] = new Loc(-2, 6);
		
		//Third Orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[2][i] = new Loc(-2, i+4);
		}
		orientations[2][3] = new Loc(-1, 5);
		
		//Fourth Orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[3][i] = new Loc((i+1)*(-1), 5);
		}
		orientations[3][3] = new Loc(-2, 4);
		
	}
}
