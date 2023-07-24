import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * AudioManager
 */
public class AudioManager {
    // MADE IN MY SELFFFF
    private AudioFileFormat fileFormat;
    private AudioInputStream inputStream;
    private Clip clip;
    private int loop;
    private boolean active = false;

    public AudioManager(File audioFile, int loop) {
        try {
            fileFormat = AudioSystem.getAudioFileFormat(audioFile);
            inputStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (IOException e) {
            System.err.println("AudioManager IO Err");
            System.err.println(e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.err.println("AudioManager not supported file");
            System.err.println(e.getMessage());
        }

        this.loop = loop;
        setUp();
    }

    public void setUp() {
        try {
            AudioFormat af = fileFormat.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat(), ((int) inputStream.getFrameLength() * af.getFrameSize()));
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(inputStream);
            clip.loop(this.loop);
        } catch (LineUnavailableException e) {
            System.err.println("AudioManager line unavailable Err");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("AudioManager IO Err");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("AudioManager Err");
            System.err.println(e.getMessage());
        }
    }

    public void changeState(boolean state) {
        if (state)
            clip.start();
        else
            clip.stop();
    }

    public void changeState() {
        active = !active;
        changeState(active);
    }
}