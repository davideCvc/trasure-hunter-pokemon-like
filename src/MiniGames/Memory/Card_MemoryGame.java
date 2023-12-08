package MiniGames.Memory;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Card
 */
public class Card_MemoryGame extends JButton {
    private int width;
    private int height;
    private Panel_MemoryGame mP;
    boolean hidden = true;

    private BufferedImage cover;
    public BufferedImage card;

    String name;

    public Card_MemoryGame(File cover, File card, String name, int width, int height, Panel_MemoryGame mP) {
        this.width = width;
        this.height = height;
        this.mP = mP;
        this.name = name;

        try {
            this.cover = ImageIO.read(cover);
            this.card = ImageIO.read(card);
        } catch (Exception e) {
            System.err.println("card img Error");
            System.err.println(e.getMessage());
        }

        setUp();
    }

    public void setUp() {
        this.setSize(new Dimension(this.width, this.height));
        this.setIcon(new ImageIcon(cover));
        this.setBorder(null);

        this.addActionListener(e -> {
            if (hidden)
                click();
        });
    }

    public void rotate() {
        this.hidden = !this.hidden;
        imgManager();
    }

    public void rotate(boolean hidden) {
        this.hidden = hidden;
        imgManager();
    }

    private void click() {
        mP.checkCard(this);
    }

    private void imgManager() {
        this.setIcon(new ImageIcon(this.hidden ? cover : card));
    }
}