package main;

import setting.SettingMenuForm;
import setting.Size;

import javax.swing.*;
import java.awt.*;

public class StartMenuForm extends JFrame {
    /**
     * 시작메뉴 화면 구성(임시)
     */
    public StartMenuForm(){

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
    }

    private void btnStartActionPerformed(){
        this.setVisible(false);
        Tetris.start(new Size());
    }

    private void btnSettingMenuActionPerformed(){
        this.setVisible(false);
        Tetris.showSettingMenu();
    }

    private void btnScoreBoardActionPerformed(){
        this.setVisible(false);
        Tetris.showScoreBoard();
    }

    private void btnQuitActionPerformed(){
        System.exit(0);
    }
}
