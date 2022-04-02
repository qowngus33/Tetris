package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import blocks.*;
import main.Tetris;
import scoreboard.ScoreBoardMenu;
import setting.SettingItem;

public class itemGameBoard extends JPanel {

	private static final long serialVersionUID = 2434035659171694595L;

	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final char BORDER_CHAR = 'X';

	private SettingItem settingItem;
	private JTextPane gamePane;
	private JTextPane nextBlockPane;
	private JTextPane label;
	private int[][] board;
	private String [][] colorBoard;
	private SimpleAttributeSet styleSet;
	private Timer timer;
	private Block curr;
	private Block nextBlock;
	private int score = 0;
	private int level = 0;
	private int lineNum = 0;
	int x = 3; // Default Position.
	int y = 0;

	private static int initInterval = 1000;

	public itemGameBoard() {
		setSize(380, 800);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setVisible(true);
		settingItem = SettingItem.getInstance();

		// Board display setting.
		// label for displaying scores
		label = new JTextPane();
		label.setVisible(true);
		label.setText("score: " + getScore() + "");
		label.setOpaque(true);
		label.setBackground(Color.white);
		label.setForeground(Color.BLACK);
		JPanel scorePanel = new JPanel();
		scorePanel.add(label);
		
		// Main game display
		gamePane = new JTextPane();
		gamePane.setEditable(false);
		gamePane.setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		gamePane.setBorder(border);

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
		this.add(gamePane, BorderLayout.SOUTH);
		this.add(eastPanel, BorderLayout.EAST);

		// Document default style.
		setStyle();

		nextBlock = getRandomBlock();
		// Set timer for block drops.
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawGameBoard();
			}
		});

		// Initialize board for the game.
		board = new int[HEIGHT][WIDTH];
		colorBoard = new String [HEIGHT+2][WIDTH+2];
		for(int i=0;i<HEIGHT+2;i++)
			for(int j=0;j<WIDTH+2;j++)
				colorBoard[i][j] = "WHITE"; 
		setFocusable(true);
		requestFocus();
		initControls();
		// Create the first block and draw.
		setNextBlock();
		placeBlock();
		drawGameBoard();
		timer.start();

	}

	private void initControls() {

		InputMap im = this.getInputMap();
		ActionMap am = this.getActionMap();

		im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		im.put(KeyStroke.getKeyStroke("LEFT"), "left");
		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "down");
		im.put(KeyStroke.getKeyStroke("SPACE"), "space");

		am.put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(curr.getItem() == "weight" && detectCrash('D'))) {
					moveRight();
					drawGameBoard();
				}
			}
		});
		am.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(curr.getItem() == "weight" && detectCrash('D'))) {
					moveLeft();
					drawGameBoard();
				}
			}
		});
		am.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (curr.getItem() != "weight") {
					rotateBlock();
					drawGameBoard();
				}
				// System.out.println("up");
			}
		});
		am.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawGameBoard();
				// System.out.println("down");
			}
		});
		am.put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dropBlock();
				drawGameBoard();
				drawNextBlockBoard();
			}
		});
		
		am.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });
	}

	private void setStyle() {
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

	private Block getRandomBlock() {
		Random rnd = new Random(System.currentTimeMillis());
		int block = rnd.nextInt(1000) % 6;
		switch (block) {
		case 0:
			return new IBlock();
		case 1:
			return new JBlock();
		case 2:
			return new LBlock();
		case 3:
			return new ZBlock();
		case 4:
			return new SBlock();
		case 5:
			return new TBlock();
		}
		return new OBlock();
	}

	private Block getItemBlock() {
		Random rnd = new Random(System.currentTimeMillis());
		int block = rnd.nextInt(1000) % 2;
		switch (block) {
		case 1:
			return new WBlock();
		case 0:
			Block temp = getRandomBlock();
			temp.setItem("L");
			temp.setShape(1, 0, 2);
			return temp;
		}
		return new WBlock();

	}

	public void rotateBlock() {
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

	public void dropBlock() {
		eraseCurr();
		while (y < HEIGHT - curr.height() && !detectCrash('D')) {
			y++;
			score = getScore() + 1;
		}
		if (curr.getItem() == "weight") {
			for (int i = x; i < x + curr.width(); i++) {
				for (int j = 0; j < HEIGHT; j++) {
					board[j][i] = 0;
				}
			}
			y = HEIGHT - 2;
		} else if (curr.getItem() == "L") {
			Litem();
		}
		placeBlock(); // 밑으로 내려가지 않게 고정
		eraseOneLine();
		setNextBlock();
		placeBlock();
	}

	private void placeBlock() {
		StyledDocument doc = gamePane.getStyledDocument();
		//SimpleAttributeSet styles = new SimpleAttributeSet();
		// StyleConstants.setForeground(styles, curr.getColor());

		for (int j = 0; j < curr.height(); j++) {
			int rows = y + j == 0 ? 0 : y + j - 1;
			int offset = rows * (WIDTH + 3) + x + 1;
			doc.setParagraphAttributes(0, doc.getLength(), styleSet, true);
			//doc.setCharacterAttributes(offset, curr.width(), gamePane.getStyle(curr.getColor()),true);
			gamePane.setStyledDocument(doc);
			for (int i = 0; i < curr.width(); i++) {
				if (y + j < HEIGHT && x + i < WIDTH)
					if (curr.getShape(i, j) != 0) {
						board[y + j][x + i] = curr.getShape(i, j);
						colorBoard[y + j][x + i] = curr.getColor();
					}
			}
		}
	}

	private void eraseCurr() throws java.lang.ArrayIndexOutOfBoundsException {
		for (int i = x; i < x + curr.width(); i++) {
			for (int j = y; j < y + curr.height(); j++) {
				if (curr.getShape(i - x, j - y) >= 1 && i < WIDTH && j < HEIGHT) {
					board[j][i] = 0;
					colorBoard[j][i] = "WHITE";
				}
			}
		}
	}

	protected void moveDown() {
		eraseCurr();
		if (y < HEIGHT - curr.height() && !detectCrash('D'))
			y++;
		else {
			if (curr.getItem() == "weight") {
				for (int i = x; i < x + curr.width(); i++) {
					for (int j = 0; j < HEIGHT; j++) {
						board[j][i] = 0;
					}
				}
				y = HEIGHT - 2;
			} else if (curr.getItem() == "L") {
				Litem();
			}
			placeBlock(); // 밑으로 내려가지 않게 고정
			eraseOneLine();

			if (isGameEnded()) { // 게임이 종료됨.
				gameOver();
				return;
			}

			setNextBlock();
		}
		score = getScore() + 1;
		label.setText("score: " + getScore() + "");
		placeBlock();
	}

	protected void setNextBlock() {
		curr = nextBlock;
		if (lineNum >= 10) {
			nextBlock = getItemBlock();
			lineNum = 0;
		} else {
			nextBlock = getRandomBlock();
		}
		x = 3;
		y = 0;
		drawNextBlockBoard();
	}

	protected void Litem() {
		int line = 0;
		for (int i = 0; i < curr.width(); i++) {
			for (int j = 0; j < curr.height(); j++) {
				if (curr.getShape(i, j) == 2) {
					line = j;
					break;
				}
			}
		}
		StyledDocument doc = gamePane.getStyledDocument();
		gamePane.setStyledDocument(doc);
		for (int i = 0; i < WIDTH; i++) {
			board[line + y][i] = 1;
		}
	}

	protected void gameOver() {
		timer.stop();
		label.setText("Game Ended. Score: " + getScore());
		placeBlock();

		try {
			ScoreBoardMenu sbf = new ScoreBoardMenu(score);
			sbf.setVisible(true);
			Tetris.disposeGameMenu();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void speedUp() {
		if (getScore() / 100 > level && initInterval > 100) {
			level += 1;
			initInterval -= 10;
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
		for (int i = 0; i < WIDTH; i++) {
			if (board[0][i] == 1)
				return true;
		}
		return false;
	}

	protected void moveRight() {
		eraseCurr();
		if (x < WIDTH - curr.width() && !detectCrash('R'))
			x++;
		placeBlock();
	}

	protected void moveLeft() {
		eraseCurr();
		if (x > 0 && !detectCrash('L')) {
			x--;
		}
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
				score = getScore() + 10;
				lineNum++;
				label.setText("score: " + getScore() + "");
			}
		}
	}

	protected boolean detectCrash(char position) {
		boolean result = false;
		switch (position) {
		case 'L':
			for (int i = 0; i < curr.height(); i++) {
				for (int j = 0; j < curr.width(); j++) {
					if (curr.getShape(j, i) == 1) {
						if (board[i + y][j + x - 1] == 1) {
							result = true;
							break;
						}
						j = curr.width();
					}
				}
			}
			break;
		case 'R':
			for (int i = 0; i < curr.height(); i++) {
				for (int j = curr.width() - 1; j >= 0; j--) {
					if (curr.getShape(j, i) == 1) {
						if (board[i + y][j + x + 1] == 1) {
							result = true;
							break;
						}
						j = -1;
					}
				}
			}
			break;
		case 'D':
			for (int i = 0; i < curr.width(); i++) {
				for (int j = curr.height() - 1; j >= 0; j--) {
					if (curr.getShape(i, j) == 1) {
						if (board[j + y + 1][i + x] == 1) {
							result = true;
							break;
						}
						j = -1;
					}
				}
			}
			break;
		default:
			System.out.println("Wrong Character");
			break;
		}

		return result;
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
					doc.setCharacterAttributes(13+i*(WIDTH+3)+j+1, 1, gamePane.getStyle(colorBoard[i][j]), false);
		gamePane.setStyledDocument(doc);
	}

	public void drawNextBlockBoard() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (int i = 0; i < 2; i++) {
			sb.append("  ");
			for (int j = 0; j < 5; j++) {
				if (nextBlock.width() > j && nextBlock.height() > i) {
					if (nextBlock.getShape(j, i) == 1) {
						sb.append("O");
					} else if (nextBlock.getShape(j, i) == 2) {
						sb.append("L");
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
		// Jtextpane

		StyledDocument doc = nextBlockPane.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		doc.setCharacterAttributes(0, doc.getLength(), gamePane.getStyle(nextBlock.getColor()), false);
		nextBlockPane.setStyledDocument(doc);
	}

	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			label.setText("score: " + getScore() + "");
		} else {
			timer.stop();
			label.setText("paused");
		}
	}

	public void reset() {
		this.board = new int[20][10];
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
		lineNum = 0;
		label.setText("score: " + getScore() + "");
	}

	public int getScore() {
		return score;
	}
}