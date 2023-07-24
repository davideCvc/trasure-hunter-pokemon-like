package MiniGames.EscapeGame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Bot
 */
public class Bot_EscapeGame extends JLabel implements Runnable {
    private Thread botThread;
    private BufferedImage Img;
    private int width;
    private int height;
    private Map_EscapeGame p;
    private Vector<Tile_EscapeGame> tiles;
    private Tile_EscapeGame spawnTile;

    private static final int BOT_SPEED = 1;

    public boolean mute;

    public Bot_EscapeGame(BufferedImage Img, int width, int height, Map_EscapeGame p, Vector<Tile_EscapeGame> tiles, Tile_EscapeGame spawnTile, boolean mute) {
        this.Img = Img;
        this.width = width;
        this.height = height;
        this.p = p;
        this.tiles = tiles;
        this.botThread = new Thread(this);
        this.spawnTile = spawnTile;
        this.mute = mute;

        setUp();
        this.botThread.start();
    }

    private void setUp() {
        this.setIcon(new ImageIcon(Img));
        this.setSize(new Dimension(this.width, this.height));
        this.setDoubleBuffered(true);
        this.setLocation(spawnTile.getX(), spawnTile.getY());
    }

    private void botUpdate() {
        try {
            Point playerPoint = p.player.getLocation();
            Point botPoint = this.getLocation();
            if (playerPoint.x > botPoint.x)
                moveBot(BOT_SPEED, 0);
            if (playerPoint.x < botPoint.x)
                moveBot(-BOT_SPEED, 0);
            if (playerPoint.y > botPoint.y)
                moveBot(0, BOT_SPEED);
            if (playerPoint.y < botPoint.y)
                moveBot(0, -BOT_SPEED);
        } catch (Exception e) {
        }
    }

    private void moveBot(int x, int y) {
        Point position = this.getLocation();
        this.setLocation(position.x + x, position.y + y);

        tiles.forEach(e -> {
            if (e.collision && checkCollision(e, this))
                moveBot(-x, -y);
        });

        if (checkCollision(this, p.player)) {
            if (mute)
                new Thread(() -> new Sonoro_EscapeGame(new File("./src/MiniGames/EscapeGame/sound/ghost.wav"), 0)).start();
            p.escapeGame.fail();
        }
    }

    public <T extends Component, OT extends Component> boolean checkCollision(T element1, OT element2) {
        if (element1 != null && element2 != null)
            return element1.getBounds().intersects(element2.getBounds());
        return false;
    }

    @Override
    public void run() {
        while (this.botThread != null && p.escapeGame.end == 0) {
            botUpdate();
            try {
                Thread.sleep(16);
            } catch (Exception e) {
            }
        }
    }
}