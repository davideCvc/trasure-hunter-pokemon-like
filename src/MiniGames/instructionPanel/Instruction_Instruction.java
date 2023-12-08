package MiniGames.instructionPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Instruction
 */
public class Instruction_Instruction extends JFrame {
    public String title;
    public ArrayList<Slide_Instruction> slides;
    private ImageIcon icon;

    Panel_Instruction iP;
    ButtonPanel_Instruction bP;

    boolean esc = false;

    public Instruction_Instruction(String title, ImageIcon icon, ArrayList<Slide_Instruction> slides) {
        this.slides = slides;
        this.title = title;
        this.icon = icon;
        setUp();
    }

    private void setUp() {
        this.setTitle(this.title);
        this.setSize(new Dimension(810, 886));
        this.setIconImage(icon.getImage());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                end();
            }
        });
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setBackground(Color.black);
        this.requestFocus();

        // ? text section
        iP = new Panel_Instruction(800, 800, slides, this);
        iP.setLocation(0, 0);
        this.add(iP);

        // ? button section
        bP = new ButtonPanel_Instruction(800, 50, this);
        bP.setLocation(0, 800);
        this.add(bP);
    }

    public void startInstruction() {
        this.setVisible(true);
        while (!esc) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        this.dispose();
    }

    public void end() {
        this.esc = true;
    }
}