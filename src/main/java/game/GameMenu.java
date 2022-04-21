package game;

import main.Tetris;
import setting.SettingItem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameMenu extends JFrame{
    // private itemGameBoard gameBoard;
    private SettingItem settingItem;
    private GameBoard gameBoard;

    public GameMenu() throws IOException {
        super("Tetris");
        gameBoard = new GameBoard();
        init();
    }

    public GameMenu(int a) throws IOException {
        super("Tetris");
        gameBoard = new ItemGameBoard();
        init();
    }

    private void init() throws IOException {
        settingItem = SettingItem.getInstance();
        setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
        setResizable(false);
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);
        setLocationRelativeTo(null);
        setVisible(true);

        JButton settingButton = new JButton("설정");
        JButton startMenuBtn = new JButton("시작메뉴로");
        JButton scoreBoardBtn = new JButton("스코어보드");

        settingButton.addActionListener(e -> btnSettingActionPerformed());
        startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
        scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());

        JPanel innerPanel = new JPanel(new GridLayout(0,3));
        innerPanel.add(settingButton);
        innerPanel.add(startMenuBtn);
        innerPanel.add(scoreBoardBtn);

        JPanel outerPanel = new JPanel();

        outerPanel.add(gameBoard);
        outerPanel.add(innerPanel);
        setContentPane(outerPanel);
        outerPanel.setForeground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void btnSettingActionPerformed(){
        gameBoard.stopTimer();
        dispose();
        Tetris.showSettingMenu();
    }

    private void btnStartMenuActionPerformed(){
        gameBoard.stopTimer();
        dispose();
        Tetris.showStartMenu();
    }

    private void btnScoreBoardActionPerformed(){
        gameBoard.stopTimer();
        dispose();
        Tetris.showScoreBoard();
    }
}