package MiniGames.jumpGame;

import java.awt.BorderLayout;
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
public class Game_JumpGame {
    public static final ArrayList<Slide_Instruction> instructions = new ArrayList<>(Arrays.asList(
        new Slide_Instruction(new File("./src/MiniGames/jumpGame/instructionIMG/img1.png"), "per muoverti usa i tasti WASD e per saltare premi la barra spaziatrice"),
        new Slide_Instruction(new File("./src/MiniGames/jumpGame/instructionIMG/img2.png"), "nella mappa troverai blocchi come questi sui quali puoi salire, o forse no? "),
        new Slide_Instruction(new File("./src/MiniGames/jumpGame/instructionIMG/img3.png"), "stai attento che la lava è calda!"),
        new Slide_Instruction(new File("./src/MiniGames/jumpGame/instructionIMG/img4.png"), "troverai tante monete luccicanti, prendine il più possibile"),
        new Slide_Instruction(new File("./src/MiniGames/jumpGame/instructionIMG/img5.png"), "stai attento a non farti acciuffare da lui"),
        new Slide_Instruction(new File("./src/MiniGames/jumpGame/instructionIMG/img6.png"), "alla fine della mappa troverai una porta con la quale tornare indietro")
        ));

    private JFrame gf;
    Map_JumpGame em;
    MenuBar_JumpGame mb;

    public int end;

    private static final int MAP_TXT_NUMBER = 2;

    public Game_JumpGame() {}

    public int startGame(boolean mute) {
        createFrame();

        // ? barra delle info
        mb = new MenuBar_JumpGame(800, 50);
        this.gf.add(mb);

        // ? pannello con chest
        this.em = new Map_JumpGame(175 * 32, 15 * 32, mb, this, "./src/MiniGames/jumpGame/map/map"+new Random().nextInt(MAP_TXT_NUMBER)+".txt", mute);
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
            this.gf.setIconImage(new ImageIcon(ImageIO.read(new File("./src/MiniGames/jumpGame/img/sub/sub1.png"))).getImage());
        } catch (IOException e) {
        }
        this.gf.setSize(new Dimension(810, 565));
        this.gf.setLayout(new BorderLayout());
        this.gf.setLocationRelativeTo(null);
        this.gf.setLayout(null);
        this.gf.setResizable(false);
        
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