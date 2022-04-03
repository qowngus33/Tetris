package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.text.*;

import blocks.*;
import main.Tetris;
import scoreboard.ScoreBoardMenu;
import setting.Mode;
import setting.SettingItem;

public class GameBoard extends JPanel {

	private static final long serialVersionUID = 2434035659171694595L;

	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final char BORDER_CHAR = 'X';

	protected GetRandomBlock getRandomBlock;
	protected SettingItem settingItem;
	protected String[][] colorBoard;
	protected JTextPane gamePane;
	protected JTextPane nextBlockPane;
	protected JTextPane label;
	protected int[][] board;
	protected KeyListener playerKeyListener;
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
	protected Mode mode;

	public GameBoard() {
		setSize(380, 800);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setVisible(true);
		settingItem = SettingItem.getInstance();
		mode = settingItem.getMode();
		getRandomBlock = new GetRandomBlock();
		gameMode = "regul";

		// Board display setting.
		// label for displaying scores
		label = new JTextPane();
		label.setVisible(true);
		updateScore();
		label.setOpaque(true);
		label.setBackground(Color.white);
		label.setForeground(Color.BLACK);

		// Main game display
		gamePane = new JTextPane();
		gamePane.setEditable(false);
		gamePane.setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		gamePane.setBorder(border);
		JPanel scorePanel = new JPanel();
		scorePanel.add(label);

		// Pane for diplaying next block to be put
		nextBlockPane = new JTextPane();
		nextBlockPane.setEditable(false);
		nextBlockPane.setBackground(Color.BLACK);
		nextBlockPane.setBorder(border);

		// Additory panel for layout
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(nextBlockPane, BorderLayout.NORTH);
		eastPanel.add(scorePanel, BorderLayout.CENTER);
		add(gamePane, BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);

		//set style
		setStyle();
		
		// Set timer for block drops.
		timer = new Timer(settingItem.getInitInterval(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawGameBoard();
			}
		});

		// Initialize board for the game.
		board = new int[HEIGHT][WIDTH];
		colorBoard = new String[HEIGHT + 2][WIDTH + 2];
		for (int i = 0; i < HEIGHT + 2; i++)
			for (int j = 0; j < WIDTH + 2; j++)
				colorBoard[i][j] = "WHITE";

		setFocusable(true);
		requestFocus();
		initControls();

		// Create the first block and draw.
		nextBlock = getRandomBlock.getRandomBlockMode(mode);
		curr = getRandomBlock.getRandomBlockMode(mode);
		
		placeBlock();
		drawGameBoard();
		drawNextBlockBoard();
		timer.start();
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
					drawGameBoard();
				}
			}
		});
		am.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					moveLeft();
					drawGameBoard();
				}
			}
		});
		am.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					rotateBlock();
					drawGameBoard();
				}
			}
		});
		am.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					moveDown();
					drawGameBoard();
				}
			}
		});
		am.put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					dropBlock();
					drawGameBoard();
					drawNextBlockBoard();
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

	protected void setStyle() {
		styleSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(styleSet, settingItem.getFontSize());

		Style r = gamePane.addStyle("RED", null);
		StyleConstants.setForeground(r, Color.RED);
		Style c = gamePane.addStyle("CYAN", null);
		StyleConstants.setForeground(c, Color.CYAN);
		Style b = gamePane.addStyle("BLUE", null);
		StyleConstants.setForeground(b, Color.BLUE);
		Style o = gamePane.addStyle("ORANGE", null);
		StyleConstants.setForeground(o, Color.ORANGE);
		Style y = gamePane.addStyle("YELLOW", null);
		StyleConstants.setForeground(y, Color.YELLOW);
		Style g = gamePane.addStyle("GREEN", null);
		StyleConstants.setForeground(g, Color.GREEN);
		Style m = gamePane.addStyle("MAGENTA", null);
		StyleConstants.setForeground(m, Color.MAGENTA);
		Style w = gamePane.addStyle("WHITE", null);
		StyleConstants.setForeground(w, Color.WHITE);

		StyleConstants.setFontFamily(styleSet, Font.MONOSPACED);
		StyleConstants.setBold(styleSet, true);
		StyleConstants.setForeground(styleSet, Color.WHITE);
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
	}
	
	protected void eraseCurr() throws java.lang.ArrayIndexOutOfBoundsException {
		for (int i = x; i < x + curr.width(); i++) {
			for (int j = y; j < y + curr.height(); j++) {
				if (curr.getShape(i - x, j - y) >= 1 && i < WIDTH && j < HEIGHT) {
					board[j][i] = 0;
					colorBoard[j][i] = "WHITE";
				}
			}
		}
	}
	
	protected void placeBlock() {
		StyledDocument doc = gamePane.getStyledDocument();
	
		for (int j = 0; j < curr.height(); j++) {
			doc.setParagraphAttributes(0, doc.getLength(), styleSet, true);
			gamePane.setStyledDocument(doc);
			for (int i = 0; i < curr.width(); i++)
				if (y + j < HEIGHT && x + i < WIDTH)
					if(board[y+j][x+i]==0) {
						board[y + j][x + i] = curr.getShape(i, j);
						colorBoard[y + j][x + i] = curr.getColor();
					}
		}
	}
	
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
		
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		for (int i = 0; i < HEIGHT; i++)
			for (int j = 0; j < WIDTH; j++)
					doc.setCharacterAttributes(13 + i * (WIDTH + 3) + j + 1, 1, gamePane.getStyle(colorBoard[i][j]),false);
		gamePane.setStyledDocument(doc);
	}

	public void drawNextBlockBoard() {
		StyledDocument doc = nextBlockPane.getStyledDocument();
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (int i = 0; i < 2; i++) {
			sb.append("  ");
			for (int j = 0; j < 5; j++) {
				if (nextBlock.width() > j && nextBlock.height() > i) {
					if (nextBlock.getShape(j, i) == 1) {
						sb.append("O");
					} else {
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
	
	protected void moveRight() {
		eraseCurr();
		if (x < WIDTH - curr.width() && !detectCrash('R'))
			x++;
		placeBlock();
	}

	protected void moveLeft() {
		eraseCurr();
		if (x > 0 && !detectCrash('L'))
			x--;
		placeBlock();
	}
	
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
			curr = nextBlock;
			nextBlock = getRandomBlock.getRandomBlockMode(mode);
			x = 3;
			y = 0;
			drawNextBlockBoard();
		}
		score++;
		updateScore();
		placeBlock();
	}
	
	protected void rotateBlock() {
		eraseCurr();
		if (x + curr.height() >= WIDTH)
			x = WIDTH - curr.height();
		if (y + curr.width() >= HEIGHT)
			y = HEIGHT - curr.width();
		curr.rotate();
		for (int i = x; i < x + curr.width(); i++) {
			for (int j = y; j < y + curr.height(); j++) {
				if (board[j][i] == 1 && curr.getShape(i - x, j - y) == 1) {
					curr.rotate();
					curr.rotate();
					curr.rotate();
					break;
				}
			}
		}
		placeBlock();
	}

	protected void dropBlock() {
		eraseCurr();
		while (y < HEIGHT - curr.height() && !detectCrash('D')) {
			y++;
			score++;
		}
		placeBlock(); // 밑으로 내려가지 않게 고정
		eraseOneLine();

		curr = nextBlock;
		nextBlock = getRandomBlock.getRandomBlockMode(mode);
		x = 3;
		y = 0;
		placeBlock();
	}

	protected void eraseOneLine() {
		for (int i = 0; i < HEIGHT; i++) {
			boolean lineClear = true;
			for (int j = 0; j < WIDTH; j++) {
				if (board[i][j] == 0) {
					lineClear = false;
					j = WIDTH;
				}
			}
			if (lineClear) {
				speedUp();
				System.out.println("one line cleared!");
				for (int k = i; k > 1; k--) {
					for (int l = 0; l < WIDTH; l++) {
						board[k][l] = board[k - 1][l];
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
					if (curr.getShape(j, i) == 1 && board[i + y][j + x - 1] == 1) {
						result = true;
						j = curr.width();
					}
			break;
		case 'R':
			for (int i = 0; i < curr.height(); i++)
				for (int j = curr.width() - 1; j >= 0; j--)
					if (curr.getShape(j, i) == 1 && board[i+y][j+x+1] == 1) {
						result = true;
						break;
					}
			break;
		case 'D':
			for (int i = 0; i < curr.width(); i++) 
				for (int j = curr.height() - 1; j >= 0; j--) 
					if (curr.getShape(i, j) == 1 && board[j + y + 1][i + x] == 1) {
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
	
	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			updateScore();
		} else {
			timer.stop();
			label.setText("paused");
		}
	}

	public void reset() {
		board = new int[20][10];
		timer.stop();
		initInterval = 1000;
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawGameBoard();
			}
		});
		timer.start();
		score = 0;
		updateScore();
	}
	

	protected void gameOver() {
		timer.stop();
		removeKeyListener(playerKeyListener);
		Tetris.disposeGameMenu();
		try {
			ScoreBoardMenu sbf = new ScoreBoardMenu(score, mode.toString(),gameMode);
			sbf.setVisible(true);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					drawGameBoard();
				}
			});
			timer.start();
		}
	}

	protected boolean isGameEnded() {
		for (int i = 0; i < WIDTH; i++)
			if (board[0][i] == 1) return true;
		return false;
	}

}
