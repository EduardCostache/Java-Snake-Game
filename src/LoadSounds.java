import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class LoadSounds {

    private Clip appleClip;
    private Clip gameOverClip;
    private FloatControl volControl;

    LoadSounds()
    {
        try {
            File appleFile = new File("apple_sound.wav");
            File gameOverFile = new File("game_over.wav");
            AudioInputStream appleAudio = AudioSystem.getAudioInputStream(appleFile);
            appleClip = AudioSystem.getClip();
            appleClip.open(appleAudio);
            AudioInputStream gameOverAudio = AudioSystem.getAudioInputStream(gameOverFile);
            gameOverClip = AudioSystem.getClip();
            gameOverClip.open(gameOverAudio);

            volControl = (FloatControl) appleClip.getControl(FloatControl.Type.MASTER_GAIN);
            volControl.setValue(-10.0f);

            volControl = (FloatControl) gameOverClip.getControl(FloatControl.Type.MASTER_GAIN);
            volControl.setValue(-10.0f);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playAppleSound()
    {
        appleClip.start();
        appleClip.setMicrosecondPosition(0);
    }

    public void playGameOverSound()
    {
        gameOverClip.start();
        gameOverClip.setMicrosecondPosition(0);
    }
}
