package setting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BtnColorBlindModeTest {

    @Test
    public void btnColorBlindOnActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        boolean colorBlind = true;
        boolean settingColorBlind;

        settingItem.btnColorBlindOnActionPerformed();
        settingColorBlind = settingItem.isColorBlind();

        Assertions.assertEquals(colorBlind, settingColorBlind);
    }

    @Test
    public void btnColorBlindOffActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        boolean colorBlind = false;
        boolean settingColorBlind;

        settingItem.btnColorBlindOffActionPerformed();
        settingColorBlind = settingItem.isColorBlind();

        Assertions.assertEquals(colorBlind, settingColorBlind);

    }
}
