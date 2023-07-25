
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import MiniGames.ChestGame.Game_ChestGame;
import MiniGames.EscapeGame.Game_EscapeGame;
import MiniGames.Memory.Game_MemoryGame;
import MiniGames.instructionPanel.Instruction_Instruction;
import MiniGames.jumpGame.Game_JumpGame;

public class SpecialTile extends Tile implements Runnable {

    public Map map;
    private Thread tileThread;
    private boolean taked = false;
    BufferedImage floorIcon;
    ImageIcon npc;
    private static final int SLEEP_TIME = 60_000;

    private ImageIcon chestGame = null, escapeGame = null, jumpGame = null, memoryGame = null;

    private int ret;

    private boolean exit = false;

    public SpecialTile(ImageIcon npc, BufferedImage floorIcon, int tileSize, boolean collision, Map map) {
        super(npc, tileSize, collision, map);

        this.npc = npc;
        this.floorIcon = floorIcon;

        this.map = map;
        this.tileThread = new Thread(this);
        this.tileThread.start();
    }

    public <T extends Component, OT extends Component> boolean checkCollision(T element1, OT element2) {
        return (element1 == null || element2 == null) ? false : element1.getBounds().intersects(element2.getBounds());
    }

    public int starMiniGame() {
        ret = 0;
        this.map.game.setVisible(false);

        try {
            chestGame = new ImageIcon(ImageIO.read(new File("./src/MiniGames/ChestGame/img/broken-chest.png")));
            escapeGame = new ImageIcon(ImageIO.read(new File("./src/MiniGames/EscapeGame/img/bot.png")));
            jumpGame = new ImageIcon(ImageIO.read(new File("./src/MiniGames/jumpGame/img/sub/sub2.png")));
            memoryGame = new ImageIcon(ImageIO.read(new File("./src/MiniGames/Memory/img/cover.png")));
        } catch (Exception e) {
            System.err.println("instruction icon Err");
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        int randGame = new Random().nextInt(4);

        switch (randGame) {
        case 0 -> new Thread(() -> {
            new Instruction_Instruction("chest game instruction", chestGame, Game_ChestGame.instructions).startInstruction();
            ret = -1;
        }).start();
        case 1 -> new Thread(() -> {
            new Instruction_Instruction("escape game instruction", escapeGame, Game_EscapeGame.instructions).startInstruction();
            ret = -1;
        }).start();
        case 2 -> new Thread(() -> {
            new Instruction_Instruction("jump game instruction", jumpGame, Game_JumpGame.instructions).startInstruction();
            ret = -1;
        }).start();
        case 3 -> new Thread(() -> {
            new Instruction_Instruction("memory game instruction", memoryGame, Game_MemoryGame.instructions).startInstruction();
            ret = -1;
        }).start();
        default -> System.out.println("index out of bounds!");
        }

        do {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        } while (ret == 0);

        ret = 0;
        switch (randGame) {
        case 0 -> new Thread(() -> ret = new Game_ChestGame().startGame(map.mute)).start();
        case 1 -> new Thread(() -> ret = new Game_EscapeGame().startGame(map.mute)).start();
        case 2 -> new Thread(() -> ret = new Game_JumpGame().startGame(map.mute)).start();
        case 3 -> new Thread(() -> ret = new Game_MemoryGame().startGame(map.mute)).start();
        default -> System.out.println("index out of bounds!");
        }

        do {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        } while (ret == 0);

        this.map.game.setVisible(true);
        return ret;
    }

    private void changeIcon() {
        this.setIcon(!taked ? this.npc : new ImageIcon(this.floorIcon));
    }

    public void kill() {
        exit = true;
    }

    @Override
    public void run() {
        while (!exit) {
            if (this.taked || this.map.end != 0)
                this.tileThread = null;

            if (!taked && checkCollision(this, this.map.player)) {
                this.map.kB.W_pressed = false;
                this.map.kB.A_pressed = false;
                this.map.kB.S_pressed = false;
                this.map.kB.D_pressed = false;

                int value = starMiniGame();
                this.map.addPoint(value < 0 ? 0 : value);

                taked = true;

                changeIcon();
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            if (taked) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (Exception e) {
                }
                taked = false;
                changeIcon();
            }
        }
    }

}
