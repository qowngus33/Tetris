package game;

import main.Tetris;
import scoreboard.ScoreBoardMenu;
import setting.SettingItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.KeyStroke;
import javax.swing.InputMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameMenu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SettingItem settingItem;
	private GameBoard gameBoard;
	protected NextBlockPane nextBlockPane;
	protected static int initInterval;
	protected JLabel scoreLabel;
	protected JLabel levelLabel;
	protected JLabel lineLabel;
	protected String gameMode;
	protected Timer timer;
	protected ScoreBoardMenu scoreBoardMenu;

	public GameMenu(boolean isItemMode) throws IOException {
		super("Tetris");
		settingItem = SettingItem.getInstance();
		setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
		SettingItem.isItemMode = isItemMode;
		
		if (isItemMode) {
			gameBoard = new ItemGameBoard();
			gameMode = "item";
		} else {
			gameBoard = new GameBoard();
			gameMode = "regul";
		}
		gameBoard.gamePane.setFontSize(settingItem.getFontSize());
		gameBoard.drawBoard();

		setResizable(false);
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		nextBlockPane = new NextBlockPane(settingItem.isColorBlind());
		nextBlockPane.setFontSize(settingItem.getFontSize());
		nextBlockPane.drawNextBlockBoard(gameBoard.getNextBlock());
		
		// Side display
		scoreLabel = new JLabel(gameBoard.getScore() + "", JLabel.CENTER);
		TitledBorder tborder = new TitledBorder("SCORE");
		tborder.setTitlePosition(TitledBorder.ABOVE_TOP);
		tborder.setTitleJustification(TitledBorder.CENTER);
		scoreLabel.setBorder(tborder);
		scoreLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		scoreLabel.setBackground(Color.white);

		levelLabel = new JLabel(gameBoard.getLevel() + "", JLabel.CENTER);
		TitledBorder tborder2 = new TitledBorder("LEVEL");
		tborder2.setTitlePosition(TitledBorder.ABOVE_TOP);
		tborder2.setTitleJustification(TitledBorder.CENTER);
		levelLabel.setBorder(tborder2);
		levelLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		levelLabel.setBackground(Color.white);

		lineLabel = new JLabel(gameBoard.getLineNum() + "", JLabel.CENTER);
		TitledBorder tborder3 = new TitledBorder("LINE");
		tborder3.setTitlePosition(TitledBorder.ABOVE_TOP);
		tborder3.setTitleJustification(TitledBorder.CENTER);
		lineLabel.setBorder(tborder3);
		lineLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		lineLabel.setBackground(Color.white);
		updateScore();

		// left panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(nextBlockPane);
		rightPanel.add(scoreLabel);
		rightPanel.add(levelLabel);
		rightPanel.add(lineLabel);
		rightPanel.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.setBackground(Color.WHITE);

		//upper panel
		JPanel upperPanel = new JPanel();
		upperPanel.add(gameBoard);
		upperPanel.add(rightPanel);
		upperPanel.setBackground(Color.white);
		
		//lower panel
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

		//outer panel
		JPanel outerPanel = new JPanel();
		outerPanel.add(upperPanel);
		outerPanel.add(lowerPanel);
		setContentPane(outerPanel);
		outerPanel.setForeground(Color.WHITE);

		// Set timer for block drops.
		initInterval = settingItem.getInitInterval();
		setTimer();
		setFocusable(true);
		requestFocus();
		initControls();
	}

	protected void initControls() {
		JRootPane rootPane = this.getRootPane();
		InputMap im = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am=  rootPane.getActionMap();
	
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
					gameBoard.moveRight();
				}
			}
		});
		am.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					gameBoard.moveLeft();
				}
			}
		});
		am.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					gameBoard.rotateBlock();
				}
			}
		});
		am.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					nextBlockPane.drawNextBlockBoard(gameBoard.getNextBlock());
					gameBoard.moveDown();
				}
			}
		});
		am.put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					gameBoard.dropBlock();
					nextBlockPane.drawNextBlockBoard(gameBoard.getNextBlock());
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
	
	protected void setTimer() {
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameBoard.moveDown();
				nextBlockPane.drawNextBlockBoard(gameBoard.getNextBlock());
				updateScore();
			}
		});
		timer.start();
	}

	protected void updateScore() {
		scoreLabel.setText(String.format("%5s", gameBoard.getScore() + "") + " ");
		levelLabel.setText(String.format("%5s", gameBoard.getLevel() + "") + " ");
		lineLabel.setText(String.format("%5s", gameBoard.getLineNum() + "") + " ");

		if (gameBoard.getLineNum() / 7 >= gameBoard.getLevel() && initInterval > 120) {
			gameBoard.setLevel(gameBoard.getLevel() + 1);
			initInterval -= settingItem.getReduceSpeed();
			timer.stop();
			setTimer();
		}
		if (gameBoard.getGameEnded()) { // 게임이 종료됨.
			gameOver();
			return;
		}
	}

	public void pause() {
		if (!timer.isRunning()) {
			timer.start();
			gameBoard.drawBoard();
		} else {
			timer.stop();
			gameBoard.setGameBoardText("PAUSE");
		}
	}

	protected void gameOver() {
		timer.stop();
		try {
			scoreBoardMenu = new ScoreBoardMenu(gameBoard.getScore(), gameBoard.getModeName(), gameMode);
			scoreBoardMenu.setVisible(true);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispose();
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
}