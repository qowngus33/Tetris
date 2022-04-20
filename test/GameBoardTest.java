package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import blocks.Block;
import blocks.GetRandomBlock;

class GameBoardTest {
	GameBoard obj = new GameBoard();
	GetRandomBlock getRandomBlock = new getRandomBlock();
	Block b = getRandomBlock.getRandomBlockMode(mode);
	
	@Test
	public void test() {
		
		
		for(int i=0;i<1000;i++) {
			Block c = b;
			c.rotate();
			c.rotate();
			if(c!=b)
				fail("Not correctly rotated.");
		}
		b = c;
		
	}

}
