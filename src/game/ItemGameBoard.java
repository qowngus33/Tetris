package game;

import java.awt.Color;
import java.util.Random;
import javax.swing.text.StyledDocument;

import blocks.*;

public class ItemGameBoard extends GameBoard {

	private static final long serialVersionUID = 1L;
	private int lineChange = 10;

	public ItemGameBoard() {
		System.out.println("Item mode");
		this.setFocusable(true);
		this.mode = settingItem.getMode();
		setNextBlock();
		this.timer.start();
		this.lineNum = 0;
		this.gameMode = "item";
	}

	private Block getItemBlock() {
		Random rnd = new Random(System.currentTimeMillis());
		int block = rnd.nextInt(1000) % 4;
		switch (block) {
		case 0:
			return new WBlock();
		case 1:
			Block temp = getRandomBlock.getRandomBlockMode(mode);
			rnd = new Random(System.currentTimeMillis());
			block = rnd.nextInt(1000) % 4+1;
			int count = 0;
			for(int i=0;i<temp.width();i++)
				for(int j=0;j<temp.height();j++) 
					if(temp.getShape(i, j)==1) {
						count++;
						if(count==block)
							temp.setShape(i, j, 2);
					}
						
			temp.setItem("L");
			return temp;
		case 2:
			return new CBlock();
		}
		return new BBlock();
	}

	@Override
	public void dropBlock() {
		gamePane.eraseCurr(x,y,curr);	
		while (y < HEIGHT - curr.height() && !detectCrash('D')) {
			y++;
			score++;
		}
		items();
		if (curr.getItem() != "bomb") gamePane.placeBlock(x,y,curr);
		eraseLine();
		setNextBlock();
		gamePane.placeBlock(x,y,curr);
	}
	
	@Override
	protected void moveDown() {
		gamePane.eraseCurr(x,y,curr);	
		if (y < HEIGHT - curr.height() && !detectCrash('D'))
			y++;
		else {
			gamePane.placeBlock(x,y,curr);
			eraseLine();
			if (isGameEnded()) { // 게임이 종료됨.
				gameOver();
				return;
			}
			items();
			setNextBlock();
		}
		eraseLine();
		score += 1;
		updateScore();
		gamePane.placeBlock(x,y,curr);
	}
	
//	protected void moveDown() {
//		gamePane.eraseCurr(x,y,curr);
//		if (y < HEIGHT - curr.height() && !detectCrash('D'))
//			y++;
//		else {
//			gamePane.placeBlock(x,y,curr); // 밑으로 내려가지 않게 고정
//			//eraseLine();
//			if (isGameEnded()) { // 게임이 종료됨.
//				gameOver();
//				return;
//			}
//			curr = nextBlock;
//			nextBlock = getRandomBlock.getRandomBlockMode(mode);
//			x = 3;
//			y = 0;
//			nextBlockPane.drawNextBlockBoard(nextBlock);
//		}
//		score++;
//		updateScore();
//		gamePane.placeBlock(x,y,curr);
//	}

	protected void setNextBlock() {
		this.curr = this.nextBlock;
		if (lineNum >= lineChange) {
			this.nextBlock = this.getItemBlock();
			lineNum = 0;
		} else {
			this.nextBlock = getRandomBlock.getRandomBlockMode(mode);
		}
		x = 3;
		y = 0;
		nextBlockPane.drawNextBlockBoard(this.nextBlock);
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
		for(int i=x;i<Math.min(x+4,WIDTH);i++)
			for(int j=y;j<Math.min(y+4,HEIGHT);j++)
				gamePane.setBoard(j,i,0);
	}
}