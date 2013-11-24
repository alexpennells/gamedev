package pacman;

import java.util.ArrayList;

/**
 * An object representing the enemies in the game.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class Stalfos extends GameObject
{
    /**
     * The direction that the enemy is facing.
     */
    public Direction facing;
    
    /**
     * The speed that the enemy moves around the room at.
     */
    private int speed;
    
    /**
     * The index of the currently visible frame of the animation.
     */
    private double imageIndex;
    
    /**
     * The animation speed of the enemy sprite.
     */
    private double imageSpeed;
    
    /**
     * Creates an instance of the enemy at the specified position.
     * 
     * @param objects A reference to all the other objects in the game.
     * @param xPos The X position to create the enemy at.
     * @param yPos The Y position to create the enemy at.
     */
    public Stalfos(GameObjects objects, int xPos, int yPos)
    {
        super(objects, xPos, yPos);
        facing = Direction.DOWN;
        speed = 2;
        hPadding = 9;
        vPadding = 4;
        imageIndex = 0.0;
        imageSpeed = 0.15;
    }
    
    /**
     * Determines whether or not to change its direction and then moves
     * in the direction which it facing.
     */
    public void move()
    {
        chooseNewDirection();
        
        // Increment the animation of the sprite.
        imageIndex += imageSpeed;
        if (imageIndex > 1.95)
        {
            imageIndex = 0.0;
        }
        
        // Move the enemy in the direction it is currently facing.
        switch (facing)
        {
            case DOWN:
                y += speed;
                break;
            
            case UP:
                y -= speed;
                break;
                
            case RIGHT:
                x += speed;
                break;
                
            case LEFT:
                x -= speed;
                break;
        }
    }
    
    /**
     * Checks whether or not the player has collided with the enemy.
     * 
     * @return True if the player has run into the enemy; otherwise, false.
     */
    public boolean checkCollisions()
    {
        if (isCollidingWithPrecise(objects.link))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * Determine which direction the enemy should start facing based on its current position.
     */
    public void chooseNewDirection()
    {
        ArrayList<Direction> directions = getAvailableDirections();
        
        // Check whether Link is in any of the available directions.
        for (int i = 0; i < directions.size(); i++)
        {
            if (isLinkInDirection(directions.get(i)))
            {
                facing = directions.get(i);
                return;
            }
        }
        
        // Don't change direction if it is already heading down a specific path.
        if (facing == Direction.DOWN || facing == Direction.UP)
        {
            if (directions.size() == 2 && directions.contains(Direction.UP) && directions.contains(Direction.DOWN))
            {
                return;
            }
        }
        else if (facing == Direction.RIGHT || facing == Direction.LEFT)
        {
            if (directions.size() == 2 && directions.contains(Direction.LEFT) && directions.contains(Direction.RIGHT))
            {
                return;
            }
        }
        

        // Don't allow the Stalfos to randomly turn around.
        switch (facing)
        {
            case UP:
                directions.remove(Direction.DOWN);
                break;

            case DOWN:
                directions.remove(Direction.UP);
                break;

            case LEFT:
                directions.remove(Direction.RIGHT);
                break;

            case RIGHT:
                directions.remove(Direction.LEFT);
                break;
        }

        // Randomly pick one of the available directions.
        facing = directions.get(objects.rand.nextInt(directions.size()));
    }
    
    /**
     * Calculates the horizontal position on the spritesheet of the current sprite.
     * 
     * @return The horizontal position of the current sprite on the spritesheet.
     */
    public int getSpriteCoordinateX()
    {
        return ((int)imageIndex) * 32;
    }
    
    /**
     * Calculates the vertical position on the spritesheet of the current sprite.
     * 
     * @return The vertical position of the current sprite on the spritesheet.
     */
    public int getSpriteCoordinateY()
    {       
        int coordinateY = 0;
        
        switch (facing)
        {
            case DOWN:
                coordinateY =  0;
                break;
                
            case UP:
                coordinateY = 32;
                break;
                
            case LEFT:
                coordinateY = 64;
                break;
                
            case RIGHT:
                coordinateY = 96;
                break;
        }
        
        return coordinateY;
    }
    
    /**
     * Constructs an array containing the directions relative to 
     * the enemy that do not have a wall in the way.
     * 
     * @return An array of possible directions for the enemy to move.
     */
    private ArrayList<Direction> getAvailableDirections()
    {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        
        if (!objects.isWallAtPosition(x, y - speed))
        {
            directions.add(Direction.UP);
        }
        
        if (!objects.isWallAtPosition(x, y + speed))
        {
            directions.add(Direction.DOWN);
        }
        
        if (!objects.isWallAtPosition(x - speed, y))
        {
            directions.add(Direction.LEFT);
        }
        
        if (!objects.isWallAtPosition(x + speed, y))
        {
            directions.add(Direction.RIGHT);
        }
        
        return directions;
    }
    
    /**
     * Determines if the player is visible in a specific direction
     * relative to the enemy.
     * 
     * @param direction The direction to check for the player.
     * @return True if the player is in the specified direction; otherwise, false.
     */
    private boolean isLinkInDirection(Direction direction)
    {
        int xPos = x;
        int yPos = y;
        
        // Search in the direction until a wall or Link is encountered.
        while (!objects.isWallAtPosition(xPos, yPos))
        {
            // Increment the direction specified.
            switch (direction)
            {
                case UP:
                    yPos -= 32;
                    break;
                    
                case DOWN:
                    yPos += 32;
                    break;
                    
                case RIGHT: 
                    xPos += 32;
                    break;
                    
                case LEFT:
                    xPos -= 32;
                    break;
            }
            
            // Check for a collision at the current position with Link.
            GameObject position = new GameObject(objects, xPos, yPos);
            if (position.isCollidingWith(objects.link))
            {
                return true;
            }
        }
        
        return false;
    }
}
