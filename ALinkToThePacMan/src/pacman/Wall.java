package pacman;

/**
 * Represents the solid walls preventing the player from leaving the room.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class Wall extends GameObject
{
    /**
     * Creates a wall at the specified position in the room.
     * 
     * @param objects A reference to all the other objects in the game.
     * @param xPos The X position to create the wall.
     * @param yPos The Y position to create the wall.
     */
    public Wall(GameObjects objects, int xPos, int yPos)
    {
        super(objects, xPos, yPos);
    }
}
