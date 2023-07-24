import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * GamePanel
 */
public class GameMainPanel extends JFrame {
    private static final String ICON_URL = "./img/icon.png";
    private static final String BACK_URL = "./img/back.gif";

    private BufferedImage icon;
    private ImageIcon back;

    private JTextField nameArea;
    private JButton newMatchBtn, recordsBtn, volumeBtn;
    private BufferedImage wood1, wood2, wood3;

    private static final int BTN_WIDTH = 300;
    private static final int BTN_HEIGHT = 100;

    private boolean audioActive = true;

    private AudioManager song;

    private int ret = 0;

    public GameMainPanel() {
        setUp();
        this.song.changeState();
    }

    private void setUp() {
        this.setTitle("Treasure Hunter");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        try {
            // ? set music
            this.song = new AudioManager(new File("./sound/exploration.wav"), 1);

            // ? get buttons icons
            this.wood1 = ImageIO.read(new File("./img/wood1.png"));
            this.wood2 = ImageIO.read(new File("./img/wood2.png"));
            this.wood3 = ImageIO.read(new File("./img/wood3.png"));

            // ? set icon and background
            this.icon = ImageIO.read(new File(ICON_URL));
            this.back = new ImageIcon(BACK_URL);
            this.setIconImage(this.icon);
            this.setContentPane(new ImagePanel(this.back));
        } catch (Exception e) {
            System.err.println("back img Err");
            System.err.println(e.getMessage());
            this.setBackground(Color.BLUE);
        }

        addButtons();

        this.setVisible(true);
    }

    private void addButtons() {
        // ? title
        JLabel title = new JLabel("Treasure Hunter");
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds((this.getWidth() / 2) - (800 / 2), 50, 800, 150);
        try {
            title.setFont(createGameFont(50));
        } catch (Exception e) {
        }
        this.add(title);

        // ? name field
        this.nameArea = new JTextField("> inserisci nome <");
        this.nameArea.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.DARK_GRAY));
        this.nameArea.setHorizontalAlignment(JTextField.CENTER);
        this.nameArea.setBounds((this.getWidth() / 2) - (BTN_WIDTH / 2), 220, BTN_WIDTH, 50);
        try {
            this.nameArea.setFont(createGameFont(15));
        } catch (Exception e) {
        }
        this.add(this.nameArea);

        // ? match btn
        this.newMatchBtn = new JButton("Nuova Partita");
        setUpBtn(newMatchBtn, 330);
        this.newMatchBtn.setIcon(new ImageIcon(wood1));
        this.newMatchBtn.addActionListener(e -> {
            if (!this.nameArea.getText().trim().isEmpty()) {
                if (checkNameValidity(this.nameArea.getText().trim())) {

                    new Thread(() -> {
                        ChangeVisibility();
                        if (audioActive)
                            this.song.changeState();

                        int point = startGame(this.audioActive);
                        writeValue(this.nameArea.getText().trim(), point <= 0 ? 0 : point);

                        ChangeVisibility();
                        if (audioActive)
                            this.song.changeState();
                    }).start();

                } else {
                    this.nameArea.setText("nome invalido!");
                }
            }
        });
        this.add(newMatchBtn);

        // ? records btn
        this.recordsBtn = new JButton("Mostra i Punteggi");
        setUpBtn(recordsBtn, 630, 550);
        this.recordsBtn.setIcon(new ImageIcon(wood2));
        this.recordsBtn.addActionListener(e -> {
            ChangeVisibility();
            new RecordFrame(this, "./records/records.txt");
        });
        this.add(recordsBtn);

        // ? volume btn
        this.volumeBtn = new JButton("Disattiva Audio");
        setUpBtn(volumeBtn, 50, 550);
        this.volumeBtn.setIcon(new ImageIcon(wood3));
        this.volumeBtn.addActionListener(e -> {
            ChangeAudioState();
            this.song.changeState();
        });
        this.add(volumeBtn);
    }

    private void writeValue(String name, int value) {
        try {
            new FileIO("./records/records.txt").append(name + ":" + value);
        } catch (IOException e) {
        }
    }

    private void setUpBtn(JButton btn, int y) {
        setUpBtn(btn, (this.getWidth() / 2) - (BTN_WIDTH / 2), y);
    }

    private void setUpBtn(JButton btn, int x, int y) {
        btn.setBounds(x, y, BTN_WIDTH, BTN_HEIGHT);
        try {
            btn.setFont(createGameFont(15));
        } catch (Exception e) {
        }
        btn.setForeground(new Color(255, 215, 0));
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.CENTER);
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    private boolean checkNameValidity(String name) {
        // ? check name lenght
        if (name.length() > 5 || name.length() <= 1)
            return false;

        // ? check for invalid characters
        String bannedChars = ";:/\\!$£&%()[]{}@#^'?";
        for (String c : bannedChars.split(""))
            if (name.contains(c))
                return false;

        return true;
    }

    private int startGame(boolean audio) {

        new Thread(() -> ret = new Game().startGame(audio)).start();
        ret = 0;
        do {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        } while (ret == 0);
        return ret;
    }

    public void ChangeVisibility(boolean visible) {
        this.setVisible(visible);
    }

    public void ChangeVisibility() {
        ChangeVisibility(!this.isVisible());
    }

    public void ChangeAudioState(boolean state) {
        this.audioActive = state;
        this.volumeBtn.setText(state ? "Disattiva audio" : "Attiva audio");
    }

    public void ChangeAudioState() {
        ChangeAudioState(!this.audioActive);
    }
}