package MiniGames.jumpGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard_JumpGame extends KeyAdapter {

    boolean W_pressed, A_pressed, S_pressed, D_pressed, SPACE_pressed;

    public KeyBoard_JumpGame() {
        super();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
            case KeyEvent.VK_W -> W_pressed = true;
            case KeyEvent.VK_A -> A_pressed = true;
            case KeyEvent.VK_S -> S_pressed = true;
            case KeyEvent.VK_D -> D_pressed = true;
            case KeyEvent.VK_SPACE -> SPACE_pressed = true;
            default -> System.out.println("tasto non mappato");
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
            case KeyEvent.VK_W -> W_pressed = false;
            case KeyEvent.VK_A -> A_pressed = false;
            case KeyEvent.VK_S -> S_pressed = false;
            case KeyEvent.VK_D -> D_pressed = false;
            case KeyEvent.VK_SPACE -> SPACE_pressed = false;
            default -> System.out.println("tasto non mappato");
        }
    }
}