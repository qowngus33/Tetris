package setting;

import game.GameForm;
import main.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingMenuForm extends JFrame {

    public GameForm gameForm;

    public SettingMenuForm() {

        setTitle("설정메뉴");
        setSize(380, 800);
        setBackground(Color.WHITE);

        // 화면 크기 조절 버튼 & 이벤트 설정
        JLabel sizeLabel = new JLabel("화면 크기 선택");
        String[] sizeNames = {"small", "medium", "large"};
        JRadioButton[] sizeBtns = new JRadioButton[3];
        ButtonGroup sizeBtnGroup = new ButtonGroup();

        for(int i = 0; i < sizeBtns.length; i++){
            sizeBtns[i] = new JRadioButton(sizeNames[i]);
            sizeBtnGroup.add(sizeBtns[i]);
        }

        JPanel sizePanel = new JPanel(new GridLayout(0, 4));
        sizePanel.add(sizeLabel);
        for(int i = 0; i < sizeBtns.length; i++){
            sizePanel.add(sizeBtns[i]);
        }
        sizeBtns[0].addActionListener(e -> btnSmallBtnActionPerformed());
        sizeBtns[1].addActionListener(e -> btnMediumBtnActionPerformed());
        sizeBtns[2].addActionListener(e -> btnLargeBtnActionPerformed());


        // 게임 시작 버튼, 시작 메뉴 버튼 & 이벤트 설정
        JButton gameBtn = new JButton("게임으로");
        JButton startMenuBtn = new JButton("시작메뉴로");

        gameBtn.addActionListener(e -> btnGameActionPerformed());
        startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());

        JPanel panel = new JPanel(new GridLayout(2, 0));
        panel.add(gameBtn);
        panel.add(startMenuBtn);


        // panel 추가
        this.getContentPane().setLayout(new GridLayout(2, 0));
        this.getContentPane().add(sizePanel);
        this.getContentPane().add(panel);

    }

    private void btnGameActionPerformed(){
        this.setVisible(false);
        Tetris.start(new Size());
    }

    private void btnStartMenuActionPerformed(){
        this.setVisible(false);
        Tetris.showStartMenu();
    }

    /**
     * 화면 크기 조절
     */
    private void btnSmallBtnActionPerformed() {
        Size.boardWidth = 380;
        Size.boardHeight = 700;
        Size.fontSize = 15;
    }

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
    

}
