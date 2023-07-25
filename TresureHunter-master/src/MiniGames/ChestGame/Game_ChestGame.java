package MiniGames.ChestGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import MiniGames.instructionPanel.Slide_Instruction;

/**
 * ChestGame
 */
public class Game_ChestGame {
    public static final ArrayList<Slide_Instruction> instructions = new ArrayList<>(Arrays.asList(
        new Slide_Instruction(new File("./src/MiniGames/ChestGame/instructionIMG/img1.png"), "appena inizierà il gioco ti troverai difronte diverse casse come questa!"),
        new Slide_Instruction(new File("./src/MiniGames/ChestGame/instructionIMG/img2.png"), "se la colpisci 1 volta la romperai e diventerà come questa"),
        new Slide_Instruction(new File("./src/MiniGames/ChestGame/instructionIMG/img3.png"), "se la colpisci dinuovo potresti essere sfortunato e trovare una cassa vuota"),
        new Slide_Instruction(new File("./src/MiniGames/ChestGame/instructionIMG/img4.png"), "potresti avere un pò di fortuna e trovare un piede di porco, permetterà di rompere 2 casse in più"),
        new Slide_Instruction(new File("./src/MiniGames/ChestGame/instructionIMG/img5.png"), "il tuo obbiettivo è trovare il tesoro!")
        ));

    public int end = 0;
    private JFrame gf;

    public Game_ChestGame() {}

    public int startGame(boolean mute) {
        this.gf = new JFrame("Chest Game");
        this.gf.setSize(new Dimension(810, 886));
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

        try {
            this.gf.setIconImage(new ImageIcon(ImageIO.read(new File("./src/MiniGames/ChestGame/img/broken-chest.png"))).getImage());
        } catch (IOException e) {
        }

        // ? barra delle info
        MenuBar_ChestGame cmb = new MenuBar_ChestGame(800, 50);
        this.gf.add(cmb);

        // ? pannello con chest
        Panel_ChestGame cp = new Panel_ChestGame(800, 800, cmb, this, mute);
        this.gf.add(cp);

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