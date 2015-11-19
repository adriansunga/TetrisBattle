package game;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Piece {
	private Color color;

	protected ArrayList<Loc> location;

	//protected boolean[][][] orientations;
	
	protected Loc[][] orientations;
	private int index; // orientation index
	
	private int verticalCount;
	private int lateralCount;
	
	//private GameManager gameManager;

	public Piece() {
		//gameManager = gm;
		
		//orientations = new boolean[4][4][4];
		orientations = new Loc[4][4];
		
		location = new ArrayList<Loc>();
		
		verticalCount = 0;
		lateralCount = 0;
	}
	
	protected abstract void initializeLocation();
	
	protected abstract void setUpOrientations();

	public void setLocation(ArrayList<Loc> location) {
		this.location = location;
	}

	public void dropDown() {
		//System.out.println("dropdown location array size: " + location.size());
		
		for (int i = 0; i < location.size(); i++) {
			//System.out.println("location before = " + location.get(i));
			Loc l = location.get(i);
			l.row++;
			location.set(i, l);
			//System.out.println("location after = " + location.get(i));
		}
		
		verticalCount++;
	}

	public void shiftRight() {
		for (int i = 0; i < location.size(); i++) {
			Loc l = location.get(i);
			l.col++;
			location.set(i, l);
		}
		
		lateralCount++;
	}

	public void shiftLeft() {
		for (int i = 0; i < location.size(); i++) {
			Loc l = location.get(i);
			l.col--;
			location.set(i, l);
		}
		
		lateralCount--;
	}

	public void rotate() {
		index++;
		index %= orientations.length;

		ArrayList<Loc> temp = new ArrayList<Loc>();
		for(int i = 0; i < 4; i++)
		{
			Loc l = orientations[index][i];
			int row = l.row + verticalCount;
			int col = l.col + lateralCount;
			
			
			//TODO: || gameManager.getTileColor(row,col) != Color.BLACK add to if statement
			// to check whether or not it collides with existing colored tile
			if(row < 0 || row >= 20 || col < 0 || col >= 10)
			{
				System.out.println("Rotation collision");
				return;
			}
			
			temp.add(new Loc(row, col));
		}
		
		location = temp;
	}

	
	public ArrayList<Loc> getLocation() {
		return location;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color newColor) {
		color = newColor;
	}
}
