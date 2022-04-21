package game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import blocks.Block;

public class GamePane extends JTextPane {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected SimpleAttributeSet styleSet;
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final char BORDER_CHAR = 'X';
	protected int[][] board;
	protected String[][] colorBoard;

	public GamePane() {
		setEditable(false);
		setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 12),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
		setBorder(border);

		styleSet = new SimpleAttributeSet();
		simpleColor();
		StyleConstants.setLineSpacing(styleSet, (float) -0.2);
		StyleConstants.setFontSize(styleSet, 20);
		StyleConstants.setFontFamily(styleSet, Font.MONOSPACED);
		StyleConstants.setBold(styleSet, true);
		StyleConstants.setForeground(styleSet, Color.WHITE);
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

		board = new int[HEIGHT][WIDTH];
		colorBoard = new String[HEIGHT + 2][WIDTH + 2];
		for (int i = 0; i < HEIGHT + 2; i++)
			for (int j = 0; j < WIDTH + 2; j++)
				colorBoard[i][j] = "BLACK";
	}

	public GamePane(int iii) {
		setEditable(false);
		setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 12),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
		setBorder(border);

		System.out.println("색 맹 ");
		styleSet = new SimpleAttributeSet();
		colorBlindColor();

		StyleConstants.setLineSpacing(styleSet, (float) -0.2);
		StyleConstants.setFontSize(styleSet, 20);
		StyleConstants.setFontFamily(styleSet, Font.MONOSPACED);
		StyleConstants.setBold(styleSet, true);
		StyleConstants.setForeground(styleSet, Color.WHITE);
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

		board = new int[HEIGHT][WIDTH];
		colorBoard = new String[HEIGHT + 2][WIDTH + 2];
		for (int i = 0; i < HEIGHT + 2; i++)
			for (int j = 0; j < WIDTH + 2; j++)
				colorBoard[i][j] = "BLACK";
	}

	private void simpleColor(){
		Style r = addStyle("RED", null);
		StyleConstants.setForeground(r, new Color(225, 80, 80));
		Style c = addStyle("CYAN", null);
		StyleConstants.setForeground(c, new Color(96, 186, 240));
		Style b = addStyle("BLUE", null);
		StyleConstants.setForeground(b, new Color(60, 106, 210));
		Style o = addStyle("ORANGE", null);
		StyleConstants.setForeground(o, new Color(242, 126, 40));
		Style y = addStyle("YELLOW", null);
		StyleConstants.setForeground(y, new Color(245, 215, 80));
		Style g = addStyle("GREEN", null);
		StyleConstants.setForeground(g, new Color(141, 200, 60));
		Style m = addStyle("MAGENTA", null);
		StyleConstants.setForeground(m, new Color(180, 57, 160));
		Style w = addStyle("WHITE", null);
		StyleConstants.setForeground(w, Color.WHITE);
		Style B = addStyle("BLACK", null);
		StyleConstants.setForeground(B, Color.BLACK);
	}

	private void colorBlindColor(){
		System.out.println("색 맹 ");
		Style r = addStyle("RED", null);
		StyleConstants.setForeground(r, new Color(0, 66, 52));
		Style c = addStyle("CYAN", null);
		StyleConstants.setForeground(c, new Color(0, 206, 235));
		Style b = addStyle("BLUE", null);
		StyleConstants.setForeground(b, new Color(0, 0, 225));
		Style o = addStyle("ORANGE", null);
		StyleConstants.setForeground(o, new Color(0, 165, 0));
		Style y = addStyle("YELLOW", null);
		StyleConstants.setForeground(y, new Color(0, 255, 0));
		Style g = addStyle("GREEN", null);
		StyleConstants.setForeground(g, new Color(0, 152, 186));
		Style m = addStyle("MAGENTA", null);
		StyleConstants.setForeground(m, new Color(0, 53, 83));
		Style w = addStyle("WHITE", null);
		StyleConstants.setForeground(w, Color.WHITE);
		Style B = addStyle("BLACK", null);
		StyleConstants.setForeground(B, Color.BLACK);
	}

	public SimpleAttributeSet getStyleSet() {
		return styleSet;
	}

	public int getBoard(int i, int j) {
		if (i < 0 || i >= HEIGHT || j < 0 || j >= WIDTH)
			return 1;
		return board[i][j];
	}

	public void setBoard(int i, int j, int value) {
		board[i][j] = value;
	}

	public String getColorBoard(int i, int j) {
		return colorBoard[i][j];
	}

	public void setColorBoard(int i, int j, String value) {
		colorBoard[i][j] = value;
	}

	public void setFontSize(int fontSize) {
		StyleConstants.setFontSize(styleSet, fontSize);
	}

	public void drawGameBoard() {
		StyledDocument doc = this.getStyledDocument();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) sb.append("O");
				else if (board[i][j] == 2) sb.append("L");
				else sb.append(" ");
			}
			if (i != board.length - 1) sb.append("\n");
		}
		this.setText(sb.toString());
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		for (int i = 0; i < HEIGHT; i++)
			for (int j = 0; j < WIDTH; j++)
				doc.setCharacterAttributes(i * (WIDTH + 1) + j, 1, this.getStyle(colorBoard[i][j]), false);
		this.setStyledDocument(doc);
	}

	public void placeBlock(int x, int y, Block curr) {
		StyledDocument doc = this.getStyledDocument();
		for (int j = 0; j < curr.height(); j++) {
			doc.setParagraphAttributes(0, doc.getLength(), styleSet, true);
			this.setStyledDocument(doc);
			for (int i = 0; i < curr.width(); i++)
				if (y + j < HEIGHT && x + i < WIDTH)
					if (this.getBoard(y + j, x + i) == 0) {
						this.setBoard(y + j, x + i, curr.getShape(i, j));
						this.setColorBoard(y + j, x + i, curr.getColor());
					}
		}
	}
	protected void eraseCurr(int x, int y, Block curr) {
		for (int i = x; i < x + curr.width(); i++) {
			for (int j = y; j < y + curr.height(); j++) {
				if (curr.getShape(i - x, j - y) >= 1 && i < WIDTH && j < HEIGHT) {
					setBoard(j, i, 0);
					setColorBoard(j, i, "BLACK");
				}
			}
		}
	}
	public void resetBoard() {
		//To be edited ...
	}
}