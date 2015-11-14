package game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Piece
{	
	private Color color;

	private ArrayList<Point> locations;
	
	protected boolean[][] orientations;	
	private int index;	//orientation index
	
	public Piece()
	{
		//generate random color
		color = new Color((int)Math.random()*256, (int)Math.random()*256, (int)Math.random()*256);
	}
	
	
	public void moveDown()
	{
		for(Point p : locations)
		{
			p.setLocation(p.getX(), p.getY()+1);
		}
	}
	
	public Piece rotate()
	{
		index++;
		index %= 4;
		
		return this;
	}
	
	public boolean[] getOrientation()
	{
		return orientations[index];
	}
	
	public Color getColor()
	{
		return color;
	}
}
