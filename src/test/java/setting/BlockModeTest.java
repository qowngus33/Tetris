package setting;

import blocks.Block;
import blocks.GetRandomBlock;
import blocks.IBlock;
import game.GameBoard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import setting.SettingItem;

public class BlockModeTest {

    GetRandomBlock randomBlock = new GetRandomBlock();

    @Test
    public void getRandomBlockEasyMode() {

        System.out.println();

        int[] countBlock = new int[7];
        int count = 7200;

        for (int i = 0; i < count; i++) {
            Block block = randomBlock.getRandomBlockEasyMode();

            switch (block.getClass().toString()) {
                case "class blocks.IBlock":
                    ++countBlock[0];
                    break;
                case "class blocks.JBlock":
                    ++countBlock[1];
                    break;
                case "class blocks.LBlock":
                    ++countBlock[2];
                    break;
                case "class blocks.OBlock":
                    ++countBlock[3];
                    break;
                case "class blocks.SBlock":
                    ++countBlock[4];
                    break;
                case "class blocks.TBlock":
                    ++countBlock[5];
                    break;
                case "class blocks.ZBlock":
                    ++countBlock[6];
                    break;
            }
        }

        System.out.println("Easy Mode");
        for(int i = 0; i < 7; i++){
            System.out.print("[" + i + "] " + countBlock[i] + " ");
        }
        System.out.println();

        double epsilon = 7200 * 0.05;

        Assertions.assertEquals(1200, countBlock[0], epsilon);
        Assertions.assertEquals(1000, countBlock[1], epsilon);
    }

    @Test
    public void getRandomBlockHardMode() {

        int[] countBlock = new int[7];
        int count = 6800;

        for (int i = 0; i < count; i++) {
            Block block = randomBlock.getRandomBlockHardMode();

            switch (block.getClass().toString()) {
                case "class blocks.IBlock":
                    ++countBlock[0];
                    break;
                case "class blocks.JBlock":
                    ++countBlock[1];
                    break;
                case "class blocks.LBlock":
                    ++countBlock[2];
                    break;
                case "class blocks.OBlock":
                    ++countBlock[3];
                    break;
                case "class blocks.SBlock":
                    ++countBlock[4];
                    break;
                case "class blocks.TBlock":
                    ++countBlock[5];
                    break;
                case "class blocks.ZBlock":
                    ++countBlock[6];
                    break;
            }
        }

        System.out.println("Hard Mode");
        for (int i = 0; i < 7; i++) {
            System.out.print("[" + i + "] " + countBlock[i] + " ");
        }
        System.out.println();

        double epsilon = 6800 * 0.05;

        Assertions.assertEquals(800, countBlock[0], epsilon);
        Assertions.assertEquals(1000, countBlock[1], epsilon);
    }
}