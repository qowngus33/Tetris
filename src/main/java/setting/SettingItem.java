package setting;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import scoreboard.ScoreBoardFile;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingItem {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static SettingItem instance;

    static {
        try {
            instance = new SettingItem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final ArrayList<String> singleKeys = new ArrayList<>();
    private final ArrayList<String> multiKeys = new ArrayList<>();
    private String leftKey;
    private String rightKey;
    private String downKey;
    private String rotateKey;
    private String dropKey;
    private String p1LeftKey;
    private String p1RightKey;
    private String p1DownKey;
    private String p1RotateKey;
    private String p1DropKey;
    private String p2LeftKey;
    private String p2RightKey;
    private String p2DownKey;
    private String p2RotateKey;
    private String p2DropKey;
    private String pauseKey;
    private int boardWidth;
    private int boardHeight;
    private int fontSize;
    private int initInterval;
    private String modeName;
    private boolean isColorBlind;
    private int reduceSpeed;
    public static boolean isItemMode;
    public static boolean isFightMode;
    public static boolean isTimeAttackMode;

    public static final int SMALL_WIDTH = 300;
    public static final int MEDIUM_WIDTH = 350;
    public static final int LARGE_WIDTH = 400;

    public static final int SMALL_HEIGHT = 510;
    public static final int MEDIUM_HEIGHT = 593;
    public static final int LARGE_HEIGHT = 676;

    public static final int SMALL_FONT = 19;
    public static final int MEDIUM_FONT = 23;
    public static final int LARGE_FONT = 28;

    private SettingItem() throws IOException {
        Map<String, String> saveFile = loadFile();

        leftKey = saveFile.get("leftKey");
        rightKey = saveFile.get("rightKey");
        downKey = saveFile.get("downKey");
        rotateKey = saveFile.get("rotateKey");
        dropKey = saveFile.get("dropKey");
        pauseKey = saveFile.get("pauseKey");

        boardWidth = Integer.parseInt(saveFile.get("boardWidth"));
        boardHeight = Integer.parseInt(saveFile.get("boardHeight"));
        fontSize = Integer.parseInt(saveFile.get("fontSize"));

        initInterval = Integer.parseInt(saveFile.get("initInterval"));
        reduceSpeed = Integer.parseInt(saveFile.get("reduceSpeed"));
        modeName = saveFile.get("modeName");
        isColorBlind = Boolean.parseBoolean(saveFile.get("colorBlind"));

        p1LeftKey = saveFile.get("p1LeftKey");
        p1RightKey = saveFile.get("p1RightKey");
        p1DownKey = saveFile.get("p1DownKey");
        p1RotateKey = saveFile.get("p1RotateKey");
        p1DropKey = saveFile.get("p1DropKey");

        p2LeftKey = saveFile.get("p2LeftKey");
        p2RightKey = saveFile.get("p2RightKey");
        p2DownKey = saveFile.get("p2DownKey");
        p2RotateKey = saveFile.get("p2RotateKey");
        p2DropKey = saveFile.get("p2DropKey");
        addKey();
    }

    /**
     * 설정 불러오기
     */
    public Map<String, String> loadFile() throws IOException {
        TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {
        };
        File file = new File("savefile.json");
        if(!file.exists()) {
            file.createNewFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("{\"leftKey\":\"LEFT\",\"rightKey\":\"RIGHT\",\"downKey\":\"DOWN\",\"rotateKey\":\"UP\",\"dropKey\":\"SPACE\",\"p1LeftKey\":\"A\",\"p1RightKey\":\"D\",\"p1DownKey\":\"S\",\"p1RotateKey\":\"W\",\"p1DropKey\":\"SPACE\",\"p2LeftKey\":\"LEFT\",\"p2RightKey\":\"RIGHT\",\"p2DownKey\":\"DOWN\",\"p2RotateKey\":\"UP\",\"p2DropKey\":\"ENTER\",\"pauseKey\":\"P\",\"initInterval\":1000,\"reduceSpeed\":100,\"modeName\":\"NORMAL\",\"boardWidth\":350,\"boardHeight\":593,\"fontSize\":23,\"colorBlind\":false}");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return objectMapper.readValue(file, typeReference);
    }

    /**
     * 설정 저장
     */
    public void btnSaveSettingActionPerformed() throws IOException {

        SaveFile saveFile = new SaveFile(leftKey, rightKey, downKey, rotateKey, dropKey, pauseKey,
                initInterval, reduceSpeed, modeName, boardWidth, boardHeight, fontSize, isColorBlind,p1LeftKey,p1RightKey,p1DownKey,p1RotateKey,p1DropKey,
                p2LeftKey,p2RightKey,p2DownKey,p2RotateKey,p2DropKey);
        objectMapper.writeValue(new File("savefile.json"), saveFile);
    }

    public static SettingItem getInstance() throws IOException {
        if(instance == null){
            return new SettingItem();
        }
        return instance;
    }

    public void initKeySetting(){
        leftKey = "LEFT";
        rightKey = "RIGHT";
        downKey = "DOWN";
        rotateKey = "UP";
        dropKey = "SPACE";
        pauseKey = "P";
        p1LeftKey = "A";
        p1RightKey = "D";
        p1DownKey = "S";
        p1RotateKey = "W";
        p1DropKey = "SPACE";
        p2LeftKey = "LEFT";
        p2RightKey = "RIGHT";
        p2DownKey = "DOWN";
        p2RotateKey = "UP";
        p2DropKey = "ENTER";
        addKey();
    }

    /**
     * 화면 크기 조절
     */
    public void btnSmallBtnActionPerformed() {
        boardWidth = SMALL_WIDTH;
        boardHeight = SMALL_HEIGHT;
        fontSize = SMALL_FONT;
    }

    // default
    public void btnMediumBtnActionPerformed() {
        boardWidth = MEDIUM_WIDTH;
        boardHeight = MEDIUM_HEIGHT;
        fontSize = MEDIUM_FONT;
    }

    public void btnLargeBtnActionPerformed() {
        boardWidth = LARGE_WIDTH;
        boardHeight = LARGE_HEIGHT;
        fontSize = LARGE_FONT;
    }

    /**
     * 색맹 모드
     */
    public void btnColorBlindOnActionPerformed() {
        isColorBlind = true;
    }

    public void btnColorBlindOffActionPerformed() {
        isColorBlind = false;
    }

    /**
     * 스코어보드 초기화
     */
    public void btnInitScoreBoardActionPerformed(){
        // score board init logic
        ScoreBoardFile sb = new ScoreBoardFile();
        sb.eraseFile();
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Score Board have been reset.");
    }

    /**
     * 난이도 모드
     */
    public void btnEasyModeActionPerformed(){
        initInterval = 1200;
        reduceSpeed = 80;
        modeName = "EASY";
    }

    public void btnNormalModeActionPerformed(){
        initInterval = 1000;
        reduceSpeed = 100;
        modeName = "NORMAL";
    }

    public void btnHardModeActionPerformed(){
        initInterval = 800;
        reduceSpeed = 120;
        modeName = "HARD";
    }

    public String getLeftKey() {
        return leftKey;
    }

    public String getRightKey() {
        return rightKey;
    }

    public String getDownKey() {
        return downKey;
    }

    public String getRotateKey() {
        return rotateKey;
    }

    public String getDropKey() {
        return dropKey;
    }

    public String getP1LeftKey() {
        return p1LeftKey;
    }

    public String getP1RightKey() {
        return p1RightKey;
    }

    public String getP1DownKey() {
        return p1DownKey;
    }

    public String getP1RotateKey() {
        return p1RotateKey;
    }

    public String getP1DropKey() {
        return p1DropKey;
    }

    public String getP2LeftKey() {
        return p2LeftKey;
    }

    public String getP2RightKey() {
        return p2RightKey;
    }

    public String getP2DownKey() {
        return p2DownKey;
    }

    public String getP2RotateKey() {
        return p2RotateKey;
    }

    public String getP2DropKey() {
        return p2DropKey;
    }

    public String getPauseKey() {
        return pauseKey;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getInitInterval() {
        return initInterval;
    }
    public int getReduceSpeed() {
        return reduceSpeed;
    }
    public String getModeName() {
        return modeName;
    }

    public boolean isColorBlind() {
        return isColorBlind;
    }

    public void setLeftKey(String key, int player) {
        if(player==0 && !singleKeys.contains(key))
            this.leftKey = key;
        else if(player==1 && !multiKeys.contains(key))
            this.p1LeftKey = key;
        else if(player==2 && !multiKeys.contains(key))
            this.p2LeftKey = key;
        else
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Key already in use");
        addKey();
    }

    public void setRightKey(String key, int player) {
        if(player==0 && !singleKeys.contains(key))
            this.rightKey = key;
        else if(player==1 && !multiKeys.contains(key))
            this.p1RightKey = key;
        else if(player==2 && !multiKeys.contains(key))
            this.p2RightKey = key;
        else
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Key already in use");
        addKey();
    }

    public void setDownKey(String key, int player) {
        if(player==0 && !singleKeys.contains(key))
            this.downKey = key;
        else if(player==1 && !multiKeys.contains(key))
            this.p1DownKey = key;
        else if(player==2 && !multiKeys.contains(key))
            this.p2DownKey = key;
        else
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Key already in use");
        addKey();
    }

    public void setRotateKey(String key, int player) {
        if(player==0 && !singleKeys.contains(key))
            this.rotateKey = key;
        else if(player==1 && !multiKeys.contains(key))
            this.p1RotateKey = key;
        else if(player==2 && !multiKeys.contains(key))
            this.p2RotateKey = key;
        else
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Key already in use");
        addKey();
    }

    public void setDropKey(String key, int player) {
        if(player==0 && !singleKeys.contains(key))
            this.dropKey = key;
        else if(player==1 && !multiKeys.contains(key))
            this.p1DropKey = key;
        else if(player==2 && !multiKeys.contains(key))
            this.p2DropKey = key;
        else
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Key already in use");
        addKey();
    }

    public void addKey() {
        singleKeys.clear();
        multiKeys.clear();

        singleKeys.add(leftKey);
        singleKeys.add(rightKey);
        singleKeys.add(downKey);
        singleKeys.add(rotateKey);
        singleKeys.add(dropKey);

        multiKeys.add(p1LeftKey);
        multiKeys.add(p1RightKey);
        multiKeys.add(p1DownKey);
        multiKeys.add(p1RotateKey);
        multiKeys.add(p1DropKey);

        multiKeys.add(p2LeftKey);
        multiKeys.add(p2RightKey);
        multiKeys.add(p2DownKey);
        multiKeys.add(p2RotateKey);
        multiKeys.add(p2DropKey);
    }
}