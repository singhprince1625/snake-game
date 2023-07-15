import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {
    int h = 400;
    int w = 600;

    int maxDots = 3200;
    int dotSize = 10;
    int dot;

    // for snake position
    int x[];
    int y[];

    // Apple position
    int appleX;    
    int appleY;

    // Image
    Image ball , apple, head;

    // private Timer timer;
    
    Board(){
        setPreferredSize(new Dimension(w,h));
        setBackground(Color.BLACK);
    }

    // Initialize Game
    public void initGame(){
        dot = 3;
        x = new int[maxDots];
        y = new int[maxDots];

        x[0] = 50;
        y[0] = 50;
        for (int i = 1; i < dot; i++){
            x[i] = x[i-1] + dotSize;
            y[i] = y[0];
        }
        // Initialize Apple Position.
        appleX = 150;        
        appleY = 150;
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

}
