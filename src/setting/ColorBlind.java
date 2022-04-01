package setting;

import java.io.Serializable;

public class ColorBlind implements Serializable{

    public static boolean isColorBlind;

    public static void setIsColorBlind(ColorBlind colorBlind) {
        ColorBlind.isColorBlind = colorBlind.isColorBlind;
    }

    public static void initColorBlind(){
        ColorBlind.isColorBlind = false;
    }
}
