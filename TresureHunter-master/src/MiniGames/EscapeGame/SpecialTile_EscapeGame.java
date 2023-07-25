package MiniGames.EscapeGame;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

/**
 * SpecialTile
 */
public class SpecialTile_EscapeGame extends Tile_EscapeGame implements Runnable {
    private Thread tileThread;
    private Game_EscapeGame p;

    boolean collided = false;

    private BufferedImage notCollidedImg;
    private BufferedImage collidedImg;

    public boolean mute;

    public SpecialTile_EscapeGame(BufferedImage notCollidedImg, BufferedImage collidedImg, int tileSize, boolean collision, Game_EscapeGame p, boolean mute) {
        super(notCollidedImg, tileSize, collision, p.em);
        this.p = p;
        this.tileThread = new Thread(this);

        this.notCollidedImg = notCollidedImg;
        this.collidedImg = collidedImg;

        this.mute = mute;

        setUp();
        this.tileThread.start();
    }

    private void setUp() {}

    private void updateTile() {
        if (!collided && p.em.player.checkCollision(p.em.player, this)) {
            if (mute)
                new Thread(() -> new Sonoro_EscapeGame(new File("./src/MiniGames/EscapeGame/sound/takeSound.wav"), 0)).start();
            changeCollided();
            this.p.em.specialtileN--;
        }
    }

    public void setCollided(boolean cl) {
        collided = cl;
        this.setIcon(new ImageIcon(cl ? collidedImg : notCollidedImg));
    }

    public void changeCollided() {
        setCollided(!collided);
    }

    @Override
    public void run() {
        while (this.tileThread != null && this.p.end == 0) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            updateTile();
        }
    }
}