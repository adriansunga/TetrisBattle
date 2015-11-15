package tetrisGUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TilePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3396514054069793883L;
	private Color color = Color.BLACK;
	
	public TilePanel(){
		setPreferredSize(new Dimension(25, 25));
        setBackground(color);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
		setBackground(color);
		
		//set the outline for the box	
		//if its black, make it gray
		if(color == Color.BLACK){
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		}
		//if its not black, i.e. the box is red, make it black
		else{
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		}
	}
}
