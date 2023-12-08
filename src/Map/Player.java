package Map;

import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JLabel;

public class Player extends JLabel implements Runnable {

    KeyBoard kB;
    public int playerX;
    public int playerY;
    public static String direction;
    Thread gameThread;
    public Rectangle solidRect = new Rectangle(0, 0, 40, 40);

    Map map;
    boolean updateThread = true;

    ArrayList<Tile> collided;
    ImageSet playerIMGS;
    ImageSet playerIMGS_NPC;
    boolean playerPosition;

    public Player(Map map) {
        this.map = map;
        kB = map.kB;
        collided = new ArrayList<>();
        gameThread = new Thread(this);
        direction = "down";

        try {
            playerIMGS = new ImageSet("./src/Map/sub/", "sub", 0, 9, ".png");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        gameThread.start();
    }

    public void imgManager(boolean pos) {
        if (!kB.W_pressed && !kB.A_pressed && !kB.S_pressed && !kB.D_pressed)
            this.setIcon(this.playerIMGS.getImage("sub0"));
        if (kB.W_pressed && !kB.A_pressed && !kB.S_pressed && !kB.D_pressed)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub4" : "sub5"));
        if (!kB.W_pressed && !kB.A_pressed && kB.S_pressed && !kB.D_pressed)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub1" : "sub2"));
        if (!kB.W_pressed && kB.A_pressed && !kB.S_pressed && !kB.D_pressed)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub6" : "sub7"));
        if (!kB.W_pressed && !kB.A_pressed && !kB.S_pressed && kB.D_pressed)
            this.setIcon(this.playerIMGS.getImage(pos ? "sub8" : "sub9"));

        // System.out.println(kB);
    }

    public void imgManager(int x, int y) {
        if (y == 0 && x == 0)
            this.setIcon(this.playerIMGS.getImage("sub0"));
        if (y > 0 && x == 0)
            this.setIcon(this.playerIMGS.getImage(playerPosition ? "sub4" : "sub5"));
        if (y < 0 && x == 0)
            this.setIcon(this.playerIMGS.getImage(playerPosition ? "sub1" : "sub2"));
        if (y == 0 && x > 0)
            this.setIcon(this.playerIMGS.getImage(playerPosition ? "sub6" : "sub7"));
        if (y == 0 && x < 0)
            this.setIcon(this.playerIMGS.getImage(playerPosition ? "sub8" : "sub9"));

        // System.out.println(kB);
    }

    private void update() {
        // imgManager(playerPosition);
        map.tiles.forEach(e -> {
            if (e.collision == false)
                collided.add(e);
        });
        if (kB.W_pressed) {
            map.moveMap(0, Map.mapSpeed);
            collided.forEach(e -> {
                if ((this.getBounds().intersects(e.getBounds())))
                    map.moveMap(0, -Map.mapSpeed);
            });
        } else if (kB.A_pressed) {
            map.moveMap(Map.mapSpeed, 0);
            collided.forEach(e -> {
                if ((this.getBounds().intersects(e.getBounds())))
                    map.moveMap(-Map.mapSpeed, 0);
            });
        } else if (kB.S_pressed) {
            map.moveMap(0, -Map.mapSpeed);
            collided.forEach(e -> {
                if ((this.getBounds().intersects(e.getBounds())))
                    map.moveMap(0, Map.mapSpeed);
            });
        } else if (kB.D_pressed) {
            map.moveMap(-Map.mapSpeed, 0);
            collided.forEach(e -> {
                if ((this.getBounds().intersects(e.getBounds())))
                    map.moveMap(Map.mapSpeed, 0);
            });
        }
        collided.clear();
    }

    @Override
    public void run() {
        int playerPositionDelay = 0;

        while (gameThread != null) {
            update();
            playerPositionDelay++;
            if (playerPositionDelay == 5) {
                this.playerPosition = !this.playerPosition;
                playerPositionDelay = 0;
            }
            if (!kB.W_pressed && !kB.A_pressed && !kB.S_pressed && !kB.D_pressed)
                this.setIcon(this.playerIMGS.getImage("sub0"));
            try {
                Thread.sleep(16);
            } catch (Exception e) {
            }
            // ! LOG FOR VISUALIZE FPS
            // if (fps == 0) {
            // start = System.currentTimeMillis();
            // }
            // update();
            // imgManager(playerPosition);

            try {
                Thread.sleep((int) 1000 / 256);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ! LOG FOR VISUALIZE FPS
            // if (++fps == 60) {
            // System.out.println("time for 60 frame: " + (System.currentTimeMillis() -
            // start) + "ms");
            // fps = 0;
            // }
        }
    }

}
