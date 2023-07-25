package MiniGames.jumpGame;

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
public class Map_JumpGame extends JPanel {
    public BufferedImage skyImg;
    public BufferedImage grassImg;
    public BufferedImage dirtImg;
    public BufferedImage chestImg;
    public BufferedImage coinImg;
    public BufferedImage lavaImg;
    public BufferedImage stoneImg;
    public BufferedImage doorTopImg;
    public BufferedImage doorBottomImg;
    public BufferedImage botImg;

    public Game_JumpGame jumpGame;

    private static final int tileSize = 32;

    private ArrayList<String> mapTxt;
    private Vector<Tile_JumpGame> tiles;
    public Vector<Bot_JumpGame> bots;

    public int width;
    public int height;

    public Player_JumpGame player;
    public int coinTaked;

    public Vector<Tile_JumpGame> spawnTile;
    public Vector<Tile_JumpGame> playerSpawnTile;

    private boolean mute;

    public Map_JumpGame(int width, int height, MenuBar_JumpGame mb, Game_JumpGame escapeGame, String mapSchematic, boolean mute) {
        this.width = width;
        this.height = height;
        this.jumpGame = escapeGame;
        this.tiles = new Vector<>();
        this.spawnTile = new Vector<>();
        this.playerSpawnTile = new Vector<>();
        this.mute = mute;

        try {
            skyImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/sky.png"));
            grassImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/grass.png"));
            dirtImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/dirt.png"));
            chestImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/chest.png"));
            coinImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/coin.png"));
            lavaImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/lava.png"));
            stoneImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/stone.png"));
            doorTopImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/doorTop.png"));
            doorBottomImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/tilesIcon/doorBottom.png"));
            botImg = ImageIO.read(new File("./src/MiniGames/jumpGame/img/bot.png"));
            mapTxt = new FileIO_JumpGame(mapSchematic).read();
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
        this.player = new Player_JumpGame(32, 32 * 1, this.width, this.height, tiles, playerSpawnTile.get(new Random().nextInt(playerSpawnTile.size())), escapeGame, mute);

        generateBots();
        bots.forEach(this::add);
        this.add(player);
        tiles.forEach(this::add);
    }

    private void generateMap() {
        mapTxt.forEach(e -> {
            String[] line = e.split(" ");
            for (int col = 0; col < line.length; col++) {
                switch (line[col]) {
                case "." -> tiles.add(new Tile_JumpGame(skyImg, tileSize, false, this));
                case "f" -> tiles.add(new Tile_JumpGame(skyImg, tileSize, true, this));
                case "g" -> tiles.add(new Tile_JumpGame(grassImg, tileSize, true, this));
                case "d" -> tiles.add(new Tile_JumpGame(dirtImg, tileSize, true, this));
                case "c" -> tiles.add(new Tile_JumpGame(chestImg, tileSize, true, this));
                case "r" -> tiles.add(new Tile_JumpGame(stoneImg, tileSize, true, this));
                case "t" -> tiles.add(new Tile_JumpGame(stoneImg, tileSize, false, this));
                case "l" -> tiles.add(new Tile_JumpGame(lavaImg, tileSize, false, this));
                case "a" -> tiles.add(new Tile_JumpGame(skyImg, tileSize, false, true, false, this));
                case "p" -> tiles.add(new Tile_JumpGame(skyImg, tileSize, false, false, true, this));
                case "m" -> tiles.add(new specialTile_JumpGame(coinImg, tileSize, false, this, mute));
                case "x" -> tiles.add(new doorTile_JumpGame(doorTopImg, tileSize, false, this, mute));
                case "y" -> tiles.add(new doorTile_JumpGame(doorBottomImg, tileSize, false, this, mute));
                default -> tiles.add(new Tile_JumpGame(skyImg, tileSize, false, this));
                }
            }
        });

        for (int i = 0, k = 0; i < tiles.size() / (width / tileSize); i++) {
            for (int j = 0; j < tiles.size() / (height / tileSize); j++, k++) {
                tiles.get(k).setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                if (tiles.get(k).spawn)
                    spawnTile.add(tiles.get(k));
                if (tiles.get(k).spawnPlayer)
                    playerSpawnTile.add(tiles.get(k));
            }
        }
    }

    private void generateBots() {
        this.bots = new Vector<>();
        if (spawnTile.size() > 0)
            spawnTile.forEach(e -> this.bots.add(new Bot_JumpGame(botImg, 32, 32, this, tiles, e, mute)));
    }
}