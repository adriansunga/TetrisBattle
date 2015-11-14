package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TetrisClient extends Thread{
	private BufferedReader br;
	private PrintWriter pw;
	
	public TetrisClient(String hostname, int port) {
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
			//go back to the main menu
		}
	}
	
	public void parseMessage(String message) {
		
	}
}
