package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import main.Tetris;
import setting.SettingItem;

public class FightMenu extends JFrame implements KeyListener {

	private SettingItem settingItem;
	private GameBoard gameBoard1;
	private GameBoard gameBoard2;;
	private GamePane gamePane1;
	private GamePane gamePane2;
	protected NextBlockPane nextBlockPane1;
	protected NextBlockPane nextBlockPane2;
	protected static int initInterval;
	protected JLabel scoreLabel1;
	protected JLabel scoreLabel2;
	protected String gameMode;
	protected Timer timer;

	public FightMenu(boolean isItemMode) throws IOException {
		super("Tetris Fight");
		settingItem = SettingItem.getInstance();
		setSize(settingItem.getBoardWidth() * 2, settingItem.getBoardHeight());
		SettingItem.isItemMode = isItemMode;
		setResizable(false);
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// lower panel
		JButton settingButton = new JButton("Settings");
		JButton startMenuBtn = new JButton("Startmenu");
		JButton scoreBoardBtn = new JButton("Scoreboard");
		settingButton.addActionListener(e -> btnSettingActionPerformed());
		startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
		scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());
		JPanel lowerPanel = new JPanel(new GridLayout(0, 3));
		lowerPanel.add(settingButton);
		lowerPanel.add(startMenuBtn);
		lowerPanel.add(scoreBoardBtn);

		if (isItemMode) {
			gameBoard1 = new ItemGameBoard();
			gameBoard2 = new ItemGameBoard();
			gameMode = "item";
		} else {
			gameBoard1 = new GameBoard();
			gameBoard2 = new GameBoard();
			gameMode = "regul";
		}
		gameBoard1.gamePane.setFontSize(settingItem.getFontSize());
		gameBoard1.drawBoard();
		gameBoard2.gamePane.setFontSize(settingItem.getFontSize());
		gameBoard2.drawBoard();
		nextBlockPane1 = new NextBlockPane(settingItem.isColorBlind());
		nextBlockPane1.setFontSize(settingItem.getFontSize());
		nextBlockPane1.drawNextBlockBoard(gameBoard1.getNextBlock());
		nextBlockPane2 = new NextBlockPane(settingItem.isColorBlind());
		nextBlockPane2.setFontSize(settingItem.getFontSize());
		nextBlockPane2.drawNextBlockBoard(gameBoard2.getNextBlock());
		
		JPanel sidePanel1 = new JPanel();
		sidePanel1.setBackground(Color.white);
		JPanel sidePanel2 = new JPanel();
		sidePanel2.setBackground(Color.white);
		gamePane1 = new GamePane();
		gamePane1.draw();
		sidePanel1.add(gamePane1);
		gamePane2 = new GamePane();
		gamePane2.draw();
		sidePanel2.add(gamePane2);

		// Side display
		scoreLabel1 = new JLabel(gameBoard1.getScore() + "", JLabel.CENTER);
		TitledBorder tborder = new TitledBorder("SCORE");
		tborder.setTitlePosition(TitledBorder.ABOVE_TOP);
		tborder.setTitleJustification(TitledBorder.CENTER);
		scoreLabel1.setBorder(tborder);
		scoreLabel1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		scoreLabel1.setBackground(Color.white);

		// Side display
		scoreLabel2 = new JLabel(gameBoard2.getScore() + "", JLabel.CENTER);
		scoreLabel2.setBorder(tborder);
		scoreLabel2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		scoreLabel2.setBackground(Color.white);
		updateScore();

		// left panel
		JPanel rightPanel1 = new JPanel();
		rightPanel1.setLayout(new BoxLayout(rightPanel1, BoxLayout.Y_AXIS));
		rightPanel1.add(nextBlockPane1);
		rightPanel1.add(scoreLabel1);
		rightPanel1.add(sidePanel1);
		rightPanel1.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel1.setBackground(Color.WHITE);
		JPanel rightPanel2 = new JPanel();
		rightPanel2.setLayout(new BoxLayout(rightPanel2, BoxLayout.Y_AXIS));
		rightPanel2.add(nextBlockPane2);
		rightPanel2.add(scoreLabel2);
		rightPanel2.add(sidePanel2);
		rightPanel2.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel2.setBackground(Color.WHITE);

		// upper panel
		JPanel panel1 = new JPanel();
		panel1.add(gameBoard1);
		panel1.add(rightPanel1);
		panel1.setBackground(Color.white);
		JPanel panel2 = new JPanel();
		panel2.add(gameBoard2);
		panel2.add(rightPanel2);
		panel2.setBackground(Color.white);
		JPanel upperPanel = new JPanel(new GridLayout(0, 2));
		upperPanel.add(panel1);
		upperPanel.add(panel2);

		// outer panel
		JPanel outerPanel = new JPanel();
		outerPanel.add(upperPanel);
		outerPanel.add(lowerPanel);
		setContentPane(outerPanel);
		outerPanel.setForeground(Color.WHITE);

		// Set timer for block drops.
		initInterval = settingItem.getInitInterval();
		addKeyListener(this);
		setTimer();
		setFocusable(true);
		requestFocus();
	}

	protected void setTimer() {
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameBoard1.moveDown();
				gameBoard2.moveDown();
				updateScore();
			}
		});
		timer.start();
	}

	protected void updateScore() {
		scoreLabel1.setText(String.format("%5s", gameBoard1.getScore() + "") + " ");
		scoreLabel2.setText(String.format("%5s", gameBoard2.getScore() + "") + " ");
		if (Math.max(gameBoard1.getLineNum(), gameBoard2.getLineNum()) / 7 >= gameBoard1.getLevel()) {
			gameBoard1.setLevel(gameBoard1.getLevel() + 1);
			gameBoard2.setLevel(gameBoard2.getLevel() + 1);
			initInterval -= settingItem.getReduceSpeed();
			timer.stop();
			setTimer();
		}
		if (gameBoard1.getGameEnded() || gameBoard2.getGameEnded()) { // 게임이 종료됨.
			gameOver();
			return;
		}
		if(gameBoard1.getLineNumForFightMode()>1) {
			gamePane2.setLines(gameBoard1.getErasedLine());
			gameBoard1.resetErasedLine();
		}
		if(gameBoard2.getLineNumForFightMode()>1) {
			gamePane1.setLines(gameBoard2.getErasedLine());
			gameBoard2.resetErasedLine();
		}
	}

	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			gameBoard1.drawBoard();
			gameBoard2.drawBoard();
		} else {
			timer.stop();
			gameBoard1.setGameBoardText("PAUSE");
			gameBoard2.setGameBoardText("PAUSE");
		}
	}

	protected void gameOver() {
		timer.stop();
		if (gameBoard1.getScore() > gameBoard2.getScore()) {
			gameBoard1.setGameBoardText("WIN!");
			gameBoard2.setGameBoardText("LOSE");
		} else {
			gameBoard1.setGameBoardText("LOSE");
			gameBoard2.setGameBoardText("WIN!");
		}
	}

	private void btnScoreBoardActionPerformed() {
		timer.stop();
		dispose();
		Tetris.showScoreBoard();
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (timer.isRunning())
				gameBoard2.moveRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (timer.isRunning())
				gameBoard2.moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (timer.isRunning()) {
				gameBoard2.moveDown();
				nextBlockPane2.drawNextBlockBoard(gameBoard2.getNextBlock());
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (timer.isRunning())
				gameBoard2.rotateBlock();
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (timer.isRunning()) {
				gameBoard2.dropBlock();
				nextBlockPane2.drawNextBlockBoard(gameBoard2.getNextBlock());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if ((e.getKeyChar()+"").toLowerCase().charAt(0) == 'a') {
			if (timer.isRunning()) {
				gameBoard1.moveLeft();
			}
		} else if ((e.getKeyChar()+"").toLowerCase().charAt(0) == 's') {
			if (timer.isRunning()) {
				gameBoard1.moveDown();
				nextBlockPane1.drawNextBlockBoard(gameBoard1.getNextBlock());
			}
		} else if ((e.getKeyChar()+"").toLowerCase().charAt(0) == 'w') {
			if (timer.isRunning())
				gameBoard1.rotateBlock();
		} else if ((e.getKeyChar()+"").toLowerCase().charAt(0) == 'd') {
			if (timer.isRunning())
				gameBoard1.moveRight();
		} else if ((e.getKeyChar()+"").toLowerCase().charAt(0) == 'r') {
			if (timer.isRunning()) {
				gameBoard1.dropBlock();
				nextBlockPane1.drawNextBlockBoard(gameBoard1.getNextBlock());
			}
		} else if ((e.getKeyChar()+"").toLowerCase().charAt(0) == 'p') {
			pause();
		}
	}
}
