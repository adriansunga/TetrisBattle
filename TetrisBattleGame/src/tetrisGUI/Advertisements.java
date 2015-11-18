package tetrisGUI;

import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Advertisements extends Thread{
		private long startTime;
		private JButton jb;
		private int index = 0;
		private Vector<String> pictures;
		private Vector<String> text;
		private JTextField jtf;
		
		public Advertisements(JButton jb, JTextField jtf){
			this.jb = jb;
			this.jtf = jtf;
			startTime = System.currentTimeMillis();
			pictures = new Vector<String>(); 
			text = new Vector<String>(); 
			
			pictures.addElement("201.jpg");
			text.addElement("Do you have 40 hours a week? Take my class, I dare you.");
			
			pictures.addElement("car.jpg");
			text.addElement("15 minutes could save you 15% or more!");
			
			pictures.addElement("law.jpg");
			text.addElement("Do you have a DUI? You cant afford not to call me!");
			
			pictures.addElement("vote.png");
			text.addElement("#Miller2016");
			
			ImageIcon originalButton = new ImageIcon("images/" + pictures.elementAt(index));
			Image img = originalButton.getImage();
			Image newImage = img.getScaledInstance(originalButton.getIconWidth()/4, originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
			ImageIcon ButtonImage1 = new ImageIcon(newImage);
			jb.setIcon(ButtonImage1);
			jtf.setText(text.elementAt(index));
		}
		
		public void run(){
			while(true){
				long currTime = System.currentTimeMillis();
				//make sure first picture is there atleast 15 seconds.
				if((currTime-startTime) /1000 < 15){
					continue;
				}
				if(((currTime-startTime) /1000) % 15 == 0){
					index++;
					if(index >= pictures.size()){
						index = 0;
					}
					ImageIcon originalButton = new ImageIcon("images/" + pictures.elementAt(index));
					Image img = originalButton.getImage();
					Image newImage = img.getScaledInstance(originalButton.getIconWidth()/4, originalButton.getIconHeight()/4, java.awt.Image.SCALE_SMOOTH);
					ImageIcon ButtonImage1 = new ImageIcon(newImage);
					jb.setIcon(ButtonImage1);
					jtf.setText(text.elementAt(index));
				}
				Thread.yield();
			}
		}
}
