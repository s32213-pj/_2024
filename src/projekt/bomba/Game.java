package projekt.bomba;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements KeyListener, Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 640;
    public static final Font main = new Font("Bebas neue Regular", Font.PLAIN, 28);
    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private long startTime;
    private long elapsed;
    private boolean set;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);


    }

    private void update() {

    }

    private void render() {

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //tablica renderujaca
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;

        //nanosekundy ostatniego apdejta
        double then = System.nanoTime();
        double unprocessed = 0;
        while (running) {
            boolean shouldRender = false;
            double now = System.nanoTime();
            unprocessed =+ (now - then) / nsPerUpdate;
            then = now;
            //kolejka apdejtów
            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;

            }
            //render
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            //zegarek stupek na sekunde
            if (System.currentTimeMillis() - fpsTimer > 1000) {
                System.out.println("%d fps %d updates", fps, updates);
                System.out.println();
                updates = 0;
                fpsTimer += 1000;
            }
        }
        public synchronized void start(){
            if(running) return;
            running = true;
            game = new Thread(this, "game");
            game.start();
        }

    }
}