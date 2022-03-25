package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ScoreBoard {
	private Vector<Pair> v;
	
	public void getScoreBoardFile() throws IOException {
		v = new Vector<Pair>();
		File file=new File("scoreboard.txt");
		if(!file.exists())
			 file.createNewFile();
		BufferedReader br;
		try {
			 String line=null;
			br = new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null) {
				String[] splited = line.split(" ");
				Pair p = new Pair(splited[0],Integer.parseInt(splited[1]));
				v.add(p);
			}
			br.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Collections.sort(v, new PairComparator());
	}
	
	public String readScoreBoard() throws IOException {
		getScoreBoardFile();
		String sb = new String();
		
		for(int i = 0; i < Math.min(v.size(),20) ; i++) 
			sb += ("  "+String.format("%02d", i+1) +"      "+v.get(i).name + "      " + String.format("%04d", v.get(i).score)+"\n");
		
		return sb;
	}
	
	public String writeScoreBoard(String name, String score) throws IOException {
		File file=new File("scoreboard.txt");
        if(!file.exists())
            file.createNewFile();
        
        BufferedReader br;
        String sb = new String();
		try {
			 String line=null;
			br = new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null) {
				sb += line;
				sb += "\n";
			}
			br.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        
        String temp = name+" "+score;
        bw.write(sb);
        bw.write(temp);
        bw.newLine();
        bw.close();
        
		return null;
	}
	
	public void eraseScoreFile() throws IOException {
		File file=new File("scoreboard.txt");
        if(!file.exists())
            file.createNewFile();
    
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        bw.newLine();
        bw.close();
	}

	public int isWritable() throws IOException {
		if(!v.isEmpty()) {
			if(v.size()<20) {
				return 0;
			} else {
				return v.get(v.size()-1).score;
			}
		} else {
			getScoreBoardFile();
			if(!v.isEmpty()) {
				return v.get(v.size()-1).score;
			} else {
				return 0;
			}
		}
	}
}


class Pair {
	String name;
	Integer score;
	
	public Pair(String name, Integer score) {
		this.name = name;
		this.score = score;
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
		
