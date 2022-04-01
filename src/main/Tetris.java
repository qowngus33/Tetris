package main;

import game.GameForm;
import scoreboard.ScoreBoardForm;
import setting.SettingMenuForm;
import setting.Size;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Scanner;

public class Tetris extends JFrame {

    private static GameForm gameForm;
    private static StartMenuForm startMenuForm;
    private static SettingMenuForm settingMenuForm;
    private static ScoreBoardForm scoreBoardForm;

    public static void start(Size size){
        gameForm = new GameForm(size);
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
                startMenuForm = new StartMenuForm();
                settingMenuForm = new SettingMenuForm();
                scoreBoardForm = new ScoreBoardForm();

                startMenuForm.setVisible(true);
            }
        });
    }
}
