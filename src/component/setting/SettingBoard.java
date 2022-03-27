package component.setting;

import component.game.GameBoard;

import javax.swing.*;
import java.awt.*;

public class SettingBoard extends JFrame{

    private static SettingBoard instance;

    private JRadioButton smallRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton largeRadioButton;
    private JRadioButton colorBlindOnRadioButton;
    private JRadioButton colorBlindOffRadioButton;
    private JButton scoreInitButton;
    private JButton settingSaveButton;
    private JButton settingInitButton;

    private GameBoard gameBoard = GameBoard.getInstance();

    private SettingBoard(){
        super("SeoulTech SE Tetris");
    }

    public static SettingBoard getInstance(){
        if(instance == null){
            return new SettingBoard();
        }
        return instance;
    }

    public void initSettingBoard(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(600, 400);
        setResizable(false);
        setVisible(true);

        // 게임 화면 사이즈 화면
        JPanel sizePanel = new JPanel();
        JLabel sizeLabel = new JLabel("게임 화면 사이즈");
        smallRadioButton = new JRadioButton("Small");
        mediumRadioButton = new JRadioButton("Medium");
        largeRadioButton = new JRadioButton("Large");

        // smallRadioButton.setSelected(true);
        smallRadioButton.addActionListener(e -> setDisplaySize(Size.SMALL));
        mediumRadioButton.addActionListener(e -> setDisplaySize(Size.MEDIUM));
        largeRadioButton.addActionListener(e -> setDisplaySize(Size.LARGE));

        ButtonGroup sizeBtnGroup = new ButtonGroup();
        sizeBtnGroup.add(smallRadioButton);
        sizeBtnGroup.add(mediumRadioButton);
        sizeBtnGroup.add(largeRadioButton);

        sizePanel.add(sizeLabel);
        sizePanel.add(smallRadioButton);
        sizePanel.add(mediumRadioButton);
        sizePanel.add(largeRadioButton);

        // 색맹 모드 선택 화면
        JPanel colorBlindModePanel = new JPanel();
        JLabel colorBlindModeLabel = new JLabel("색맹 모드 선택");
        colorBlindOnRadioButton = new JRadioButton("On");
        colorBlindOffRadioButton = new JRadioButton("Off");
        colorBlindOffRadioButton.setSelected(true);

        colorBlindOnRadioButton.addActionListener(e -> turnOnOffColorBlindMode(true));
        colorBlindOffRadioButton.addActionListener(e -> turnOnOffColorBlindMode(false));

        ButtonGroup colorBlindBtnGroup = new ButtonGroup();
        colorBlindBtnGroup.add(colorBlindOnRadioButton);
        colorBlindBtnGroup.add(colorBlindOffRadioButton);

        colorBlindModePanel.add(colorBlindModeLabel);
        colorBlindModePanel.add(colorBlindOnRadioButton);
        colorBlindModePanel.add(colorBlindOffRadioButton);

        // 조작키 설정
        JPanel keyPanel = new JPanel();
        JLabel keyLabel = new JLabel("조작키 설정");

        JLabel leftKeyLabel = new JLabel("Left");
        JLabel rightKeyLabel = new JLabel("Right");
        JLabel downKeyLabel = new JLabel("Down");
        JLabel rotateKeyLabel = new JLabel("Rotate");

        keyPanel.add(keyLabel);
        keyPanel.add(leftKeyLabel);
        keyPanel.add(rightKeyLabel);
        keyPanel.add(downKeyLabel);
        keyPanel.add(rotateKeyLabel);


        // 스코어보드 설정 초기화
        JPanel scoreInitPanel = new JPanel();
        scoreInitButton = new JButton("스코어 보드 초기화");
        scoreInitButton.addActionListener(e -> initScore());
        scoreInitPanel.add(scoreInitButton);

        // 설정 저장, 초기화
        JPanel settingPanel = new JPanel();
        settingInitButton = new JButton("설정 초기화");
        // settingSaveButton = new JButton("설정 저장");

        settingInitButton.addActionListener(e -> setInit());

        settingPanel.add(settingInitButton);
        settingPanel.add(settingSaveButton);

        // 게임으로 돌아가기
        JPanel menuPanel = new JPanel();
        JButton gameButton = new JButton("게임으로 돌아가기");
        JButton startMenuButton = new JButton("시작메뉴로 돌아가기");

        gameButton.addActionListener(e -> goToGameBoard());

        menuPanel.add(gameButton);
        menuPanel.add(startMenuButton);

        getContentPane().setLayout(new GridLayout(5, 0));
        getContentPane().add(sizePanel); // 화면크기
        getContentPane().add(colorBlindModePanel); // 색맹 모드
        getContentPane().add(scoreInitPanel); // 스코어 보드 초기화
        getContentPane().add(settingPanel); // 설정 초기화, 저장
        getContentPane().add(menuPanel); // 게임으로 돌아가기

    }

    /**
     * 게임으로 돌아가기
     */
    public void goToGameBoard(){
        this.setVisible(false);
        gameBoard.initGameBoard();
    }

    /**
     * 게임 화면 크기 조절 - (small, medium, large)
     */
    public void setDisplaySize(Size size){

        switch (size){
            // small(default)
            case SMALL: {
                gameBoard.HEIGHT = 20;
                gameBoard.WIDTH = 10;
                break;
            }
            // medium
            case MEDIUM: {
                gameBoard.HEIGHT = 28;
                gameBoard.WIDTH = 14;
                break;
            }
            // large
            case LARGE: {
                gameBoard.HEIGHT = 32;
                gameBoard.WIDTH = 16;
                break;
            }
            default:
                System.out.println("잘못된 접근입니다.");
        }
    }

    /**
     * 색맹 모드 켜고 끄기
     */
    public void turnOnOffColorBlindMode(boolean isColorBlind){
        if(isColorBlind){
            //
        }else {
            //
        }

    }


    /**
     * 조작키 설정
     */
    public void setKeyType(){

    }



    /**
     * 설정 초기화
     */
    public void setInit(){

        setDisplaySize(Size.SMALL);
        smallRadioButton.setSelected(true);

        turnOnOffColorBlindMode(false);
        colorBlindOffRadioButton.setSelected(true);

    }


    /**
     * 스코어 보드 기록 초기화
     */
    public void initScore(){
        //
    }

}
