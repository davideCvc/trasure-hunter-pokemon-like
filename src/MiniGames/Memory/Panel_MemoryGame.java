package MiniGames.Memory;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

/**
 * MemoryPanel
 */
public class Panel_MemoryGame extends JPanel {
    private int width;
    private int height;

    Game_MemoryGame mG;

    int cardSize = 200;

    int point = 0;

    ArrayList<Card_MemoryGame> cardArr;

    private boolean mute;

    public Panel_MemoryGame(int width, int height, Game_MemoryGame mG, boolean mute) {
        this.width = width;
        this.height = height;
        this.mute = mute;
        this.mG = mG;
        cardArr = new ArrayList<>();

        setUp();
        addCards();
    }

    private void setUp() {
        this.setSize(new Dimension(this.width, this.height));
        this.setLayout(null);
    }

    private void addCards() {
        ArrayList<String> nameArr = new ArrayList<>(Arrays.asList("coinCard", "coinCard", "crownCard", "crownCard", "diamondCard", "diamondCard", "goldCard", "goldCard", "keyCard", "keyCard", "ringCard", "ringCard", "rubyCard", "rubyCard", "skullCard", "skullCard"));

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                int rand = new Random().nextInt(nameArr.size());
                Card_MemoryGame c = new Card_MemoryGame(new File("./src/MiniGames/Memory/img/cover.png"), new File("./src/MiniGames/Memory/img/" + nameArr.get(rand) + ".png"), nameArr.get(rand), cardSize, cardSize, this);
                nameArr.remove(rand);
                c.setLocation(j * cardSize, i * cardSize);
                this.add(c);
            }
    }

    public void checkCard(Card_MemoryGame card) {
        if (cardArr.size() < 2) {
            card.rotate();
            cardArr.add(card);
        }

        if (cardArr.size() == 2)
            new Thread(() -> {
                if (cardArr.get(0).name.equals(cardArr.get(1).name)) {
                    point++;
                    mG.mB.setInfo(point);
                    if (mute)
                        new Thread(() -> new Sonoro_MemoryGame(new File("./src/MiniGames/Memory/sound/bis.wav"), 0)).start();
                    if (point == 8)
                        mG.win(point);
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                    cardArr.forEach(e -> e.rotate());
                }
                cardArr.clear();
            }).start();
    }

}