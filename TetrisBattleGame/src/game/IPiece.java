package game;

public class IPiece extends Piece
{
	public IPiece()
	{
		super();
				
		initializeLocation();
		
		setUpOrientations();
	}
	
	protected void initializeLocation()
	{
		location.add(new Loc(-4,4));
		location.add(new Loc(-3,4));
		location.add(new Loc(-2,4));
		location.add(new Loc(-1,4));
	}
	
	protected void setUpOrientations()
	{
		//First orientation
		for(int i = 0; i < 4; i++)
		{
			orientations[0][i] = new Loc((i+1)*(-1),4);
		}
		
		//Second orientation
		for(int i = 0; i < 4; i++)
		{
			orientations[1][i] = new Loc(-3,i+3);
		}
		
		//Third orientation
		for(int i = 0; i < 4; i++)
		{
			orientations[2][i] = new Loc((i+1)*(-1),5);
		}
		
		//Fourth orientation
		for(int i = 0; i < 4; i++)
		{
			orientations[3][i] = new Loc(-2,i+3);
		}
	}
}
