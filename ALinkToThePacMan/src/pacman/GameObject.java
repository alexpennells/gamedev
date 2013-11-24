package pacman;

/**
 * A base object used in the game. Is implemented by all other objects in the game.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class GameObject
{
    /**
     * Specifies the X position of the object in the room.
     */
    public int x;
    
    /**
     * Specified the Y position of the object in the room.
     */
    public int y;
    
    /**
     * Specifies the previous X position of the object in the room.
     */
    public int oldX;
    
    /**
     * Specifies the previous Y position of the object in the room.
     */
    public int oldY;
    
    /**
     * Specifies the horizontal padding between the edge of the sprite and the
     * actual object within it.
     */
    public int hPadding;
    
    /**
     * Specifies the vertical padding between the edge of the sprite and the
     * actual object within it.
     */
    public int vPadding;
    
    /**
     * A reference to the other objects in the game.
     */
    public GameObjects objects;
    
    /**
     * Creates a basic game object.
     * 
     * @param gameObjects A reference to the other objects in the game.
     * @param xPos The X position to create the object at.
     * @param yPos The Y position to create the object at.
     */
    public GameObject(GameObjects gameObjects, int xPos, int yPos)
    {
        x = xPos;
        y = yPos;
        oldX = x;
        oldY = y;
        objects = gameObjects;
    }
    
    /**
     * Checks whether the object's positions overlap, causing a collision.
     * 
     * @param other The object to check for a collision with.
     * @return True if the other object is overlapping; otherwise, false.
     */
    public boolean isCollidingWith(GameObject other)
    {
        if (hasHorizontalOverlap(other) && hasVerticalOverlap(other))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Checks whether the object's horizontal positions overlap.
     * 
     * @param other The object to check for a horizontal overlap with.
     * @return True if the other object is overlapping horizontally; otherwise, false.
     */
    public boolean hasHorizontalOverlap(GameObject other)
    {
        if (other.x >= x && other.x <= getRight())
        {
            return true;
        }
        else if (other.getRight() >= x && other.getRight() <= getRight())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks whether the object's vertical positions overlap.
     * 
     * @param other The object to check for a vertical overlap with.
     * @return True if the other object is overlapping vertically; otherwise, false.
     */
    public boolean hasVerticalOverlap(GameObject other)
    {
        if (other.y >= y && other.y <= getBottom())
        {
            return true;
        }
        else if (other.getBottom() >= y && other.getBottom() <= getBottom())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks whether the object's positions overlap, while accounting
     * for padding on both objects, causing a collision.
     * 
     * @param other The object to check for a precise collision with.
     * @return True if the other object is overlapping; otherwise, false.
     */
    public boolean isCollidingWithPrecise(GameObject other)
    {
        if (hasHorizontalOverlapPrecise(other) && hasVerticalOverlapPrecise(other))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Checks whether the object's horizontal positions overlap, while
     * accounting for padding on both objects.
     * 
     * @param other The object to check for a precise horizontal overlap with.
     * @return True if the other object is overlapping horizontally; otherwise, false.
     */
    public boolean hasHorizontalOverlapPrecise(GameObject other)
    {
        if (other.getPreciseLeft() >= getPreciseLeft() && other.getPreciseLeft() <= getPreciseRight())
        {
            return true;
        }
        else if (other.getPreciseRight() >= getPreciseLeft() && other.getPreciseRight() <= getPreciseRight())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks whether the object's vertical positions overlap, while
     * accounting for padding on both objects.
     * 
     * @param other The object to check for a precise vertical overlap with.
     * @return True if the other object is overlapping vertically; otherwise, false.
     */
    public boolean hasVerticalOverlapPrecise(GameObject other)
    {
        if (other.getPreciseTop() >= getPreciseTop() && other.getPreciseTop() <= getPreciseBottom())
        {
            return true;
        }
        else if (other.getPreciseBottom() >= getPreciseTop() && other.getPreciseBottom() <= getPreciseBottom())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Calculates the position of the right side of the object.
     * 
     * @return A horizontal coordinate representing the right side of the object.
     */
    public int getRight()
    {
        return x + 31;
    }
    
    /**
     * Calculates the position of the bottom of the object.
     * 
     * @return A vertical coordinate representing the bottom of the object.
     */
    public int getBottom()
    {
        return y + 31;
    }
    
    /**
     * Calculates the position of the left side of the object while accounting for padding.
     * 
     * @return A horizontal coordinate representing the precise left side of the object.
     */
    public int getPreciseLeft()
    {
        return x + hPadding;
    }
    
    /**
     * Calculates the position of the top of the object while accounting for padding.
     * 
     * @return A vertical coordinate representing the precise top of the object.
     */
    public int getPreciseTop()
    {
        return y + vPadding;
    }
    
    /**
     * Calculates the position of the right side of the object while accounting for padding.
     * 
     * @return A horizontal coordinate representing the precise right side of the object.
     */
    public int getPreciseRight()
    {
        return x + 31 - hPadding;
    }
    
    /**
     * Calculates the position of the bottom of the object while accounting for padding.
     * 
     * @return A vertical coordinate representing the precise bottom of the object.
     */
    public int getPreciseBottom()
    {
        return y + 31 - vPadding;
    }
}
