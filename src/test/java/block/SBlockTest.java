package block;

import blocks.BBlock;
import blocks.Block;
import blocks.SBlock;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SBlockTest {

	private Block block = new SBlock();
	private Block block2 = new SBlock();

	@Test
	public void getShape() {
		Assertions.assertEquals(0, block.getShape(block.height()+100,block.width()+100));
	}

	@Test
	public void rotate() {
		// Rotate the block 90 deg. clockwise
		block.rotate();
		block.rotate();
		block.rotate();
		block.rotate();
		for(int i=0;i<block2.height();i++)
			for(int j=0;j<block2.width();j++)
				Assertions.assertEquals(block2.getShape(i,j), block.getShape(i,j));
	}
}
