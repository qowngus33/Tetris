package setting;

import java.io.Serializable;
import java.security.Key;
import java.util.Map;

public class KeySetting implements Serializable {

    public static String leftKey;
    public static String rightKey;
    public static String downKey;
    public static String rotateKey;
    public static String dropKey;
    public static String pauseKey;

    public static void initKeySetting(){

        KeySetting.leftKey = "LEFT";
        KeySetting.rightKey = "RIGHT";
        KeySetting.downKey = "DOWN";
        KeySetting.rotateKey = "UP";
        KeySetting.dropKey = "SPACE";
        KeySetting.pauseKey = "P";
    }

    public static void setKeySetting(KeySetting keySetting){
        KeySetting.leftKey = keySetting.leftKey;
        KeySetting.rightKey = keySetting.rightKey;
        KeySetting.downKey = keySetting.downKey;
        KeySetting.rotateKey = keySetting.rotateKey;
        KeySetting.dropKey = keySetting.dropKey;
        KeySetting.pauseKey = keySetting.pauseKey;
    }
}
