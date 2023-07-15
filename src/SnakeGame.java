import javax.swing.JFrame;

/**
 * snakeGame
 */


public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SnakeGame sg = new SnakeGame();
        System.out.println("hello prince");
    }
}