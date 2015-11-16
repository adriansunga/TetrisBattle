package networking;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TetrisClient extends Thread{
	private BufferedReader br;
	private PrintWriter pw;
	private String opponentName;
	private String name;
	private JPanel outerPanelForCardLayout;
	private CardLayout cardLayout;
	
	public TetrisClient(String hostname, int port, String name, JPanel outerPanelForCardLayout, CardLayout cardLayout) {
		this.name = name;
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		try {
			Socket s = new Socket(hostname, port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in TetrisClient constructor: " + ioe.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String message = br.readLine();
				if(message == null)
					break;
				parseMessage(message);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in TetrisClient.run(): " + ioe.getMessage());
		} finally {
			JOptionPane.showMessageDialog(null, "Host has left the game.", "Tetris Battle Login", JOptionPane.INFORMATION_MESSAGE);
			cardLayout.show(outerPanelForCardLayout, "welcomePanel");
		}
	}
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void parseMessage(String message) {
		if(message.substring(0, 4).equals("name"))
			parseName(message);
	}
	
	public String getUserName() {
		return name;
	}
	
	public String getOpponentName() {
		return opponentName;
	}
	
	public void parseName(String message) {
		opponentName = message.substring(5);
	}
}
