package junit.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import file.ScoreBoard;

class FileTest {

	@Test
	void test() {
		ScoreBoard sb = new ScoreBoard();
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
		try {
			String temp = sb.readScoreBoard();
			System.out.println(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
