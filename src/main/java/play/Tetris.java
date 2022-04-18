package play;

import component.StartMenu;
import game.GameMenu;
import scoreboard.ScoreBoardMenu;
import setting.SettingMenu;

import java.awt.*;
import java.io.IOException;

public class Tetris {

    private static GameMenu gameMenu;
    private static StartMenu startmenu;
    private static SettingMenu settingMenu;
    private static ScoreBoardMenu scoreBoardMenu;

    public static void start() throws IOException {
        gameMenu = new GameMenu();
        gameMenu.setVisible(true);
    }
    
    public static void itemGameStart() throws IOException{
        gameMenu = new GameMenu(1);
        gameMenu.setVisible(true);
    }
    
    public static void disposeGameMenu() {
    	gameMenu.dispose();
    }

    public static void showStartMenu(){
    	startmenu.setVisible(true);
    }

    public static void showSettingMenu(){
        settingMenu.setVisible(true);
    }

    public static void showScoreBoard(){
    	try {
			scoreBoardMenu = new ScoreBoardMenu(-1,"","");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	scoreBoardMenu.setVisible(true);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	startmenu = new StartMenu();
                try {
                    settingMenu = new SettingMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
					scoreBoardMenu = new ScoreBoardMenu(-1,"","");
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
