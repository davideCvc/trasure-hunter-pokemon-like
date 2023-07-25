
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//THE MANAGMENT OF THE PLAYER IMAGE FOR ANIMATION
public class ImageSet {
    private HashMap<String, ImageIcon> imgS = new HashMap<>();

    public ImageSet(String url, String imgName, int from, int to, String imgFormat) throws IOException {
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