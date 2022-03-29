package scoreboard;

import main.Tetris;

import javax.swing.*;
import java.awt.*;

public class ScoreBoardForm extends JFrame {

    public ScoreBoardForm(){

        setTitle("Score Board");
        setSize(380, 800);
        setBackground(Color.WHITE);

        JButton settingBtn = new JButton("설정으로");
        JButton startMenuBtn = new JButton("시작메뉴로");

        startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
        settingBtn.addActionListener(e -> btnSettingActionPerformed());

        JPanel panel = new JPanel(new GridLayout(2, 0));
        panel.add(settingBtn);
        panel.add(startMenuBtn);
        this.add(panel);
    }

    private void btnSettingActionPerformed(){
        this.setVisible(false);
        Tetris.showSettingMenu();
    }

    private void btnStartMenuActionPerformed(){
        this.setVisible(false);
        Tetris.showStartMenu();
    }
}
