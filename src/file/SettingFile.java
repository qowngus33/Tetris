package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SettingFile extends FileClass {
	public SettingFile() {
		fileName = "setting.txt";
		file = new File(fileName);
		getFile(fileName);
	}
	//To be implemented.
	
	public void saveSetting(int size,int level) {
		try {
			writeFile(""+size+level);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getSizeSetting() {
		return Integer.parseInt(""+strings[0].charAt(0));
	}
	
	public int getLevelSetting() {
		return Integer.parseInt(""+strings[0].charAt(1));
	}
	
}