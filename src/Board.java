import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private final int h = 400;
    private final int w = 600;

    private final int maxDots = 3200;
    private final int dotSize = 10;
    private final int rand_x = 59;
    private final int rand_y = 39;
    
    public int dots;
    
    // for snake position
    private int x[];
    private int y[];

    // Apple position
    private int apple_x;    
    private int apple_y;

    // Declaring snake direction
    private boolean leftDirection = true;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;

    // Declaring inGame
    private boolean inGame = true;

    // Declaring Image
    private Image ball , apple, head;
    
    // Declaring Timer
    private Timer timer;
    private final int delay = 200;
    
    Board(){
        x= new int[maxDots];
        y= new int[maxDots];

        initBoard();
    }

    // Initialize Board Panel
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(w, h));
        loadImages();
        initGame();
    }

    // Initialize Game
    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 450 + z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }
    
    //Randomize Apple position every time
    private void locateApple() {

        int r = (int) (Math.random() * rand_x);
        apple_x = ((r * dotSize));

        r = (int) (Math.random() * rand_y);
        apple_y = ((r * dotSize));
    }

    //Load images of dots, head and Apple
    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    // GameOver massage
    private void gameOver(Graphics g) {

        String msg = "Game Over";
        int score = (dots-3)*100;
        String scoremsg = "\nScore: "+ Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (w - metr.stringWidth(msg)) / 2, (h / 2)-10);
        g.drawString(scoremsg, (w - metr.stringWidth(scoremsg)) / 2,(h / 2)+10 );
    }

    // Cheak for Apple
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
    }
    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= dotSize;
        }

        if (rightDirection) {
            x[0] += dotSize;
        }

        if (upDirection) {
            y[0] -= dotSize;
        }

        if (downDirection) {
            y[0] += dotSize;
        }
    }
    //Checks collision of head with any obstacle
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= h) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= w) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

}
