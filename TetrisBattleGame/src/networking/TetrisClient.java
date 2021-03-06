package networking;

import java.awt.CardLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.MySQLDriver;
import game.GameManager;

public class TetrisClient extends Thread{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String opponentName;
	private String name;
	private JPanel outerPanelForCardLayout;
	private CardLayout cardLayout;
	private GameManager gm;
	private Socket s;
	
	public TetrisClient(String hostname, int port, String name, JPanel outerPanelForCardLayout, CardLayout cardLayout) {
		this.name = name;
		this.cardLayout = cardLayout;
		this.outerPanelForCardLayout = outerPanelForCardLayout;
		try {
			s = new Socket(hostname, port);
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in TetrisClient constructor: " + ioe.getMessage());
		}
	}
	
	public JPanel getOuterPanelForCardLayout() {
		return outerPanelForCardLayout;
	}
	
	public CardLayout getCardLayout() {
		return cardLayout;
	}
	
	@Override
	public void run() {
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			while (true) {
				Object obj = null;
				try {
					obj = ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(obj == null)
					break;
				
				if(obj instanceof String){
					String message = (String) obj;
					parseMessage(message);
				}
				
				else{
					System.out.println("Did not catch object type");
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in TetrisClient.run(): " + ioe.getMessage());
		} finally {
			gm.getPlayMusic().stop();
			MySQLDriver msql = new MySQLDriver();
			msql.connect();
			msql.addScore(getUserName(), gm.getLinesCleared() - gm.getGarbageLinesReceived());
			msql.stop();
			if(!getGM().getGameOver())
				JOptionPane.showMessageDialog(null, "Your score has been entered into the score database if higher than your previous score.","Host has left the game!", JOptionPane.INFORMATION_MESSAGE);
			cardLayout.show(outerPanelForCardLayout, "welcomePanel");
			try {
				if(oos!=null)
					oos.close();
				if(ois!=null)
					ois.close();
				if(s!=null)
					s.close();
			} catch (Exception e) {
				System.out.println("exception in tetrisClient finally: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public GameManager getGM() {
		return gm;
	}
	
	public void setGameManager(GameManager gm) {
		this.gm = gm;
	}
	
	public void sendMessage(Object obj) {
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parseMessage(String message) {
		String[] parsedMessage = message.split(":");
		String command = parsedMessage[0];
		if(message.substring(0, 4).equals("name")) {
			parseName(message);
		}
		else if(command.equals("endgame")) {
			gm.endGame(getUserName());
		}
		else if(command.equals("score")) {
			MySQLDriver msql = new MySQLDriver();
			msql.connect();
			msql.addScore(parsedMessage[1], Integer.parseInt(parsedMessage[2]));
			msql.stop();
		}
		if(command.equals(("boardpanel"))){
			gm.updateOppBoardPanel(parsedMessage[1]);
		}
		
		
		//networking for garbage line
		if(command.equals(("garbageline"))){
			for(int i = 0; i < Integer.parseInt(parsedMessage[1]) ; i++){
				gm.receiveGarbageLine();
			}
		}
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
