/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/** Main class for the Role-Playing Game engine.
 * Handles initialization, input and rendering.
 */
public class RPG extends BasicGame
{
    private World world;
    private Font font;
    public Music music;
    /** Screen width, in pixels. */
    public static final int screenwidth = 800;
    /** Screen height, in pixels. */
    public static final int screenheight = 600;
    public static final int PANELHEIGHT = 70;
    
    /** Create a new RPG object. */
    public RPG()
    {
        super("RPG Game Engine");
    }

    /** Initialize the game state.
     * @param gc The Slick game container object.
     */
    @Override
    public void init(GameContainer gc)
    throws SlickException
    {
        world = new World(screenwidth,screenheight);
        font = FontLoader.loadFont("assets/DejaVuSans-Bold.ttf", 15);
        music = new Music("assets/sounds/palette.ogg");
        music.loop(1,0.1f);
    }
  
    
    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        double dir_x = 0;
        double dir_y = 0;
        int attack = 0;
        int run = 0;
        
        // Update the player's movement direction based on keyboard presses.

        if (input.isKeyDown(Input.KEY_DOWN))
            dir_y += 1;
        if (input.isKeyDown(Input.KEY_UP))
            dir_y -= 1;
        if (input.isKeyDown(Input.KEY_LEFT))
            dir_x -= 1;
        if (input.isKeyDown(Input.KEY_RIGHT))
            dir_x += 1;
        if (input.isKeyDown(Input.KEY_S))
        	run = 1;
        if (input.isKeyDown(Input.KEY_A))
        	attack = 1;
        if (input.isKeyDown(Input.KEY_P))
        	music.stop();
        if (input.isKeyDown(Input.KEY_R))
        	music.resume();
        
        // Let World.update decide what to do with this data.
        world.update(dir_x, dir_y, delta, attack, run);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
        g.setFont(font);
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(false);
        app.setDisplayMode(screenwidth, screenheight, false);
        app.start();
    }
}
