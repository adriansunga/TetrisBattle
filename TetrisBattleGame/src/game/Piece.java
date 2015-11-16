package game;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Piece
{	
	private Color color;

	private ArrayList<Point> location;
	
	protected boolean[][][] orientations;	
	private int index;	//orientation index
	
	public Piece()
	{
		//generate random color
		color = new Color((int)Math.random()*256, (int)Math.random()*256, (int)Math.random()*256);
		orientations = new boolean[4][4][4];
	}
	
	public void setLocation(ArrayList<Point> location) {
		this.location = location;
	}
	
	public void dropDown()
	{
		for(Point p : location)
		{
			p.setLocation(p.getX() + 1, p.getY());
		}
	}
	
	public void shiftRight()
	{
		for(Point p : location)
		{
			p.setLocation(p.getX(), p.getY() -1);
		}
	}
	
	public void shiftLeft()
	{
		for(Point p : location)
		{
			p.setLocation(p.getX(), p.getY() + 1);
		}
	}
	
	
	public Piece rotate()
	{
		index++;
		index %= orientations.length;
		
		return this;
	}
	
	protected void setUpOrientations(String pieceType)
	{
		Scanner scan = null;
		try
		{
			scan = new Scanner(new File("src/game/pieces_blueprint.txt"));
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(scan == null)
		{
			return;
		}
		
		while(scan.hasNextLine())
		{			
			String line = scan.nextLine();
									
			if(line.equals(pieceType))
			{
				for(int i = 0; i < 4; i ++)
				{
					for(int r = 0; r < 4; r++)
					{
						String[] row = scan.nextLine().split(" ");
						
						for(int c = 0; c < row.length; c++)
						{
							if(row[c].equals("0"))
							{
								orientations[i][r][c] = false;
								
							}
							else
							{
								orientations[i][r][c] = true;
							}
						}						
					}
					scan.nextLine();
				}
				
				scan.close();
				return;
			}
		}		
	}
	
	
	public ArrayList<Point> getLocation()
	{
		return location;
	}
	
	public boolean[][] getOrientation()
	{
		return orientations[index];
	}
	
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color newColor) {
		color = newColor;
	}
}
