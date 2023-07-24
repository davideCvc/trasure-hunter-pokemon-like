package MiniGames.jumpGame;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * specialTile
 */
public class doorTile_JumpGame extends Tile_JumpGame implements Runnable {
    private Thread tileThread;
    public Map_JumpGame p;
    private boolean mute;

    public doorTile_JumpGame(BufferedImage buttonIcon, int tileSize, boolean collision, Map_JumpGame p, boolean mute) {
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
        while (this.tileThread != null && this.p.jumpGame.end == 0) {
            if (checkCollision(this, this.p.player)) {
                System.out.println("end");
                this.p.jumpGame.mb.setInfo("Gioco finito!");
                this.tileThread = null;
                this.p.jumpGame.win(p.coinTaked);
                if (mute)
                    new Thread(() -> new Sonoro_JumpGame(new File("./src/MiniGames/jumpGame/sound/door.wav"), 0)).start();
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
}