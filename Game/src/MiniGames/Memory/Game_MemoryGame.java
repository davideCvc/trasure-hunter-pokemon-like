package MiniGames.Memory;

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
 * Memory
 */
public class Game_MemoryGame {
    public static final ArrayList<Slide_Instruction> instructions = new ArrayList<>(Arrays.asList(
        new Slide_Instruction(new File("./src/MiniGames/Memory/instructionIMG/img1.png"), "ti troverai difronte a varie scatole contenenti tesori"),
        new Slide_Instruction(new File("./src/MiniGames/Memory/instructionIMG/img2.png"), "per prendere il tesoro devi trovarne 2 all apparenza uguali, in verità uno dei 2 è un disegno su un pezzo di cartone fatto da una scimmia pittrice")
        ));

    JFrame gf;
    Panel_MemoryGame mP;
    MenuBar_MemoryGame mB;

    int end;
    private boolean mute;

    public Game_MemoryGame() {}

    public int startGame(boolean mute) {
        this.mute = mute;
        createFrame();

        // ? barra delle info
        mB = new MenuBar_MemoryGame(800, 50);
        this.gf.add(mB);

        // ? pannello con card
        mP = new Panel_MemoryGame(800, 800, this, mute);
        mP.setLocation(0, 50);
        this.gf.add(mP);

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
        this.gf = new JFrame("Memory");
        try {
            this.gf.setIconImage(new ImageIcon(ImageIO.read(new File("./src/MiniGames/Memory/img/cover.png"))).getImage());
        } catch (IOException e) {
            System.err.println("icon Error");
            System.err.println(e.getMessage());
        }
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