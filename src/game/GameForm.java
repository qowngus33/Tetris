package game;

import main.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame{

    private GameBoard gameBoard;

    public GameForm() {

        setSize(380, 800);
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

        gameBoard = new GameBoard();
        this.add(gameBoard, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }

    private void btnSettingActionPerformed(){
        this.setVisible(false);
        Tetris.showSettingMenu();
    }

    private void btnStartMenuActionPerformed(){
        this.setVisible(false);
        Tetris.showStartMenu();
    }

    private void btnScoreBoardActionPerformed(){
        this.setVisible(false);
        Tetris.showScoreBoard();
    }
}

