package MiniGames.jumpGame;

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
 * Sonoro
 */
public class Sonoro_JumpGame extends Thread {

    AudioFileFormat aff;
    AudioInputStream ais;
    int n;
    static boolean attivo = false;

    public Sonoro_JumpGame(File sf, int n) {
        try {
            aff = AudioSystem.getAudioFileFormat(sf);
            ais = AudioSystem.getAudioInputStream(sf);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }

        this.n = n;
        start();
    }

    public void run() {
        try {
            AudioFormat af = aff.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
                    ((int) ais.getFrameLength() * af.getFrameSize()));
            Clip ol = (Clip) AudioSystem.getLine(info);
            ol.open(ais);
            ol.loop(n);
            ol.start();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}