package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ScoresList extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable scoresTable;
	private DefaultTableModel tm;
	
	ScoresList() {
		super("Top Scores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		String [] columnNames = new String[] {"Name", "Score"};
		Object[][] rowData = new Object[][] {{"", ""}};
		tm = new DefaultTableModel(rowData, columnNames);
		scoresTable = new JTable(tm);
		scoresTable.setShowGrid(true);
		readInNames();
		tm.removeRow(0);
		JScrollPane jsp = new JScrollPane(scoresTable);
		TableColumnModel columnModel = scoresTable.getColumnModel();
		for(int i = 0; i < 2; i++)
			columnModel.getColumn(i).setPreferredWidth(150);
		System.out.println(scoresTable.getRowCount());
		setSize(new Dimension(300, (scoresTable.getRowCount() + 3)*scoresTable.getRowHeight()));
		jsp.setPreferredSize(new Dimension(200, (scoresTable.getRowCount()+ 3)*scoresTable.getRowHeight()));
		setMaximumSize(new Dimension(300, 400));
		add(jsp, BorderLayout.CENTER);
		setLocationRelativeTo(null);
	}
	
	public void readInNames() {
		File file = new File("scores.txt");
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<String> names = new ArrayList<String>();
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String cName = scanner.nextLine();
				int cScore = Integer.parseInt(scanner.nextLine());
				scores.add(cScore);
				names.add(cName);
			}
			int j = 0;
			int temp = 0;
			String temp2 = "";
			for(int i = 0; i < scores.size(); i++){
			  j = i;
			  for(int k = i; k < scores.size(); k++){
			    if(scores.get(j) > scores.get(k)){
			      j = k;
			    }
			  }
			  temp = scores.get(i);
			  temp2 = names.get(i);
			  scores.add(i, scores.get(j));
			  scores.remove(i+1);
			  names.add(i, names.get(j));
			  names.remove(i+1);
			  scores.add(j, temp);
			  scores.remove(j+1);
			  names.add(j, temp2);
			  names.remove(j+1);
			}
			Collections.reverse(names);
			Collections.reverse(scores);
			for(int i = 0; i < names.size(); i++)
				tm.addRow(new Object[] {names.get(i), scores.get(i)});
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void addScore(String name, int score) {
		File file = new File("scores.txt");
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(name);
			bw.newLine();
			bw.write("" + score);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
