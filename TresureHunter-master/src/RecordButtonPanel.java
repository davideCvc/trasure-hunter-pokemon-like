import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * RecordButtonPanel
 */
public class RecordButtonPanel extends JPanel {
    RecordFrame recordFrame;
    JButton clearBtn, homeBtn;
    int width, height;
    int x, y;

    public RecordButtonPanel(RecordFrame recordFrame, int height, int x, int y) {
        this.recordFrame = recordFrame;
        this.width = recordFrame.getWidth();
        this.height = height;
        this.x = x;
        this.y = y;
        setUp();
    }

    private void setUp() {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.setOpaque(false);

        this.homeBtn = new JButton("Home");
        this.homeBtn.setBounds((((width / 4) * 1) - 150) - x, ((height / 2) - 50), 300, 100);
        this.homeBtn.setForeground(new Color(255, 215, 0));
        this.homeBtn.setHorizontalTextPosition(JButton.CENTER);
        this.homeBtn.setVerticalTextPosition(JButton.CENTER);
        this.homeBtn.addActionListener(e -> {
            home();
        });

        this.clearBtn = new JButton("Clear");
        this.clearBtn.setBounds((((width / 4) * 3) - 150) - x, ((height / 2) - 50), 300, 100);
        this.clearBtn.setForeground(new Color(255, 215, 0));
        this.clearBtn.setHorizontalTextPosition(JButton.CENTER);
        this.clearBtn.setVerticalTextPosition(JButton.CENTER);
        this.clearBtn.addActionListener(e -> {
            try {
                this.recordFrame.clearValue();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });

        try {
            homeBtn.setFont(createGameFont(15));
            clearBtn.setFont(createGameFont(15));
            homeBtn.setIcon(new ImageIcon(ImageIO.read(new File("./img/wood1.png"))));
            clearBtn.setIcon(new ImageIcon(ImageIO.read(new File("./img/wood2.png"))));
        } catch (Exception e) {
        }

        this.add(this.homeBtn);
        this.add(this.clearBtn);
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    private void home() {
        this.recordFrame.exitOperation();
    }
}