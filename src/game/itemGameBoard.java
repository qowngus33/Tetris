package game;

import java.util.Random;
import javax.swing.text.StyledDocument;

import blocks.*;

public class itemGameBoard extends GameBoard {

	private static final long serialVersionUID = 1L;
	private int lineChange = 0;

	public itemGameBoard() {
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
		eraseCurr();	
		while (y < HEIGHT - curr.height() && !detectCrash('D')) {
			y++;
			score++;
		}
		items();
		if (curr.getItem() != "bomb") placeBlock();
		eraseOneLine();
		setNextBlock();
		placeBlock();
	}
	
	@Override
	public void drawNextBlockBoard() {
		StyledDocument doc = nextBlockPane.getStyledDocument();
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (int i = 0; i < 3; i++) {
			sb.append("  ");
			for (int j = 0; j < 5; j++) {
				if (nextBlock.width() > j && nextBlock.height() > i) {
					if (nextBlock.getShape(j, i) == 1) {
						sb.append("O");
					} else if(nextBlock.getShape(j, i) == 2) {
						sb.append("L");
					}else {
						sb.append(" ");
					}
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		nextBlockPane.setText(sb.toString());
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		doc.setCharacterAttributes(0, doc.getLength(), gamePane.getStyle(nextBlock.getColor()), false);
		nextBlockPane.setStyledDocument(doc);
	}

	@Override
	protected void moveDown() {
		eraseCurr();
		if (y < HEIGHT - curr.height() && !detectCrash('D'))
			y++;
		else {
			placeBlock(); // 밑으로 내려가지 않게 고정
			eraseOneLine();
			if (isGameEnded()) { // 게임이 종료됨.
				gameOver();
				return;
			}
			items();
			setNextBlock();
		}
		eraseOneLine();
		score += 1;
		updateScore();
		placeBlock();
	}

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
		drawNextBlockBoard();
	}

	@Override
	public void drawGameBoard() {
		StyledDocument doc = gamePane.getStyledDocument();
		StringBuffer sb = new StringBuffer();
		for (int t = 0; t < WIDTH + 2; t++)
			sb.append(BORDER_CHAR);
		sb.append("\n");
		for (int i = 0; i < board.length; i++) {
			sb.append(BORDER_CHAR);
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					sb.append("O");
				} else if (board[i][j] == 2) {
					sb.append("L");
				} else {
					sb.append(" ");
				}
			}
			sb.append(BORDER_CHAR);
			sb.append("\n");
		}
		for (int t = 0; t < WIDTH + 2; t++)
			sb.append(BORDER_CHAR);
		gamePane.setText(sb.toString());
		// Jtextpane

		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		for (int i = 0; i < HEIGHT; i++)
			for (int j = 0; j < WIDTH; j++)
				if (board[i][j] != 2)
					doc.setCharacterAttributes(13 + i * (WIDTH + 3) + j + 1, 1, gamePane.getStyle(colorBoard[i][j]),false);
		gamePane.setStyledDocument(doc);
	}
	
	private void items() {
		if (curr.getItem() == "weight") {
			for (int i = x; i < x + curr.width(); i++) {
				for (int j = 0; j < HEIGHT; j++) {
					board[j][i] = 0;
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
			board[line + y][i] = 1;
	}
	
	private void crossItem() {
		for(int i=0;i<WIDTH;i++)
			board[y+1][i] = 1;
		for(int j=0;j<y+1;j++)
			board[j][x+1] = 0;
		for(int j=y+2;j<HEIGHT;j++)
			board[j][x+1] = 0;
	}
	
	private void bombItem() {
		for(int i=x;i<Math.min(x+4,WIDTH);i++)
			for(int j=y;j<Math.min(y+4,HEIGHT);j++)
				board[j][i] = 0;
	}
}