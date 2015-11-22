package networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TetrisThread extends Thread {
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private TetrisServer ts;
	
	public TetrisThread(Socket s, TetrisServer ts) {
		this.ts = ts;
		this.s = s;
	}
	
	public void sendMessage(Object obj) {
		if(oos != null) {
			try {
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
				if(obj == null) {
					break;
				}
				ts.sendMessageToAllClients(obj, this);
			}
		} catch(IOException ioe) {
			System.out.println("ioe in TetrisThread.run(): " + ioe.getMessage());
		} finally {
			ts.disconnect(this);
			try {
				if(oos!=null)
					oos.close();
				if(ois!=null)
					ois.close();
				if(s!=null)
					s.close();
			} catch (Exception e) {
				System.out.println("exception in tetrisThread finally: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
