package MiniGames.instructionPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ButtonPanel
 */
public class ButtonPanel_Instruction extends JPanel {
    private int width, height;
    Instruction_Instruction inst;

    JButton btnNext, btnBack, btnSkip;

    public ButtonPanel_Instruction(int width, int height, Instruction_Instruction inst) {
        this.width = width;
        this.height = height;
        this.inst = inst;

        setUp();
    }

    private void setUp() {
        this.setSize(new Dimension(this.width, this.height));
        this.setLayout(new GridLayout(1, 3));
        this.setBackground(Color.black);
        this.setOpaque(true);

        // ? backButton
        this.btnBack = new JButton("Back");
        buttonStile(btnBack);
        this.btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inst.iP.slideBack();
            };
        });
        this.add(btnBack);

        // ? nextButton
        this.btnNext = new JButton("Next");
        buttonStile(btnNext);
        this.btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inst.iP.slideNext();
            };
        });
        this.add(btnNext);

        // ? skipButton
        this.btnSkip = new JButton("Skip");
        buttonStile(btnSkip);
        this.btnSkip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inst.iP.slideSkip();
            };
        });
        this.add(btnSkip);

        try {
            this.btnBack.setFont(createGameFont(13));
            this.btnNext.setFont(createGameFont(13));
            this.btnSkip.setFont(createGameFont(13));
        } catch (Exception e) {
        }
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    private void buttonStile(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("arial", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(63, 72, 204)));
    }
}