package main;

import game.GameMenu;
import scoreboard.ScoreBoardMenu;
import setting.SettingMenu;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {

    private static GameMenu gameForm;
    private static StartMenu startMenuForm;
    private static SettingMenu settingMenuForm;
    private static ScoreBoardMenu scoreBoardForm;

    public static void start(){
        gameForm = new GameMenu();
        gameForm.setVisible(true);
    }

    public static void showStartMenu(){
        startMenuForm.setVisible(true);
    }

    public static void showSettingMenu(){
        settingMenuForm.setVisible(true);
    }

    public static void showScoreBoard(){
        scoreBoardForm.setVisible(true);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                startMenuForm = new StartMenu();
                settingMenuForm = new SettingMenu();
                scoreBoardForm = new ScoreBoardMenu();

                startMenuForm.setVisible(true);
            }
        });
    }
}
