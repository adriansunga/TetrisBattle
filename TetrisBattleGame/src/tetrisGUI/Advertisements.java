package tetrisGUI;

import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Advertisements extends Thread{
		private long startTime;
		private JButton jb;
		private int index = 0;
		private Vector<String> pictures;
		private Vector<String> text;
		private JLabel jl;
		
		public Advertisements(JButton jb, JLabel jl){
			this.jb = jb;
			this.jl = jl;
			startTime = System.currentTimeMillis();
			pictures = new Vector<String>(); 
			text = new Vector<String>(); 
			
			pictures.addElement("201.jpg");
			text.addElement("On the couch 40hrs a week? Take my class!");
			
			pictures.addElement("1car.jpg");
			text.addElement("15 minutes could save you 15% or more!");
			
			pictures.addElement("1law.jpg");
			text.addElement("DUI? You cant afford not to call me!");
			
			pictures.addElement("1vote.png");
			text.addElement("#Miller2016");
			
			ImageIcon originalButton = new ImageIcon("images/" + pictures.elementAt(index));
			Image img = originalButton.getImage();
			Image newImage = img.getScaledInstance(originalButton.getIconWidth(), originalButton.getIconHeight(), java.awt.Image.SCALE_SMOOTH);
			ImageIcon ButtonImage1 = new ImageIcon(newImage);
			
			jb.setIcon(ButtonImage1);
			jl.setText(text.elementAt(index));
		}
		
		public void run(){
			while(true){
				long currTime = System.currentTimeMillis();
				//make sure first picture is there atleast 15 seconds.
				if((currTime-startTime) /1000 < 7){
					continue;
				}
				if(((currTime-startTime) /1000) % 7 == 0){
					index++;
					if(index >= pictures.size()){
						index = 0;
					}
					
					ImageIcon originalButton = new ImageIcon("images/" + pictures.elementAt(index));
					Image img = originalButton.getImage();
					Image newImage = img.getScaledInstance(originalButton.getIconWidth(), originalButton.getIconHeight(), java.awt.Image.SCALE_SMOOTH);
					ImageIcon ButtonImage1 = new ImageIcon(newImage);
					
					
					jb.setIcon(ButtonImage1);
					jl.setText(text.elementAt(index));
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				Thread.yield();
			}
		}	
}
