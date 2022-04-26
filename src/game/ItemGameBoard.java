package game;

import java.util.Random;
import blocks.*;

public class ItemGameBoard extends GameBoard {

	private static final long serialVersionUID = 1L;
	private int lineChange = 1;
	private int count = 1;

	public ItemGameBoard() throws java.io.IOException {
	}

	protected Block getItemBlock() {
		Random rnd = new Random(System.currentTimeMillis());
		int block = rnd.nextInt(1000) % 5;
		switch (block) {
			case 0:
				return new WBlock();
			case 1:
				Block temp = getRandomBlock.getRandomBlockMode(modeName);
				rnd = new Random(System.currentTimeMillis());
				block = rnd.nextInt(1000) % 4;
				int count = 0;
				for(int i=0;i<temp.width();i++)
					for(int j=0;j<temp.height();j++)
						if(temp.getShape(i, j)==1) {
							if(count==block) {
								temp.setShape(i, j, 2);
								temp.setItem("L");
								return temp;
							}
							count++;
						}
				temp.setItem("L");
				return temp;
			case 2:
				return new CBlock();
			case 3:
				return new EBlock();
		}
		return new BBlock();
	}
	
	@Override
	protected void moveDown() {
		gamePane.eraseCurr(x, y, curr);
		if (!detectCrash('D')) {
			y++;
			score++;
		} else {
			gamePane.placeBlock(x, y, curr);
			items();
			newBlock();
		}
		gamePane.placeBlock(x, y, curr);
		gamePane.draw();
	}

	@Override
	protected void dropBlock() {
		gamePane.eraseCurr(x, y, curr);
		while (!detectCrash('D')) {
			y++;
			score++;
		}
		gamePane.placeBlock(x, y, curr);
		items();
		newBlock();
		gamePane.placeBlock(x, y, curr);
		gamePane.draw();
	}

	@Override
	protected void newBlock() {
		curr = nextBlock;
		if (isGameEnded()) {
			gameEnded = true;
		}
		if (lineNum/count >= lineChange) {
			count++;
			nextBlock = getItemBlock();
		} else {
			nextBlock = getRandomBlock.getRandomBlockMode(modeName);
		}
		eraseLine();
		x = 3;
		y = 0;
	}

	private void items() {
		if (curr.getItem() == "weight") {
			for (int i = x; i < x + curr.width(); i++) {
				for (int j = 0; j < HEIGHT; j++) {
					gamePane.setBoard(j,i,0);
				}
			}
			y = HEIGHT - 2;
		} else if (curr.getItem() == "L") {
			lItem();
		} else if (curr.getItem() == "bomb") {
			bombItem();
		} else if (curr.getItem() == "cross") {
			crossItem();
		} else if (curr.getItem() == "total") {
			totalItem();
		}
	}

	private void lItem() {
		int line = 0;
		for (int i = 0; i < curr.width(); i++)
			for (int j = 0; j < curr.height(); j++)
				if (curr.getShape(i, j) == 2) {
					line = j;
					break;
				}
		for (int i = 0; i < WIDTH; i++)
			gamePane.setBoard(line + y,i,1);
	}

	private void crossItem() {
		for(int i=0;i<WIDTH;i++)
			gamePane.setBoard(y+1,i,1);
		for(int j=0;j<y+1;j++)
			gamePane.setBoard(j,x+1,0);
		for(int j=y+2;j<HEIGHT;j++)
			gamePane.setBoard(j,x+1,0);
	}

	private void bombItem() {
		for(int i=Math.max(0,x-2);i<Math.min(x+4,WIDTH);i++)
			for(int j=Math.max(0,y-2);j<Math.min(y+4,HEIGHT);j++)
				gamePane.setBoard(j,i,0);
	}
	private void totalItem() {
		for(int i=0;i<WIDTH;i++)
			for(int j=0;j<HEIGHT;j++)
				gamePane.setBoard(j,i,0);
	}
}