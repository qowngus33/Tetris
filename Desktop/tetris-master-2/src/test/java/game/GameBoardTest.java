package game;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertSame;

public class GameBoardTest {
    GameBoard gameBoard = new GameBoard();
    GameBoard gameBoard2 = new GameBoard();
    public GameBoardTest() throws IOException {}

    @Test
    public void keyTest(){
        gameBoard2 = gameBoard;
        gameBoard.moveLeft();
        gameBoard.moveRight();
        assertSame(gameBoard, gameBoard2);

        gameBoard.rotateBlock();
        gameBoard.rotateBlock();
        gameBoard.rotateBlock();
        assertSame(gameBoard, gameBoard2);

        gameBoard.dropBlock();
        gameBoard2.dropBlock();
        assertSame(gameBoard, gameBoard2);

        gameBoard.moveDown();
        gameBoard2.moveDown();
        assertSame(gameBoard, gameBoard2);
    }
}
