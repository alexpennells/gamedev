package pacman;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains any objects or data that may be required by many separate objects.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class GameObjects
{
    /**
     * The character controlled by the player.
     */
    public Link link;
    
    /**
     * An array of the stalfos trying to kill the player.
     */
    public Stalfos[] stalfos;
    
    /**
     * An array of the walls of the game.
     */
    public ArrayList<Wall> walls;
    
    /**
     * An array of the rupees of the game. When rupees are collected, they are
     * removed from this array.
     */
    public ArrayList<Rupee> rupees;
    
    /**
     * A random number generator.
     */
    public Random rand;
    
    /**
     * Handles the audio output for the game.
     */
    public AudioManager audioManager;
    
    /**
     * Keeps track of the player score throughout the game.
     */
    public double score;
    
    /**
     * Initializes all the objects required to play the game.
     */
    public GameObjects()
    {
        createLink();
        createStalfos();        
        createWallsAndRupees();
        rand = new Random(System.currentTimeMillis());
        audioManager = new AudioManager();
        score = 0;
    }
    
    /**
     * Checks whether or not there is a wall at the specified position.
     * 
     * @param x The X position to check for a wall.
     * @param y The Y position to check for a wall.
     * @return True if there is a wall at the specified position; otherwise, false.
     */
    public boolean isWallAtPosition(int x, int y)
    {
        // Create an object at the specified position to check for collisions with a wall.
        GameObject temp = new GameObject(this, x, y);
        
        for (int i = 0; i < walls.size(); i++)
        {            
            if (walls.get(i).isCollidingWith(temp))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Creates the Link object to be controlled by the player.
     */
    private void createLink()
    {
        link = new Link(this, 32, 32);
    }
    
    /**
     * Creates the stalfos which are trying to kill the player.
     */
    private void createStalfos()
    {
        stalfos = new Stalfos[3];
        stalfos[0] = new Stalfos(this, 576, 32);
        stalfos[1] = new Stalfos(this, 32, 416);
        stalfos[2] = new Stalfos(this, 576, 416);
    }
    
    /**
     * Loads the level from a text file and creates the required walls and rupees.
     */
    private void createWallsAndRupees()
    {
        int fileByte;
        int x = 0, y = 0;
        
        rupees = new ArrayList<Rupee>();
        walls = new ArrayList<Wall>();
        
        try
        {
            FileInputStream levelFile = new FileInputStream("resources/level.txt");
            
            // Read the file until the end of the file stream.
            for(fileByte = levelFile.read(); fileByte != -1; fileByte = levelFile.read())
            {
                // Check for the end of the current line.
                if (fileByte == '\n')
                {
                    x = 0;
                    y += 32;
                    continue;
                }
                
                // Check if a new wall was specified at the current position.
                if (fileByte == '#')
                {
                    walls.add(new Wall(this, x, y));
                }
                
                // Check if a rupee was specified at the current position.
                if (fileByte == '.')
                {
                    rupees.add(new Rupee(this, x, y));
                }
                
                x += 32;
            }
            
            levelFile.close();
        }
        catch (Exception e)
        {
            System.out.println("Unable to load the level file.");
        }
    }
}
