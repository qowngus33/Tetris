package game;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import blocks.Block;

class itemModeTest {

	@Test
	public void getItemBlock() {

		ItemGameBoard itemGameBoard = null;
		try {
			itemGameBoard = new ItemGameBoard();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(itemGameBoard.getModeName());
		int[] countBlock = new int[11];
		int[] countPositionBlock = new int[6];
		int count = 1000000;

		for (int i = 0; i < count; i++) {
			Block block = itemGameBoard.getItemBlock();
			switch (block.getClass().toString()) {
			case "class blocks.IBlock":
				++countBlock[0];
				break;
			case "class blocks.JBlock":
				++countBlock[1];
				break;
			case "class blocks.LBlock":
				++countBlock[2];
				break;
			case "class blocks.OBlock":
				++countBlock[3];
				break;
			case "class blocks.SBlock":
				++countBlock[4];
				break;
			case "class blocks.TBlock":
				++countBlock[5];
				break;
			case "class blocks.ZBlock":
				++countBlock[6];
				break;
			case "class blocks.WBlock":
				++countBlock[7];
				break;
			case "class blocks.CBlock":
				++countBlock[8];
				break;
			case "class blocks.BBlock":
				++countBlock[9];
				break;
			case "class blocks.EBlock":
				++countBlock[10];
				break;
			}

			int n = 0;
			for (int j = 0; j < block.height(); j++)
				for (int k = 0; k < block.width(); k++)
					if (block.getShape(k, j) != 0) {
						if (block.getShape(k, j) == 2) {
							++countPositionBlock[n];
							k = block.width();
							j = block.height();
						}
						n++;
					}
		}

		System.out.println("countBlock: ");
		int LItemNum = 0;
		for (int i = 0; i < 11; i++) {
			System.out.print("[" + i + "] " + countBlock[i] + " ");
			if (i < 7)
				LItemNum += countBlock[i];
		}

		System.out.println("\ncountPositionBlock: ");

		int LItemNumCompare = 0;
		for (int i = 0; i < 6; i++) {
			System.out.print("[" + i + "] " + countPositionBlock[i] + " ");
			LItemNumCompare += countPositionBlock[i];
		}

		Assert.assertEquals(LItemNum, LItemNumCompare, 5);
	}

}
