package game;

public class LPiece extends Piece
{
	public LPiece(GameManager gm)
	{
		super(gm);	
				
		initializeLocation();
		
		setUpOrientations();
	}
	
	protected void initializeLocation()
	{
		location.add(new Loc(-3,4));
		location.add(new Loc(-2,4));
		location.add(new Loc(-1,4));
		location.add(new Loc(-1,5));
	}
	
	protected void setUpOrientations()
	{
		//First orientation;
		for(int i = 0; i < 3; i++)
		{
			orientations[0][i] = new Loc((i+1)*(-1), 4);
		}
		orientations[0][3] = new Loc(-1, 5);
		
		//Second orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[1][i] = new Loc(-2, i+4);
		}
		orientations[1][3] = new Loc(-1,4); 
		
		//Third orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[2][i] = new Loc((i+1)*(-1), 5);
		}
		orientations[2][3] = new Loc(-3, 4);
		
		//Fourth orientation
		for(int i = 0; i < 3; i++)
		{
			orientations[3][i] = new Loc(-2, i+4);
		}
		orientations[3][3] = new Loc(-3,6);
	}
}