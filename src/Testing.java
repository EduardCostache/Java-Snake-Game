import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Testing {
    private static File appleFile;
    private static AudioInputStream appleAudio;
    private static Clip appleClip;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            appleFile = new File("apple_sound.wav");
            appleAudio = AudioSystem.getAudioInputStream(appleFile);
            appleClip = AudioSystem.getClip();
            appleClip.open(appleAudio);


            String response = "";

            while (!response.equals("q")) {
                System.out.println("P to play");
                System.out.println("Q to play");

                response = scanner.next();

                switch (response) {
                    case ("p") -> appleClip.start();
                    case ("r") -> appleClip.setMicrosecondPosition(0);
                    case ("q") -> appleClip.stop();
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
