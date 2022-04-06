package scoreboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.*;

import file.ScoreBoardFile;
import file.ScoreBoardFile;
import main.Tetris;

public class ScoreBoardMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private ScoreBoardFile sb;
	private JPanel outerPanel = new JPanel();
	private JPanel lowerPanel = new JPanel();
	private JPanel upperPanel = new JPanel();

	private JLabel label = new JLabel();
	private JButton enterBtn = new JButton("Enter");
	private JButton exitBtn = new JButton("Exit");
	private JButton startBtn = new JButton("Start Menu");
	private JTextPane scoreboard = new JTextPane();
	private JTextField nameEnter = new JTextField(10);
	private int oneLineLength = 41;
	private int score;
	private String mode;
	private String level = "normal";

	public ScoreBoardMenu(int score, String level, String mode) throws NumberFormatException, IOException {
		super("ScoreBoard"); // 타이틀
		this.setResizable(false);
		this.score = score;
		this.level = level;
		this.mode = mode;

		setSize(380, 800);
		scoreboard.setEditable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// load scoreboard file
		try {
			sb = new ScoreBoardFile();
			scoreboard.setText(sb.readScoreBoard());
			if (score < sb.isWritable()) {
				enterBtn.setEnabled(false);
				nameEnter.setEditable(false);
				label.setText("역대 테트리스 게임 점수");
			} else {
				label.setText("Enter name in two alphabet.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// set style
		Style redStyle = scoreboard.addStyle("Red", null);
		StyleConstants.setForeground(redStyle, Color.RED);
		StyleConstants.setBold(redStyle, true);

		Style boldStyle = scoreboard.addStyle("Bold", null);
		StyleConstants.setBold(boldStyle, true);

		StyledDocument doc = scoreboard.getStyledDocument();
		SimpleAttributeSet mainAttribute = new SimpleAttributeSet();
		StyleConstants.setFontFamily(mainAttribute, "Courier");
		StyleConstants.setFontSize(mainAttribute, 15);
		doc.setParagraphAttributes(0, doc.getLength(), mainAttribute, false);
		doc.setCharacterAttributes(0, oneLineLength * 3, scoreboard.getStyle("Bold"), true);

		SimpleAttributeSet styleSet = new SimpleAttributeSet();
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

		// set layout
		upperPanel.setLayout(new BorderLayout());
		upperPanel.add(startBtn, BorderLayout.EAST);
		upperPanel.add(label, BorderLayout.CENTER);
		upperPanel.add(exitBtn, BorderLayout.WEST);
		label.setHorizontalAlignment(JLabel.CENTER);

		lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		lowerPanel.add(nameEnter);
		lowerPanel.add(enterBtn);

		outerPanel.setLayout(new BorderLayout());
		outerPanel.add(scoreboard, BorderLayout.CENTER);
		outerPanel.add(lowerPanel, BorderLayout.SOUTH);
		outerPanel.add(upperPanel, BorderLayout.NORTH);
		add(outerPanel);

		// button action
		startBtn.addActionListener(e -> btnStartMenuActionPerformed());
		enterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryWriteScoreboard();
			}
		});
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public void reloadData() {
		try {
			scoreboard.setText(sb.readScoreBoard());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tryWriteScoreboard() {
		String name = nameEnter.getText();
		StyledDocument doc = scoreboard.getStyledDocument();
		if (name.length() > 2 || name.length() < 2) {
			label.setText("Enter name in two alphabet.");
		} else {
			label.setText("Name Entered.");
			nameEnter.setText("");
			try {
				int temp = sb.writeScoreBoard(name, score, level, mode);
				String scoreString = sb.readScoreBoard();
				scoreboard.setText(scoreString);

				doc.setCharacterAttributes(0, oneLineLength * 3, scoreboard.getStyle("Bold"), false);
				doc.setCharacterAttributes((oneLineLength * temp), oneLineLength, scoreboard.getStyle("Red"), false);
				enterBtn.setEnabled(false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void btnStartMenuActionPerformed() {
		this.dispose();
		Tetris.showStartMenu();
	}
}
