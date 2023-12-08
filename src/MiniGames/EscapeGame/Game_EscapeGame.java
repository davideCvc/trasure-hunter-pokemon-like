package MiniGames.EscapeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import MiniGames.instructionPanel.Slide_Instruction;

/**
 * ChestGame
 */
public class Game_EscapeGame{
    public static final ArrayList<Slide_Instruction> instructions = new ArrayList<>(Arrays.asList(
        new Slide_Instruction(new File("./src/MiniGames/EscapeGame/instructionIMG/img1.png"), "per muoverti usa i tasti WASD"),
        new Slide_Instruction(new File("./src/MiniGames/EscapeGame/instructionIMG/img2.png"), "nella mappa ci saranno dei fantasmi che ti inseguono"),
        new Slide_Instruction(new File("./src/MiniGames/EscapeGame/instructionIMG/img3.png"), "l'obbiettico è prendere tutte le spade come questa senza farsi acciuffare dai fantasmi")
        ));

    public int end = 0;
    private JFrame gf;

    Map_EscapeGame em;
    MenuBar_EscapeGame mb;

    private static final int MAP_TXT_NUMBER = 4;

    public Game_EscapeGame() {}

    public int startGame(boolean mute) {
        createFrame();

        // ? barra delle info
        mb = new MenuBar_EscapeGame(800, 50);
        this.gf.add(mb);

        // ? pannello con chest
        em = new Map_EscapeGame(800, 800, mb, this, "./src/MiniGames/EscapeGame/map/map" + (new Random().nextInt(MAP_TXT_NUMBER) + 1) + ".txt", mute);
        this.gf.add(em);

        this.gf.setVisible(true);

        // ? exit
        while (this.end == 0) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }

        this.gf.dispose();
        return this.end;
    }

    private void createFrame() {
        this.gf = new JFrame("Chest Game");
        try {
            this.gf.setIconImage(new ImageIcon(ImageIO.read(new File("./src/MiniGames/EscapeGame/img/bot.png"))).getImage());
        } catch (IOException e) {
            System.err.println("icon Error");
            System.err.println(e.getMessage());
        }
        this.gf.setSize(new Dimension(810, 886));
        this.gf.setLayout(new BorderLayout());
        this.gf.setLocationRelativeTo(null);
        this.gf.setLayout(null);
        this.gf.setResizable(false);
        this.gf.setBackground(Color.BLACK);

        this.gf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitOperation();
            }
        });
    }

    public void win(int value) {
        this.end = value;
    }
    protected void exitOperation() {
        fail();
        this.gf.dispose();
    }

    public void fail() {
        this.end = -1;
    }
}