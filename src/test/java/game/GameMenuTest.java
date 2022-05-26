package game;

import blocks.Block;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class GameMenuTest {
    GameMenu gameMenu = new GameMenu(false);
	GameMenu itemGameMenu = new GameMenu(true);
    public GameMenuTest() throws IOException {}

	/*
   @Test
	public void moveDown(){
		for(int i=0;i<100;i++) {
			assertTimeout(ofMillis(1000), () -> {
				gameMenu.moveDown();
			});
		}

	   for(int i=0;i<100;i++) {
		   assertTimeout(ofMillis(1000), () -> {
			   itemGameMenu.moveDown();
		   });
	   }
   }
<<<<<<< HEAD

	@Test
	public void keyTest(){
		Robot robot;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}

		for(int i=0;i<10;i++) {
			assertTimeout(ofMillis(300), () -> {
				robot.keyPress(KeyEvent.VK_DOWN);
			});
		}

		for(int i=0;i<100;i++) {
			assertTimeout(ofMillis(600), () -> {
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyPress(KeyEvent.VK_RIGHT);
			});
		}
		for(int i=0;i<100;i++) {
			assertTimeout(ofMillis(300), () -> {
				robot.keyPress(KeyEvent.VK_UP);
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
*/
}
