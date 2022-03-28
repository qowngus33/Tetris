package main;

import component.Board;

public class Tetris {

	public static void main(String[] args) {
		Board main = new Board();
		main.setSize(400, 560);
		main.setVisible(true);
	}
}