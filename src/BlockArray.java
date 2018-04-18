import java.util.Random;

import org.newdawn.slick.tiled.TiledMap;

/**
 * Create and initialize an array to record the map properties (I.e block or not block)
 * @author Ang Mink Yii
 */
public class BlockArray {
	
	/** x and y locations divided by pixelsPerTile */
	private int xTile;
	private int yTile;
	/** total number of pixels and number of pixels per tile */
	private int totalPixels;
	private int pixelsPerTile;
	/** A variable to record the ID of map tiles. */
	private int tileId;
	/** A variable of type String to record the output of the method TiledMap.getTileProperty(). */
	private String tileProperty;
	/** An array of type String  */
	private String[][] blockArray;
	/** An array to store the x and y locations generated in the location generator method */
	int[] locationArray = new int[2];
    
	/**
	 * Initialize and create an array of type String with a desired size to store properties of the map parsed in.
	 * @param sizeOfArray The desired size of the array.
	 * @param map The map parsed in.
	 */
    public BlockArray(int sizeOfArray, TiledMap map, int pixelsPerTile, int totalPixels){
       
    	this.pixelsPerTile = pixelsPerTile;
    	this.totalPixels = totalPixels;
    	blockArray = new String[sizeOfArray][sizeOfArray];
    	
    	//Loop to access each block of the array
    	for(int i=0;i<sizeOfArray;i++){
        	for(int j=0;j<sizeOfArray;j++){
        		tileId = map.getTileId(i,j,0);
        		tileProperty = map.getTileProperty(tileId, "block","0");
        		
        		//Record the map properties tile by tile
        		blockArray[i][j] = tileProperty;
        	}
        }
    }
    
    /**
     * Method to check if units are about to walk into a blocked area
     * @param unit Unit to be checked
     * @param dir_x movement of the unit in the x direction
     * @param dir_y movement of the unit in the y direction
     * @return true or false
     */
    public boolean update(Unit unit, double dir_x, double dir_y){
    	
    	int pixelsWithinFrame = 6900;
    	
    	//Current coordinates of the player + updated movement in the x and y directions.
    	int xIncrement = (int)(unit.getXPos() + dir_x);		
    	int yIncrement = (int)(unit.getYPos() + dir_y);
    	
    	//New tile position of the player in the x and y directions.
    	int xLocation = xIncrement/pixelsPerTile;	
    	int yLocation = yIncrement/pixelsPerTile;
    	
    	if(xIncrement >= 0 && xIncrement < pixelsWithinFrame){
    		if(yIncrement >= 0 && yIncrement < pixelsWithinFrame){
    			if(blockArray[xLocation][yLocation] == "0"){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * The sliding wall approach
     * @param unit Any unit in the World
     * @param dir_x movement in the x direction
     * @param dir_y movement in the y direction
     */
    public void smoothenMovement(Unit unit, double dir_x, double dir_y){

    	int xLocation = (int)((unit.getXPos()+dir_x)/pixelsPerTile);	
    	int yLocation = (int)((unit.getYPos()+dir_y)/pixelsPerTile);
    	
    	//Check if the unit is blocked in the x or y direction
    	if(blockArray[(int)(unit.getXPos()/pixelsPerTile)][yLocation] != "0"){
    			//Slide along the wall
        		unit.setXPos(unit.getXPos()+dir_x*unit.getSpeed());
        		if(dir_y > 0){
        			unit.setYPos(unit.getYPos()-dir_y*unit.getSpeed());
        		}
        		if(dir_y < 0){
        			unit.setYPos(unit.getYPos()-dir_y*unit.getSpeed());
        		}
    	}
    	//Check if the unit is blocked in the x or y direction
    	if(blockArray[xLocation][(int)(unit.getYPos()/pixelsPerTile)] != "0"){
    		//Slide along the wall
    		unit.setYPos(unit.getYPos()+dir_y*unit.getSpeed());
    		if(dir_x > 0){
    			unit.setXPos(unit.getXPos()-dir_x*unit.getSpeed());
    		}
    		if(dir_x < 0){
    			unit.setXPos(unit.getXPos()-dir_x*unit.getSpeed());
    		}
    	}	
    }
    
    /**
     * Method to generate a random position
     * @param min Minimum number to be generated
     * @param max Maximum number to be generated
     * @return A random number between min and max
     */
	public int generatePos(int min, int max){
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/**
	 * Method to randomly generate a location which isn't blocked on the map.
	 * Used to generate random monsters/items if necessary but in this assignment, we were 
	 * already given the locations of every monster.
	 * @return A random unblocked location on the map
	 */
	public int[] locationGenerator(){
		this.xTile = generatePos(0,totalPixels)/pixelsPerTile;
		this.yTile = generatePos(0,totalPixels)/pixelsPerTile;
		
		while(checkBound(xTile,yTile)==true || blockArray[xTile][yTile] != "0"){
			xTile = generatePos(0,totalPixels)/pixelsPerTile;
			yTile = generatePos(0,totalPixels)/pixelsPerTile;
		}
		
		locationArray[0] = xTile*pixelsPerTile;
		locationArray[1] = yTile*pixelsPerTile;
		return locationArray;	
	}
	
	/**
	 * Method to check if the location generated is outside the boundaries of the map
	 * @param xTile 
	 * @param yTile
	 * @return true if its out of boundaries and false if its inside the boundaries
	 */
	public boolean checkBound(int xTile, int yTile){
		if(xTile >= (totalPixels/pixelsPerTile) || yTile >= (totalPixels/pixelsPerTile)){
			return true;
		}
		return false;
	}
	
    /**
     * Getter method to retrieve the blockArray.
     * @return The blockArray of String type.
     */
    public String[][] getArray(){
    	return blockArray;
    }
}
