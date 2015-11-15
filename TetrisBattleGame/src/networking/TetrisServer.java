package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TetrisServer {
	private Vector<TetrisThread> ttVector;
	
	public TetrisServer(int port) {
		ttVector = new Vector<TetrisThread>();
		try {
			ServerSocket ss = new ServerSocket(port);
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
	
	public void sendMessageToAllClients(String message, TetrisThread sender) {
		System.out.println(message);
		for(TetrisThread tt : ttVector) {
			if(tt != sender)
				tt.sendMessage(message);
		}
	}
}
