package MiniGames.EscapeGame;

import java.awt.Component;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JLabel;

/**
 * Player
 */
public class Player_EscapeGame extends JLabel implements Runnable {
    private static final int PLAYER_SPEED = 4;
    public Thread playerThread;
    Game_EscapeGame eg;

    private int width;
    private int height;

    private int mapWidth;
    private int mapHeight;

    private KeyBoard_EscapeGame kB = new KeyBoard_EscapeGame();
    private Vector<Tile_EscapeGame> tiles;

    ImageSet_EscapeGame playerIMGS;

    private Tile_EscapeGame spawTile;

    boolean playerPosition = false;

    public Player_EscapeGame(int width, int height, int mapWidth, int mapHeight, Vector<Tile_EscapeGame> tiles, Tile_EscapeGame spawnTile,
            Game_EscapeGame eg) {
        this.width = width;
        this.height = height;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tiles = tiles;
        this.spawTile = spawnTile;
        this.eg = eg;

        try {
            playerIMGS = new ImageSet_EscapeGame("./src/MiniGames/EscapeGame/img/sub/", "sub", 0, 9, ".png");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        setUp();

        this.playerThread = new Thread(this);
        this.playerThread.start();
    }

    private void setUp() {
        this.setSize(this.width, this.height);
        this.setLocation(spawTile.getX() - (width / 4), spawTile.getY() - (height / 4));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(this.kB);
    }

    private void updatePlayer() {
        if (this.kB.W_pressed)
            movePlayer(0, -PLAYER_SPEED);
        if (this.kB.A_pressed)
            movePlayer(-PLAYER_SPEED, 0);
        if (this.kB.S_pressed)
            movePlayer(0, PLAYER_SPEED);
        if (this.kB.D_pressed)
            movePlayer(PLAYER_SPEED, 0);
    }

    private void movePlayer(int x, int y) {
        Point position = this.getLocation();
        if (position.x + x >= 0 && position.y + y >= 0 && position.x + x <= mapWidth - width
                && position.y + y <= mapHeight - height)
            this.setLocation(position.x + x, position.y + y);

        tiles.forEach(e -> {
            if (e.collision && checkCollision(e, this))
                movePlayer(-x, -y);
        });

        imgManager(playerPosition, x, y);
    }

    public <T extends Component, OT extends Component> boolean checkCollision(T element1, OT element2) {
        if (element1 != null && element2 != null)
            return element1.getBounds().intersects(element2.getBounds());
        return false;
    }

    public void imgManager(boolean pos, int x, int y) {
        if (y < 0)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub4" : "sub5"));
        if (y > 0)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub1" : "sub2"));
        if (x < 0)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub6" : "sub7"));
        if (x > 0)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub8" : "sub9"));
    }

    @Override
    public void run() {
        int playerPositionDelay = 0;

        while (playerThread != null && eg.end == 0) {
            updatePlayer();
            playerPositionDelay++;

            if (playerPositionDelay == 10) {
                this.playerPosition = !this.playerPosition;
                playerPositionDelay = 0;
            }

            if (!kB.W_pressed && !kB.A_pressed && !kB.S_pressed && !kB.D_pressed)
                this.setIcon(this.playerIMGS.getImage("sub0"));

            try {
                Thread.sleep(16);
            } catch (Exception e) {
            }
        }
    }
}