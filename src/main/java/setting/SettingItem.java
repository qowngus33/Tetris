package setting;

 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.DeserializationFeature;
 import setting.exception.EmptyKeyException;
 import com.fasterxml.jackson.databind.ObjectMapper;

 import javax.swing.*;
 import file.ScoreBoardFile;
 import java.io.File;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;

public class SettingItem {

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static SettingItem instance;

    static {
        try {
            instance = new SettingItem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private String leftKey;
     private String rightKey;
     private String downKey;
     private String rotateKey;
     private String dropKey;
     private String pauseKey;
     private int boardWidth;
     private int boardHeight;
     private int fontSize;
     private int initInterval;
     private String modeName;
     private boolean isColorBlind;
     private int reduceSpeed;

     private ScoreBoardFile sb;

     private SettingItem() throws IOException {
         Map<String, String> saveFile = loadFile();

         leftKey = saveFile.get("leftKey");
         rightKey = saveFile.get("rightKey");
         downKey = saveFile.get("downKey");
         rotateKey = saveFile.get("rotateKey");
         dropKey = saveFile.get("dropKey");

         boardWidth = Integer.parseInt(saveFile.get("boardWidth"));
         boardHeight = Integer.parseInt(saveFile.get("boardHeight"));
         fontSize = Integer.parseInt(saveFile.get("fontSize"));

         initInterval = Integer.parseInt(saveFile.get("initInterval"));
         reduceSpeed = Integer.parseInt(saveFile.get("reduceSpeed"));
         modeName = saveFile.get("modeName");
         isColorBlind = Boolean.parseBoolean(saveFile.get("isColorBlind"));
     }

    /**
     * 설정 불러오기
     */
     public Map<String, String> loadFile() throws IOException {
         TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {};
         HashMap<String, String> fileMap = objectMapper.readValue(new File("savefile.json"), typeReference);
         return fileMap;
     }

    /**
     * 설정 저장
     */
    public void btnSaveSettingActionPerformed() throws IOException {

        SaveFile saveFile = new SaveFile(leftKey, rightKey, downKey, rotateKey, dropKey, pauseKey,
                initInterval, reduceSpeed, modeName, boardWidth, boardHeight, fontSize, isColorBlind);
        objectMapper.writeValue(new File("savefile.json"), saveFile);
    }

     public static SettingItem getInstance() throws IOException {
         if(instance == null){
             return new SettingItem();
         }
         return instance;
     }

     /**
      * 화면 크기 조절
      */
     public void btnSmallBtnActionPerformed() {
         boardWidth = 300;
         boardHeight = 500;
         fontSize = 19;
     }

     // default
     public void btnMediumBtnActionPerformed() {
         boardWidth = 350;
         boardHeight = 583;
         fontSize = 23;
     }

     public void btnLargeBtnActionPerformed() {
         boardWidth = 400;
         boardHeight = 666;
         fontSize = 28;
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
    	 sb = new ScoreBoardFile();
    	 try {
			sb.eraseFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "스코어보드가 초기화 되었습니다..");
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

    /**
     * 키 초기화
     */
    public void initKeySetting(){
        leftKey = "LEFT";
        rightKey = "RIGHT";
        downKey = "DOWN";
        dropKey = "DROP";
        rotateKey = "ROTATE";
    }

    public ObjectMapper getObjectMapper() {
         return objectMapper;
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

     public String getModeName() {
         return modeName;
     }

     public boolean isColorBlind() {
         return isColorBlind;
     }

     public ScoreBoardFile getSb() {
         return sb;
     }

    public void setLeftKey(String leftKey) {
        this.leftKey = leftKey;
    }

    public void setRightKey(String rightKey) {
        this.rightKey = rightKey;
    }

    public void setDownKey(String downKey) {
        this.downKey = downKey;
    }

    public void setRotateKey(String rotateKey) {
        this.rotateKey = rotateKey;
    }

    public void setDropKey(String dropKey) {
        this.dropKey = dropKey;
    }

}