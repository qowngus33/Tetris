package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import main.Tetris;
import setting.SettingItem;

import static java.lang.System.currentTimeMillis;

public class FightMenu extends JFrame implements KeyListener {

	private SettingItem settingItem;
	private GameBoard [] gameBoard;
	private GamePane [] gamePane;

	private static int initInterval;
	private JLabel [] scoreLabel;
	private JLabel [] lineLabel;

	private Timer timer;
	private final long startTime;
	private final long exitTime = 60000;
	private final boolean isTimeAttackMode;
	private boolean isGameEnded;

	public FightMenu(boolean isItemMode,boolean isTimeAttackMode) throws IOException {
		super("Tetris Fight");
		settingItem = SettingItem.getInstance();
		setSize(settingItem.getBoardWidth() * 2, settingItem.getBoardHeight());
		SettingItem.isItemMode = isItemMode;
		this.isTimeAttackMode = isTimeAttackMode;
		setResizable(false);
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// lower panel
		JButton settingButton = new JButton("Settings");
		JButton startMenuBtn = new JButton("Start Menu");
		JButton scoreBoardBtn = new JButton("EXIT");
		JButton restartBtn = new JButton("restart");
		settingButton.addActionListener(e -> btnSettingActionPerformed());
		startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
		scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());
		restartBtn.addActionListener(e -> btnRestartActionPerformed());
		JPanel lowerPanel = new JPanel(new GridLayout(0, 4));
		lowerPanel.add(settingButton);
		lowerPanel.add(startMenuBtn);
		lowerPanel.add(scoreBoardBtn);
		lowerPanel.add(restartBtn);

		JPanel upperPanel = new JPanel(new GridLayout(0, 2));
		gameBoard = new GameBoard[2];
		gamePane = new GamePane[2];
		scoreLabel = new JLabel[2];
		lineLabel = new JLabel[2];
		upperPanel.add(init(isItemMode,0));
		upperPanel.add(init(isItemMode,1));

		// outer panel
		JPanel outerPanel = new JPanel();
		outerPanel.add(upperPanel);
		outerPanel.add(lowerPanel);
		setContentPane(outerPanel);
		outerPanel.setForeground(Color.WHITE);

		// Set timer for block drops.
		initInterval = settingItem.getInitInterval();
		JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "PLAYER1: A←,S↓,D→,W(rotate),SPACE BAR(drop)\nPLAYER2: Arrow keys, ENTER(drop)");
		startTime = currentTimeMillis();
		addKeyListener(this);
		updateScore();
		setTimer();
		setFocusable(true);
		requestFocus();
	}

	private JPanel init(boolean isItemMode, int i) throws IOException {
		if (isItemMode) {
			gameBoard[i] = new ItemGameBoard();
		} else {
			gameBoard[i] = new GameBoard();
		}
		gameBoard[i].gamePane.setFontSize(settingItem.getFontSize());
		gameBoard[i].drawBoard();
		gameBoard[i].nextBlockPane.drawNextBlockBoard(gameBoard[i].getNextBlock());

		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(Color.white);
		gamePane[i] = new GamePane();
		gamePane[i].draw();
		sidePanel.add(gamePane[i]);

		// Side display
		scoreLabel[i] = new JLabel(gameBoard[i].getScore() + "", JLabel.CENTER);
		TitledBorder border = new TitledBorder("SCORE");
		border.setTitlePosition(TitledBorder.ABOVE_TOP);
		border.setTitleJustification(TitledBorder.CENTER);
		scoreLabel[i].setBorder(border);
		scoreLabel[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		scoreLabel[i].setBackground(Color.white);
		lineLabel[i] = new JLabel(gameBoard[i].getLineNum() + "", JLabel.CENTER);
		TitledBorder border2 = new TitledBorder("LINE");
		border2.setTitlePosition(TitledBorder.ABOVE_TOP);
		border2.setTitleJustification(TitledBorder.CENTER);
		lineLabel[i].setBorder(border2);
		lineLabel[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		lineLabel[i].setBackground(Color.white);

		// left panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(gameBoard[i].nextBlockPane);
		rightPanel.add(scoreLabel[i]);
		rightPanel.add(lineLabel[i]);
		rightPanel.add(sidePanel);
		rightPanel.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.setBackground(Color.WHITE);

		// upper panel
		JPanel panel = new JPanel();
		panel.add(gameBoard[i]);
		panel.add(rightPanel);
		panel.setBackground(Color.white);

		return panel;
	}

	protected void setTimer() {
		timer = new Timer(initInterval, e -> {
			moveDown(0);
			moveDown(1);
			updateScore();
		});
		timer.start();
	}

	protected void updateScore() {
		for(int i=0;i<2;i++){
			scoreLabel[i].setText(String.format("%5s", gameBoard[i].getScore() + "") + " ");
			lineLabel[i].setText(String.format("%5s", gameBoard[i].getLineNum() + "") + " ");
			int temp = (i==0)?1:0;
			if (gameBoard[i].getErasedLine().length > 1)
				gamePane[temp].setLines(gameBoard[i].getErasedLine());
			if (gameBoard[i].getLineNum()/ 7 >= gameBoard[i].getLevel()) {
				gameBoard[i].setLevel(gameBoard[i].getLevel() + 1);
				gameBoard[temp].setLevel(gameBoard[temp].getLevel() + 1);
				initInterval -= settingItem.getReduceSpeed();
				timer.stop();
				setTimer();
			}
		}
	}

	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			gameBoard[0].drawBoard();
			gameBoard[1].drawBoard();
		} else {
			timer.stop();
			gameBoard[0].setGameBoardText("PAUSE");
			gameBoard[1].setGameBoardText("PAUSE");
		}
	}

	protected void moveDown(int i) {
		if (!gameBoard[i].moveDown()) {
			if(newBlock(i)){
				gameBoard[i].placeBlock();
			} else{
				gameBoard[i].placeBlock();
				gameBoard[i].drawBoard();
			}
		} else{
			gameBoard[i].placeBlock();
			gameBoard[i].drawBoard();
		}
	}

	protected boolean newBlock(int i) {
		gameBoard[i].placeBlock(); // 밑으로 내려가지 않게 고정
		System.out.println(currentTimeMillis()-startTime);
		if (gameBoard[i].isGameEnded() || (isTimeAttackMode && currentTimeMillis()-startTime>exitTime)) {
			timer.stop();
			isGameEnded = true;
			gameOver();
			return false;
		}
		int temp = (i == 0) ? 1:0;
		gameBoard[i].gamePane.addLines(gameBoard[temp].erasedLine);
		gameBoard[temp].resetErasedLine();
		gamePane[i].setLines(gameBoard[temp].getErasedLine());

		boolean isErased = gameBoard[i].eraseLine();
		gameBoard[i].curr = gameBoard[i].nextBlock;
		if(SettingItem.isItemMode && (gameBoard[i].lineNum/gameBoard[i].count >= gameBoard[i].lineChange)) {
			gameBoard[i].nextBlock = gameBoard[i].getRandomBlock.getItemBlock(gameBoard[i].modeName);
			gameBoard[i].count = gameBoard[i].count+1;
		} else {
			gameBoard[i].nextBlock = gameBoard[i].getRandomBlock.getRandomBlockMode(gameBoard[i].modeName);
		}
		gameBoard[i].x = 3;
		gameBoard[i].y = 0;
		gameBoard[i].nextBlockPane.drawNextBlockBoard(gameBoard[i].getNextBlock());
		return isErased;
	}
	private void gameOver() {
		String text = "draw";
		if(gameBoard[0].isGameEnded() && !gameBoard[1].isGameEnded()) {
			gameBoard[0].setGameBoardText("LOSE");
			gameBoard[1].setGameBoardText("WIN!");
			text = "player2 win";
		} else if(!gameBoard[0].isGameEnded() && gameBoard[1].isGameEnded()){
			gameBoard[0].setGameBoardText("WIN!");
			gameBoard[1].setGameBoardText("LOSE");
			text = "player1 win";
		} else if(gameBoard[0].isGameEnded() && gameBoard[1].isGameEnded()){
			gameBoard[0].setGameBoardText("DRAW!");
			gameBoard[1].setGameBoardText("DRAW!");
		} else{
			if(gameBoard[0].score>gameBoard[1].score){
				gameBoard[0].setGameBoardText("WIN!");
				gameBoard[1].setGameBoardText("LOSE");
				text = "player1 win";
			} else if(gameBoard[0].score<gameBoard[1].score){
				gameBoard[0].setGameBoardText("LOSE");
				gameBoard[1].setGameBoardText("WIN!");
				text = "player2 win";
			} else{
				gameBoard[0].setGameBoardText("DRAW!");
				gameBoard[1].setGameBoardText("DRAW!");
			}
		}
		JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), text);
	}

	private void btnScoreBoardActionPerformed() {
		timer.stop();
		dispose();
		System.exit(0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (timer.isRunning())
				gameBoard[1].moveRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (timer.isRunning())
				gameBoard[1].moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (timer.isRunning()) {
				moveDown(1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (timer.isRunning())
				gameBoard[1].rotateBlock();
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (timer.isRunning()) {
				gameBoard[1].dropBlock();
				if(newBlock(1)){
					gameBoard[1].placeBlock();
				} else{
					if(!isGameEnded){
						gameBoard[1].placeBlock();
						gameBoard[1].drawBoard();
					}
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			if (timer.isRunning())
				gameBoard[0].moveRight();
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			if (timer.isRunning())
				gameBoard[0].moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			if (timer.isRunning())
				moveDown(0);
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			if (timer.isRunning())
				gameBoard[0].rotateBlock();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (timer.isRunning()) {
				gameBoard[0].dropBlock();
				if(newBlock(0)){
					gameBoard[0].placeBlock();
				} else{
					if(!isGameEnded){
						gameBoard[0].placeBlock();
						gameBoard[0].drawBoard();
					}
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			pause();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stud
	}

	private void btnSettingActionPerformed() {
		timer.stop();
		dispose();
		Tetris.showSettingMenu();
	}

	private void btnStartMenuActionPerformed() {
		timer.stop();
		dispose();
		Tetris.showStartMenu();
	}

	private void btnRestartActionPerformed() {
		timer.stop();
		dispose();
		try {
			Tetris.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//to be edited
	}
}