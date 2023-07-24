package MiniGames.instructionPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * InstructionPanel
 */
public class Panel_Instruction extends JPanel {
    private int height, width;
    private ArrayList<Slide_Instruction> slides;

    private JButton icon;
    private JTextArea textArea;

    private int slideIndex = 0;

    Instruction_Instruction instruction;

    public Panel_Instruction(int width, int height, ArrayList<Slide_Instruction> slides, Instruction_Instruction instruction) {
        this.slides = slides;
        this.width = width;
        this.height = height;
        this.instruction = instruction;

        setUp();
        slidesManager();
    }

    private void setUp() {
        this.setSize(new Dimension(this.width, this.height));
        this.setLayout(new GridLayout(2, 1));

        // ? image icon
        this.icon = new JButton();
        this.icon.setBorder(BorderFactory.createEmptyBorder());
        this.add(icon);

        // ? text section
        this.textArea = new JTextArea();
        // this.textArea.setFont(new Font("arial", Font.BOLD, 20));
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setForeground(Color.WHITE);
        this.textArea.setBackground(new Color(63, 72, 204));
        this.textArea.setOpaque(true);
        this.textArea.setEditable(false);
        this.textArea.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(63, 72, 204)));
        this.add(textArea);

        try {
            textArea.setFont(createGameFont(13));
        } catch (Exception e) {
        }
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    private void slidesManager() {
        this.icon.setIcon(new ImageIcon(slides.get(slideIndex).image));
        this.textArea.setText(slides.get(slideIndex).text);
    }

    public void slideNext() {
        if (++slideIndex == slides.size())
            this.instruction.end();
        else
            slidesManager();
    }

    public void slideSkip() {
        this.instruction.end();
    }

    public void slideBack() {
        if (slideIndex - 1 >= 0)
            slideIndex--;
        slidesManager();
    }
}