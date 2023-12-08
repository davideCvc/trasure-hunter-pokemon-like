package Map;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tile extends JLabel {
    int tileSize;
    static int tileN = 0;
    int currentTileN;
    JPanel p;
    public boolean collision;
    BufferedImage buttonIcon;
    ImageIcon npc;

    public Tile(BufferedImage buttonIcon, int tileSize, boolean collision, JPanel p) {
        this.p = p;
        this.tileSize = tileSize;
        this.collision = collision;
        this.buttonIcon = buttonIcon;
        Tile.tileN++;
        this.currentTileN = Tile.tileN;
        setIcon(new ImageIcon(buttonIcon));
        setPreferredSize(new Dimension(tileSize, tileSize));
    }
    public Tile(ImageIcon npc, int tileSize, boolean collision, JPanel p) {
        this.p = p;
        this.tileSize = tileSize;
        this.collision = collision;
        this.npc = npc;
        Tile.tileN++;
        this.currentTileN = Tile.tileN;
        setIcon(npc);
        setPreferredSize(new Dimension(tileSize, tileSize));
    }
}