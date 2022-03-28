package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import blocks.Block;
import blocks.IBlock;
import blocks.JBlock;
import blocks.LBlock;
import blocks.OBlock;
import blocks.SBlock;
import blocks.TBlock;
import blocks.ZBlock;

public class Board extends JFrame {

	private static final long serialVersionUID = 2434035659171694595L;
	
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final char BORDER_CHAR = 'X';
	
	private JTextPane gamePane;
	private JTextPane nextBlockPane;
	private JTextPane label;
	private int[][] board;
	private KeyListener playerKeyListener;
	private KeyListener GameOverKeyListener;
	private SimpleAttributeSet styleSet;
	private Timer timer;
	private Block curr;
	private Block nextBlock;
	private int score = 0;
	private int level = 0;
	int x = 3; //Default Position.
	int y = 0;
	
	private static int initInterval = 1000;
	
	public Board() {
		super("SeoulTech SE Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Board display setting.
		label = new JTextPane();
		label.setVisible(true);
		label.setText("score: "+score+"");
		label.setOpaque(true);
		label.setBackground(Color.white);
		label.setForeground(Color.BLACK);
		
		gamePane = new JTextPane();
		nextBlockPane = new JTextPane();
		
		gamePane.setEditable(false);
		gamePane.setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		gamePane.setBorder(border);
		
		nextBlockPane.setEditable(false);
		nextBlockPane.setBackground(Color.BLACK);
		nextBlockPane.setBorder(border);
		
		JPanel eastPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		scorePanel.add(label);
		
		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(nextBlockPane,BorderLayout.NORTH);
		
		this.getContentPane().add(scorePanel, BorderLayout.NORTH);
		this.getContentPane().add(gamePane, BorderLayout.CENTER);
		this.getContentPane().add(eastPanel,BorderLayout.EAST);
		
		//Document default style.
		styleSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(styleSet, 18);
		StyleConstants.setFontFamily(styleSet, Font.MONOSPACED);
		StyleConstants.setBold(styleSet, true);
		StyleConstants.setForeground(styleSet, Color.WHITE);
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
		
		//Set timer for block drops.
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawGameBoard();
			}
		});
		
		//Initialize board for the game.
		board = new int[HEIGHT][WIDTH];
		playerKeyListener = new PlayerKeyListener();
		addKeyListener(playerKeyListener);
		addKeyListener(GameOverKeyListener);
		setFocusable(true);
		requestFocus();
		
		//Create the first block and draw.
		nextBlock = getRandomBlock();
		curr = getRandomBlock();
		placeBlock();
		drawGameBoard();
		drawNextBlockBoard();
		timer.start();
	}
	
	private Block getRandomBlock() {
		Random rnd = new Random(System.currentTimeMillis());
		int block = rnd.nextInt(1000)%6;
		switch(block) {
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
	
	private void placeBlock() {
		StyledDocument doc = gamePane.getStyledDocument();
		SimpleAttributeSet styles = new SimpleAttributeSet();
		//StyleConstants.setForeground(styles, curr.getColor());
		
		for(int j=0; j<curr.height(); j++) {
			int rows = y+j == 0 ? 0 : y+j-1;
			int offset = rows * (WIDTH+3) + x + 1;
			doc.setCharacterAttributes(offset, curr.width(), styles, true);
			for(int i=0; i<curr.width(); i++) {
				if(y+j<HEIGHT && x+i < WIDTH)
					if(board[y+j][x+i]==0)
						board[y+j][x+i] = curr.getShape(i, j);
			}
		}
	}
	
	
	private void eraseCurr() throws java.lang.ArrayIndexOutOfBoundsException {
		for(int i=x; i<x+curr.width(); i++) {
			for(int j=y; j<y+curr.height(); j++) {
				if(curr.getShape(i-x, j-y)==1 && i<WIDTH && j<HEIGHT)
					board[j][i] = 0;
			}
		}
	}

	
	protected void moveDown() {
		eraseCurr();
		if(y < HEIGHT - curr.height() && !detectCrash('D'))
			y++;
		else {
			placeBlock(); //밑으로 내려가지 않게 고정 
			eraseOneLine();
			
			if(isGameEnded()) { //게임이 종료됨. 
				gameOver();
				return;
			}
			
			curr = nextBlock;
			nextBlock = getRandomBlock();
			x = 3;
			y = 0;
			drawNextBlockBoard();
		}
		score++;
		label.setText("score: "+score+"");
		placeBlock();
	}
	
	protected void gameOver() {
		timer.stop();
		removeKeyListener(playerKeyListener);
		label.setText("Game Ended. Score: "+score);
		placeBlock();
		new ScoreBoard(score);
	}
	
	protected void speedUp() {
		if(score/100 > level && initInterval>100) {
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
		for(int i=0;i<WIDTH;i++) {
			if(board[0][i]==1)
				return true;
		}
		return false;
	}
	
	protected void moveRight() {
		eraseCurr();
		if(x < WIDTH - curr.width() && !detectCrash('R')) x++;
		placeBlock();
	}

	protected void moveLeft() {
		eraseCurr();
		if(x > 0 && !detectCrash('L')) {
			x--;
		}
		placeBlock();
	}

	protected void eraseOneLine() {
		for(int i=0;i<HEIGHT;i++) {
			boolean lineClear = true;
			for(int j=0;j<WIDTH;j++) {
				if(board[i][j]==0) {
					lineClear = false;
					j = WIDTH;
				}
			}
			if(lineClear) {
				speedUp();
				System.out.println("one line cleared!");
				for(int k=i;k>1;k--) {
					for(int l=0;l<WIDTH;l++) {
						board[k][l] = board[k-1][l];
					}
				}
				 score += 10;
				 label.setText("score: "+score+"");
			}
		}
	}
	
	protected boolean detectCrash(char position) {
		boolean result = false;
		switch(position) {
		case 'L':
			for(int i=0;i<curr.height();i++) {
				for(int j=0;j<curr.width();j++) {
					if(curr.getShape(j, i)==1) {
						if(board[i+y][j+x-1]==1) {
							result = true;
							break;
						}
						j = curr.width();
					}
				}
			}
			break;
		case 'R':
			for(int i=0;i<curr.height();i++) {
				for(int j=curr.width()-1;j>=0;j--) {
					if(curr.getShape(j, i)==1) {
						if(board[i+y][j+x+1]==1) {
							result = true;
							break;
						}
						j = -1;
					}
				}
			}
			break;
		case 'D':
			for(int i=0;i<curr.width();i++) {
				for(int j=curr.height()-1;j>=0;j--) {
					if(curr.getShape(i, j)==1) {
						if(board[j+y+1][i+x]==1) {
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
		for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
		sb.append("\n");
		for(int i=0; i < board.length; i++) {
			sb.append(BORDER_CHAR);
			for(int j=0; j < board[i].length; j++) {
				if(board[i][j] == 1) {
					sb.append("O");
				} else {
					sb.append(" ");
				}
			}
			sb.append(BORDER_CHAR);
			sb.append("\n");
		}
		for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
		gamePane.setText(sb.toString());
		//Jtextpane

		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		gamePane.setStyledDocument(doc);
	}
	
	public void drawNextBlockBoard() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for(int i=0; i < 2; i++) {
			sb.append("  ");
			for(int j=0; j < 5; j++) {
				if(nextBlock.width()>j && nextBlock.height()>i) {
					if(nextBlock.getShape(j, i) == 1) {
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
		//Jtextpane
		
		StyledDocument doc = nextBlockPane.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		nextBlockPane.setStyledDocument(doc);
	}
	
	public void pause() {
		if(!timer.isRunning()) {
			timer.start();
			label.setText("score: "+score+"");
			addKeyListener(playerKeyListener);
		}
		else {
			timer.stop();
			label.setText("paused");
			removeKeyListener(playerKeyListener);
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
		playerKeyListener = new PlayerKeyListener();
		addKeyListener(playerKeyListener);
		setFocusable(true);
		requestFocus();
		
		nextBlock = getRandomBlock();
		curr = getRandomBlock();
		placeBlock();
		drawGameBoard();
		drawNextBlockBoard();
		
		score = 0;
		label.setText("score: "+score+"");
		
		timer.start();
	}

	public class PlayerKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			switch(e.getKeyChar()) {
			case 'p':
				pause();
				break;
			case 's':
				reset();
				break;
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				moveDown();
				drawGameBoard();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				drawGameBoard();
				break;
			case KeyEvent.VK_LEFT:
				moveLeft();
				drawGameBoard();
				break;
			case KeyEvent.VK_UP: //Rotate 수정해야함 
				eraseCurr();
				if(x+curr.height()>=WIDTH) x = WIDTH - curr.height();
				curr.rotate();
				for(int i=x;i<x+curr.width();i++) {
					for(int j=y;j<y+curr.height();j++) {
						if(board[j][i]==1 && curr.getShape(i-x, j-y)==1) {
							curr.rotate();
							curr.rotate();
							curr.rotate();
							break;
						}
					}
				}
				placeBlock();
				drawGameBoard();
				break;
			case KeyEvent.VK_SPACE:
				eraseCurr();
				while(y < HEIGHT - curr.height() && !detectCrash('D')) {
					y++;
				}
				placeBlock(); //밑으로 내려가지 않게 고정 
				eraseOneLine();
				curr = nextBlock;
				nextBlock = getRandomBlock();
				x = 3;
				y = 0;
				placeBlock();
				drawGameBoard();
				drawNextBlockBoard();
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	}
}
