package main;

import game.GameMenu;
import scoreboard.ScoreBoardMenu;
import setting.SettingMenu;

import java.awt.*;
import java.io.IOException;

import component.StartMenu;

public class Tetris {

    private static GameMenu gameForm;
    private static StartMenu startmenu;
    private static SettingMenu settingMenuForm;
    private static ScoreBoardMenu scoreBoardForm;

    public static void start(){
        gameForm = new GameMenu();
        gameForm.setVisible(true);
    }
    
    public static void disposeGameMenu() {
    	gameForm.setVisible(false);
    }

    public static void showStartMenu(){
    	startmenu.setVisible(true);
    }

    public static void showSettingMenu(){
        settingMenuForm.setVisible(true);
    }

    public static void showScoreBoard(){
    	scoreBoardForm.reloadData();
    	scoreBoardForm.dispose();
    	try {
			scoreBoardForm = new ScoreBoardMenu(-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	scoreBoardForm.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	startmenu = new StartMenu();
                settingMenuForm = new SettingMenu();
                try {
					scoreBoardForm = new ScoreBoardMenu(-1);
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
