package pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents the character that the player controls.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class Link extends GameObject implements KeyListener
{
    /**
     * The direction that the player is facing.
     */
    public Direction facing;
    
    /**
     * True if the game is currently running; otherwise, false.
     */
    public boolean gameRunning;
    
    /**
     * The last key that was pressed by the player.
     */
    private Direction previousInput;
    
    /**
     * A counter used to keep track of the number of frames since the player
     * last pressed a key on the keyboard.
     */
    private int inputDelay;
    
    /**
     * The speed that the player moves around the room at.
     */
    private int speed;
    
    /**
     * The index of the currently visible frame of the animation.
     */
    private double imageIndex;
    
    /**
     * The animation speed of the character sprite.
     */
    private double imageSpeed;
    
    /**
     * Creates the character which is controlled by the player.
     * 
     * @param objects A reference to the all the objects of the game.
     * @param xPos The X position to create the player object at.
     * @param yPos The Y position to create the player object at.
     */
    public Link(GameObjects objects, int xPos, int yPos)
    {
        super(objects, xPos, yPos);
        facing = Direction.DOWN;
        inputDelay = 0;
        speed = 2;
        hPadding = 9;
        vPadding = 6;
        imageIndex = 0.0;
        imageSpeed = 0.15;
        gameRunning = false;
    }
    
    /**
     * Moves the player based on the direction that they are facing.
     */
    public void move()
    {
        oldX = x;
        oldY = y;
        
        // Increment the animation of the sprite.
        imageIndex += imageSpeed;
        if (imageIndex > 3.9)
        {
            imageIndex = 0.0;
        }
        
        handleDelayedKeyInput();
        
        // Move the character in the direction that they are facing.
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
     * Handle any collisions between the player and walls/rupees. The player should
     * bounce off of any walls and collect all rupees that he collides with.
     */
    public void checkCollisions()
    {
        // Check for collisions with walls.
        if (objects.isWallAtPosition(x, y))
        {
            x = oldX;
            y = oldY;
            inputDelay = 0;
            
            // Bounce off of any walls that the player runs into.
            switch (facing)
            {
                case UP:
                    facing = Direction.DOWN;
                    break;
                
                case DOWN:
                    facing = Direction.UP;
                    break;
                    
                case LEFT:
                    facing = Direction.RIGHT;
                    break;
                    
                case RIGHT:
                    facing = Direction.LEFT;
                    break;
            }
        }
        
        // Check for collisions with rupees.
        for (int i = 0; i < objects.rupees.size(); i++) 
        {
            Rupee rupee = objects.rupees.get(i);
            
            if (!rupee.increaseY && isCollidingWithPrecise(rupee))
            {
                // Start the collection animation for the rupee.
                rupee.increaseY = true;
                objects.audioManager.playRupeeSound();
            }
        }
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
     * Handles a key typed event.
     * 
     * @param e A key event for when a key is typed.
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
    }
    
    /**
     * Handles a key pressed event.
     * 
     * @param e A key event for when a key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (gameRunning)
        {
            /*
             * If the change in direction cannot be applied immediately, keep
             * track of the direction specified in case the change in 
             * direction can be applied later.
             */
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (!objects.isWallAtPosition(x, y - speed))
                    {
                        facing = Direction.UP;
                        inputDelay = 0;
                    }
                    else
                    {
                        previousInput = Direction.UP;
                        inputDelay = 30;
                    }

                    break;

                case KeyEvent.VK_DOWN:
                    if (!objects.isWallAtPosition(x, getBottom() + speed))
                    {
                        facing = Direction.DOWN;
                        inputDelay = 0;
                    }
                    else
                    {
                        previousInput = Direction.DOWN;
                        inputDelay = 30;
                    }

                    break;

                case KeyEvent.VK_LEFT:
                    if (!objects.isWallAtPosition(x - speed, y))
                    {
                        facing = Direction.LEFT;
                        inputDelay = 0;
                    }
                    else
                    {
                        previousInput = Direction.LEFT;
                        inputDelay = 30;
                    }

                    break;

                case KeyEvent.VK_RIGHT:
                    if (!objects.isWallAtPosition(getRight() + speed, y))
                    {
                        facing = Direction.RIGHT;
                        inputDelay = 0;
                    }
                    else
                    {
                        previousInput = Direction.RIGHT;
                        inputDelay = 30;
                    }

                    break;
            }
        }
    }
    
    /**
     * Handles a key released event.
     * 
     * @param e A key event for when a key is released.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
    
    /**
     * Determines whether or not it is possible to move in the last direction 
     * inputted by the player and changes the direction the player is facing.
     */
    private void handleDelayedKeyInput()
    {
        /*
         * Any input by the user which does not take effect immediately will
         * possibly be applied for the next 30 frames(0.5 sec). If the 
         * direction specified becomes possible, have the player face that direction.
         */
        if (inputDelay != 0)
        {
            switch (previousInput)
            {
                case UP:
                    if (!objects.isWallAtPosition(x, y - speed))
                    {
                        facing = Direction.UP;
                        inputDelay = 0;
                    }

                    break;
                    
                case DOWN:
                    if (!objects.isWallAtPosition(x, getBottom() + speed))
                    {
                        facing = Direction.DOWN;
                        inputDelay = 0;
                    }

                    break;
                    
                case LEFT:
                    if (!objects.isWallAtPosition(x - speed, y))
                    {
                        facing = Direction.LEFT;
                        inputDelay = 0;
                    }

                    break;
                    
                case RIGHT:
                    if (!objects.isWallAtPosition(getRight() + speed, y))
                    {
                        facing = Direction.RIGHT;
                        inputDelay = 0;
                    }

                    break;
            }
            
            if (inputDelay != 0)
            {
                inputDelay--;
            }
        }
    }
}
