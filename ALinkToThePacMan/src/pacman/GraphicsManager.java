package pacman;

import java.io.File;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Pre-loads and stores all necessary sprites and backgrounds of the game.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class GraphicsManager
{
    /**
     * The background image of the screen.
     */
    public BufferedImage background;
    
    /**
     * The sprite used for the rupees.
     */
    public BufferedImage rupee;
    
    /**
     * The spritesheet used for link.
     */
    public BufferedImage link;
    
    /**
     * The spritesheet used for the stalfos.
     */
    public BufferedImage stalfos;
    
    /**
     * The image used as a splash screen at the beginning of the game.
     */
    public BufferedImage title;
    
    /**
     * The frame of the score display used at the end of the game.
     */
    public BufferedImage scoreDisplay;
    
    /**
     * Pre-loads all graphics required for the game.
     */
    public GraphicsManager()
    {
        try 
        {
            background = ImageIO.read(new File("resources/images/background.png"));
            rupee = ImageIO.read(new File("resources/images/rupee.png"));
            link = ImageIO.read(new File("resources/images/link.png"));
            stalfos = ImageIO.read(new File("resources/images/stalfos.png"));
            title = ImageIO.read(new File("resources/images/title.png"));
            scoreDisplay = ImageIO.read(new File("resources/images/score.png"));
        }
        catch (Exception e)
        {
            System.out.println("Unable to load sprites.");
        }
    }
}
