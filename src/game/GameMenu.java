package game;

 import main.Tetris;
 import setting.SettingItem;

 import javax.swing.*;
 import java.awt.*;

 public class GameMenu extends JFrame{
     // private itemGameBoard gameBoard;
     private SettingItem settingItem;
     private GameBoard gameBoard;

     public GameMenu() {
         settingItem = SettingItem.getInstance();

         setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
         setBackground(Color.WHITE);
         setForeground(Color.BLACK);
         setLocationRelativeTo(null);
         setVisible(true);
         
         JButton settingButton = new JButton("설정");
         JButton startMenuBtn = new JButton("시작메뉴로");
         JButton scoreBoardBtn = new JButton("스코어보드");

         settingButton.addActionListener(e -> btnSettingActionPerformed());
         startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
         scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());

         JPanel panel = new JPanel(new GridLayout(0,3));
         panel.add(settingButton);
         panel.add(startMenuBtn);
         panel.add(scoreBoardBtn);

         gameBoard = new GameBoard();
         this.add(gameBoard, BorderLayout.CENTER);
         this.add(panel, BorderLayout.SOUTH);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     
     public GameMenu(int a) {
         settingItem = SettingItem.getInstance();

         setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
         setBackground(Color.WHITE);
         setForeground(Color.BLACK);
         setLocationRelativeTo(null);
         setVisible(true);
         
         JButton settingButton = new JButton("설정");
         JButton startMenuBtn = new JButton("시작메뉴로");
         JButton scoreBoardBtn = new JButton("스코어보드");

         settingButton.addActionListener(e -> btnSettingActionPerformed());
         startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
         scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());

         JPanel panel = new JPanel(new GridLayout(0,3));
         panel.add(settingButton);
         panel.add(startMenuBtn);
         panel.add(scoreBoardBtn);

         gameBoard = new ItemGameBoard();
         this.add(gameBoard, BorderLayout.CENTER);
         this.add(panel, BorderLayout.SOUTH);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
  
     private void btnSettingActionPerformed(){
         dispose();
         Tetris.showSettingMenu();
     }

     private void btnStartMenuActionPerformed(){
         dispose();
         Tetris.showStartMenu();
     }

     private void btnScoreBoardActionPerformed(){
         dispose();
         Tetris.showScoreBoard();
     }
 }