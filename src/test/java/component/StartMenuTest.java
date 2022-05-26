package component;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import scoreboard.ScoreBoardFile;

public class StartMenuTest {
    StartMenu startMenu = new StartMenu();

    @Test
    public void keyTest() {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 100; i++) {
            assertTimeout(ofMillis(600), () -> {
                robot.keyPress(KeyEvent.VK_LEFT);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyPress(KeyEvent.VK_D);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyPress(KeyEvent.VK_SPACE);
            });
        }
    }
}




