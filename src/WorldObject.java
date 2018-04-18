
public abstract class WorldObject {
	
	/** Total number of pixels of the map */
	public static final int totalPixels = 6900;
	
	/** x and y location of this WorldObject */
	private double xPos;
	private double yPos;
	
	/**
	 * Getter method to retrieve the x location 
	 * @return The x location
	 */
	public double getXPos(){
		return xPos;
	}
	
	/**
	 * Getter method to retrieve the y location
	 * @return The y location
	 */
	public double getYPos(){
		return yPos;
	}
	
	/**
	 * Set the x location
	 * @param xPos X location to be set
	 */
	public void setXPos(double xPos){
		this.xPos = xPos;
	}
	
	/**
	 * Set the y location
	 * @param yPos Y location to be set
	 */
	public void setYPos(double yPos){
		this.yPos = yPos;
	}
}
	
	
