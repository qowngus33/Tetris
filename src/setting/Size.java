package setting;

import java.io.Serializable;

public class Size implements Serializable {

    public static int boardWidth;
    public static int boardHeight;
    public static int fontSize;

    public static void initSize(){
        boardWidth = 500;
        boardHeight = 900;
        fontSize = 23;
    }

    public static void setSize(Size size){
        Size.boardWidth = size.boardWidth;
        Size.boardHeight = size.boardHeight;
        Size.fontSize = size.fontSize;

        System.out.println("size");
    }
}
