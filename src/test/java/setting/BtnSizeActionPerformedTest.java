package setting;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class BtnSizeActionPerformedTest {

    @Test
    public void btnSmallBtnActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        int settingWidth = 0;
        int settingHeight = 0;
        int settingFont = 0;

        settingItem.btnSmallBtnActionPerformed();
        settingWidth = settingItem.getBoardWidth();
        settingHeight = settingItem.getBoardHeight();
        settingFont = settingItem.getFontSize();

        Assertions.assertEquals(SettingItem.SMALL_WIDTH, settingWidth);
        Assertions.assertEquals(SettingItem.SMALL_HEIGHT, settingHeight);
        Assertions.assertEquals(SettingItem.SMALL_FONT, settingFont);
    }

    @Test
    public void btnMediumBtnActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        int settingWidth = 0;
        int settingHeight = 0;
        int settingFont = 0;

        settingItem.btnMediumBtnActionPerformed();

        settingWidth = settingItem.getBoardWidth();
        settingHeight = settingItem.getBoardHeight();
        settingFont = settingItem.getFontSize();

        Assertions.assertEquals(SettingItem.MEDIUM_WIDTH, settingWidth);
        Assertions.assertEquals(SettingItem.MEDIUM_HEIGHT, settingHeight);
        Assertions.assertEquals(SettingItem.MEDIUM_FONT, settingFont);
    }

    @Test
    public void btnLargeBtnActionPerformed() throws IOException {
        SettingItem settingItem = SettingItem.getInstance();

        int settingWidth = 0;
        int settingHeight = 0;
        int settingFont = 0;

        settingItem.btnLargeBtnActionPerformed();

        settingWidth = settingItem.getBoardWidth();
        settingHeight = settingItem.getBoardHeight();
        settingFont = settingItem.getFontSize();

        Assertions.assertEquals(SettingItem.LARGE_WIDTH, settingWidth);
        Assertions.assertEquals(SettingItem.LARGE_HEIGHT, settingHeight);
        Assertions.assertEquals(SettingItem.LARGE_FONT, settingFont);
    }
}
