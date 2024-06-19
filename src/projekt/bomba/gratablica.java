package projekt.bomba;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

class gratablica {
    public static final int ROWS = 4;
    public static final int COLS = 4;
    private final int staringTiles = 2;
    private dachowka[][] board;
    private boolean dead;
    private boolean won;
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private int x;
    private int y;

    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * dachowka.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * dachowka.HEIGHT;

    private boolean hasStarted;

    public gratablica(int x, int y) {
        this.x = x;
        this.y = y;
        board = new dachowka[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        createBoardImage();
        start();
    }

    private void createBoardImage() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = SPACING + (SPACING + dachowka.WIDTH) * col;
                int y = SPACING + (SPACING + dachowka.HEIGHT) * row;
                g.fillRoundRect(x, y, dachowka.WIDTH, dachowka.HEIGHT, 15, 15);
            }
        }
        g.dispose();
    }

    public void render(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);

        //rysowanie dachówek
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] != null) {
                    board[row][col].render(g2d);
                }
            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }

    public void update() {
        checkKeys();
    }

    private void checkKeys() {
        if (Klawa.typed(KeyEvent.VK_LEFT)) {
            // poruszanie kafelek w lewo
            if (!hasStarted) hasStarted = true;
        }
        if (Klawa.typed(KeyEvent.VK_RIGHT)) {
            // poruszanie kafelek w prawo
            if (!hasStarted) hasStarted = true;
        }
        if (Klawa.typed(KeyEvent.VK_UP)) {
            // poruszanie kafelek w góre
            if (!hasStarted) hasStarted = true;
        }
        if (Klawa.typed(KeyEvent.VK_DOWN)) {
            // poruszanie kafelek w dół
            if (!hasStarted) hasStarted = true;
        }
    }
    private void start() {
        for (int i = 0; i < staringTiles; i++) {
            addTile();
        }
    }
    private void addTile() {
    }
}