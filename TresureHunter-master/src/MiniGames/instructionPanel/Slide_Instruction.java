package MiniGames.instructionPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Slide
 */
public class Slide_Instruction {

    public BufferedImage image;
    public String text;

    public Slide_Instruction(File image, String text) {
        try {
            this.image = ImageIO.read(image);
        } catch (IOException e) {
            System.err.println("slide image Error");
            System.err.println(e.getMessage());
        }
        this.text = text;
    }
}