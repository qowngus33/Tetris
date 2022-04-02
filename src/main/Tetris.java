package main;

import game.GameForm;
import scoreboard.ScoreBoardForm;
import setting.SettingMenuForm;

import java.awt.*;
import java.io.IOException;

import component.StartMenu;

public class Tetris {

    private static GameForm gameForm;
    private static StartMenu startmenu;
    private static SettingMenuForm settingMenuForm;
    private static ScoreBoardForm scoreBoardForm;

    public static void start(){
        gameForm = new GameForm();
        gameForm.setVisible(true);
    }

    public static void showStartMenu(){
    	startmenu.setVisible(true);
    }

    public static void showSettingMenu(){
        settingMenuForm.setVisible(true);
    }

    public static void showScoreBoard(){
    	scoreBoardForm.reloadData();
        scoreBoardForm.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	startmenu = new StartMenu();
                settingMenuForm = new SettingMenuForm();
                try {
					scoreBoardForm = new ScoreBoardForm(-1);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                startmenu.setVisible(true);
            }
        });
    }
}
