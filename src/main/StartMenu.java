package main;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {
    /**
     * 시작메뉴 화면 구성(임시)
     */
    public StartMenu(){
        setSize(400, 400);

        JButton btnStart = new JButton("Start");
        JButton btnQuit = new JButton("Quit");
        JButton btnSettingMenu = new JButton("Setting Menu");
        JButton btnScoreBoardButton = new JButton("Score Board");

        /**
         * 버튼 이벤트
         */
        btnStart.addActionListener(e -> btnStartActionPerformed());
        btnSettingMenu.addActionListener(e -> btnSettingMenuActionPerformed());
        btnQuit.addActionListener(e -> btnQuitActionPerformed());
        btnScoreBoardButton.addActionListener(e -> btnScoreBoardActionPerformed());

        JPanel panel = new JPanel(new GridLayout(4, 0));
        panel.add(btnStart);
        panel.add(btnSettingMenu);
        panel.add(btnQuit);
        panel.add(btnScoreBoardButton);

        this.getContentPane().add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void btnStartActionPerformed(){
        dispose();
        Tetris.start();
    }

    private void btnSettingMenuActionPerformed(){
        dispose();
        Tetris.showSettingMenu();
    }

    private void btnScoreBoardActionPerformed(){
        dispose();
        Tetris.showScoreBoard();
    }

    private void btnQuitActionPerformed(){
        System.exit(0);
    }
}
