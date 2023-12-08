package MiniGames.ChestGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

/**
 * ChestMenuBar
 */
public class MenuBar_ChestGame extends JMenuBar {
    public int width;
    public int height;

    public JLabel crowbarUsages;
    public JLabel openableChest;

    private static final int WIN = -1;

    public MenuBar_ChestGame(int width, int height) {
        this.width = width;
        this.height = height;

        this.setSize(new Dimension(this.width, this.height));
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(new Color(94, 66, 45));
        this.setBorder(BorderFactory.createEmptyBorder());

        generateLabel();
    }

    private void generateLabel() {
        this.crowbarUsages = new JLabel();
        this.crowbarUsages.setSize(new Dimension(this.width / 2, this.height));
        this.crowbarUsages.setHorizontalAlignment(JLabel.CENTER);
        this.crowbarUsages.setVerticalAlignment(JLabel.CENTER);
        this.crowbarUsages.setForeground(Color.YELLOW);
        this.add(this.crowbarUsages);

        this.openableChest = new JLabel();
        this.openableChest.setSize(new Dimension(this.width / 2, this.height));
        this.openableChest.setHorizontalAlignment(JLabel.CENTER);
        this.openableChest.setVerticalAlignment(JLabel.CENTER);
        this.openableChest.setForeground(Color.YELLOW);
        this.add(this.openableChest);

        try {
            crowbarUsages.setFont(createGameFont(13));
            openableChest.setFont(createGameFont(13));
        } catch (Exception e) {
        }
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    protected void setCrowbarInfo(int value) {
        this.crowbarUsages.setText(value == WIN ? "TESORO" : "utilizzi rimanenti: " + value);
    }

    protected void setChestInfo(int value, int BRAKE_HIT) {
        this.openableChest.setText(value == WIN ? "TROVATO" : "chest che puoi aprire: " + (double) value / BRAKE_HIT);
    }
}
