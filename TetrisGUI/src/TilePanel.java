import java.awt.Color;

import javax.swing.JPanel;

public class TilePanel extends JPanel{
	Color color = Color.BLACK;
	
	public TilePanel(){
        setBackground(color);  
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
		setBackground(color);
	}
}
