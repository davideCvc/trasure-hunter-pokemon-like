package MiniGames.jumpGame;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Tail
 */
public class Tile_JumpGame extends JLabel {
    static int tileN = 0;
    int currentTileN;
    JPanel p;
    int tileSize;
    boolean collision;
    boolean spawn;
    boolean spawnPlayer;

    BufferedImage buttonIcon;

    public Tile_JumpGame(BufferedImage buttonIcon, JPanel p) {
        this.buttonIcon = buttonIcon;
        this.tileSize = 64;
        this.collision = false;
        this.spawn = false;
        this.spawnPlayer = false;
        this.p = p;
        Tile_JumpGame.tileN++;
        this.currentTileN = Tile_JumpGame.tileN;

        setIcon(new ImageIcon(buttonIcon));
        setSize(new Dimension(tileSize, tileSize));
    }

    public Tile_JumpGame(BufferedImage buttonIcon, int tileSize, JPanel p) {
        this.buttonIcon = buttonIcon;
        this.tileSize = tileSize;
        this.collision = false;
        this.spawn = false;
        this.p = p;
        Tile_JumpGame.tileN++;
        this.currentTileN = Tile_JumpGame.tileN;

        setIcon(new ImageIcon(buttonIcon));
        setSize(new Dimension(tileSize, tileSize));
    }

    public Tile_JumpGame(BufferedImage buttonIcon, int tileSize, boolean collision, JPanel p) {
        this.buttonIcon = buttonIcon;
        this.tileSize = tileSize;
        this.collision = collision;
        this.spawn = false;
        this.p = p;
        Tile_JumpGame.tileN++;
        this.currentTileN = Tile_JumpGame.tileN;

        setIcon(new ImageIcon(buttonIcon));
        setSize(new Dimension(tileSize, tileSize));
    }

    public Tile_JumpGame(BufferedImage buttonIcon, int tileSize, boolean collision, boolean spawn, boolean spawnPlayer,
            JPanel p) {
        this.buttonIcon = buttonIcon;
        this.tileSize = tileSize;
        this.collision = collision;
        this.spawn = spawn;
        this.spawnPlayer = spawnPlayer;
        this.p = p;
        Tile_JumpGame.tileN++;
        this.currentTileN = Tile_JumpGame.tileN;

        setIcon(new ImageIcon(buttonIcon));
        setSize(new Dimension(tileSize, tileSize));
    }

    @Override
    public String toString() {
        return "Tile [currentTileN=" + currentTileN + ", p=" + p + ", tileSize=" + tileSize + ", collision=" + collision
                + ", spawn=" + spawn + ", buttonIcon=" + buttonIcon + "]";
    }
}