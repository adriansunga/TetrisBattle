package tetrisGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.MySQLDriver;

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
		MySQLDriver msql = new MySQLDriver();
		msql.connect();
		ArrayList<String> names = msql.readScores();
		ArrayList<Integer> scores = msql.readScores2();
		msql.stop();
		for(int i = 0; i < names.size(); i++)
			tm.addRow(new Object[] {names.get(i), scores.get(i)});
	}
}
