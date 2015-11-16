package tetrisGUI;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

public class PlayMusic {

    PlayMusic() throws Exception {
        File file = new File("music/Tetris.wav");
        Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream( file );
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
}
