package MiniGames.jumpGame;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

/**
 * specialTile
 */
public class specialTile_JumpGame extends Tile_JumpGame implements Runnable {
    private Thread tileThread;

    public Map_JumpGame p;

    private boolean taked = false;

    private boolean mute;

    public specialTile_JumpGame(BufferedImage buttonIcon, int tileSize, boolean collision, Map_JumpGame p, boolean mute) {
        super(buttonIcon, tileSize, collision, false, false, p);
        this.p = p;
        this.tileThread = new Thread(this);
        this.mute = mute;

        setUp();
        this.tileThread.start();
    }

    private void setUp() {}

    public <T extends Component, OT extends Component> boolean checkCollision(T element1, OT element2) {
        return (element1 == null || element2 == null) ? false : element1.getBounds().intersects(element2.getBounds());
    }

    @Override
    public void run() {
        while (this.tileThread != null) {
            if (this.taked || this.p.jumpGame.end != 0)
                this.tileThread = null;

            if (!taked && checkCollision(this, this.p.player)) {
                taked = true;
                this.setIcon(new ImageIcon(this.p.skyImg));
                this.p.jumpGame.mb.setInfo(this.p.jumpGame.mb.value + 1);
                this.p.coinTaked++;
                if (mute)
                    new Thread(() -> new Sonoro_JumpGame(new File("./src/MiniGames/jumpGame/sound/coin.wav"), 0)).start();
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

}