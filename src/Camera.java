/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import org.newdawn.slick.SlickException;

/** Represents the camera that controls our viewpoint.
 */
public class Camera extends WorldObject
{

    /** The unit this camera is following */
    private Player unitFollow;
    
    /** The width and height of the screen */
    /** Screen width, in pixels. */
    public final int screenwidth;
    /** Screen height, in pixels. */
    public final int screenheight;
    /** The number of pixels in a 1x1 tile */
    public final int pixelsPerTile;
    /** The total number of pixels of the whole map */
    public final int totalPixels;
    
    /** The x and y coordinates of the player needed to locate the position of the camera */
    private int xPos;
    private int yPos;
    /** The camera's position in the world in term of tiles, in x and y directions. */
    private double cameraTileX;
    private double cameraTileY;
    
    
    /** Returns the minimum x value on screen 
     */
    public int getMinX(){
    	return xPos - (screenwidth/2);
    }
    
    /** Returns the maximum x value on screen 
     */
    public int getMaxX(){
    	return xPos + (screenwidth/2);
    }
    
    /** Returns the minimum y value on screen 
     */
    public int getMinY(){
    	return yPos - (screenheight/2);
    }
    
    /** Returns the maximum y value on screen 
     */
    public int getMaxY(){
    	return yPos + (screenheight/2);
    }

    
    /**
     * Getter method to retrieve the tile number of the camera in the x direction on the left side of the window
     * @return The tile number for camera on the left side of the screen.
     */
    public int getCameraTileX(){
    	return (int)cameraTileX;
    }
    
    /**
     * Getter method to retrieve the tile number of the camera in the y direction on the left side of the window.
     * @return The tile number for camera on the left side of the screen.
     */
    public int getCameraTileY(){
    	return (int)cameraTileY;
    }
    
     /**
     * The position of the camera (pixels) in the x direction based on the position of the player.
     * (Updates when player moves)
     * @return The position of the camera in pixels.
     */
    public int getCameraPixelsY(){
    	//Setting up boundaries so the camera doesn't go out of bound in the x direction
    	if(getMinY() >= 0 && getMaxY() <= totalPixels){	
    		return yPos - (screenheight/2);
    	}
    	if(getMaxY() > 6912){
    		return totalPixels - screenheight;
    	}
    	if(getMinY() < 0){
    		return 0;
    	}
    	return 0;
    }
    
    /**
     * The position of the camera (pixels) in the y direction based on the position of the player.
     * (Updates when player moves)
     * @return The position of the camera in pixels.
     */
    public int getCameraPixelsX(){
    	//Setting up boundaries so the camera doesn't go out of bound in the y direction
    	if(getMinX() >= 0 && getMaxX() <= totalPixels){
    		return xPos - (screenwidth/2);
    	}											
    	if(getMaxX() > totalPixels){
    		return totalPixels - screenwidth;
    	}
    	if(getMinX() < 0){
    		return 0;
    	}
    	return 0;
    }
    
    
    /**
     * Number of pixels (less than 71)to be rendered in the x direction so the game plays smoothly.
     * Number of pixels that are yet enough to be rendered as a tile.
     * @return The number of pixels to be rendered to smoothen the game play.
     */
    public int getRemainderPosX(){
    	//The remainder of the pixel number of the camera in the x direction
    	return -(getCameraPixelsX()%pixelsPerTile);  
    }
    
    /**
     * Number of pixels (less than 71)to be rendered in the y direction so the game plays smoothly.
     * Number of pixels that are yet enough to be rendered as a tile.
     * @return The number of pixels to be rendered to smoothen the game play.
     */
    public int getRemainderPosY(){
    	//The remainder of the pixel number of the camera in the y direction
    	return -(getCameraPixelsY()%pixelsPerTile); 
    }

    /** Create a new World object. */
    public Camera(Player player, int screenwidth, int screenheight, int pixelsPerTile, int totalPixels)
    {   
    	this.screenheight = screenheight;
    	this.screenwidth = screenwidth;
    	this.xPos = (int) player.getXPos();	//To record the player's x and y position
    	this.yPos = (int) player.getYPos();
    	this.unitFollow = player;
    	this.pixelsPerTile = pixelsPerTile;
    	this.totalPixels = totalPixels;
    }

    /** Update the game camera to re-center it's viewpoint around the player 
     */
    public void update(double x, double y)
    throws SlickException
    {
    	this.xPos = (int) x;	//Update the player's position in the x direction
    	this.yPos = (int) y;	//Update the player's position in the y direction
    	followUnit(unitFollow);  	
    }
    
    /** Tells the camera to follow a given unit. 
     */
    public void followUnit(Object unit)
    throws SlickException
    {
    	this.cameraTileX = (getCameraPixelsX()/pixelsPerTile);
    	this.cameraTileY = (getCameraPixelsY()/pixelsPerTile);
    }
}