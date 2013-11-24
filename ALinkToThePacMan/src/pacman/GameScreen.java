package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Game Screen which handles all the drawing of the game.
 * 
 * @author Alex Pennells
 * @since 2013-01-27
 */
public class GameScreen extends JFrame
{
    /**
     * True if the game is running; otherwise, false.
     */
    public boolean gameRunning;
    
    /**
     * True if the game is over; otherwise, false.
     */
    public boolean gameOver;
    
    /**
     * The panel on the frame used for drawing the game objects.
     */
    private DrawPane drawPane;
    
    /**
     * A reference to all the other objects in the game.
     */
    private GameObjects objects;
    
    /**
     * Creates the game screen.
     * 
     * @param gameObjects 
     */
    public GameScreen(GameObjects gameObjects)
    {
        super("A Link to the... PacMan!!!  Score: 0");
        objects = gameObjects;
        addKeyListener(gameObjects.link);
        gameRunning = false;
        gameOver = false;
        
        // Configure the screen for drawing.
        drawPane = new DrawPane(gameObjects);
        setContentPane(drawPane);
        
        // Configure the appearance of the window.
        setSize(640, 502);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /**
     * Re-draws the screen and updates the score on the title bar of the window.
     */
    public void redrawScreen()
    {
        this.setTitle("A Link to the... PacMan!!!  Score: " + (int) objects.score);
        drawPane.repaint();
    }
    
    /**
     * A modified JPanel used to draw all of the game sprites to the screen.
     */
    private class DrawPane extends JPanel
    {
        /**
         * A reference to all the other game objects.
         */
        private GameObjects objects;
        
        /**
         * Pre-loads all the graphics required by the game.
         */
        private GraphicsManager graphics;
        
        /**
         * Creates the drawing panel.
         * 
         * @param gameObjects A reference to all the other game objects.
         */
        public DrawPane(GameObjects gameObjects)
        {
            objects = gameObjects;
            graphics = new GraphicsManager();
        }
        
        /**
         * Redraws the screen.
         * 
         * @param gIn The graphics of the panel.
         */
        @Override
        public void paintComponent(Graphics gIn)
        {
            Graphics2D g = (Graphics2D)gIn.create();
            
            // Redraw the background.
            g.clearRect(0, 0, 640, 480);
            g.drawImage(graphics.background, null, 0, 0);
            
            // Draw link.
            BufferedImage linkImage = graphics.link.getSubimage(objects.link.getSpriteCoordinateX(), objects.link.getSpriteCoordinateY(), 32, 32);
            g.drawImage(linkImage, null, objects.link.x, objects.link.y);
            
            // Draw all the rupees.
            for (int i = 0; i < objects.rupees.size(); i++)
            {
                Rupee rupee = objects.rupees.get(i);
                g.drawImage(graphics.rupee, null, rupee.drawX, rupee.drawY);
            }
            
            // Draw all the stalfos.
            for (int i = 0; i < objects.stalfos.length; i++)
            {
                BufferedImage stalfosImage = graphics.stalfos.getSubimage(objects.stalfos[i].getSpriteCoordinateX(), objects.stalfos[i].getSpriteCoordinateY(), 32, 32);
                g.drawImage(stalfosImage, null, objects.stalfos[i].x, objects.stalfos[i].y);
            }
            
            if (!gameRunning)
            {
                if (!gameOver)
                {
                    // Draw the title screen.
                    g.drawImage(graphics.title, null, 194, 182);
                }
                else
                {
                    // Draw the score display screen if the game is over.
                    g.setColor(Color.BLACK);
                    g.drawImage(graphics.scoreDisplay, null, 232, 210);
                    g.drawString("Score: " + (int) objects.score, 272, 245);
                }
            }
        }
    }
}
