package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class ScoreBoardFile extends FileClass {

	private Vector<Pair> v;
	private int index = 0;

	public ScoreBoardFile() {
		v = new Vector<Pair>();
		fileName = "scoreboard.txt";
		getFile(fileName);
		getScoreBoard();
	}

	public void getScoreBoard() {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i] != null) {
				String[] splited = strings[i].split(" ");
				if (splited.length == 3 && splited[1].matches("[+-]?\\d*(\\.\\d+)?")) {
					Pair p = new Pair(splited[0], Integer.parseInt(splited[1]), splited[2]);
					v.add(p);
				}
			}
		}
		Collections.sort(v, new PairComparator());
	}

	public String readScoreBoard() throws IOException {
		String sb = new String();
		for (int i = 0; i < Math.min(v.size(), 20); i++) {
			sb += ("  " + String.format("%02d", i + 1) + String.format("%8s", v.get(i).name) + "      "
					+ String.format("%04d", v.get(i).score) + String.format("%12s", v.get(i).level) + "\n");
		}
		return sb;
	}

	public int writeScoreBoard(String name, String score, String level) throws IOException {
		v.add(new Pair(name, Integer.parseInt(score), level));
		Collections.sort(v, new PairComparator());
		for (int i = 0; i < Math.min(v.size(), 20); i++) {
			if (v.get(i).getName() == name)
				this.index = i;
		}
		BufferedReader br;
		String sb = new String();
		try {
			String line = null;
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				sb += line;
				sb += "\n";
			}
			br.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String temp = name + " " + score + " " + level; // to be edited
		bw.write(sb);
		bw.write(temp);
		bw.newLine();
		bw.close();

		return index;
	}

	public int isWritable() throws IOException {
		if (!v.isEmpty()) {
			if (v.size() < 20) {
				return 0;
			} else {
				return v.get(Math.min(v.size() - 1, 19)).score;
			}
		}
		return 0;
	}
}

class Pair {
	String name;
	Integer score;
	String level;

	public Pair() {
		this.name = "DF";
		this.score = 0;
		this.level = "normal";
	}

	public Pair(String name, Integer score, String level) {
		this.name = name;
		this.score = score;
		this.level = level;
	}

	public Integer getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
}

class PairComparator implements Comparator<Pair> {
	public int compare(Pair arg0, Pair arg1) {
		return arg1.score.compareTo(arg0.score);
	}
}
