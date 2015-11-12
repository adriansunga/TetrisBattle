import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetrisClientWindow extends JFrame{
	private static final long serialVersionUID = 123456789;
	//will be a set size for the entire window. Both mix and max are the same
	private final static Dimension minSize = new Dimension(960,640);
	private final static Dimension maxSize = new Dimension(960,640);
	
	CardLayout cardLayout;
	JPanel outerPanelForCardLayout;
	
	public TetrisClientWindow(){
		super("Tetris Battle");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
		outerPanelForCardLayout = new JPanel();
		outerPanelForCardLayout.setLayout(cardLayout);
		
		//create all panels to add to cardLayout
		//give them all a name
		LoginPanel loginPanel = new LoginPanel(cardLayout);
		outerPanelForCardLayout.add(loginPanel, "loginPanel");
		//other layouts
		//other layouts
		
		add(outerPanelForCardLayout);
		
		//My notes - how to use cardlayouts
		/*
		JPanel secondPanel = new JPanel();
		JButton button2 = new JButton("2");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				c1.show(outerPanel, "first");
			}
		});
		
		outerPanel.add(firstPanel, "first");
		outerPanel.add(secondPanel, "second");
		*/
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		System.out.println("Main program started in TetrisClientWindow");
		TetrisClientWindow TCW = new TetrisClientWindow();
	}
	
}
