package MiniGames.ChestGame;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * ChestPanel
 */
public class Panel_ChestGame extends JPanel {
    public int width;
    public int height;

    public int chest_width;
    public int chest_height;

    ArrayList<Chest_ChestGame> chestList;

    public int usage = 10; // ? chest che si possono aprire (per aprire 1 chest servono 2 colpi)

    public Panel_ChestGame(int width, int height, MenuBar_ChestGame chestMenuBar, Game_ChestGame chestGame, boolean mute) {
        this.width = width;
        this.height = height;
        this.chest_width = width / 4;
        this.chest_height = height / 4;
        this.chestList = new ArrayList<>();

        this.setLayout(null);
        this.setBounds(0, 50, this.width, this.height);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.chestList.add(new Chest_ChestGame(this.chest_width, this.chest_height, j * this.chest_width, i * this.chest_height, this, chestMenuBar, chestGame, mute));
            }
        }

        setRandomItem();
        this.chestList.forEach(this::add);
    }

    private void setRandomItem() {
        this.chestList.get(new Random().nextInt(this.chestList.size())).setTreasureChest();
        this.chestList.get(new Random().nextInt(this.chestList.size())).setTreasureChest();
        this.chestList.get(new Random().nextInt(this.chestList.size())).setCrowbarChest();
        this.chestList.get(new Random().nextInt(this.chestList.size())).setCrowbarChest();
    }
}