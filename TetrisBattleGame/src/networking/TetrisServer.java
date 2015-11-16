package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TetrisServer {
	private Vector<TetrisThread> ttVector;
	private String username;
	private TetrisClient tc;
	
	public TetrisServer(int port, String username) {
		ttVector = new Vector<TetrisThread>();
		this.username = username;
		try {
			ServerSocket ss = new ServerSocket(port);
			tc = new TetrisClient("localhost", port, username);
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
	
	public void sendMessageToAllClients(String message, TetrisThread sender) {
		System.out.println(message);
		for(TetrisThread tt : ttVector) {
			if(tt != sender)
				tt.sendMessage(message);
		}
	}
}