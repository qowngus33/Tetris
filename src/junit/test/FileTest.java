package junit.test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import file.ScoreBoardFile;

class FileTest {

	@Test
	void test() {
		ScoreBoardFile sb = null;
		try {
			sb = new ScoreBoardFile();
			String temp = sb.readScoreBoard();
			System.out.println(temp);
		} catch (NumberFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
//			sb.writeScoreBoard("SY", "10");
//			sb.writeScoreBoard("DG", "40");
//			sb.writeScoreBoard("SE", "30");
//			sb.writeScoreBoard("SJ", "70");
//			sb.writeScoreBoard("MG", "100");
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
