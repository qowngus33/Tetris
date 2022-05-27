package setting;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SaveFileTest {

    SaveFile sv = new SaveFile();
    SaveFile sv2;
     String leftKey = "p";
    String rightKey = "q";
    String downKey = "w";
    String rotateKey = "e";
    String dropKey = "r";
    String pauseKey = "t";
    Integer initInterval = 1000;
    Integer reduceSpeed = 100;
    String modeName = "EASY";
    Integer boardWidth = 100;
    Integer boardHeight = 101;
    Integer fontSize = 10;
    boolean isColorBlind = false;

    @Test
    public void SaveFile(){
        sv2 = new SaveFile(leftKey,rightKey,downKey,rotateKey,dropKey,pauseKey,initInterval,reduceSpeed,modeName,
                boardWidth,boardHeight,fontSize,isColorBlind);
        Assertions.assertEquals(leftKey, sv2.getLeftKey());
        Assertions.assertEquals(rightKey, sv2.getRightKey());
        Assertions.assertEquals(downKey, sv2.getDownKey());
        Assertions.assertEquals(rotateKey, sv2.getRotateKey());
        Assertions.assertEquals(pauseKey, sv2.getPauseKey());
        Assertions.assertEquals(initInterval, sv2.getInitInterval());
        Assertions.assertEquals(reduceSpeed, sv2.getReduceSpeed());
        Assertions.assertEquals(modeName, sv2.getModeName());
        Assertions.assertEquals(boardWidth, sv2.getBoardWidth());
        Assertions.assertEquals(boardHeight, sv2.getBoardHeight());
        Assertions.assertEquals(fontSize, sv2.getFontSize());
        Assertions.assertEquals(isColorBlind, sv2.isColorBlind());
    }

}
