package game;

import main.Tetris;
import setting.Size;

import javax.swing.*;
import java.awt.*;

public class GameForm extends JFrame{
    private GameBoard gameBoard;

    public GameForm(Size size) {

        setSize(size.boardWidth, size.boardHeight);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        JButton settingButton = new JButton("설정");
        JButton startMenuBtn = new JButton("시작메뉴로");
        JButton scoreBoardBtn = new JButton("스코어보드");

        settingButton.addActionListener(e -> btnSettingActionPerformed());
        startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
        scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());

        JPanel panel = new JPanel(new GridLayout(0,3));
        panel.add(settingButton);
        panel.add(startMenuBtn);
        panel.add(scoreBoardBtn);

        gameBoard = new GameBoard(size);
        this.add(gameBoard, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void btnSettingActionPerformed(){
        dispose();
        Tetris.showSettingMenu();
    }

    private void btnStartMenuActionPerformed(){
        dispose();
        Tetris.showStartMenu();
    }

    private void btnScoreBoardActionPerformed(){
        dispose();
        Tetris.showScoreBoard();
    }
}

