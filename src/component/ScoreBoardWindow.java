package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
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

import file.ScoreBoard;

public class ScoreBoardWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private ScoreBoard sb;
	private int score;

	public ScoreBoardWindow(int score) {
        super("ScoreBoard"); //타이틀
        this.score = score;
        JPanel outerPanel = new JPanel();
        JPanel innerPanel = new JPanel();
        JLabel label = new JLabel();
        JButton btn = new JButton("Enter");
        JTextPane scoreboard = new JTextPane();
        JTextField nameEnter = new JTextField(5);
        scoreboard.setEditable(false);
        sb = new ScoreBoard();
        setSize(200, 425); //창 크기 설정
        
        label.setHorizontalAlignment(JLabel.CENTER);
        innerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        innerPanel.add(nameEnter);
        innerPanel.add(btn);
        
        try {
			scoreboard.setText(sb.readScoreBoard());
			if(score<sb.isWritable()) {
				btn.setEnabled(false);
				label.setText("GAME OVER");
			} else {
				label.setText("Enter name in two alphabet.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        StyledDocument doc = scoreboard.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setFontFamily(center, Font.MONOSPACED);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(scoreboard,BorderLayout.CENTER);
        outerPanel.add(innerPanel,BorderLayout.SOUTH);
        outerPanel.add(label,BorderLayout.NORTH);
        add(outerPanel);

        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
 
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String name = nameEnter.getText();
            	if(name.length()>2 || name.length()<1) {
            		label.setText("Enter name in two alphabet.");
            	} else {
            		label.setText("Name Entered.");
            		try {
            			sb.writeScoreBoard(name, Integer.toString(score));
        				scoreboard.setText(sb.readScoreBoard());
        				btn.setEnabled(false);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            }
        });
    }
	
	public int getScore() {
		return score;
	}
}