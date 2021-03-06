package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import database.MySQLDriver;
import networking.TetrisClient;
import tetrisGUI.BoardPanel;
import tetrisGUI.NextPiecePanel;
import tetrisGUI.PlayMusic;
import tetrisGUI.TilePanel;

public class GameManager {
	private int garbageLinesReceived = 0;
	private int garbageLinesSent = 0;
	private Color[][] boardTiles;
	private final int matrixHeight = 20;
	private final int matrixWidth = 10;
	private Color backgroundColor = Color.BLACK;
	private int pieceSpeed = 1000; // in ms
	private int speedUpFactor = 0;
	private BoardPanel boardPanel;
	private PiecePlacer piecePlacer;
	private Piece currentPiece;
	private Timer dropPieceTimer;
	private TetrisClient tetrisClient;
	boolean firstTime = true;
	private int defaultSpeed = 1000;
	private boolean isTwoPlayer = false;
	private int level = 0;
	private NextPiecePanel nextPiecePanel;
	private int numLinesCleared = 0;
	private Random rand = new Random();
	private PlayMusic pm;
	
	private int receivedGarbageLines = 0;
	// TODO: if time, add more cute colors #thrive
	
	private boolean gameOver;
	private BoardPanel oppBoardPanel;

	public GameManager(PiecePlacer piecePlacer, TetrisClient tc, NextPiecePanel nextPiecePanel) {
		gameOver = false;
		this.tetrisClient = tc;
		boardTiles = new Color[matrixHeight][matrixWidth];
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				boardTiles[i][j] = backgroundColor;
			}
		}
		this.piecePlacer = piecePlacer;
		currentPiece = null;
		this.nextPiecePanel = nextPiecePanel;
		tc.sendMessage("name:" + tc.getUserName());
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public void setPlayMusic(PlayMusic pm) {
		this.pm = pm;
	}
	
	public PlayMusic getPlayMusic() {
		return pm;
	}

	public GameManager(PiecePlacer piecePlacer, NextPiecePanel nextPiecePanel) {
		gameOver = false;
		boardTiles = new Color[matrixHeight][matrixWidth];
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				boardTiles[i][j] = backgroundColor;
			}
		}
		this.piecePlacer = piecePlacer;
		currentPiece = null;
		this.nextPiecePanel = nextPiecePanel;

	}

	public void setPiecePlacer(PiecePlacer pp) {
		piecePlacer = pp;
	}

	public void setTwoPlayer(boolean isTwoPlayer) {
		this.isTwoPlayer = isTwoPlayer;
	}

	public void setBoardPanel(BoardPanel bp) {
		boardPanel = bp;
	}

	public void nextPiece() {
		// check if game is over
		if (currentPiece != null) {
			for (Loc l : currentPiece.getLocation()) {
				if (!l.isOnBoard()) {
					endGame();
					return;
				}
			}
		}
		
		currentPiece = piecePlacer.nextPiece();

		dropPiece();
		nextPiecePanel.setNextPiece();
	}

	public void dropPiece() {
		dropPieceTimer = new Timer(pieceSpeed, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (gameOver)  {
					return;
				}
				if (canMove("down")) {
					if (firstTime) {
						dropPieceTimer.setDelay(defaultSpeed);
						firstTime = false;
					}
					move("down");
				} else {
					setGarbageLines(receivedGarbageLines);
					receivedGarbageLines = 0;
					int numCleared = clearLines();
					if (isTwoPlayer) {
						sendGarbageLine(numCleared);
					}
					updateSpeed();
					dropPieceTimer.stop();
					pieceSpeed = 15;
					firstTime = true;
					nextPiece();
					
				}
			}
		});
		dropPieceTimer.start();
	}

	public void rotatePiece() {
		setToBackground(backgroundColor);

		currentPiece.rotate();

		setToBackground(currentPiece.getColor());

		updateView();
	}

	// TODO: only send -1 lines
	public void sendGarbageLine(int numToSend) {
		// Networking
		tetrisClient.sendMessage("garbageline:" + numToSend);
		garbageLinesSent = garbageLinesSent + numToSend;
	}

	public int getGarbageLinesSent() {
		return garbageLinesSent;
	}

	public void receiveGarbageLine() {
		// only cares about this turn
		receivedGarbageLines++;
		// overall
		garbageLinesReceived++;
//		try {
//			while (!hasLanded) {
//				Thread.sleep(10);
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	public void setGarbageLines(int lines) {
		for (int line =0; line < lines; line++) {
		// Determine location of the notch
		int notchLoc = rand.nextInt(10);

		// Populate row with vals accounting for notch
		boolean[] garbageRow = new boolean[10];
		for (int i = 0; i < garbageRow.length; i++) {
			if (i != notchLoc) {
				garbageRow[i] = true;
			} else {
				garbageRow[i] = false;
			}
		}

		// Change tile matrix accordingly

		// Push tiles up
		for (int r = 0; r < matrixHeight - 1; r++) {
			for (int c = 0; c < matrixWidth; c++) {
				boardTiles[r][c] = boardTiles[r + 1][c];
			}
		}
		// Add garbage row to the bottom
		for (int i = 0; i < garbageRow.length; i++) {
			if (garbageRow[i]) {
				boardTiles[matrixHeight - 1][i] = Color.GRAY;
			} else {
				boardTiles[matrixHeight - 1][i] = backgroundColor;
			}
		}

		// Refresh BoardPanel
		updateView();
		}
	}

	public int getGarbageLinesReceived() {
		return garbageLinesReceived;
	}

	// Check to see if you should send a line
	private boolean isLineFull(int rowNumber) {
		for (int i = 0; i < matrixWidth; i++) {
			if (boardTiles[rowNumber][i].equals(backgroundColor)) {
				return false;
			}
		}
		return true;
	}

	// Check to see if the piece can move down further
	// if the spot is occupied and it's not my piece then false
	private boolean canMove(String direction) {
		if (currentPiece.getLocation() == null) {
			return false;
		}

		for (Loc loc : currentPiece.getLocation()) {
			Loc nextPoint = nextPoint(loc, direction);
			if (nextPoint == null) {
				return false;
			}
			// if there's someone else's piece blocking you
			if ((new Loc(nextPoint.row, nextPoint.col).isOnBoard())
					&& boardTiles[nextPoint.row][nextPoint.col] != backgroundColor && !isMyPiece(nextPoint)) {
				return false;
			}
		}

		return true;
	}

	// if I occupy a certain given spot
	private boolean isMyPiece(Loc thisSpot) {
		for (Loc loc : currentPiece.getLocation()) {
			if (loc.row == thisSpot.row && loc.col == thisSpot.col) {
				return true;
			}
		}
		return false;
	}

	// Returns next lowest point (if null, then out of bounds)
	private Loc nextPoint(Loc l, String direction) {
		if (direction.equals("down")) {
			// Is at bottom?
			// System.out.println("p.getx= " + l.row);
			if (l.row >= matrixHeight - 1) {
				return null;
			}
			return new Loc(l.row + 1, l.col);
		} else if (direction.equals("left")) {
			if (l.col <= 0) {
				return null;
			}
			return new Loc(l.row, l.col - 1);
		} else { // right
			if (l.col >= matrixWidth - 1) {
				return null;
			}
			return new Loc(l.row, l.col + 1);
		}
	}

	public void move(String direction) {
		if (direction.equals("left") && canMove("left")) {
			setToBackground(backgroundColor);
			currentPiece.shiftLeft();
			setToBackground(currentPiece.getColor());
			updateView();
		} else if (direction.equals("right") && canMove("right")) {
			setToBackground(backgroundColor);
			currentPiece.shiftRight();
			setToBackground(currentPiece.getColor());
			updateView();
		} else if (direction.equals("down") && canMove("down")) {
			setToBackground(backgroundColor);
			currentPiece.dropDown();
			// if want to stop them from changing colors every move: remove
			// following two lines
			// int index = new Random().nextInt(pieceColors.length);
			// currentPiece.setColor(pieceColors[index]);
			setToBackground(currentPiece.getColor());
			updateView();
		}
	}

	// sets points where piece is to black so you can redraw the new positions
	private void setToBackground(Color color) {
		for (Loc l : currentPiece.getLocation()) {
			if (l.isOnBoard()) {
				boardTiles[l.row][l.col] = color;
			}
		}
	}

	public void testFunction() {
		currentPiece = new OPiece(this);
		// int index = new Random().nextInt(pieceColors.length);
		// currentPiece.setColor(pieceColors[index]);
		System.out.println("current piece: " + currentPiece);
		System.out.println("location arr size in testfunction: " + currentPiece.getLocation().size());
		setToBackground(currentPiece.getColor());
		System.out.println("current piece color: " + currentPiece.getColor());
		updateView();
	}

	public void startGame() {
		nextPiece();
	}

	public void zoomDown(int speedDelay) {
		pieceSpeed = speedDelay;
		dropPieceTimer.setDelay(pieceSpeed);
	}

	private int clearLines() {
		// to make sure we don't send gb lines for gb lines cleared
		int numGarbageLines = 0;
		ArrayList<Integer> linesToShift = new ArrayList<Integer>();
		for (int r = 0; r < matrixHeight; r++) {
			if (isLineFull(r)) {
				if (isGarbageLine(r)) {
					numGarbageLines++;
				}
				linesToShift.add(r);
				numLinesCleared++;
				for (int c = 0; c < matrixWidth; c++) {
					boardTiles[r][c] = backgroundColor;
				}
			}
		}

		for (Integer removedRow : linesToShift) {
			shiftDownBoard(removedRow);
		}
		updateView();
		return linesToShift.size() - numGarbageLines;
	}
	
	private boolean isGarbageLine(int row) {
		int numGrey = 0;
		for (int i = 0; i < matrixWidth; i++) {
			if (boardTiles[row][i].equals(Color.GRAY)) {
				numGrey++;
			}
		}
		if (numGrey == matrixWidth -1) {
			return true;
		}
		return false;
	}

	// recursion #lyf $wag
	private void shiftDownBoard(int row) {
		if (row == 0) {
			return;
		}
		for (int col = 0; col < matrixWidth; col++) {
			boardTiles[row][col] = boardTiles[row - 1][col];
		}
		shiftDownBoard(row - 1);
	}

	// Call this every time but line's clear and it will decide whether or not
	// to speed up/ by how much
	public void updateSpeed() {
		// should work b/c of integer arithmetic
		if(isTwoPlayer)
			return;
		//System.out.println("speed up factor: " + speedUpFactor);
		//System.out.println("numlinescleared/6: " + numLinesCleared/6);
		if (speedUpFactor != numLinesCleared/6) {
			// speed by 10% every lines cleared, needs to be checked
			speedUpFactor = numLinesCleared/6;
			if(!(defaultSpeed - 30*speedUpFactor <= 0))
				defaultSpeed = defaultSpeed - 30*speedUpFactor;
			level = speedUpFactor;
		}
		//System.out.println("default speed: " + defaultSpeed);
	}

	public int getLevel() {
		return level;
	}

	public int getLinesCleared() {
		return numLinesCleared;
	}

	public Color getTileColor(int row, int col) {
		if (row < 0) {
			return null;
		}

		return boardTiles[row][col];
	}

	private void updateView() {
		TilePanel[][] tileMatrix = boardPanel.getTileMatrix();
		for (int i = 0; i < matrixHeight; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (boardTiles[i][j] != backgroundColor) {
					// System.out.println("adding to board row, col: " + i + ",
					// " + j);
				}
				tileMatrix[i][j].setColor(boardTiles[i][j]);
			}
		}
		boardPanel.revalidate();
		boardPanel.repaint();
		
		if(isTwoPlayer && !gameOver){
			String temp = "boardpanel:";
			for (int i = 0; i < matrixHeight; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					if (boardTiles[i][j] == Color.BLACK) {
						temp += "q";
					}
					else if(boardTiles[i][j] == Color.RED){
						temp += "r";
					}
					else if(boardTiles[i][j] == Color.ORANGE){
						temp += "o";
					}
					else if(boardTiles[i][j] == Color.YELLOW){
						temp += "y";
					}
					else if(boardTiles[i][j] ==  Color.GREEN){
						temp += "g";
					}
					else if(boardTiles[i][j] ==  Color.BLUE){
						temp += "b";
					}
					else if(boardTiles[i][j] == Color.CYAN){
						temp += "c";
					}
					else if(boardTiles[i][j] == Color.MAGENTA){
						temp += "m";
					}
					else{
						temp += "a";
					}
				}
			}
			tetrisClient.sendMessage(temp);
		}

	}
	
	public void endGame(String winner) {
		gameOver = true;
		JOptionPane.showMessageDialog(null, "You have won! Your score has been entered into the score database if higher than your previous score!", 
				"YOU HAVE WON!", JOptionPane.INFORMATION_MESSAGE);
		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		msql.addScore(tetrisClient.getUserName(), numLinesCleared - garbageLinesReceived);
		tetrisClient.sendMessage("score:"+tetrisClient.getUserName()+":"+(numLinesCleared-garbageLinesReceived));
		msql.stop();
		pm.stop();
		tetrisClient.getCardLayout().show(tetrisClient.getOuterPanelForCardLayout(), "welcomePanel");
	}

	public void endGame() {
		gameOver = true;
		
		//dropPieceTimer.stop();
		
		if (!isTwoPlayer) {
			JOptionPane.showMessageDialog(null, "Game is over.");
			boardPanel.clickBackToMenuButton();
		} else {
			tetrisClient.sendMessage("endgame");
			JOptionPane.showMessageDialog(null, "You lost! Your score has been entered into the score database if higher than your previous score!", 
					"YOU HAVE LOST!", JOptionPane.INFORMATION_MESSAGE);
			MySQLDriver msql = new MySQLDriver();
			msql.connect();
			msql.addScore(tetrisClient.getUserName(), numLinesCleared - garbageLinesReceived);
			tetrisClient.sendMessage("score:"+tetrisClient.getUserName()+":"+(numLinesCleared-garbageLinesReceived));
			msql.stop();
			pm.stop();
			tetrisClient.getCardLayout().show(tetrisClient.getOuterPanelForCardLayout(), "welcomePanel");
		}
	}
	
	public void stopDropTimer()
	{
		dropPieceTimer.stop();
	}
	
	public void updateOppBoardPanel(String string) {
		if(oppBoardPanel != null && !gameOver)
		oppBoardPanel.setArray(string);
	}
	

	public void setOppBoardPanel(BoardPanel obp){
		System.out.println("Set Opp board panel");
		this.oppBoardPanel = obp;
	}
	
	
}
