package game;

import blocks.*;
import setting.Mode;
import setting.SettingItem;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameBoard extends JPanel{

    private SettingItem settingItem;

    private static final long serialVersionUID = 2434035659171694595L;

    public int HEIGHT = 20;
    public int WIDTH = 10;
    public static final char BORDER_CHAR = 'X';

    private JTextPane gamePane;
    private JTextPane nextBlockPane;
    private JTextPane label;
    private int[][] board;
    private KeyListener playerKeyListener;
    private SimpleAttributeSet styleSet;
    private Timer timer;
    private Block curr;
    private Block nextBlock;
    private int score = 0;
    private int level = 0;
    int x = 3; //Default Position.
    int y = 0;

    /**
     * Mode 추가
     */
    private static int initInterval;
    private Mode mode;

    public GameBoard(){

        settingItem = SettingItem.getInstance();
        mode = settingItem.getMode();

        setVisible(true);
        setSize(settingItem.getBoardWidth(), settingItem.getBoardHeight());
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        //Board display setting.
        label = new JTextPane();
        label.setVisible(true);
        label.setText("score: "+score+"");
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setForeground(Color.BLACK);

        gamePane = new JTextPane();
        nextBlockPane = new JTextPane();

        gamePane.setEditable(false);
        gamePane.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        gamePane.setBorder(border);

        nextBlockPane.setEditable(false);
        nextBlockPane.setBackground(Color.BLACK);
        nextBlockPane.setBorder(border);

        JPanel eastPanel = new JPanel();
        JPanel scoreBoard = new JPanel();
        scoreBoard.add(label);

        eastPanel.add(nextBlockPane,BorderLayout.CENTER);
        //eastPanel.add(scoreBoard,BorderLayout.SOUTH);

        this.add(scoreBoard, BorderLayout.NORTH);
        this.add(gamePane, BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);

        //Document default style.
        styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, settingItem.getFontSize());
        StyleConstants.setFontFamily(styleSet, "Courier");
        StyleConstants.setBold(styleSet, true);
        StyleConstants.setForeground(styleSet, Color.WHITE);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

        //Set timer for block drops.
        /**
         * Mode 추가
         */
        timer = new Timer(settingItem.getInitInterval(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
                drawGameBoard();
            }
        });

        //Initialize board for the game.
        board = new int[HEIGHT][WIDTH];
        initControls();

        setFocusable(true);
        requestFocus();

        //Create the first block and draw.
        /**
         * Mode 추가
         */
        nextBlock = getRandomBlockMode(mode);
        curr = getRandomBlockMode(mode);
        placeBlock();
        drawGameBoard();
        drawNextBlockBoard();
        timer.start();
    }

    /**
     * 난이도 추가 - 블럭 확률
     */
    public Block getRandomBlockMode(Mode mode){
        switch (mode){
            case EASY:
                return getRandomBlockEasyMode();
            case NORMAL:
                return getRandomBlockNormalMode();
            case HARD:
                return getRandomBlockHardMode();
        }
        return getRandomBlockNormalMode();
    }

    public Block getRandomBlockEasyMode(){
        int random = (int)(Math.random() * 72);

        if(random < 10){
            return new JBlock();
        }else if(random < 20){
            return new LBlock();
        }else if(random < 30){
            return new ZBlock();
        }else if(random < 40){
            return new SBlock();
        }else if(random < 50){
            return new TBlock();
        }else if(random < 60){
            return new OBlock();
        }else {
            return new IBlock(); // 가중치 12
        }
    }

    // normal mode
    public Block getRandomBlockNormalMode() {
        Random rnd = new Random(System.currentTimeMillis());
        int block = rnd.nextInt(1000)%6;
        switch(block) {
            case 0:
                return new IBlock();
            case 1:
                return new JBlock();
            case 2:
                return new LBlock();
            case 3:
                return new ZBlock();
            case 4:
                return new SBlock();
            case 5:
                return new TBlock();
        }
        return new OBlock();
    }

    // hard mode
    public Block getRandomBlockHardMode(){
        int random = (int)(Math.random() * 68);

        if(random < 10){
            return new JBlock();
        }else if(random < 20){
            return new LBlock();
        }else if(random < 30){
            return new ZBlock();
        }else if(random < 40){
            return new SBlock();
        }else if(random < 50){
            return new TBlock();
        }else if(random < 60){
            return new OBlock();
        }else {
            return new IBlock(); // 가중치 8
        }
    }

    /**
     * 키 이벤트
     */
    private void initControls(){

        InputMap im = this.getInputMap();
        ActionMap am = this.getActionMap();

        im.put(KeyStroke.getKeyStroke(settingItem.getRightKey()), "right");
        im.put(KeyStroke.getKeyStroke(settingItem.getLeftKey()), "left");
        im.put(KeyStroke.getKeyStroke(settingItem.getRotateKey()), "up");
        im.put(KeyStroke.getKeyStroke(settingItem.getDownKey()), "down");
        im.put(KeyStroke.getKeyStroke(settingItem.getDropKey()), "space");
        im.put(KeyStroke.getKeyStroke(settingItem.getPauseKey()), "pause");

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()){
                    moveRight();
                    drawGameBoard();
                }
            }
        });

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()){
                    moveLeft();
                    drawGameBoard();
                }
            }
        });

        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()){
                    rotateBlock();
                    drawGameBoard();
                }
            }
        });

        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    moveDown();
                    drawGameBoard();
                }
            }
        });

        am.put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()){
                    dropBlock();
                    drawGameBoard();
                    drawNextBlockBoard();
                }
            }
        });

        am.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });
    }

    private void placeBlock() {
        StyledDocument doc = gamePane.getStyledDocument();
        SimpleAttributeSet styles = new SimpleAttributeSet();
        //StyleConstants.setForeground(styles, curr.getColor());

        for(int j=0; j<curr.height(); j++) {
            int rows = y+j == 0 ? 0 : y+j-1;
            int offset = rows * (WIDTH+3) + x + 1;
            doc.setCharacterAttributes(offset, curr.width(), styles, true);
            for(int i=0; i<curr.width(); i++) {
                if(y+j<HEIGHT && x+i < WIDTH)
                    if(board[y+j][x+i]==0)
                        board[y+j][x+i] = curr.getShape(i, j);
            }
        }
    }

    private void eraseCurr() throws ArrayIndexOutOfBoundsException {
        for(int i=x; i<x+curr.width(); i++) {
            for(int j=y; j<y+curr.height(); j++) {
                if(curr.getShape(i-x, j-y)==1 && i<WIDTH && j<HEIGHT)
                    board[j][i] = 0;
            }
        }
    }

    protected void moveDown() {
        eraseCurr();
        if(y < HEIGHT - curr.height() && !detectCrash('D'))
            y++;
        else {
            placeBlock(); //밑으로 내려가지 않게 고
            eraseOneLine();

            if(gameEnded()) {
                timer.stop();
                label.setText("Game Ended. Press s to restart.");
                placeBlock();
                return;
            }

            curr = nextBlock;
            nextBlock = getRandomBlockMode(mode);
            x = 3;
            y = 0;
            drawNextBlockBoard();
        }
        placeBlock();
    }

    protected void speedUp() {
        if(score/100 > level && initInterval>100) {
            level += 1;
            initInterval -= 100;
            timer.stop();
            timer = new Timer(initInterval, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveDown();
                    drawGameBoard();
                }
            });
            timer.start();
        }
    }

    protected boolean gameEnded() {
        for(int i=0;i<WIDTH;i++) {
            if(board[0][i]==1)
                return true;
        }
        return false;
    }

    protected void moveRight() {
        eraseCurr();
        if(x < WIDTH - curr.width() && !detectCrash('R')) x++;
        placeBlock();
    }

    protected void moveLeft() {
        eraseCurr();
        if(x > 0 && !detectCrash('L')) {
            x--;
        }
        placeBlock();
    }

    protected void eraseOneLine() {
        for(int i=0;i<HEIGHT;i++) {
            boolean lineClear = true;
            for(int j=0;j<WIDTH;j++) {
                if(board[i][j]==0) {
                    lineClear = false;
                    j = WIDTH;
                }
            }
            if(lineClear) {
                speedUp();
                System.out.println("one line cleared!");
                for(int k=i;k>1;k--) {
                    for(int l=0;l<WIDTH;l++) {
                        board[k][l] = board[k-1][l];
                    }
                }
                score += 10;
                label.setText("score: "+score+"");
            }
        }
    }

    protected boolean detectCrash(char position) {
        boolean result = false;
        switch(position) {
            case 'L':
                for(int i=0;i<curr.height();i++) {
                    for(int j=0;j<curr.width();j++) {
                        if(curr.getShape(j, i)==1) {
                            if(board[i+y][j+x-1]==1) {
                                result = true;
                                break;
                            }
                            j = curr.width();
                        }
                    }
                }
                break;
            case 'R':
                for(int i=0;i<curr.height();i++) {
                    for(int j=curr.width()-1;j>=0;j--) {
                        if(curr.getShape(j, i)==1) {
                            if(board[i+y][j+x+1]==1) {
                                result = true;
                                break;
                            }
                            j = -1;
                        }
                    }
                }
                break;
            case 'D':
                for(int i=0;i<curr.width();i++) {
                    for(int j=curr.height()-1;j>=0;j--) {
                        if(curr.getShape(i, j)==1) {
                            if(board[j+y+1][i+x]==1) {
                                result = true;
                                break;
                            }
                            j = -1;
                        }
                    }
                }
                break;
            default:
                System.out.println("Wrong Character");
                break;
        }

        return result;
    }

    public void drawGameBoard() {

        StringBuffer sb = new StringBuffer();
        for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
        sb.append("\n");
        for(int i=0; i < board.length; i++) {
            sb.append(BORDER_CHAR);
            for(int j=0; j < board[i].length; j++) {
                if(board[i][j] == 1) {
                    sb.append("O");
                } else {
                    sb.append(" ");
                }
            }
            sb.append(BORDER_CHAR);
            sb.append("\n");
        }
        for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
        gamePane.setText(sb.toString());
        //Jtextpane

        StyledDocument doc = gamePane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        gamePane.setStyledDocument(doc);
    }

    public void drawNextBlockBoard() {

        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        for(int i=0; i < 2; i++) {
            sb.append("  ");
            for(int j=0; j < 5; j++) {
                if(nextBlock.width()>j && nextBlock.height()>i) {
                    if(nextBlock.getShape(j, i) == 1) {
                        sb.append("O");
                    } else {
                        sb.append(" ");
                    }
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        nextBlockPane.setText(sb.toString());
        //Jtextpane

        StyledDocument doc = nextBlockPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
        nextBlockPane.setStyledDocument(doc);
    }

    public void pause() {
        if(!timer.isRunning()) {
            timer.start();
            label.setText("score: "+score+"");
        }
        else {
            timer.stop();
            label.setText("paused");
        }
    }

    public void reset() {
        this.board = new int[20][10];
        timer.stop();
        initInterval = 1000;
        timer = new Timer(initInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
                drawGameBoard();
            }
        });
        timer.start();
        score = 0;
        label.setText("score: "+score+"");
    }

    public void rotateBlock(){
        eraseCurr();
        if(x+curr.height()>=WIDTH) x = WIDTH - curr.height();
        curr.rotate();
        for(int i=x;i<x+curr.width();i++) {
            for(int j=y;j<y+curr.height();j++) {
                if(board[j][i]==1 && curr.getShape(i-x, j-y)==1) {
                    curr.rotate();
                    curr.rotate();
                    curr.rotate();
                    break;
                }
            }
        }
        placeBlock();
    }

    public void dropBlock(){
        eraseCurr();
        while(y < HEIGHT - curr.height() && !detectCrash('D')) {
            y++;
        }
        placeBlock(); //밑으로 내려가지 않게 고정
        eraseOneLine();
        curr = nextBlock;
        nextBlock = getRandomBlockMode(mode);
        x = 3;
        y = 0;
        placeBlock();
    }
}
