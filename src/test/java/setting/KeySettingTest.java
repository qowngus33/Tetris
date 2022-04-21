package setting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class KeySettingTest {

    @Test
    public void keySetting() throws IOException {

        String keyType = "LEFT";
        String keyName = "J";
        KeySetting keySetting = new KeySetting(keyType);
        keySetting.btnKeyActionPerformed(keyName);

        SettingItem settingItem = SettingItem.getInstance();
        String leftKey = settingItem.getLeftKey();

        Assertions.assertEquals(keyName, leftKey);
    }
}
