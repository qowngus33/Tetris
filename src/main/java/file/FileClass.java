package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class FileClass {

	protected File file;
	protected String fileName;
	protected String [] strings;

	public FileClass() {
	}

	public void getFile(String filename) {
		fileName = filename;
		file = new File(filename);
		strings = new String[100];
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		BufferedReader br;
		try {
			String line = null;
			br = new BufferedReader(new FileReader(file));
			int index = 0;
			try {
				while ((line = br.readLine()) != null) {
					strings[index] = line;
					index++;
					if(index>=99)
						break;
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void writeFile(String content) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.newLine();
		bw.close();
	}

	public void eraseFile() throws IOException {
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("파일삭제 성공");
			} else {
				System.out.println("파일삭제 실패");
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}
	}
}
