package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TetrisClient extends Thread{
	private BufferedReader br;
	private PrintWriter pw;
	private String opponentName;
	private String name;
	
	public TetrisClient(String hostname, int port, String name) {
		this.name = name;
		try {
			Socket s = new Socket(hostname, port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.start();
			sendMessage("name:" + name);
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
			//go back to the main menu
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
