package game;

public class Loc {
	public int row;
	public int col;

	public Loc(int r, int c)
	{
		row = r;
		col = c;
	}
	
	public boolean isOnBoard()
	{
		return row >= 0 && col >= 0 && row < 20 && col < 10;
	}
}