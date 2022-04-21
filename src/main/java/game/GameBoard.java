package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;

import blocks.Block;
import blocks.GetRandomBlock;
import play.Tetris;
import scoreboard.ScoreBoardMenu;
import setting.SettingItem;

public class GameBoard extends JPanel {

	private static final long serialVersionUID = 2434035659171694595L;

	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final char BORDER_CHAR = 'X';

	protected GetRandomBlock getRandomBlock;
	protected SettingItem settingItem;
	protected GamePane gamePane;
	protected NextBlockPane nextBlockPane;
	protected JLabel scoreLabel;
	protected JLabel levelLabel;
	protected JLabel lineLabel;
	protected ScoreBoardMenu sbf;

	protected SimpleAttributeSet styleSet;
	protected Timer timer;
	protected Block curr;
	protected Block nextBlock;
	protected String gameMode;
	protected int score = 0;
	protected int level = 1;
	protected int lineNum = 0;
	protected int x = 3; // Default Position.
	protected int y = 0;

	/**
	 * Mode 추가
	 */
	protected static int initInterval;
	protected String modeName;

	public GameBoard() throws IOException{
		System.out.println("Normal mode");
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setVisible(true);
		settingItem = SettingItem.getInstance();
		modeName = settingItem.getModeName();
		getRandomBlock = new GetRandomBlock();
		gameMode = "regul";

		// Side display
		scoreLabel = new JLabel(score + "", JLabel.CENTER);
		TitledBorder tborder = new TitledBorder("SCORE");
		tborder.setTitlePosition(TitledBorder.ABOVE_TOP);// 지정한 위치에 타이틀을 나타내주는 보더...
		tborder.setTitleJustification(TitledBorder.CENTER);// 자리맞춤을 가운데로 지정...
		scoreLabel.setBorder(tborder);
		scoreLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		scoreLabel.setBackground(Color.white);

		levelLabel = new JLabel(level + "", JLabel.CENTER);
		TitledBorder tborder2 = new TitledBorder("LEVEL");
		tborder2.setTitlePosition(TitledBorder.ABOVE_TOP);// 지정한 위치에 타이틀을 나타내주는 보더...
		tborder2.setTitleJustification(TitledBorder.CENTER);// 자리맞춤을 가운데로 지정...
		levelLabel.setBorder(tborder2);
		levelLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		levelLabel.setBackground(Color.white);

		lineLabel = new JLabel(lineNum + "", JLabel.CENTER);
		TitledBorder tborder3 = new TitledBorder("LINE");
		tborder3.setTitlePosition(TitledBorder.ABOVE_TOP);// 지정한 위치에 타이틀을 나타내주는 보더...
		tborder3.setTitleJustification(TitledBorder.CENTER);// 자리맞춤을 가운데로 지정...
		lineLabel.setBorder(tborder3);
		lineLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		lineLabel.setBackground(Color.white);
		updateScore();

		// Main game display
		if (settingItem.isColorBlind()) {
			gamePane = new GamePane(1);
		} else if(!settingItem.isColorBlind()) {
			gamePane = new GamePane();
		}
		gamePane.setFontSize(settingItem.getFontSize());

		if (settingItem.isColorBlind()) {
			nextBlockPane = new NextBlockPane(1);
		} else {
			nextBlockPane = new NextBlockPane();
		}
<<<<<<< HEAD:src/main/java/game/GameBoard.java
=======

//		gamePane = new GamePane();
//		gamePane.setFontSize(settingItem.getFontSize());
//		nextBlockPane = new NextBlockPane();
//		nextBlockPane.setFontSize(settingItem.getFontSize());
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameBoard.java

		// Additory panel for layout
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(nextBlockPane);
		eastPanel.add(scoreLabel);
		eastPanel.add(levelLabel);
		eastPanel.add(lineLabel);
		eastPanel.setAlignmentX(LEFT_ALIGNMENT);
		eastPanel.setBackground(Color.WHITE);
		add(gamePane, BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);

		// Set timer for block drops.
		initInterval = settingItem.getInitInterval();
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				gamePane.drawGameBoard();
				eraseLine();
				speedUp();
			}
		});

		// Create the first block and draw.
<<<<<<< HEAD:src/main/java/game/GameBoard.java
		nextBlock = getRandomBlock.getRandomBlockMode(modeName);
		curr = getRandomBlock.getRandomBlockMode(modeName);
=======
		nextBlock = getRandomBlock.getRandomBlockMode(mode);
		curr = getRandomBlock.getRandomBlockMode(mode);
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameBoard.java

		gamePane.placeBlock(x, y, curr);
		gamePane.drawGameBoard();
		nextBlockPane.drawNextBlockBoard(nextBlock);
		timer.start();
		setFocusable(true);
		requestFocus();
		initControls();
	}

	protected void initControls() {

		InputMap im = this.getInputMap();
		ActionMap am = this.getActionMap();

		im.put(KeyStroke.getKeyStroke(settingItem.getRightKey()), "right");
		im.put(KeyStroke.getKeyStroke(settingItem.getLeftKey()), "left");
		im.put(KeyStroke.getKeyStroke(settingItem.getRotateKey()), "up");
		im.put(KeyStroke.getKeyStroke(settingItem.getDownKey()), "down");
		im.put(KeyStroke.getKeyStroke(settingItem.getDropKey()), "space");
		im.put(KeyStroke.getKeyStroke(settingItem.getPauseKey()), "pause");

		am.put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					if (curr.getItem() == "weight" && detectCrash('D')) {
					} else {
						moveRight();
						gamePane.drawGameBoard();
					}
				}
			}
		});
		am.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					if (curr.getItem() == "weight" && detectCrash('D')) {
					} else {
						moveLeft();
						gamePane.drawGameBoard();
					}
				}
			}
		});
		am.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					rotateBlock();
					gamePane.drawGameBoard();
				}
			}
		});
		am.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					moveDown();
					gamePane.drawGameBoard();
				}
			}
		});
		am.put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					dropBlock();
					gamePane.drawGameBoard();
					nextBlockPane.drawNextBlockBoard(nextBlock);
				}
			}
		});
		am.put("pause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
	}

	protected void moveRight() {
		gamePane.eraseCurr(x, y, curr);
		if (x < WIDTH - curr.width() && !detectCrash('R'))
			x++;
		gamePane.placeBlock(x, y, curr);
	}

	protected void moveLeft() {
		gamePane.eraseCurr(x, y, curr);
		if (x > 0 && !detectCrash('L'))
			x--;
		gamePane.placeBlock(x, y, curr);
	}

	protected void moveDown() {
		gamePane.eraseCurr(x, y, curr);
		if (y < HEIGHT - curr.height() && !detectCrash('D'))
			y++;
		else {
			gamePane.placeBlock(x, y, curr); // 밑으로 내려가지 않게 고정
			if (isGameEnded()) { // 게임이 종료됨.
				gameOver();
				return;
			}
			curr = nextBlock;
			nextBlock = getRandomBlock.getRandomBlockMode(modeName);
			x = 3;
			y = 0;
			nextBlockPane.drawNextBlockBoard(nextBlock);
		}
		score++;
		updateScore();
		gamePane.placeBlock(x, y, curr);
	}

	protected void rotateBlock() {
		gamePane.eraseCurr(x, y, curr);
		if (x + curr.height() >= WIDTH)
			x = WIDTH - curr.height();
		if (y + curr.width() >= HEIGHT)
			y = HEIGHT - curr.width();
		curr.rotate();
		for (int i = x; i < x + curr.width(); i++) {
			for (int j = y; j < y + curr.height(); j++) {
				if (gamePane.getBoard(j, i) == 1 && curr.getShape(i - x, j - y) == 1) {
					curr.rotate();
					curr.rotate();
					curr.rotate();
					break;
				}
			}
		}
		gamePane.placeBlock(x, y, curr);
	}

	protected void dropBlock() {
		gamePane.eraseCurr(x, y, curr);
		while (y < HEIGHT - curr.height() && !detectCrash('D')) {
			y++;
			score++;
		}
		gamePane.placeBlock(x, y, curr); // 밑으로 내려가지 않게 고정
		eraseLine();
		if (isGameEnded()) { // 게임이 종료됨.
			gameOver();
			return;
		}
		curr = nextBlock;
		nextBlock = getRandomBlock.getRandomBlockMode(modeName);
		x = 3;
		y = 0;
		gamePane.placeBlock(x, y, curr);
	}

	protected void eraseLine() {
		for (int i = 0; i < HEIGHT; i++) {
			boolean lineClear = true;
			for (int j = 0; j < WIDTH; j++) {
				if (gamePane.getBoard(i, j) == 0) {
					lineClear = false;
					j = WIDTH;
				}
			}
			if (lineClear) {
				for (int k = i; k > 1; k--) {
					for (int l = 0; l < WIDTH; l++) {
						gamePane.setBoard(k, l, gamePane.getBoard(k - 1, l));
						gamePane.setColorBoard(k, l, gamePane.getColorBoard(k - 1, l));
					}
				}
				score += 10;
				lineNum++;
				updateScore();
				speedUp();
			}
		}
	}

	protected boolean detectCrash(char position) {
		boolean result = false;
		switch (position) {
<<<<<<< HEAD:src/main/java/game/GameBoard.java
			case 'L':
				for (int i = 0; i < curr.height(); i++)
					for (int j = 0; j < curr.width(); j++)
						if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x - 1) != 0) {
							result = true;
							break;
						} else if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x - 1) == 0) {
							j = curr.width();
						}
				break;
			case 'R':
				for (int i = 0; i < curr.height(); i++)
					for (int j = curr.width() - 1; j >= 0; j--)
						if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x + 1) != 0) {
							result = true;
							break;
						} else if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x - 1) == 0) {
							j = -1;
						}
				break;
			case 'D':
				for (int i = 0; i < curr.width(); i++)
					for (int j = curr.height() - 1; j >= 0; j--)
						if (curr.getShape(i, j) != 0 && gamePane.getBoard(j + y + 1, i + x) != 0) {
							result = true;
							break;
						} else if (curr.getShape(i, j) != 0 && gamePane.getBoard(j + y + 1, i + x) == 0) {
							j = -1;
						}
				break;
			default:
				System.out.println("Wrong Character");
				break;
=======
		case 'L':
			for (int i = 0; i < curr.height(); i++)
				for (int j = 0; j < curr.width(); j++)
					if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x - 1) != 0) {
						result = true;
						break;
					} else if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x - 1) == 0) {
						j = curr.width();
					}
			break;
		case 'R':
			for (int i = 0; i < curr.height(); i++)
				for (int j = curr.width() - 1; j >= 0; j--)
					if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x + 1) != 0) {
						result = true;
						break;
					} else if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y, j + x - 1) == 0) {
						j = -1;
					}
			break;
		case 'D':
			for (int i = 0; i < curr.width(); i++)
				for (int j = curr.height() - 1; j >= 0; j--)
					if (curr.getShape(i, j) != 0 && gamePane.getBoard(j + y + 1, i + x) != 0) {
						result = true;
						break;
					} else if (curr.getShape(i, j) != 0 && gamePane.getBoard(j + y + 1, i + x) == 0) {
						j = -1;
					}
			break;
		default:
			System.out.println("Wrong Character");
			break;
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameBoard.java
		}
		return result;
	}

	protected void updateScore() {
		scoreLabel.setText(String.format("%5s", score + "") + " ");
		levelLabel.setText(String.format("%5s", level + "") + " ");
		lineLabel.setText(String.format("%5s", lineNum + "") + " ");
	}

	protected void speedUp() {
		if (lineNum / 7 >= level && initInterval > 120) {
			level += 1;
<<<<<<< HEAD:src/main/java/game/GameBoard.java
			//To be edited ...
			//initInterval -= settingItem.getReduceSpeed();
			initInterval -= 100;
			//System.out.println(settingItem.getReduceSpeed());
=======
			initInterval -= settingItem.getReduceSpeed();
			System.out.println(settingItem.getReduceSpeed());
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameBoard.java
			timer.stop();
			timer = new Timer(initInterval, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					moveDown();
					gamePane.drawGameBoard();
					eraseLine();
				}
			});
			timer.start();
		}
	}

	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			updateScore();
			gamePane.drawGameBoard();
		} else {
			timer.stop();
			String pause = "";
			for (int i = 0; i < 9; i++)
				pause += "          \n";
			pause += "  PAUSED  \n";
			for (int i = 0; i < 9; i++)
				pause += "          \n";
			gamePane.setText(pause);
		}
	}

	protected boolean isGameEnded() {
		for (int i = 0; i < WIDTH; i++) {
			if (gamePane.getBoard(0, i) != 0) {
				System.out.println("Game ended");
				return true;
			}
		}
		return false;
	}

	protected void gameOver() {
		timer.stop();
		this.setFocusable(false);
		try {
<<<<<<< HEAD:src/main/java/game/GameBoard.java
			sbf = new ScoreBoardMenu(score, modeName.toString(), gameMode);
=======
			sbf = new ScoreBoardMenu(score, mode.toString(), gameMode);
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameBoard.java
			sbf.setVisible(true);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tetris.disposeGameMenu();
	}

	public void stopTimer() {
		timer.stop();
	}

<<<<<<< HEAD:src/main/java/game/GameBoard.java
}
=======
}
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameBoard.java
