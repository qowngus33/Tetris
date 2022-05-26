package scoreboard;

import blocks.Block;
import game.GameBoard;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ScoreBoardMenuTest {
	ScoreBoardMenu sb1 = new ScoreBoardMenu();
	ScoreBoardMenu sb2;

	@Test
	public void ScoreBoardMenu(){
		try {
			sb2 = new ScoreBoardMenu(-1,null,null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			sb2 = new ScoreBoardMenu(10,"10","HARD");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void isAlphaOrDigit(){
		try {
			sb2 = new ScoreBoardMenu(10,"10","HARD");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Assert.assertTrue(sb2.isAlphaOrDigit("0"));
		Assert.assertTrue(sb2.isAlphaOrDigit("9999"));
		Assert.assertTrue(sb2.isAlphaOrDigit("999w"));
		Assert.assertTrue(sb2.isAlphaOrDigit("a"));
		Assert.assertTrue(sb2.isAlphaOrDigit("A"));
		Assert.assertTrue(sb2.isAlphaOrDigit("e"));
		Assert.assertTrue(sb2.isAlphaOrDigit("z"));
		Assert.assertTrue(sb2.isAlphaOrDigit("Z"));
		Assert.assertFalse(sb2.isAlphaOrDigit("ahn"));
		Assert.assertFalse(sb2.isAlphaOrDigit("+"));
		Assert.assertFalse(sb2.isAlphaOrDigit("#"));
		Assert.assertFalse(sb2.isAlphaOrDigit("#"));
		Assert.assertFalse(sb2.isAlphaOrDigit("ppp+"));

	}

	@Test
	public void tryWriteScoreboard(){
		try {
			sb2 = new ScoreBoardMenu(10,"10","HARD");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		sb2.nameEnter.setText("Hi");
		sb2.tryWriteScoreboard();
	}


}
