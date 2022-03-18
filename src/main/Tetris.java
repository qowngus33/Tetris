package main;

import component.Board;

public class Tetris {

	public static void main(String[] args) {
		Board main = new Board();
		main.setSize(380, 580);
		main.setResizable(false);
		main.setVisible(true);
	}
}