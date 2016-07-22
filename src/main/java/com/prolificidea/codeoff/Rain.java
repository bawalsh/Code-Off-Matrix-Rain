import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Rain extends JPanel {

    private Drop[] drops;
    private BufferedImage image;
    private int globalX;
    private boolean backwards;
    private int[] dropX;
    private Random rng = new Random();
    private int last;
    private int dropCounter;

    Rain() {

        globalX = 0;
        backwards = false;
        dropCounter = 0;

        try {
            image = ImageIO.read(new File("cloud.png"));
        } catch (IOException ex) {
            // handle exception...
        }

        drops = new Drop[500];
        dropX = new int[drops.length];

        //for (int i = 0; i < drops.length; i++) {
        //    dropX[i] = globalX+getRandomInteger(60, 140);
        //    drops[i] = new Drop(i * Config.FONT_SIZE);
        //}

    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g.fillRect(0, 0, Config.SCREEN_SIZE, Config.SCREEN_SIZE);
        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.PLAIN, Config.FONT_SIZE);
        g2.setFont(font);

        for (int i = 0; i < dropCounter; i++) {
            drops[i].draw(g2, dropX[i]);
        }

        if (Math.abs(last-globalX) >= 20) {
            drops[dropCounter] = new Drop(dropCounter * Config.FONT_SIZE, (dropCounter+1)/5);
            dropX[dropCounter] = globalX+getRandomInteger(70, 130);
            last = globalX;
            dropCounter++;
        }

        g2.drawImage(image, globalX, 0, null);

        if (globalX == 0) {
            backwards = false;
        }

        if (globalX == Config.SCREEN_SIZE-215) {
            backwards = true;
        }

        if (backwards) {
            globalX--;
        } else {
            globalX++;
        }


        try {
            Thread.sleep(10);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        repaint();
    }


    public int getRandomInteger(int min, int max) {
        int randomNum = rng.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
