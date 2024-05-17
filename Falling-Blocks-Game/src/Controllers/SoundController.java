package Controllers;

import view.MainWindow;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Controls the sound effects and background music for the game.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * <p>
 * The SoundController class manages the loading and playback of various sound
 * effects and background music used within the game.</p>
 *
 * <p>
 * It includes methods to play the background music (BSO), floor landing sound,
 * and line clearing sound. Additionally, it provides methods to stop and resume
 * the background music.</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public class SoundController {

    private Clip BSO;
    private Clip floorSound;
    private Clip lineSound;
    private long BSOposition; // Registro de la posición de reproducción del BSO
    
    String bsoPath = "/audio/bso.wav";
    String lineSoundPath = "/audio/line.wav";
    String floorSoundPath = "/audio/floor.wav";

    private final LogExceptionController logException;

    /**
     * Constructs a SoundController object.
     *
     * @param mainWindow the main window of the game
     */
    public SoundController(MainWindow mainWindow) {
        this.logException = mainWindow.getLogException();
    }

    // <editor-fold defaultstate="collapsed" desc="GettersAndSetters">  
    /**
     * Gets the background sound effect.
     *
     * @return The background sound effect.
     */
    public Clip getBSO() {
        return BSO;
    }

    /**
     * Gets the floor sound effect.
     *
     * @return The floor sound effect.
     */
    public Clip getFloorSound() {
        return floorSound;
    }

    /**
     * Gets the line sound effect.
     *
     * @return The line sound effect.
     */
    public Clip getLineSound() {
        return lineSound;
    }
    // </editor-fold>  

    /**
     * Plays the background music (BSO).
     */
    public void playBSO() {
        InputStream audioSrc = null;
        BufferedInputStream bufferedIn = null;
        AudioInputStream audioStream = null;
        try {
            audioSrc = getClass().getResourceAsStream(bsoPath);
            bufferedIn = new BufferedInputStream(audioSrc);
            audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            BSO = AudioSystem.getClip();
            BSO.open(audioStream);

            // Agregar un LineListener para detectar cuando la reproducción se complete
            BSO.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (BSOposition != -1) {
                        return;
                    }
                    if (event.getType() == LineEvent.Type.STOP) {
                        BSO.setMicrosecondPosition(0); // Vuelve al principio del audio
                        BSO.start(); // Reproduce nuevamente
                    }
                }
            });

            BSO.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logException.addExceptionLog(ex);
        } finally {
            try {
                if (audioStream != null) {
                    audioStream.close();
                }
                if (bufferedIn != null) {
                    bufferedIn.close();
                }
                if (audioSrc != null) {
                    audioSrc.close();
                }
            } catch (IOException ex) {
                logException.addExceptionLog(ex);
            }
        }
    }

    /**
     * Plays the floor landing sound.
     */
    public void playFloorSound() {
        InputStream audioSrc = null;
        BufferedInputStream bufferedIn = null;
        AudioInputStream audioStream = null;
        try {
            audioSrc = getClass().getResourceAsStream(floorSoundPath);
            bufferedIn = new BufferedInputStream(audioSrc);
            audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            floorSound = AudioSystem.getClip();
            floorSound.open(audioStream);
            floorSound.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logException.addExceptionLog(ex);
        } finally {
            try {
                if (audioStream != null) {
                    audioStream.close();
                }
                if (bufferedIn != null) {
                    bufferedIn.close();
                }
                if (audioSrc != null) {
                    audioSrc.close();
                }
            } catch (IOException ex) {
                logException.addExceptionLog(ex);
            }
        }
    }

    /**
     * Plays the line clearing sound.
     */
    public void playLineSound() {
        InputStream audioSrc = null;
        BufferedInputStream bufferedIn = null;
        AudioInputStream audioStream = null;
        try {
            audioSrc = getClass().getResourceAsStream(lineSoundPath);
            bufferedIn = new BufferedInputStream(audioSrc);
            audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            lineSound = AudioSystem.getClip();
            lineSound.open(audioStream);
            lineSound.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logException.addExceptionLog(ex);
        } finally {
            try {
                if (audioStream != null) {
                    audioStream.close();
                }
                if (bufferedIn != null) {
                    bufferedIn.close();
                }
                if (audioSrc != null) {
                    audioSrc.close();
                }
            } catch (IOException ex) {
                logException.addExceptionLog(ex);
            }
        }
    }

    /**
     * Stops the line clearing sound.
     */
    public void stopLineSound() {
        if (lineSound != null) {
            lineSound.stop();
        }
    }

    /**
     * Stops the floor landing sound.
     */
    public void stopFloorSound() {
        if (floorSound != null) {
            floorSound.stop();
        }
    }

    /**
     * Stops the background music (BSO).
     */
    public void stopBSO() {
        if (BSO != null) {
            BSO.stop();
        }
    }

    public void pauseBSO() {
        if (BSO != null && BSO.isRunning()) {
            BSOposition = BSO.getMicrosecondPosition(); // Guarda la posición actual de reproducción
            BSO.stop();
        }
    }

    /**
     * Resumes the background music (BSO).
     */
    public void resumeBSO() {
        if (BSO != null && !BSO.isRunning()) {
            BSO.setMicrosecondPosition(BSOposition); // Establece la posición de reproducción guardada
            BSOposition = -1;
            BSO.start();
        }
    }
}
