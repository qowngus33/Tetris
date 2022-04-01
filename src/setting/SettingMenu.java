package setting;

import main.Tetris;

import javax.swing.*;
import java.awt.*;

public class SettingMenu extends JFrame {

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

    private SettingItem settingItem;

    public SettingMenu() {

        settingItem = SettingItem.getInstance();

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
        sizeBtns[0].addActionListener(e -> settingItem.btnSmallBtnActionPerformed());
        sizeBtns[1].addActionListener(e -> settingItem.btnMediumBtnActionPerformed());
        sizeBtns[2].addActionListener(e -> settingItem.btnLargeBtnActionPerformed());

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
        colorBlindOnBtn.addActionListener(e -> settingItem.btnColorBlindOnActionPerformed());
        colorBlindOffBtn.addActionListener(e -> settingItem.btnColorBlindOffActionPerformed());

        JPanel colorBlindPanel = new JPanel(new GridLayout(0, 3));
        colorBlindPanel.add(colorBlindLabel);
        colorBlindPanel.add(colorBlindOnBtn);
        colorBlindPanel.add(colorBlindOffBtn);

        // 스코어 보드 기록 초기화
        initScoreBoardBtn = new JButton("스코어 보드 기록 초기화");
        initScoreBoardBtn.addActionListener(e -> settingItem.btnInitScoreBoardActionPerformed());

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

    public JPanel displayKeySetting(){

        JButton leftKey = new JButton("LEFT");
        JButton rightKey = new JButton("RIGHT");
        JButton downKey = new JButton("DOWN");
        JButton rotateKey = new JButton("ROTATE");
        JButton dropKey = new JButton("DROP");

        // btnKeyAction안에 String 값 전달만 하면 됨
        leftKey.addActionListener(e -> settingItem.btnLeftKeyActionPerformed("J"));
        rightKey.addActionListener(e -> settingItem.btnRightKeyActionPerformed("L"));
        downKey.addActionListener(e -> settingItem.btnDownKeyActionPerformed("K"));
        rotateKey.addActionListener(e -> settingItem.btnRotateKeyActionPerformed("I"));
        dropKey.addActionListener(e -> settingItem.btnDropKeyActionPerformed("SPACE"));

        JPanel keySettingPanel = new JPanel();
        keySettingPanel.add(leftKey);
        keySettingPanel.add(rightKey);
        keySettingPanel.add(downKey);
        keySettingPanel.add(rotateKey);
        keySettingPanel.add(dropKey);

        return keySettingPanel;
    }

    public void btnGameActionPerformed(){
        dispose();
        Tetris.start();
    }

    public void btnStartMenuActionPerformed(){
        dispose();
        Tetris.showStartMenu();
    }

    /**
     * 설정 초기화
     */
    public void btnInitSettingActionPerformed() {
        settingItem.btnMediumBtnActionPerformed();
        sizeBtns[1].setSelected(true);

        settingItem.initKeySetting();

        settingItem.btnColorBlindOffActionPerformed();
        colorBlindOffBtn.setSelected(true);

        JOptionPane.showMessageDialog(initScoreBoardPanel,"설정이 초기화되었습니다.");
    }
}
