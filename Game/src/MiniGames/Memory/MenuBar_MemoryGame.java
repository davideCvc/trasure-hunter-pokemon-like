package MiniGames.Memory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * MemoryBar
 */
public class MenuBar_MemoryGame extends JPanel {
    public int width;
    public int height;

    public JLabel infoBar;
    public JLabel openableChest;

    int value = 0;

    public MenuBar_MemoryGame(int width, int height) {
        this.width = width;
        this.height = height;

        setUp();
        generateLabel();
        setInfo(value);
    }

    private void setUp() {
        this.setSize(new Dimension(this.width, this.height));
        this.setBackground(new Color(94, 66, 45));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    private void generateLabel() {
        this.infoBar = new JLabel();
        this.infoBar.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoBar.setVerticalAlignment(SwingConstants.CENTER);
        this.infoBar.setSize(new Dimension(this.width, this.height));
        this.infoBar.setFont(new Font("arial", Font.BOLD, 20));
        this.infoBar.setForeground(Color.YELLOW);
        this.add(this.infoBar, BorderLayout.CENTER);
        try {
            this.infoBar.setFont(createGameFont(13));
        } catch (Exception e) {
        }
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    protected void setInfo(int value) {
        this.value = value;
        this.infoBar.setText("Coppie combinate: " + value + "/8");
    }

    protected void setInfo(String str) {
        this.infoBar.setText(str);
    }
}