package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Vector;

import setting.SaveFile;

public class SettingFile extends FileClass {

	public SettingFile() {
		fileName = "settingFile.txt";
		getFile(fileName);
	
	}

//	leftKey = saveFile.get("leftKey");
//    rightKey = saveFile.get("rightKey");
//    downKey = saveFile.get("downKey");
//    rotateKey = saveFile.get("rotateKey");
//    dropKey = saveFile.get("dropKey");
//    pauseKey = saveFile.get("pauseKey");
//
//    boardWidth = Integer.parseInt(saveFile.get("boardWidth"));
//    boardHeight = Integer.parseInt(saveFile.get("boardHeight"));
//    fontSize = Integer.parseInt(saveFile.get("fontSize"));
//
//    initInterval = Integer.parseInt(saveFile.get("initInterval"));
//    reduceSpeed = Integer.parseInt(saveFile.get("reduceSpeed"));
//    modeName = saveFile.get("modeName");
//    isColorBlind = Boolean.parseBoolean(saveFile.get("isColorBlind"));
	
	public String get(String str) {
		switch (str) {
		case "leftKey":
			return strings[0];
		case "rightKey":
			return strings[1];
		case "downKey":
			return strings[2];
		case "rotateKey":
			return strings[3];
		case "dropKey":
			return strings[4];
		case "pauseKey":
			return strings[5];
		case "initInterval":
			return strings[6];
		case "reduceSpeed":
			return strings[7];
		case "modeName":
			return strings[8];
		case "boardWidth":
			return strings[9];
		case "boardHeight":
			return strings[10];
		case "fontSize":
			return strings[11];
		case "isColorBlind":
			return strings[12];
		}
		return null;
	}

	public void saveSetting(SaveFile savefile) {
		String sb = new String();
		sb += savefile.getLeftKey();
		sb += "\n";
		sb += savefile.getRightKey();
		sb += "\n";
		sb += savefile.getDownKey();
		sb += "\n";
		sb += savefile.getRotateKey();
		sb += "\n";
		sb += savefile.getDropKey();
		sb += "\n";
		sb += savefile.getPauseKey();
		sb += "\n";
		sb += savefile.getInitInterval();
		sb += "\n";
		sb += savefile.getReduceSpeed();
		sb += "\n";
		sb += savefile.getModeName();
		sb += "\n";
		sb += savefile.getBoardWidth();
		sb += "\n";
		sb += savefile.getBoardHeight();
		sb += "\n";
		sb += savefile.getFontSize();
		sb += "\n";
		sb += savefile.getIsColorBlind();
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(sb);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
