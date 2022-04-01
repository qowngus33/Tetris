package setting;

import game.GameForm;
import main.Tetris;
import setting.exception.EmptyKeyException;

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

public class SettingMenuForm extends JFrame implements Serializable {

    private JLabel sizeLabel;
    private JRadioButton[] sizeBtns;
    private ButtonGroup sizeBtnGroup;
    private JPanel sizePanel;
    private JPanel keyPanel;
    private JPanel keySettingPanel;
    private JButton gameBtn;
    private JButton startMenuBtn;
    private JButton initSettingBtn;
    private JButton saveSettingBtn;
    private JLabel colorBlindLabel;
    private JRadioButton colorBlindOnBtn;
    private JRadioButton colorBlindOffBtn;
    private ButtonGroup colorBlindBtnGroup;
    private JButton initScoreBoardBtn;
    private JPanel initScoreBoardPanel;
    private JPanel settingBtnPanel;

    private GameForm gameForm;

    // private static Map<String, Object> initDataMap;

    public SettingMenuForm() {

        KeySetting.initKeySetting();
        Size.initSize();
        ColorBlind.initColorBlind();

        setTitle("설정메뉴");
        setSize(600, 800);
        setBackground(Color.WHITE);

        // 화면 크기 조절 버튼 & 이벤트 설정
        String[] sizeNames = {"small", "medium", "large"};
        sizeLabel = new JLabel("화면 크기");
        sizeBtns = new JRadioButton[3];
        sizeBtnGroup = new ButtonGroup();

        for(int i = 0; i < sizeBtns.length; i++){
            sizeBtns[i] = new JRadioButton(sizeNames[i]);
            sizeBtnGroup.add(sizeBtns[i]);
        }

        sizePanel = new JPanel(new GridLayout(0, 4));
        sizePanel.add(sizeLabel);
        for(int i = 0; i < sizeBtns.length; i++){
            sizePanel.add(sizeBtns[i]);
        }
        sizeBtns[1].setSelected(true); // medium
        sizeBtns[0].addActionListener(e -> btnSmallBtnActionPerformed());
        sizeBtns[1].addActionListener(e -> btnMediumBtnActionPerformed());
        sizeBtns[2].addActionListener(e -> btnLargeBtnActionPerformed());

        // 조작키 설정
        keyPanel = new JPanel(new BorderLayout());
        keySettingPanel = displayKeySetting();
        keyPanel.add(new JLabel("조작키 설정"), BorderLayout.NORTH);
        keyPanel.add(keySettingPanel,BorderLayout.CENTER);
        // 키패드 추가 필요

        // 색맹 모드 켜고 끄기
        colorBlindLabel = new JLabel("색맹 모드");
        colorBlindOnBtn = new JRadioButton("on");
        colorBlindOffBtn = new JRadioButton("off");
        colorBlindBtnGroup = new ButtonGroup();
        colorBlindBtnGroup.add(colorBlindOnBtn);
        colorBlindBtnGroup.add(colorBlindOffBtn);

        colorBlindOffBtn.setSelected(true); // default
        colorBlindOnBtn.addActionListener(e -> btnColorBlindOnActionPerformed());
        colorBlindOffBtn.addActionListener(e -> btnColorBlindOffActionPerformed());

        JPanel colorBlindPanel = new JPanel(new GridLayout(0, 3));
        colorBlindPanel.add(colorBlindLabel);
        colorBlindPanel.add(colorBlindOnBtn);
        colorBlindPanel.add(colorBlindOffBtn);

        // 스코어 보드 기록 초기화
        initScoreBoardBtn = new JButton("스코어 보드 기록 초기화");
        initScoreBoardBtn.addActionListener(e -> btnInitScoreBoardActionPerformed());

        initScoreBoardPanel = new JPanel();
        initScoreBoardPanel.add(initScoreBoardBtn);

        // 게임 시작 버튼, 시작 메뉴 버튼 & 이벤트 설정
        gameBtn = new JButton("게임으로");
        startMenuBtn = new JButton("시작메뉴로");
        initSettingBtn = new JButton("설정 초기화");
        saveSettingBtn = new JButton("설정 저장");

        gameBtn.addActionListener(e -> btnGameActionPerformed());
        startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
        initSettingBtn.addActionListener(e -> btnInitSettingActionPerformed());
        // saveSettingBtn.addActionListener(e -> btnSaveSettingActionPerformed());

        settingBtnPanel = new JPanel(new GridLayout(5, 0));
        settingBtnPanel.add(gameBtn);
        settingBtnPanel.add(startMenuBtn);
        settingBtnPanel.add(initSettingBtn);
        settingBtnPanel.add(saveSettingBtn);

        // panel 추가
        this.getContentPane().setLayout(new GridLayout(5, 0));
        this.getContentPane().add(sizePanel);
        this.getContentPane().add(keyPanel);
        this.getContentPane().add(initScoreBoardPanel);
        this.getContentPane().add(colorBlindPanel);
        this.getContentPane().add(settingBtnPanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private JPanel displayKeySetting(){

        JButton leftKey = new JButton("LEFT");
        JButton rightKey = new JButton("RIGHT");
        JButton downKey = new JButton("DOWN");
        JButton rotateKey = new JButton("ROTATE");
        JButton dropKey = new JButton("DROP");

        // btnKeyAction안에 String 값 전달만 하면 됨
        leftKey.addActionListener(e -> btnLeftKeyActionPerformed("J"));
        rightKey.addActionListener(e -> btnRightKeyActionPerformed("L"));
        downKey.addActionListener(e -> btnDownKeyActionPerformed("K"));
        rotateKey.addActionListener(e -> btnRotateKeyActionPerformed("I"));
        dropKey.addActionListener(e -> btnDropKeyActionPerformed("SPACE"));

        JPanel keySettingPanel = new JPanel();
        keySettingPanel.add(leftKey);
        keySettingPanel.add(rightKey);
        keySettingPanel.add(downKey);
        keySettingPanel.add(rotateKey);
        keySettingPanel.add(dropKey);

        return keySettingPanel;
    }

    private void btnGameActionPerformed(){
        dispose();
        Tetris.start(new Size());
    }

    private void btnStartMenuActionPerformed(){
        dispose();
        Tetris.showStartMenu();
    }

    /**
     * 색맹 모드
     */
    private void btnColorBlindOnActionPerformed() {

    }

    private void btnColorBlindOffActionPerformed() {

    }

    /**
     * 스코어보드 초기화
     */
    private void btnInitScoreBoardActionPerformed(){
        // score board init logic
        JOptionPane.showMessageDialog(initScoreBoardPanel,"스코어보드가 초기화되었습니다.");
    }

    /**
     * 설정 초기화
     */
    private void btnInitSettingActionPerformed() {
        btnMediumBtnActionPerformed();
        sizeBtns[1].setSelected(true);

        KeySetting.initKeySetting();

        btnColorBlindOffActionPerformed();
        colorBlindOffBtn.setSelected(true);

        JOptionPane.showMessageDialog(initScoreBoardPanel,"설정이 초기화되었습니다.");
    }

    /**
     * 설정 불러오기
     */
//    private Map<String, Object> loadData(){
//
//    }

    /**
     * 설정 저장
     */
    private void btnSaveSettingActionPerformed() throws IOException {

        String[][] keySetting = new String[5][1];
        keySetting[0][0] = KeySetting.leftKey;

        File csv = new File("setting.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv));


        JOptionPane.showMessageDialog(initScoreBoardPanel,"설정이 저장되었습니다.");
    }

    /**
     * 화면 크기 조절
     */
    private void btnSmallBtnActionPerformed() {
        Size.boardWidth = 380;
        Size.boardHeight = 700;
        Size.fontSize = 15;
    }

    // default
    private void btnMediumBtnActionPerformed() {
        Size.boardWidth = 500;
        Size.boardHeight = 900;
        Size.fontSize = 23;
    }

    private void btnLargeBtnActionPerformed() {
        Size.boardWidth = 900;
        Size.boardHeight = 1500;
        Size.fontSize = 30;
    }

    /**
     * 조작키 설정
     */
    private void btnLeftKeyActionPerformed(String key){
        if(key == null){
            throw new EmptyKeyException("LEFT 키 값이 없습니다.");
        }
        KeySetting.leftKey = key;
    }

    private void btnRightKeyActionPerformed(String key){
        if(key == null){
            throw new EmptyKeyException("RIGHT 키 값이 없습니다.");
        }
        KeySetting.rightKey = key;
    }

    private void btnDownKeyActionPerformed(String key){
        if(key == null){
            throw new EmptyKeyException("DOWN 키 값이 없습니다.");
        }
        KeySetting.downKey = key;
    }

    private void btnDropKeyActionPerformed(String key){
        if(key == null){
            throw new EmptyKeyException("DROP 키 값이 없습니다.");
        }
        KeySetting.dropKey = key;
    }

    private void btnRotateKeyActionPerformed(String key){
        if(key == null){
            throw new EmptyKeyException("ROTATE 키 값이 없습니다.");
        }
        KeySetting.rotateKey = key;
    }
}
