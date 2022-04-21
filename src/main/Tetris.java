package main;

import game.GameMenu;
import scoreboard.ScoreBoardMenu;
import setting.SettingMenu;
import startmenu.StartMenu;

import java.awt.*;
import java.io.IOException;

public class Tetris {

    private static GameMenu gameForm;
    private static StartMenu startmenu;
    private static SettingMenu settingMenuForm;
    private static ScoreBoardMenu scoreBoardForm;

    public static void start(){
        gameForm = new GameMenu();
        gameForm.setVisible(true);
    }
    
    public static void itemGameStart(){
        gameForm = new GameMenu(1);
        gameForm.setVisible(true);
    }
    
    public static void disposeGameMenu() {
    	gameForm.dispose();
    }

    public static void showStartMenu(){
    	startmenu.dispose();
    	startmenu = new StartMenu();
    }

    public static void showSettingMenu(){
        settingMenuForm.setVisible(true);
    }

    public static void showScoreBoard(){
    	scoreBoardForm = new ScoreBoardMenu();
    	scoreBoardForm.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	startmenu = new StartMenu();
                settingMenuForm = new SettingMenu();
                startmenu.setVisible(true);
            }
        });
    }
}
