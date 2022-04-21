package setting;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BtnModeActionPerformedTest {

    @Test
    public void btnEasyModeActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        int initInterval = 1200;
        int reduceSpeed = 80;
        String modeName = "EASY";

        int settingInitInterval = 0;
        int settingReduceSpeed = 0;
        String settingModeName;

        settingItem.btnEasyModeActionPerformed();
        settingInitInterval = settingItem.getInitInterval();
        settingReduceSpeed = settingItem.getReduceSpeed();
        settingModeName = settingItem.getModeName();

        Assertions.assertEquals(initInterval, settingInitInterval);
        Assertions.assertEquals(reduceSpeed, settingReduceSpeed);
        Assertions.assertEquals(modeName, settingModeName);
    }

    @Test
    public void btnNormalModeActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        int initInterval = 1000;
        int reduceSpeed = 100;
        String modeName = "NORMAL";

        int settingInitInterval = 0;
        int settingReduceSpeed = 0;
        String settingModeName;

        settingItem.btnNormalModeActionPerformed();
        settingInitInterval = settingItem.getInitInterval();
        settingReduceSpeed = settingItem.getReduceSpeed();
        settingModeName = settingItem.getModeName();

        Assertions.assertEquals(initInterval, settingInitInterval);
        Assertions.assertEquals(reduceSpeed, settingReduceSpeed);
        Assertions.assertEquals(modeName, settingModeName);
    }

    @Test
    public void btnHardModeActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        int initInterval = 800;
        int reduceSpeed = 120;
        String modeName = "HARD";

        int settingInitInterval = 0;
        int settingReduceSpeed = 0;
        String settingModeName;

        settingItem.btnHardModeActionPerformed();
        settingInitInterval = settingItem.getInitInterval();
        settingReduceSpeed = settingItem.getReduceSpeed();
        settingModeName = settingItem.getModeName();

        Assertions.assertEquals(initInterval, settingInitInterval);
        Assertions.assertEquals(reduceSpeed, settingReduceSpeed);
        Assertions.assertEquals(modeName, settingModeName);
    }
}
