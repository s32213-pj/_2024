package projekt.bomba;

import java.awt.*;
import java.awt.image.BufferedImage;

class dachowka {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    public static final int SLIDE_SPEED = 20;
    public static final int ARC_WIDTH = 15;
    public static final int ARC_HEIGHT = 15;
    private int value;
    private BufferedImage tileImage;
    private Color background;
    private Color text;
    private Font font;
    private int x;
    private  int y;

    public void Tile(int value, int x, int y){
        this.value = value;
        this.x = x;
        this.y = y;
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        drawImage();


    }

    private void drawImage(){
        Graphics2D g = (Graphics2D)tileImage.getGraphics();
        if(value == 2){
            background = new Color(0xe9e9e9);
            text = new Color(0x000000);
        }
        else if (value == 4){
            background = new Color(0xe6daad);
            text = new Color(0x000000);
        }
        else if (value == 8){
            background = new Color(0xF5D068);
            text = new Color(0x000000);
        }
        else if (value == 16){
            background = new Color(0xf28007);
            text = new Color(0xffffff);
        }
        else if (value ==32){
            background = new Color(0xf55e3b);
            text = new Color(0xffffff);
        }
        else if (value == 64){
            background = new Color(0xff0000);
            text = new Color(0xffffff);
        }
        else if (value == 128){
            background = new Color(0xC3FF49);
            text = new Color(0xffffff);
        }
        else if (value == 256){
            background = new Color(0x04F5AD);
            text = new Color(0xffffff);
        }
        else if (value == 512){
            background = new Color(0x00D7DF);
            text = new Color(0xffffff);
        }
        else if (value == 1024){
            background = new Color(0x0065C2);
            text = new Color(0xffffff);
        }
        else if (value == 2048){
            background = new Color(0x0011C2);
            text = new Color(0xffffff);
        }
        else{
            background = Color.black;
            text = Color.white;
        }
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(background);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(text);

        if (value <= 64) {
            font = Game.main.deriveFont(36f);

        }
        else {
            font = Game.main;
        }
        g.setFont(font);
        int drawX = WIDTH / 2 -DrawUtils.getMessageWidth("" + value, font, g) /2;
        int drawY = HEIGHT / 2 +DrawUtils.getMessageHeight("" + value, font, g) /2;
        g.drawString("" + value, drawX, drawY);
        g.dispose();
    }

    public void render(Graphics2D g2d) {
    }
}
