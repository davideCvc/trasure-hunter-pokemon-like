package Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame {
    Integer end = 0;
    JLabel pointNumLabel;
    int frameWidth = 1000;
    int frameHeight = 800;
    int pointGame = 0;
    Boolean mute;
    Map map;

    public Game() {}

    public int startGame(Boolean mute) {
        setTitle("mappa");
        setSize(frameWidth, frameHeight);
        setResizable(false  );
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                end();
            }
        });
        setLocationRelativeTo(null);
        setLayout(null);
        try {
            this.setIconImage(ImageIO.read(new File("./src/MiniGames/EscapeGame/img/sub/sub0.png")));
            this.mute = mute;
            map = new Map("./src/Map/map/map1.txt", this);
            map.setSize(new Dimension(4096, 4096));
            add(map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        setVisible(true);

        this.setLayout(null);

        pointNumLabel = new JLabel("");
        pointNumLabel.setBackground(Color.YELLOW);
        pointNumLabel.setOpaque(true);
        pointNumLabel.setBounds(800, 0, 200, 100);
        pointNumLabel.setHorizontalAlignment(JLabel.CENTER);
        pointNumLabel.setVisible(true);
        this.setVisible(true);
        this.add(pointNumLabel);

        while (this.end == 0) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
        this.dispose();
        return end;
    }

    public void end() {
        this.map.tiles.forEach((e) -> {
            if (e instanceof SpecialTile) {
                SpecialTile SP = (SpecialTile) e;
                SP.kill();
            }
        });
        this.dispose();
        this.end = this.map.totalpoint == 0 ? -1 : this.map.totalpoint;
        System.out.println(end);
    }
}
