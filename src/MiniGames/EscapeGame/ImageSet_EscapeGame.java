package MiniGames.EscapeGame;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * PlayerIcon
 */
public class ImageSet_EscapeGame {
    private HashMap<String, ImageIcon> imgS = new HashMap<>();

    public ImageSet_EscapeGame(String url, String imgName, int from, int to, String imgFormat) throws IOException {
        try {
            for (int i = from; i < to + 1; i++)
                imgS.put(imgName + i, new ImageIcon(ImageIO.read(new File(url + imgName + i + imgFormat))));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IOException();
        }
    }

    public ImageIcon getImage(String Imgname) {
        return this.imgS.get(Imgname);
    }

    public HashMap<String, ImageIcon> getSet() {
        return this.imgS;
    }
}