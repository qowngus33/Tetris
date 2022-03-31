package setting;

import main.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingMenuForm extends Frame {

    public SettingMenuForm() {

        setTitle("설정메뉴");
        setSize(380, 800);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        
        JButton gameBtn = new JButton("게임으로");
        JButton startMenuBtn = new JButton("시작메뉴로");

        gameBtn.addActionListener(e -> btnGameActionPerformed());
        startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());

        JPanel panel = new JPanel(new GridLayout(2, 0));
        panel.add(gameBtn);
        panel.add(startMenuBtn);
        this.add(panel);
    }

    private void btnGameActionPerformed(){
        this.setVisible(false);
        Tetris.start();
    }

    private void btnStartMenuActionPerformed(){
        this.setVisible(false);
        Tetris.showStartMenu();
    }
}
