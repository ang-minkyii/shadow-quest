import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class TomeOfAgility extends Item{
	
	/** Image of this unit */
	private Image tomeImage;
	/** Ratio of speed to be increased */
	public double speedRatio = 0.5;
	
	/**
	 *  Instantiate the Tome of Agility
	 * @param folder Directory to which the image can be found
	 * @param xPos Location of item in the x direction
	 * @param yPos Location of item in the y direction
	 * @throws SlickException
	 */
	public TomeOfAgility(String folder, int xPos, int yPos) 
	throws SlickException{
		tomeImage = new Image(folder);
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setStatus(0);
	}
	
	/**
	 * Add items to inventory
	 * Increases player's attack cool down time
	 */
	@Override
	public void applyEffect(Player unitPlayer) {
		// TODO Auto-generated method stub
		if(this.getStatus() == 0){
			unitPlayer.setCdTime(unitPlayer.getCdTime()*speedRatio);
			unitPlayer.addItemsToInventory(tomeImage);
			unitPlayer.setTome(1);
		}
	
	}
	
	/**
	 * Draw item on the map
	 */
	@Override
	public void drawItem(float xaxis, float yaxis) {
		// TODO Auto-generated method stub
		if(this.getStatus() == 0){
			tomeImage.draw(xaxis,yaxis);
		}
	}
	
	/**
	 * Update to detect player within a certain range to apply effect on the player
	 */
	@Override
	public void update(Player unitPlayer) {
		// TODO Auto-generated method stub
		if(withinRange(unitPlayer,50)==true){
			applyEffect(unitPlayer);
			setStatus(1);
		}
	}
}
