package play;

import game.GameMenu;
import scoreboard.ScoreBoardMenu;
import setting.SettingMenu;
import component.StartMenu;

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

    public static void itemGameStart() throws IOException {
        gameMenu = new GameMenu(1);
        gameMenu.setVisible(true);
    }

    public static void disposeGameMenu() {
        gameMenu.dispose();
    }

    public static void showStartMenu() {
        startmenu.dispose();
        startmenu = new StartMenu();
    }

    public static void showSettingMenu() {
        settingMenu.setVisible(true);
    }

    public static void showScoreBoard() {
        scoreBoardMenu = new ScoreBoardMenu();
        scoreBoardMenu.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                startmenu = new StartMenu();
                try {
                    settingMenu = new SettingMenu();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                startmenu.setVisible(true);
            }
        });
    }
}