package game;

public class SPiece extends Piece
{
	public SPiece()
	{
		super();
				
		initializeLocation();
		
		setUpOrientations();
	}
	
	protected void initializeLocation()
	{
		location.add(new Loc(-2,6));
		location.add(new Loc(-2,5));
		location.add(new Loc(-1,5));
		location.add(new Loc(-1, 4));
	}
	
	protected void setUpOrientations()
	{
		//First orientation
		orientations[0][0] = new Loc(-1, 4);
		orientations[0][1] = new Loc(-1, 5);
		orientations[0][2] = new Loc(-2, 5);
		orientations[0][3] = new Loc(-2, 6);
		
		//Second orientation
		orientations[1][0] = new Loc(-3, 4);
		orientations[1][1] = new Loc(-2, 4);
		orientations[1][2] = new Loc(-2, 5);
		orientations[1][3] = new Loc(-1, 5);
		
		//Third orientation
		orientations[2][0] = new Loc(-2, 4);
		orientations[2][1] = new Loc(-2, 5);
		orientations[2][2] = new Loc(-3, 5);
		orientations[2][3] = new Loc(-3, 6);
		
		//Fourth orientation
		orientations[3][0] = new Loc(-3, 5);
		orientations[3][1] = new Loc(-2, 5);
		orientations[3][2] = new Loc(-2, 6);
		orientations[3][3] = new Loc(-1, 6);
	
				
	}
}
