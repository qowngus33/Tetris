package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
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
	protected JTextPane label;
	
	protected SimpleAttributeSet styleSet;
	protected Timer timer;
	protected Block curr;
	protected Block nextBlock;
	protected int score = 0;
	protected int level = 0;
	protected int lineNum = 0;
	protected String gameMode;
	protected int x = 3; // Default Position.
	protected int y = 0;

	/**
	 * Mode 추가
	 */
	protected static int initInterval;
	protected String modeName;

	public GameBoard() throws IOException{
		// System.out.println("Normal mode");
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setVisible(true);
		settingItem = SettingItem.getInstance();
		modeName = settingItem.getModeName();
		getRandomBlock = new GetRandomBlock();
		gameMode = "regul";

		// label for displaying scores
		label = new JTextPane();
		label.setEditable(false);
		label.setVisible(true);
		updateScore();
		label.setOpaque(true);
		label.setBackground(Color.white);
		label.setForeground(Color.BLACK);

		// Main game display
		gamePane = new GamePane();
		gamePane.setFontSize(settingItem.getFontSize());
		nextBlockPane = new NextBlockPane();
		nextBlockPane.setFontSize(settingItem.getFontSize());
		JPanel scorePanel = new JPanel();
		scorePanel.add(label);

		// Additory panel for layout
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(nextBlockPane, BorderLayout.NORTH);
		eastPanel.add(scorePanel, BorderLayout.CENTER);
		add(gamePane, BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);
		

		// timer
		// Set timer for block drops.
		timer = new Timer(settingItem.getInitInterval(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				gamePane.drawGameBoard();
				eraseLine();
			}
		});
		timer.start();

		setFocusable(true);
		requestFocus();
		initControls();

		// Create the first block and draw.
		nextBlock = getRandomBlock.getRandomBlockMode(modeName);
		curr = getRandomBlock.getRandomBlockMode(modeName);
		
		gamePane.placeBlock(x,y,curr);
		gamePane.drawGameBoard();
		nextBlockPane.drawNextBlockBoard(nextBlock);
	}


	protected void moveDown() {
		gamePane.eraseCurr(x,y,curr);
		if (y < HEIGHT - curr.height() && !detectCrash('D'))
			y++;
		else {
			gamePane.placeBlock(x,y,curr); // 밑으로 내려가지 않게 고정
			//eraseLine();
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
		gamePane.placeBlock(x,y,curr);
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
					moveRight();
					gamePane.drawGameBoard();
				}
			}
		});
		am.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					moveLeft();
					gamePane.drawGameBoard();
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
		gamePane.eraseCurr(x,y,curr);
		if (x < WIDTH - curr.width() && !detectCrash('R'))
			x++;
		gamePane.placeBlock(x,y,curr);
	}

	protected void moveLeft() {
		gamePane.eraseCurr(x,y,curr);
		if (x > 0 && !detectCrash('L'))
			x--;
		gamePane.placeBlock(x,y,curr);
	}
	

	protected void rotateBlock() {
		gamePane.eraseCurr(x,y,curr);
		if (x + curr.height() >= WIDTH)
			x = WIDTH - curr.height();
		if (y + curr.width() >= HEIGHT)
			y = HEIGHT - curr.width();
		curr.rotate();
		for (int i = x; i < x + curr.width(); i++) {
			for (int j = y; j < y + curr.height(); j++) {
				if (gamePane.getBoard(j,i) == 1 && curr.getShape(i - x, j - y) == 1) {
					curr.rotate();
					curr.rotate();
					curr.rotate();
					break;
				}
			}
		}
		gamePane.placeBlock(x,y,curr);
	}

	protected void dropBlock() {
		gamePane.eraseCurr(x,y,curr);
		while (y < HEIGHT - curr.height() && !detectCrash('D')) {
			y++;
			score++;
		}
		gamePane.placeBlock(x,y,curr); // 밑으로 내려가지 않게 고정
		eraseLine();
		curr = nextBlock;
		nextBlock = getRandomBlock.getRandomBlockMode(modeName);
		x = 3;
		y = 0;
		gamePane.placeBlock(x,y,curr);
	}

	protected void eraseLine() {
		for (int i = 0; i < HEIGHT; i++) {
			boolean lineClear = true;
			for (int j = 0; j < WIDTH; j++) {
				if (gamePane.getBoard(i,j) == 0) {
					lineClear = false;
					j = WIDTH;
				}
			}
			if (lineClear) {
				speedUp();
				for (int k = i; k > 1; k--) {
					for (int l = 0; l < WIDTH; l++) {
						gamePane.setBoard(k,l,gamePane.getBoard(k - 1,l));
						gamePane.setColorBoard(k,l,gamePane.getColorBoard(k - 1,l));
					}
				}
				score += 10;
				lineNum++;
				updateScore();
			}
		}
	}

	protected boolean detectCrash(char position) {
		boolean result = false;
		switch (position) {
		case 'L':
			for (int i = 0; i < curr.height(); i++)
				for (int j = 0; j < curr.width(); j++)
					if (curr.getShape(j, i) != 0 && gamePane.getBoard(i + y,j + x - 1) != 0) {
						result = true;
						j = curr.width();
					}
			break;
		case 'R':
			for (int i = 0; i < curr.height(); i++)
				for (int j = curr.width() - 1; j >= 0; j--)
					if (curr.getShape(j, i) != 0 && gamePane.getBoard(i+y,j+x+1) != 0) {
						result = true;
						break;
					}
			break;
		case 'D':
			for (int i = 0; i < curr.width(); i++) 
				for (int j = curr.height() - 1; j >= 0; j--) 
					if (curr.getShape(i, j) != 0 && gamePane.getBoard(j + y + 1,i + x) != 0) {
						result = true;
						break;
					}
			break;
		default:
			System.out.println("Wrong Character");
			break;
		}
		return result;
	}
	
	protected void updateScore() {
		label.setText("score: " + score + "");
	}

	protected void speedUp() {
		if (score / 100 > level && initInterval > 100) {
			level += 1;
			initInterval -= 100;
			timer.stop();
			timer = new Timer(initInterval, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					moveDown();
					gamePane.drawGameBoard();
				}
			});
			timer.start();
		}
	}
	
	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			updateScore();
		} else {
			timer.stop();
			label.setText("paused");
			gamePane.setText("paused");
		}
	}
	
	protected boolean isGameEnded() {
		for (int i = 0; i < WIDTH; i++) {
			if (gamePane.getBoard(0,i) != 0) {
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
			ScoreBoardMenu sbf = new ScoreBoardMenu(score, modeName.toString(),gameMode);
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

}
