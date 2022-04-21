package scoreboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.*;

import file.ScoreBoardFile;
<<<<<<< HEAD:src/main/java/scoreboard/ScoreBoardMenu.java
import play.Tetris;
=======
import main.Tetris;
import startmenu.ImagePanel;
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/scoreboard/ScoreBoardMenu.java

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
	private final int oneLineLength = 37;
	private int score;
	private String mode;
<<<<<<< HEAD:src/main/java/scoreboard/ScoreBoardMenu.java

	private String level = "normal";
	private String scoreWriteText = "Game over. Enter your name within\nfour characters with alphabet and number.";

=======
	
	private String level = "normal";
	private String scoreWriteText = "Game over. Enter your name within\nfour characters with alphabet and number.";
	
	private Image screenImage;
	private Graphics screenGraphic;
	private Image scoreboardBackground = new ImageIcon(Tetris.class.getResource("../images/scoreboardBackground.png"))
			.getImage();
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/scoreboard/ScoreBoardMenu.java

	public ScoreBoardMenu() {
		super("ScoreBoard"); // 타이틀
		setResizable(false);
		requestFocus();
<<<<<<< HEAD:src/main/java/scoreboard/ScoreBoardMenu.java

=======
		JPanel imagepanel = new ImagePanel(scoreboardBackground);
		imagepanel.setOpaque(false); 
		//add(imagepanel);
		
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/scoreboard/ScoreBoardMenu.java
		setSize(350, 580);
		scoreboard.setEditable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// load scoreboard file
		try {
			sb = new ScoreBoardFile();
			scoreboard.setText(sb.readScoreBoard());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// set style
		setStyle();

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
		scoreboard.setOpaque(true);
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
		enterBtn.setEnabled(false);
		nameEnter.setEditable(false);
		label.setText("역대 테트리스 게임 점수");
	}

	public ScoreBoardMenu(int score, String level, String mode) throws NumberFormatException, IOException {
		super("ScoreBoard"); // 타이틀
		setResizable(false);
		this.score = score;
		this.level = level;
		this.mode = mode;
		requestFocus();

		setSize(350, 580);
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
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Game Over");
			} else {
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),scoreWriteText);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// set style
		setStyle();

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
	
	private void setStyle() {
		Style redStyle = scoreboard.addStyle("Red", null);
		StyleConstants.setForeground(redStyle, Color.RED);
		StyleConstants.setBold(redStyle, true);

		Style boldStyle = scoreboard.addStyle("Bold", null);
		StyleConstants.setBold(boldStyle, true);

		StyledDocument doc = scoreboard.getStyledDocument();
		SimpleAttributeSet mainAttribute = new SimpleAttributeSet();
		StyleConstants.setFontFamily(mainAttribute, "Courier");
		StyleConstants.setFontSize(mainAttribute, 15);
		StyleConstants.setLineSpacing(mainAttribute, (float) 0.34);
		doc.setParagraphAttributes(0, doc.getLength(), mainAttribute, false);
		doc.setCharacterAttributes(0, oneLineLength * 3, scoreboard.getStyle("Bold"), true);

		SimpleAttributeSet styleSet = new SimpleAttributeSet();
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
	}

	private void setStyle() {
		Style redStyle = scoreboard.addStyle("Red", null);
		StyleConstants.setForeground(redStyle, Color.RED);
		StyleConstants.setBold(redStyle, true);

		Style boldStyle = scoreboard.addStyle("Bold", null);
		StyleConstants.setBold(boldStyle, true);

		StyledDocument doc = scoreboard.getStyledDocument();
		SimpleAttributeSet mainAttribute = new SimpleAttributeSet();
		StyleConstants.setFontFamily(mainAttribute, "Courier");
		StyleConstants.setFontSize(mainAttribute, 15);
		StyleConstants.setLineSpacing(mainAttribute, (float) 0.34);
		doc.setParagraphAttributes(0, doc.getLength(), mainAttribute, false);
		doc.setCharacterAttributes(0, oneLineLength * 3, scoreboard.getStyle("Bold"), true);

		SimpleAttributeSet styleSet = new SimpleAttributeSet();
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
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
		if (name.length() > 4 || name.isEmpty() || !isAlphaOrDigit(name)) {
			nameEnter.setText("");
		} else {
			nameEnter.setText("");
			nameEnter.setEditable(false);
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
<<<<<<< HEAD:src/main/java/scoreboard/ScoreBoardMenu.java
=======
	
	public void paint(Graphics g) {
		screenImage = createImage(350, 583);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(scoreboardBackground, 0, 0, null);
		paintComponents(g);
	}

>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/scoreboard/ScoreBoardMenu.java
	private void btnStartMenuActionPerformed() {
		this.dispose();
		Tetris.showStartMenu();
	}
<<<<<<< HEAD:src/main/java/scoreboard/ScoreBoardMenu.java

=======
	
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/scoreboard/ScoreBoardMenu.java
	private boolean isAlphaOrDigit(String s) {
		for(int i=0;i<s.length();i++) {
			char ch = s.charAt(i);
			if(!(ch >='A'&&ch<='Z')&& !(ch>='a'&&ch<='z') && !(ch>='0' && ch<='9'))
				return false;
		}
		return true;
	}
<<<<<<< HEAD:src/main/java/scoreboard/ScoreBoardMenu.java
}
=======
}
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/scoreboard/ScoreBoardMenu.java
