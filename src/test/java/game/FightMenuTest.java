package game;

import main.Tetris;
import org.junit.Assert;
import org.junit.Test;
import setting.SettingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class FightMenuTest {
    FightMenu fightMenu;
	SettingItem settingItem;
	{
		try {
			fightMenu = new FightMenu(false,false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public FightMenuTest(){
		try {
			settingItem = SettingItem.getInstance();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*@Test
	public void moveDown(){
		for(int i=0;i<100;i++) {
			assertTimeout(ofMillis(1000), () -> {
				fightMenu.moveDown(1);
				fightMenu.moveDown(0);
			});
		}
		try {
			fightMenu = new FightMenu(false,true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for(int i=0;i<100;i++) {
			assertTimeout(ofMillis(1000), () -> {
				fightMenu.moveDown(1);
				fightMenu.moveDown(0);
			});
		}
	}*/

	@Test
	public void keyTest(){
		Robot robot;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}

		fightMenu.pause();
		String pre1 = fightMenu.getGameBoard(0).toString();
		String pre2 = fightMenu.getGameBoard(1).toString();
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyPress(KeyEvent.VK_D);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyPress(KeyEvent.VK_W);
		fightMenu.pause();

		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(600), () -> {
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyPress(KeyEvent.VK_RIGHT);
			});
		}
		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(300), () -> {
				robot.keyPress(KeyEvent.VK_UP);
			});
		}
		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(300), () -> {
				robot.keyPress(KeyEvent.VK_DOWN);
			});
		}
		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(1000), () -> {
				robot.keyPress(KeyEvent.VK_A);
				robot.keyPress(KeyEvent.VK_D);
			});
		}
		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(1000), () -> {
				robot.keyPress(KeyEvent.VK_S);
			});
		}
		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(1000), () -> {
				robot.keyPress(KeyEvent.VK_W);
			});
		}
		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(1000), () -> {
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyPress(KeyEvent.VK_SPACE);
			});
		}
	}
}
