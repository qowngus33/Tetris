package game;

 import play.Tetris;
 import setting.SettingItem;

 import javax.swing.*;
 import java.awt.*;
<<<<<<< HEAD:src/main/java/game/GameMenu.java
 import java.io.IOException;
=======
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameMenu.java

public class GameMenu extends JFrame{
     // private itemGameBoard gameBoard;
     private SettingItem settingItem;
     private GameBoard gameBoard;

<<<<<<< HEAD:src/main/java/game/GameMenu.java
     public GameMenu() throws IOException {
         settingItem = SettingItem.getInstance();

         setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
         setBackground(Color.WHITE);
         setForeground(Color.WHITE);
         setLocationRelativeTo(null);
         setVisible(true);
         
         JButton settingButton = new JButton("설정");
         JButton startMenuBtn = new JButton("시작메뉴로");
         JButton scoreBoardBtn = new JButton("스코어보드");

         settingButton.addActionListener(e -> btnSettingActionPerformed());
         startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
         scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());

         JPanel panel = new JPanel(new GridLayout(0,3));
         panel.setBackground(Color.WHITE);
         panel.add(settingButton);
         panel.add(startMenuBtn);
         panel.add(scoreBoardBtn);

=======
     public GameMenu() {
    	 super("Tetris");
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameMenu.java
         gameBoard = new GameBoard();
         init();
     }

     
<<<<<<< HEAD:src/main/java/game/GameMenu.java
     public GameMenu(int a) throws IOException{
         settingItem = SettingItem.getInstance();

         setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
=======
     public GameMenu(int a) {
    	 super("Tetris");
    	 gameBoard = new ItemGameBoard();
    	 init();
     }
     
     private void init() {
    	 settingItem = SettingItem.getInstance();
    	 setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
    	 setResizable(false);
>>>>>>> 8a496d5bb699e85ce5949885ef89a95bc59f541a:src/game/GameMenu.java
         setBackground(Color.WHITE);
         setForeground(Color.WHITE);
         setLocationRelativeTo(null);
         setVisible(true);
         
         JButton settingButton = new JButton("설정");
         JButton startMenuBtn = new JButton("시작메뉴로");
         JButton scoreBoardBtn = new JButton("스코어보드");

         settingButton.addActionListener(e -> btnSettingActionPerformed());
         startMenuBtn.addActionListener(e -> btnStartMenuActionPerformed());
         scoreBoardBtn.addActionListener(e -> btnScoreBoardActionPerformed());

         JPanel innerPanel = new JPanel(new GridLayout(0,3));
         innerPanel.add(settingButton);
         innerPanel.add(startMenuBtn);
         innerPanel.add(scoreBoardBtn);
         
         JPanel outerPanel = new JPanel();
         
         outerPanel.add(gameBoard);
         outerPanel.add(innerPanel);
         setContentPane(outerPanel);
         outerPanel.setForeground(Color.WHITE);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
  
     private void btnSettingActionPerformed(){
    	 gameBoard.stopTimer();
         dispose();
         Tetris.showSettingMenu();
     }

     private void btnStartMenuActionPerformed(){
    	 gameBoard.stopTimer();
         dispose();
         Tetris.showStartMenu();
     }

     private void btnScoreBoardActionPerformed(){
    	 gameBoard.stopTimer();
         dispose();
         Tetris.showScoreBoard();
     }
 }