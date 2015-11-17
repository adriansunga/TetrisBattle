package tetrisGUI;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

public class PlayMusic {
	private Clip clip;

    PlayMusic() throws Exception {
        File file = new File("music/Tetris.wav");
        clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream( file );
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
    
    public void pause() {
    	clip.stop();
    }
    
    public void unpause() {
    	clip.start();
    }
    
    public void stop() {
    	clip.close();
    }
}
