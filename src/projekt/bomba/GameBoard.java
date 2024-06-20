package projekt.bomba;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

class GameBoard {
    public static final int ROWS = 4;
    public static final int COLS = 4;
    private final int startingTiles = 2;
    private Tile[][] board;
    private boolean dead;
    private boolean won;
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private int x;
    private int y;

    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    private boolean hasStarted;

    public GameBoard(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
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
                int x = SPACING + (SPACING + Tile.WIDTH) * col;
                int y = SPACING + (SPACING + Tile.HEIGHT) * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, 15, 15);
            }
        }
    }

    private void start() {
        for (int i = 0; i < startingTiles; i++) {
            spawnRandom();
        }
    }

    private void spawnRandom() {
        Random random = new Random();
        boolean notValid = true;

        while (notValid) {
            int location = random.nextInt(ROWS * COLS);
            int row = location / COLS;
            int col = location % COLS;
            Tile current = board[row][col];
            if (current == null) {
                int value = random.nextInt(10) < 9 ? 2 : 4;
                Tile tile = new Tile(value, getTileX(col), getTileY(row));
                board[row][col] = tile;
                notValid = false;
            }
        }
    }

    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }

    public void render(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);

        // Rysowanie kafelków
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.render(g2d);
            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }

    public void update() {
        checkKeys();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) continue;
                current.update();
                if (current.getValue() == 2048) {
                    won = true;
                }
            }
        }
    }

    private boolean move(int row,int col, int horizontalDirection, int vectricalDirection, Direction dir){
        boolean canMove = false;
        Tile current = board[row][col];
        if(current == null) return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while(move) {
            newCol += horizontalDirection;
            newRow += vectricalDirection;
            if (checkOutOfBounds(dir, newRow, newCol)) break;
            if (board[newRow][newCol] == null) {
                board[newRow][newCol] = current;
                board[newRow - vectricalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
            }
            else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].setCanCombine()){
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - vectricalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                //   board[newRow][newCol].setCanCombineAnimation(true);
                // dodaj score
            }
            else{
                move = false;
            }
        }
        return canMove;
    }
    private boolean checkOutOfBounds(Direction dir, int newRow, int newCol){
        if (dir == Direction.LEFT){
            return col < 0;
        }
        else if (dir == Direction.RIGHT){
            return col > COLS -1;
        }
        else if(dir == Direction.UP){
            return row < 0;
        }
        else if(dir == Direction.DOWN){
            return row > ROWS -1;
        }
        return false;
    }

    private void moveTiles(Direction dir) {
        boolean canMove = false;
        int horizontalDiretion = 0;
        int verticalDiretion = 0;


        if (dir == Direction.LEFT) { // ta funkcja odpowiada za scalanie kafelków za jednym ruchem --->2248-->0016
            horizontalDiretion = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDiretion, verticalDiretion, dir);
                    }
                    else move(row, col, horizontalDiretion, verticalDiretion, dir);
                }
            }
        }


        else if (dir == Direction.RIGHT) {
            horizontalDiretion = 1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS - 1; col >= 0; col--){
                    if (!canMove) {
                        canMove = move(row, col, horizontalDiretion, verticalDiretion, dir);
                    }
                    else move(row, col, horizontalDiretion, verticalDiretion, dir);
                }
            }
        }


        else if (dir == Direction.UP) {
            verticalDiretion = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDiretion, verticalDiretion, dir);
                    }
                    else move(row, col, horizontalDiretion, verticalDiretion, dir);
                }
            }
        }


        else if (dir == Direction.DOWN) {
            verticalDiretion = 1;
            for (int row = ROWS -1; row >= 0 ; row--){
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDiretion, verticalDiretion, dir);
                    }
                    else move(row, col, horizontalDiretion, verticalDiretion, dir);
                }
            }
        }
        else{
            System.out.println(dir+ "is not a valid direction.");
        }

        for (int row = 0 ; row < ROWS; row++){
            for(int col= 0 ;col < COLS; col++){
                Tile current = board[row][col];
                if (current == null) continue;
                current.setCanCombine(true);
            }
        }

        if (canMove){
            spawnRandom();
        }
    }


    private void checkKeys() {
        if (Keyboard.typed(KeyEvent.VK_LEFT)) {
            moveTiles(Direction.LEFT);
            // Przesuwanie kafelków w lewo
            if (!hasStarted) hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
            moveTiles(Direction.RIGHT);
            // Przesuwanie kafelków w prawo
            if (!hasStarted) hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_UP)) {
            moveTiles(Direction.UP);
            // Przesuwanie kafelków w górę
            if (!hasStarted) hasStarted = true;
        }
        if (Keyboard.typed(KeyEvent.VK_DOWN)) {
            moveTiles(Direction.DOWN);
            // Przesuwanie kafelków w dół
            if (!hasStarted) hasStarted = true;
        }
    }
}


