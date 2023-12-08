package MiniGames.EscapeGame;

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
public class MenuBar_EscapeGame extends JMenuBar {
    public int width;
    public int height;

    public JLabel infoBar;

    // private static final int WIN = -1;

    public MenuBar_EscapeGame(int width, int height) {
        this.width = width;
        this.height = height;

        this.setSize(new Dimension(this.width, this.height));
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(new Color(94, 66, 45));
        this.setBorder(BorderFactory.createEmptyBorder());

        generateLabel();
    }

    private void generateLabel() {
        this.infoBar = new JLabel();
        this.infoBar.setSize(new Dimension(this.width, this.height));
        this.infoBar.setFont(new Font("arial", Font.BOLD, 20));
        this.infoBar.setHorizontalAlignment(JLabel.CENTER);
        this.infoBar.setVerticalAlignment(JLabel.CENTER);
        this.infoBar.setForeground(Color.YELLOW);
        this.add(this.infoBar);

        try {
            infoBar.setFont(createGameFont(13));
        } catch (Exception e) {
        }
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    protected void setInfo(int value) {
        this.infoBar.setText("Spade rimanenti: " + value);
    }
}