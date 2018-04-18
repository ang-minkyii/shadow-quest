/**
 * Data Process class used to store data read from text file temporarily to be processed later.
 * @author Ang Mink Yii
 */
public class DataProcess {
	
	/** To store name */
	public String name;
	/** location in the x direction */
	public int xPos;
	/** location in the y direction */
	public int yPos;
	
	/**Instantiation of DataProcess type object */
	public DataProcess(String name, double xPos, double yPos){
		this.name = name;
		this.xPos = (int)xPos;
		this.yPos = (int)yPos;
	}
	
	/** Print data */
	public String toString(){
		return "name = " + this.name + " xPos,yPos = " + this.xPos + " " + this.yPos;
	}
}
