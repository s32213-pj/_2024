package projekt.bomba;

import java.awt.event.KeyEvent;

public class Keyboard {
    public static boolean[] pressed = new boolean[256];
    public static boolean[] prev = new boolean[256];

    private Keyboard() {}

    public static void update() {
        for (int i = 0; i < pressed.length; i++) {
            prev[i] = pressed[i];
        }
    }

    public static void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < pressed.length) {
            pressed[e.getKeyCode()] = true;
        }
    }

    public static void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < pressed.length) {
            pressed[e.getKeyCode()] = false;
        }
    }

    public static boolean typed(int keyCode) {
        return !pressed[keyCode] && prev[keyCode];
    }
}

