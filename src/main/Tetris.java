package main;

import component.game.GameBoard;
import component.setting.SettingBoard;

import java.awt.*;

public class Tetris {
	public static void main(String[] args) {

		GameBoard gameBoard = GameBoard.getInstance();
		gameBoard.startGame();
	}
}