package Map;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard extends KeyAdapter {

    boolean W_pressed, A_pressed, S_pressed, D_pressed;

    @Override
    public void keyPressed(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
        case KeyEvent.VK_W:
            W_pressed = true;
            Player.direction = "up";
            break;
        case KeyEvent.VK_A:
            A_pressed = true;
            Player.direction = "left";
            break;
        case KeyEvent.VK_S:
            S_pressed = true;
            Player.direction = "down";
            break;
        case KeyEvent.VK_D:
            D_pressed = true;
            Player.direction = "right";
            break;
        default:

            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
        case KeyEvent.VK_W -> W_pressed = false;
        case KeyEvent.VK_A -> A_pressed = false;
        case KeyEvent.VK_S -> S_pressed = false;
        case KeyEvent.VK_D -> D_pressed = false;
        default -> System.out.println("tasto non mappato");
        }
    }

    @Override
    public String toString() {
        return "KeyBoard [W_pressed=" + W_pressed + ", A_pressed=" + A_pressed + ", S_pressed=" + S_pressed + ", D_pressed=" + D_pressed + "]";
    }
}
