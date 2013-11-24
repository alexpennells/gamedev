package pacman;

/**
 * Represents a rupee that is in the room for the player to collect.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class Rupee extends GameObject
{
    /**
     * The horizontal position to draw the sprite for the rupee.
     */
    public int drawX;
    
    /**
     * The vertical position to draw the sprite for the rupee.
     */
    public int drawY;
    
    /**
     * True if the rupee has been collected and is animating up; otherwise, false.
     */
    public boolean increaseY;
    
    /**
     * Creates a rupee at the specified position.
     * 
     * @param objects A reference to all the other objects in the game.
     * @param xPos The X position to create the rupee.
     * @param yPos The Y position to create the rupee.
     */
    public Rupee(GameObjects objects, int xPos, int yPos)
    {
        super(objects, xPos, yPos);
        drawX = x + 12;
        drawY = y + 9;
        hPadding = 12;
        vPadding = 9;
        increaseY = false;
    }
    
    /**
     * Handles the animation of the rupee raising into the air when
     * it has already been collected by the player.
     * 
     * @return True if the rupee has reached the top and can be destroyed; otherwise, false.
     */
    public boolean move()
    {
        // Animate the rupee up if link has already gotten it.
        if (increaseY)
        {
            if (drawY < y - 8)
            {
                return true;
            }
            else
            {
                drawY -= 4;
            }
        }
        
        return false;
    }
}
