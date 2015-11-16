package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TetrisThread extends Thread {
	private Socket s;
	private BufferedReader br;
	private PrintWriter pw;
	private TetrisServer ts;
	
	public TetrisThread(Socket s, TetrisServer ts) {
		this.ts = ts;
		this.s = s;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
		} catch (IOException ioe) {
			System.out.println("ioe in TetrisThread constructor: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(String message) {
		if(pw != null) {
			pw.println(message);
			pw.flush();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String message = br.readLine();
				if(message == null) {
					break;
				}
				ts.sendMessageToAllClients(message, this);
			}
		} catch(IOException ioe) {
			System.out.println("ioe in TetrisThread.run(): " + ioe.getMessage());
		} finally {
			ts.disconnect(this);
		}
	}
}
