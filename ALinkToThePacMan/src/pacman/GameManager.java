package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 * Initializes the required objects and runs the game. Handles the moving, 
 * collision checking and drawing of all the instances.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class GameManager implements ActionListener, KeyListener
{
    /**
     * A reference to all of the other objects in the game.
     */
    private GameObjects gameObjects;
    
    /**
     * The timer that runs an iteration of the game loop when it expires.
     */
    private Timer gameLoopTimer;
    
    /**
     * The screen used to show the graphics of the game.
     */
    private GameScreen gameScreen;
    
    /**
     * Initializes the PacMan game.
     */
    public GameManager()
    {
        initializeGame();
    }
    
    /**
     * Handles the event sent by the expired game timer.
     * 
     * @param e An action event fired by the expired game timer.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        mainGameLoop();
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
        // Start the game if it is not already running.
        if (!gameScreen.gameOver && !gameScreen.gameRunning)
        {
            gameScreen.gameRunning = true;
            gameObjects.audioManager.playStartGameSound();
            startGameLoop();
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
     * Creates the objects necessary to play the game and initializes the class variables.
     */
    private void initializeGame()
    {
        gameObjects = new GameObjects();
        gameScreen = new GameScreen(gameObjects);
        gameScreen.addKeyListener(this);
    }
    
    /**
     * Starts the timer which handles the main game loop.
     */
    private void startGameLoop()
    {
        gameObjects.link.gameRunning = true;
        gameScreen.gameRunning = true;
        gameLoopTimer = new Timer(1000 / 60, this);
        gameLoopTimer.start();
    }
    
    /**
     * Contains the events that occur on each iteration of the game loop such as
     * moving objects, checking collisions and redrawing the screen.
     */
    private void mainGameLoop()
    {
        if (gameScreen.gameRunning)
        {
            moveObjects();
            checkCollisions();
        }
        
        gameScreen.redrawScreen();
    }
    
    /**
     * Moves all the objects to their new positions and increases
     * the score for the moving player.
     */
    private void moveObjects()
    {
        gameObjects.score += 0.05;
        
        gameObjects.link.move();
        
        // Move all the stalfos in the room.
        for (int i = 0; i < gameObjects.stalfos.length; i++)
        {
            gameObjects.stalfos[i].move();
        }
        
        // Animate any rupees that have been collected by the player.
        for (int i = 0; i < gameObjects.rupees.size(); i++)
        {
            // If the rupee animation has completed, destroy it.
            if (gameObjects.rupees.get(i).move())
            {
                gameObjects.score += 10;
                gameObjects.rupees.remove(i);
                i--;
                
                // If there are no rupees left, end the game.
                if (gameObjects.rupees.isEmpty())
                {
                    gameScreen.gameRunning = false;
                    gameScreen.gameOver = true;
                    gameObjects.audioManager.playWinGameSound();
                }
            }
        }
    }
    
    /**
     * Checks for collisions between any objects that were moved to new positions.
     */
    private void checkCollisions()
    {
        gameObjects.link.checkCollisions();
        
        for (int i = 0; i < gameObjects.stalfos.length; i++)
        {
            if (gameObjects.stalfos[i].checkCollisions())
            {
                gameScreen.gameRunning = false;
                gameScreen.gameOver = true;
                gameObjects.audioManager.playDieSound();
            }
        }
    }
}
