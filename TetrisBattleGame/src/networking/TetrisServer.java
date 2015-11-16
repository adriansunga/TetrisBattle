package networking;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TetrisServer {
	private Vector<TetrisThread> ttVector;
	private String username;
	private TetrisClient tc;
	private CardLayout cardLayout;
	private JPanel outerPanelForCardLayout;
	
	public TetrisServer(int port, String username, JPanel outerPanelForCardLayout, CardLayout cardLayout) {
		ttVector = new Vector<TetrisThread>();
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		this.username = username;
		try {
			ServerSocket ss = new ServerSocket(port);
			tc = new TetrisClient("localhost", port, username, outerPanelForCardLayout, cardLayout);
			while(ttVector.size() < 2) {
				Socket s = ss.accept();
				System.out.println("Connected: " + s.getInetAddress());
				TetrisThread tt = new TetrisThread(s, this);
				ttVector.add(tt);
				tt.start();
			}
		} catch(IOException ioe) {
			System.out.println("ioe in TetrisServer constructor: " + ioe.getMessage());
		}
	}
	
	public TetrisClient getTC() {
		return tc;
	}
	public void disconnect(TetrisThread tt) {
		JOptionPane.showMessageDialog(null, "Client has left the game.", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
		cardLayout.show(outerPanelForCardLayout, "welcomePanel");
	}
	
	public void sendMessageToAllClients(String message, TetrisThread sender) {
		System.out.println(message);
		for(TetrisThread tt : ttVector) {
			if(tt != sender)
				tt.sendMessage(message);
		}
	}
}