package setting;

 import setting.exception.EmptyKeyException;

 import javax.swing.*;

import file.ScoreBoardFile;
import file.SettingFile;
import file.ScoreBoardFile;

import java.io.IOException;

 public class SettingItem {

     private static SettingItem instance = new SettingItem();

     private String leftKey;
     private String rightKey;
     private String downKey;
     private String rotateKey;
     private String dropKey;
     private String pauseKey;

     private int boardWidth;
     private int boardHeight;
     private int fontSize;

     private boolean isColorBlind;

     private int initInterval;
     private int reduceSpeed;
     private Mode mode;

     private ScoreBoardFile sb;
     private SettingFile settingfile;

     private SettingItem(){
         initKeySetting();
         initSizeSetting();
         initColorBlindMode();
         initInitInterval();
         initMode();
     }

     public static SettingItem getInstance(){
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
     }

     public void initSizeSetting(){
         boardWidth = 350;
         boardHeight = 583;
         fontSize = 23;
     }

     public void initColorBlindMode(){
         isColorBlind = false;
     }

     public void initInitInterval(){
         initInterval = 1000;
         reduceSpeed = 100;
     }

     public void initMode() {
         mode = Mode.NORMAL;
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
      * 조작키 설정
      */
     public void btnLeftKeyActionPerformed(String key){
         if(key == null){
             throw new EmptyKeyException("LEFT 키 값이 없습니다.");
         }
         leftKey = key;
     }

     public void btnRightKeyActionPerformed(String key){
         if(key == null){
             throw new EmptyKeyException("RIGHT 키 값이 없습니다.");
         }
         rightKey = key;
     }

     public void btnDownKeyActionPerformed(String key){
         if(key == null){
             throw new EmptyKeyException("DOWN 키 값이 없습니다.");
         }
         downKey = key;
     }

     public void btnDropKeyActionPerformed(String key){
         if(key == null){
             throw new EmptyKeyException("DROP 키 값이 없습니다.");
         }
         dropKey = key;
     }

     public void btnRotateKeyActionPerformed(String key){
         if(key == null){
             throw new EmptyKeyException("ROTATE 키 값이 없습니다.");
         }
         rotateKey = key;
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
         mode = Mode.EASY;
     }

     public void btnNormalModeActionPerformed(){
         initInterval = 1000;
         reduceSpeed = 100;
         mode = Mode.NORMAL;
     }

     public void btnHardModeActionPerformed(){
         initInterval = 800;
         reduceSpeed = 120;
         mode = Mode.HARD;
     }
     /**
      * 설정 불러오기
      */


     /**
      * 설정 저장
      */
     public void btnSaveSettingActionPerformed() throws IOException {
    	 int screenSize;
    	 int level;
    	 if(mode == Mode.EASY)
    		 level = 0;
    	 else if(mode == Mode.NORMAL)
    		 level = 1;
    	 else
    		 level = 2;
    	 
    	 if(getBoardWidth() < 340)
    		 screenSize = 0;
    	 else if(getBoardWidth() > 440)
    		 screenSize = 2;
    	 else
    		 screenSize = 1;
    		
    	 settingfile = new SettingFile();
         settingfile.saveSetting(screenSize,level);
         JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "설정이 저장되었습니다.");
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

     public boolean isColorBlind() {
         return isColorBlind;
     }

     public int getInitInterval() {
         return initInterval;
     }
     
     public int getReduceSpeed() {
         return reduceSpeed;
     }

     public Mode getMode(){
         return mode;
     }
 }