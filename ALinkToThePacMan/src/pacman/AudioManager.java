package pacman;

import java.io.File;
import java.io.FileInputStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Handles the loading and playing of sound effects and background music.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class AudioManager
{
    private File rupeeSoundFile;
    private File dieSoundFile;
    private File startGameSoundFile;
    private File winGameSoundFile;
    
    /**
     * Pre-loads all the sound files used in the game and plays the background music.
     */
    public AudioManager()
    {
        try
        {
            rupeeSoundFile = new File("resources/sounds/rupee.wav").getAbsoluteFile();
            startGameSoundFile = new File("resources/sounds/start.wav").getAbsoluteFile();
            winGameSoundFile = new File("resources/sounds/win.wav").getAbsoluteFile();
            dieSoundFile = new File("resources/sounds/die.wav").getAbsoluteFile();
        }
        catch (Exception e)
        {
            System.out.println("Could not load sound effects.");
        }
        
        playBackgroundMusic();
    }
    
    /**
     * Plays the sound effect for when link collects a rupee.
     */
    public void playRupeeSound()
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(rupeeSoundFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing rupee sound.");
        }
    }
    
    /**
     * Plays the sound effect for when the player starts the game.
     */
    public void playStartGameSound()
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(startGameSoundFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing game start sound.");
        }
    }
    
    /**
     * Plays the sound effect for when the player wins the game.
     */
    public void playWinGameSound()
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(winGameSoundFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing win game sound.");
        }
    }
    
    /**
     * Plays the sound effect for when the player is killed.
     */
    public void playDieSound()
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(dieSoundFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing die sound.");
        }
    }
    
    /**
     * Starts playing the background music for the level.
     */
    public void playBackgroundMusic()
    {
        try
        {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            
            FileInputStream is = new FileInputStream("resources/sounds/bgmusic.mid");
            Sequence mySeq = MidiSystem.getSequence(is);
            sequencer.setSequence(mySeq);
            sequencer.start();
        } 
        catch (Exception ex)
        {
            System.out.println("Error with playing the background music.");
        }
    }
}
