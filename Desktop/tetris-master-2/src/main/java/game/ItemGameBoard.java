package game;

import java.util.Random;
import blocks.*;

public class ItemGameBoard extends GameBoard {

	private static final long serialVersionUID = 1L;

	public ItemGameBoard() throws java.io.IOException {
	}



	@Override
	protected void eraseLine() {
		items();
		int temp = 0;
		int[][] lines = new int[HEIGHT][WIDTH];
		for (int j = 0; j < erasedLine.length; j++)
			for (int i = 0; i < WIDTH; i++)
				lines[j][i] = erasedLine[j][i];

		for (int i = 0; i < HEIGHT; i++) {
			boolean lineClear = true;
			for (int j = 0; j < WIDTH; j++) {
				if (gamePane.getBoard(i, j) == 0) {
					lineClear = false;
					j = WIDTH;
				}
			}
			if (lineClear) {
				for (int k = 0; k < WIDTH; k++) {
					if (curr.getShape(k - x, i - y) == 0)
						lines[erasedLine.length + temp][k] = gamePane.getBoard(i, k);
					else
						lines[erasedLine.length + temp][k] = 0;
				}
				for (int k = i; k > 1; k--) {
					for (int l = 0; l < WIDTH; l++) {
						gamePane.setBoard(k, l, gamePane.getBoard(k - 1, l));
						gamePane.setColorBoard(k, l, gamePane.getColorBoard(k - 1, l));
					}
				}
				score += 10 * level;
				lineNum++;
				temp++;
			}
		}
		if (temp > 0) {
			audio();
			if (temp > 1) {
				erasedLine = new int[Math.min(10, erasedLine.length + temp)][WIDTH];
				for (int j = 0; j < erasedLine.length; j++)
					for (int i = 0; i < WIDTH; i++)
						erasedLine[j][i] = lines[j][i];
			}
		}
		gamePane.draw();
	}


	protected void items() {
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