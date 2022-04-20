package file;

import java.io.File;
import java.io.IOException;


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
		if(strings.length==2)
			return Integer.parseInt(""+strings[0].charAt(0));
		return 1;
	}
	
	public int getLevelSetting() {
		if(strings.length==2)
			return Integer.parseInt(""+strings[0].charAt(0));
		return 1;
	}
	
}