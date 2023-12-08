package MiniGames.ChestGame;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Chest
 */
public class Chest_ChestGame extends JButton {
    public int width;
    public int height;
    public int x;
    public int y;

    public int type = 0; // ? 0=empty 1=treasure -1=crowbar

    private Panel_ChestGame chestPanel;
    private MenuBar_ChestGame chestMenuBar;
    private Game_ChestGame chestGame;
    public int hit = 0;
    public static final int BRAKE_HIT = 2;

    public boolean mute;

    public Chest_ChestGame(int width, int height, int x, int y, Panel_ChestGame chestPanel, MenuBar_ChestGame chestMenuBar, Game_ChestGame chestGame, boolean mute) {
        super();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.chestPanel = chestPanel;
        this.chestMenuBar = chestMenuBar;
        this.chestGame = chestGame;
        this.mute = mute;
        setup();
    }

    private void setup() {
        this.setLocation(this.x, this.y);
        this.setSize(new Dimension(this.width, this.height));
        this.setBorder(BorderFactory.createEmptyBorder());

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                update();
            }
        });

        imgManager();
    }

    private void update() {
        if (this.chestPanel.usage > 0 && this.hit < BRAKE_HIT) {
            this.chestPanel.usage--;
            this.hit++;

            if (mute)
                new Thread(() -> new Sonoro_ChestGame(new File("./src/MiniGames/ChestGame/sound/woodBreak2.wav"), 0)).start();
            imgManager();
        }
    }

    private void imgManager() {
        switch (this.hit) {
        case 0 -> this.setIcon(new ImageIcon("./src/MiniGames/ChestGame/img/chest.png"));
        case 1 -> this.setIcon(new ImageIcon("./src/MiniGames/ChestGame/img/broken-chest.png"));
        case 2 -> {
            switch (this.type) {
            case 0 -> this.setIcon(new ImageIcon("./src/MiniGames/ChestGame/img/empty-chest.png"));
            case 1 -> {
                this.setIcon(new ImageIcon("./src/MiniGames/ChestGame/img/treasure-chest.png"));
                this.chestPanel.usage = -1;
                new Thread(() -> {
                    // JOptionPane.showMessageDialog(null, "tesoro trovato!");
                    this.chestGame.win(5);
                }).start();
            }
            case -1 -> {
                this.setIcon(new ImageIcon("./src/MiniGames/ChestGame/img/crowbar-chest.png"));
                this.chestPanel.usage += 4;
            }
            }
        }
        default -> System.out.println("err");
        }

        if (this.chestPanel.usage == 0)
            new Thread(() -> {
                // JOptionPane.showMessageDialog(null, "nada!");
                this.chestGame.fail();
            }).start();

        this.chestMenuBar.setCrowbarInfo(this.chestPanel.usage);
        this.chestMenuBar.setChestInfo(this.chestPanel.usage, BRAKE_HIT);
    }

    public void setEmptyChest() {
        this.type = 0;
    }

    public void setTreasureChest() {
        this.type = 1;
    }

    public void setCrowbarChest() {
        this.type = -1;
    }

    public boolean isEmptyChest() {
        return this.type == 0;
    }

    public boolean isTreasureChest() {
        return this.type == 1;
    }

    public boolean isCrowbarChest() {
        return this.type == -1;
    }
}