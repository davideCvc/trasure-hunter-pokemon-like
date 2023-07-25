import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// import Map.FileIO

/**
 * RecordFrame
 */
public class RecordFrame extends JFrame {
    private static final String ICON_URL = "./img/icon.png";
    private static final String BACK_URL = "./img/back.png";

    private BufferedImage icon;
    private BufferedImage back;

    private GameMainPanel gMP;

    private ArrayList<RecordAccount> recordList;

    private JScrollPane scollPane;

    private String url;

    public RecordFrame(GameMainPanel gMP, String url) {
        this.url = url;
        this.gMP = gMP;
        this.recordList = new ArrayList<>();
        setUp();
        createRecordList();
        setUpScrollPane();
    }

    private void setUp() {
        this.setTitle("Treasure Hunter");
        this.setSize(gMP.getWidth(), gMP.getHeight());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitOperation();
            }
        });

        // ? set icon and background
        try {
            icon = ImageIO.read(new File(ICON_URL));
            back = ImageIO.read(new File(BACK_URL));
            this.setIconImage(icon);
            this.setContentPane(new ImagePanel(back));
        } catch (Exception e) {
            System.err.println("back img Err");
            System.err.println(e.getMessage());
            this.setBackground(Color.BLUE);
        }

        this.add(new RecordButtonPanel(this, 120, 0, 10));

        this.setVisible(true);
    }

    private void createRecordList() {
        ArrayList<String> recordTxt = new ArrayList<>();

        try {
            recordTxt = new FileIO(this.url).read(); // ? read values from the txt
        } catch (Exception e) {
            System.err.println("record file read Err");
            System.err.println(e.getMessage());
        }

        recordTxt.forEach(e -> {
            String[] line = e.split(":"); // ? separate name from the value

            // ? new account if not already in the list
            if (recordList.indexOf(new RecordAccount(line[0])) == -1)
                recordList.add(new RecordAccount(line[0]));

            try {
                recordList.get(recordList.indexOf(new RecordAccount(line[0]))).add(Integer.parseInt(line[1])); // ? read all the items and ad the values
            } catch (Exception ex) {
                // ! if read a nan do nothing
            }
        });
    }

    private void setUpScrollPane() {
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.WHITE);
        namePanel.setLayout(new GridLayout(recordList.size(), 1));
        namePanel.setLayout(null);

        final int maxNameLenght = 5;
        recordList.forEach(a -> {
            StringBuffer str = new StringBuffer();
            str.append((a.getName().length() > maxNameLenght ? a.getName().substring(0, 5) : (a.getName() + " ".repeat(maxNameLenght - a.getName().length()))) + " > "); // ? put all the account name on the panel
            str.append("[best: " + Collections.max(a.getValues()) + "]     "); // ? find the maximum value of the account
            a.forEach(e -> str.append(e + " / ")); // ? add all the value at the account

            JLabel line = new JLabel(str.toString());
            try {
                line.setFont(createGameFont(15));
            } catch (Exception e) {
            }
            namePanel.add(line);
        });

        Component[] cList = namePanel.getComponents();
        namePanel.setPreferredSize(new Dimension(10_000, cList.length * 40));
        for (int i = 0; i < cList.length; i++) {
            cList[i].setBounds(0, i * 40, 10_000, 40);
        }

        this.scollPane = new JScrollPane(namePanel);
        this.scollPane.setBounds((gMP.getWidth() / 2) - 450, (gMP.getHeight() / 2) - 260, 900, 600);
        this.scollPane.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
        this.scollPane.setBackground(Color.WHITE);

        this.add(this.scollPane);
    }

    private Font createGameFont(int size) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, new File("./font/pixel.ttf")).deriveFont((float) size);
    }

    protected void exitOperation() {
        gMP.ChangeVisibility(true);
        this.dispose();
    }

    protected void clearValue() throws IOException {
        this.scollPane.removeAll();
        this.scollPane.repaint();
        new FileIO(this.url).clear();
        this.recordList.clear();
        createRecordList();
        setUpScrollPane();
    }

    public void ChangeVisibility(boolean visible) {
        this.setVisible(visible);
    }

    public void ChangeVisibility() {
        ChangeVisibility(!this.isVisible());
    }
}