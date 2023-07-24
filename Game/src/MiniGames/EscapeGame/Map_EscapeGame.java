package MiniGames.EscapeGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * ChestPanel
 */
public class Map_EscapeGame extends JPanel {
    private BufferedImage dirtImg;
    private BufferedImage bushImg;
    private BufferedImage specialImg;
    private BufferedImage botImg;

    public Game_EscapeGame escapeGame;

    private static final int tileSize = 32;
    private static final int BOT_NUMBER = 3;

    private ArrayList<String> mapTxt;
    private Vector<Tile_EscapeGame> tiles;
    public Vector<Bot_EscapeGame> bots;

    public int width;
    public int height;

    public Player_EscapeGame player;
    public int specialtileN;
    public int specialtileNFisso;

    public Vector<Tile_EscapeGame> spawnTile;
    public Vector<Tile_EscapeGame> playerSpawnTile;

    public boolean mute;

    public Map_EscapeGame(int width, int height, MenuBar_EscapeGame mb, Game_EscapeGame escapeGame, String mapSchematic, boolean mute) {
        this.width = width;
        this.height = height;
        this.escapeGame = escapeGame;
        this.tiles = new Vector<>();
        this.spawnTile = new Vector<>();
        this.playerSpawnTile = new Vector<>();
        this.mute = mute;

        try {
            dirtImg = ImageIO.read(new File("./src/MiniGames/EscapeGame/img/tilesIcon/dirt.png"));
            bushImg = ImageIO.read(new File("./src/MiniGames/EscapeGame/img/tilesIcon/bush.png"));
            specialImg = ImageIO.read(new File("./src/MiniGames/EscapeGame/img/tilesIcon/special.png"));
            botImg = ImageIO.read(new File("./src/MiniGames/EscapeGame/img/bot.png"));
            mapTxt = new FileIO_EscapeGame(mapSchematic).read();
        } catch (IOException e) {
            System.err.println("File Error");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        this.setLayout(null);
        this.setBounds(0, 50, this.width, this.height);

        generateMap();
        generateBots();
        this.player = new Player_EscapeGame(32, 32, 800, 800, tiles, playerSpawnTile.get(new Random().nextInt(playerSpawnTile.size())), escapeGame);

        bots.forEach(this::add);
        this.add(player);
        tiles.forEach(this::add);
    }

    private void generateMap() {
        mapTxt.forEach(e -> {
            String[] line = e.split(" ");
            for (int col = 0; col < line.length; col++) {
                switch (line[col]) {
                case "." -> tiles.add(new Tile_EscapeGame(dirtImg, tileSize, false, this));
                case "b" -> tiles.add(new Tile_EscapeGame(bushImg, tileSize, true, this));
                case "a" -> tiles.add(new Tile_EscapeGame(dirtImg, tileSize, false, true, false, this));
                case "p" -> tiles.add(new Tile_EscapeGame(dirtImg, tileSize, false, false, true, this));
                case "s" -> {
                    tiles.add(new SpecialTile_EscapeGame(specialImg, dirtImg, tileSize, false, this.escapeGame, mute));
                    specialtileN++;
                }
                default -> tiles.add(new Tile_EscapeGame(dirtImg, tileSize, false, this));
                }
            }
        });

        this.specialtileNFisso = this.specialtileN;

        for (int i = 0, k = 0; i < tiles.size() / (width / tileSize); i++) {
            for (int j = 0; j < tiles.size() / (width / tileSize); j++, k++) {
                tiles.get(k).setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                if (tiles.get(k).spawn)
                    spawnTile.add(tiles.get(k));
                if (tiles.get(k).spawnPlayer)
                    playerSpawnTile.add(tiles.get(k));
            }
        }

        new Thread(() -> {
            while (this.escapeGame.end == 0) {
                if (this.specialtileN == 0)
                    this.escapeGame.win(this.specialtileNFisso);
                this.escapeGame.mb.setInfo(this.specialtileN);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    private void generateBots() {
        this.bots = new Vector<>();
        if (spawnTile.size() > 0) {
            for (int i = 0; i < BOT_NUMBER; i++)
                this.bots.add(new Bot_EscapeGame(botImg, 32, 32, this, tiles, spawnTile.get(new Random().nextInt(spawnTile.size())), mute));
        }
    }
}