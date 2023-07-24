package MiniGames.jumpGame;

import java.awt.Component;
import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;

/**
 * Player
 */
public class Player_JumpGame extends JLabel implements Runnable {
    private static final int FPS = 60;
    private static final int PLAYER_SPEED = 4;
    private static final int PLAYER_JUMP = 256;
    public Thread playerThread;
    Game_JumpGame jG;

    boolean jumpLock = false;
    boolean jumpThread = true;
    int jumpTime = 10;

    private int width;
    private int height;

    private int mapWidth;
    private int mapHeight;

    private KeyBoard_JumpGame kB = new KeyBoard_JumpGame();
    private Vector<Tile_JumpGame> collisionTiles;

    private Tile_JumpGame spawTile;

    int spostamento = 0;

    ImageSet_JumpGame playerIMGS;
    boolean playerPosition = false;

    private boolean mute;

    public Player_JumpGame(int width, int height, int mapWidth, int mapHeight, Vector<Tile_JumpGame> tiles, Tile_JumpGame spawnTile, Game_JumpGame jG, boolean mute) {
        this.width = width;
        this.height = height;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.spawTile = spawnTile;
        this.jG = jG;
        this.mute = mute;

        try {
            playerIMGS = new ImageSet_JumpGame("./src/MiniGames/jumpGame/img/sub/", "sub", 0, 9, ".png");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // ? get only the tiles with collision
        collisionTiles = new Vector<>();
        tiles.forEach(e -> {
            if (e.collision)
                collisionTiles.add(e);
        });

        setUp();

        this.playerThread = new Thread(this);
        this.playerThread.start();
    }

    private void setUp() {
        this.setSize(this.width, this.height);
        this.setLocation(spawTile.getX(), spawTile.getY() - this.height);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(this.kB);
    }

    private void updatePlayer() {
        boolean pFC = playerFloreCollision();

        // ? gravity
        if (!pFC)
            for (int i = 0; i < 8; i++) {
                movePlayer(0, 1);
            }

        // ? jump
        if (pFC && !jumpLock && this.kB.SPACE_pressed)
            playerJump();

        // ? left
        if (this.kB.A_pressed)
            movePlayer(-PLAYER_SPEED, 0);

        // ? right
        if (this.kB.D_pressed)
            movePlayer(PLAYER_SPEED, 0);

        // ? active jump lock
        jumpLock = this.kB.SPACE_pressed;
    }

    private boolean playerFloreCollision() {
        boolean collided = false;
        this.setLocation(this.getX(), this.getY() + 1);

        // ? collision check
        for (Tile_JumpGame tile : collisionTiles) {
            if (checkCollision(tile, this))
                collided = true;
        }

        this.setLocation(this.getX(), this.getY() - 1);
        return collided;
    }

    private boolean playerJump() {
        if (jumpThread)
            new Thread(() -> {
                jumpThread = false;
                // ? jump sequence
                for (int i = 0; i < PLAYER_JUMP; i++) {
                    // ? end jump sequence
                    if (movePlayer(0, -1))
                        i = PLAYER_JUMP;
                    if (i % 2 == 0 && i != PLAYER_JUMP) {
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                        }
                    }
                }
                jumpThread = true;
            }).start();
        return jumpThread;
    }

    private boolean movePlayer(int x, int y) {
        boolean collided = false;

        imgManager(playerPosition, x, y);

        // ? player movement
        if (this.getX() + x >= 0 && this.getX() + x < mapWidth - width) {
            this.setLocation(this.getX() + x, this.getY() + y);
        }

        // ? mappa avanti
        if (this.getX() >= 500 + spostamento && spostamento <= 4800) {
            spostamento += PLAYER_SPEED;
            this.jG.em.setLocation(this.jG.em.getX() - PLAYER_SPEED, this.jG.em.getY());
        }

        // ? mappa indietro
        if (this.getX() < 280 + spostamento && spostamento > 0) {
            spostamento += -PLAYER_SPEED;
            this.jG.em.setLocation(this.jG.em.getX() + PLAYER_SPEED, this.jG.em.getY());
        }

        // ? check player death outside map
        if (this.getY() > this.mapHeight) {
            if (mute)
                new Thread(() -> new Sonoro_JumpGame(new File("./src/MiniGames/jumpGame/sound/death.wav"), 0)).start();
            this.jG.fail();
        }

        // ? collision check
        for (Tile_JumpGame tile : collisionTiles) {
            if (checkCollision(tile, this)) {
                this.setLocation(this.getX() - x, this.getY() - y);
                collided = true;
            }
        }
        return collided;
    }

    public <T extends Component, OT extends Component> boolean checkCollision(T element1, OT element2) {
        return (element1 == null || element2 == null) ? false : element1.getBounds().intersects(element2.getBounds());
    }

    public void imgManager(boolean pos, int x, int y) {
        this.setIcon(this.playerIMGS.getImage(x < 0 ? (pos ? "sub6" : "sub7") : (pos ? "sub8" : "sub9")));
    }

    @Override
    public void run() {
        int playerPositionDelay = 0;

        while (playerThread != null && jG.end == 0) {
            updatePlayer();
            playerPositionDelay++;

            if (playerPositionDelay == 10) {
                this.playerPosition = !this.playerPosition;
                playerPositionDelay = 0;
            }

            if (!kB.W_pressed && !kB.A_pressed && !kB.S_pressed && !kB.D_pressed)
                this.setIcon(this.playerIMGS.getImage("sub0"));

            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
            }
        }
    }
}