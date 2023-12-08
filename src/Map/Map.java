package Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Timer;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
// import java.util.Timer;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Map extends JPanel {
    Player player;
    // IMAGE FOR TILE
    private BufferedImage dirtImg;
    private BufferedImage bushImg;
    private BufferedImage wallImg;
    private BufferedImage scaleImg;
    private BufferedImage archImg;
    private BufferedImage treeImg;
    private BufferedImage floorImg;
    private BufferedImage bedImg;
    private BufferedImage verticalWallImgLeft;
    private BufferedImage verticalWallImgRight;
    private ImageIcon npc;
    private static final String BACK_URL = "./src/Map/gif/npc.gif";
    // SIZE ITEM MAP
    public static final int tileSize = 32;
    public static final int mapSize = 128;
    public static final int mapSpeed = 4;
    // CHORDS
    public int mapX = 0;
    public int mapY = 0;
    public int playerX = 0;
    public int playerY = 0;
    // CHORD FOR CHECK THE BORDER THE MAP
    private int choordBorderL;
    private int choordBorderR;
    private int choordBorderT;
    private int choordBorderB;
    // DATA STRUCT FOR MANAGMENTE THE MAP
    public KeyBoard kB = new KeyBoard();
    private ArrayList<String> mapTxt;
    public Vector<Tile> tiles;
    public int end = 0;
    boolean mute;
    Game game;
    JLabel pointNumLabel;
    Timer timer;
    JLabel timerLab;
    int second, minute;
    String ddSecond, ddMinute;
    Font font1 = new Font("Arial", Font.PLAIN, 70);
    DecimalFormat dFormat = new DecimalFormat("00");
    public int totalpoint = 0;

    public Map(String mapSchematic, Game game) {
        this.tiles = new Vector<>();
        this.mute = game.mute;
        this.game = game;


        setPreferredSize(new Dimension(mapSize * tileSize, mapSize * tileSize));
        System.out.println("size: " + mapSize * tileSize + " : " + mapSize * tileSize);
        pointNumLabel = new JLabel("");
        pointNumLabel.setBackground(Color.YELLOW);
        pointNumLabel.setOpaque(true);
        pointNumLabel.setBounds(775, 10, 200, 50);
        pointNumLabel.setHorizontalAlignment(JLabel.CENTER);
        pointNumLabel.setVisible(true);
        addPoint(0);
        pointNumLabel.setHorizontalTextPosition(JLabel.CENTER);
        pointNumLabel.setVerticalTextPosition(JLabel.CENTER);

        timerLab = new JLabel("");
        timerLab.setBackground(Color.BLACK);
        timerLab.setOpaque(true);
        timerLab.setBounds(10, 10, 200, 50);
        timerLab.setHorizontalAlignment(JLabel.CENTER);
        timerLab.setForeground(Color.YELLOW);
        timerLab.setFont(font1);
        timerLab.setHorizontalTextPosition(JLabel.CENTER);
        timerLab.setVerticalTextPosition(JLabel.CENTER);

        timerLab.setText("10:00");
        second = 0;
        minute = 10;
        countdownTimer();
        timer.start();

        try {
            pointNumLabel.setFont(createGameFont(15));
            timerLab.setFont(createGameFont(20));
            pointNumLabel.setIcon(new ImageIcon(ImageIO.read(new File("./src/Map/img/wood1.png"))));
            timerLab.setIcon(new ImageIcon(ImageIO.read(new File("./src/Map/img/wood2.png"))));
        } catch (Exception e) {
        }

        game.add(pointNumLabel);
        game.add(timerLab);

        // Countdown Timer
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(kB);
        setLayout(null);

        // READING THE IMAGE FOR THE TILE
        try {
            dirtImg = ImageIO.read(new File("./src/Map/img/dirt.png"));
            wallImg = ImageIO.read(new File("./src/Map/img/wallO.png"));
            bushImg = ImageIO.read(new File("./src/Map/img/bush.png"));
            verticalWallImgLeft = ImageIO.read(new File("./src/Map/img/vWallL.png"));
            scaleImg = ImageIO.read(new File("./src/Map/img/scale.png"));
            archImg = ImageIO.read(new File("./src/Map/img/arch.png"));
            treeImg = ImageIO.read(new File("./src/Map/img/tree.png"));
            verticalWallImgRight = ImageIO.read(new File("./src/Map/img/vWallR.png"));
            floorImg = ImageIO.read(new File("./src/Map/img/floor.png"));
            bedImg = ImageIO.read(new File("./src/Map/img/bed.png"));
            this.npc = new ImageIcon(BACK_URL);

            mapTxt = new FileIO(mapSchematic).read();
        } catch (IOException e) {
            System.err.println("back img Err");
            System.err.println("File Error");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        player = new Player(this);
        player.setBounds(1000 + mapX, 1000 + mapY, 30, 30);
        add(player);
        printMap();
        centerMap();
    }

    // MOVEMENT OF THE MAP AND MANAGMENT OF THE MOVEMENT OF THE PLAYER AND/OR THE MAP
    public void moveMap(int x, int y) {
        if (!(mapX + x <= -3113) && !(mapX + x >= -1)) {
            if (choordBorderL <= 0 && choordBorderR <= 0) {
                choordBorderL -= x;
                choordBorderR -= -x;
                this.mapX += x;
                setLocation(this.mapX, this.mapY);
            }
        }
        if (!(mapY + y >= 1) && !(mapY + y <= -3335)) {
            if (choordBorderT <= 0 && choordBorderB <= 0) {
                choordBorderT -= y;
                choordBorderB -= -y;
                this.mapY += y;
                setLocation(this.mapX, this.mapY);
            }
        }
        playerX += x;
        playerY += y;
        choordBorderT += y;
        choordBorderB += -y;
        choordBorderL += x;
        choordBorderR += -x;
        player.imgManager(x, y);
        player.setLocation(((1000 / 2) - (32 / 2)) - playerX, ((750 / 2) - (32 / 2)) - playerY);
        setLocation(this.mapX, this.mapY);
    }

    public void countdownTimer() {
        timer = new javax.swing.Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                second--;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                timerLab.setText(ddMinute + ":" + ddSecond);

                if (second == -1) {
                    second = 59;
                    minute--;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    timerLab.setText(ddMinute + ":" + ddSecond);
                }
                if (minute == 0 && second == 0) {
                    timer.stop();
                    // game.end = totalpoint == 0 ? -1 : totalpoint;
                    game.end();
                }
            }
        });
    }

    // READING THE FILE AND PRINTING THE VARIOUS TILE CORRESPOUNDING TO THE VARIOUS
    // LETTERS/SYMBOLS
    private void printMap() {
        mapTxt.forEach(e -> {
            new ArrayList<>(Arrays.asList(e.split(" "))).forEach(c -> {
                switch (c) {
                case "." -> tiles.add(new Tile(dirtImg, tileSize, true, this));
                case "@" -> tiles.add(new Tile(bushImg, tileSize, false, this));
                case "w" -> tiles.add(new Tile(wallImg, tileSize, false, this));
                case "s" -> tiles.add(new Tile(scaleImg, tileSize, true, this));
                case "v" -> tiles.add(new Tile(verticalWallImgLeft, tileSize, false, this));
                case "r" -> tiles.add(new Tile(verticalWallImgRight, tileSize, false, this));
                case "a" -> tiles.add(new Tile(archImg, tileSize, true, this));
                case "t" -> tiles.add(new Tile(treeImg, tileSize, true, this));
                case "f" -> tiles.add(new Tile(floorImg, tileSize, true, this));
                case "l" -> tiles.add(new Tile(bedImg, tileSize, false, this));
                case "n" -> tiles.add(new SpecialTile(npc, floorImg, tileSize, true, this));

                default -> tiles.add(new Tile(dirtImg, tileSize, true, this));
                }
            });
        });
        mapGen();
        tiles.forEach(this::add);
    }

    private void centerMap() {
        moveMap(-(mapSize * tileSize) / 3);
    }

    public void mapGen() {
        for (int i = 0, k = 0; i < mapSize; i++)
            for (int j = 0; j < mapSize; j++, k++)
                tiles.get(k).setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
    }

    public void moveMap(int xy) {
        moveMap(xy, xy);
        setLocation(this.mapX, this.mapY);
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    public void addPoint(int value) {
        this.totalpoint += value;
        this.pointNumLabel.setText("punti: " + totalpoint);
    }
}