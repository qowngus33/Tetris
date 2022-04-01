package setting;

import setting.exception.EmptyKeyException;

import javax.swing.*;
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

    private SettingItem(){
        initKeySetting();
        initSizeSetting();
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
        boardWidth = 500;
        boardHeight = 900;
        fontSize = 23;
    }

    public void initColorBlindMode(){
        isColorBlind = false;
    }

    /**
     * 화면 크기 조절
     */
    public void btnSmallBtnActionPerformed() {
        boardWidth = 380;
        boardHeight = 700;
        fontSize = 15;
    }

    // default
    public void btnMediumBtnActionPerformed() {
        boardWidth = 500;
        boardHeight = 900;
        fontSize = 23;
    }

    public void btnLargeBtnActionPerformed() {
        boardWidth = 900;
        boardHeight = 1500;
        fontSize = 30;
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

    }

    public void btnColorBlindOffActionPerformed() {

    }

    /**
     * 스코어보드 초기화
     */
    public void btnInitScoreBoardActionPerformed(){
        // score board init logic
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"스코어보드가 초기화되었습니다.");
    }


    /**
     * 설정 불러오기
     */


    /**
     * 설정 저장
     */
    public void btnSaveSettingActionPerformed() throws IOException {

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
}
