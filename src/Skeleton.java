import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Skeleton extends AggressiveUnit{
	
	/** Image of this unit */
	private Image skeletonImage;
	/**Name of this unit */
	private String name = "Skeleton";
	/** flag to flip the image only once per change of direction */
	public int flipped = 1;
	
	/**
	 * Instantiate the skeleton 
	 * @param folder directory to where the image can be found
	 * @param xPos Location of this unit in the x direction
	 * @param yPos Location of this unit in the y direction
	 * @param damage Damage of this unit to be set
	 * @param health HP of this unit to be set
	 * @param cdTime Cool down time of this unit to be set
	 * @throws SlickException
	 */
	public Skeleton(String folder, int xPos, int yPos, double damage, double health, double cdTime) 
	throws SlickException{
		skeletonImage = new Image(folder);
		chop = new Sound("assets/sounds/chop.ogg");
		die = new Sound("assets/sounds/pain.ogg");
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setDamage(damage);
		this.setHealth(health);
		this.setMaxHP(this.getHealth());
		this.setCdTime(cdTime);
		this.setCoolDown(this.getCdTime());
		this.setMonsterType(1);
	}
	
	/**
	 * Update this unit to interact with the player
	 */
	@Override
	public void update(Player unitPlayer, double dX, double dY) {
		// TODO Auto-generated method stub
		
		if(dX < 0 && flipped == 1){
			skeletonImage = skeletonImage.getFlippedCopy(true, false);
			flipped = 0;
		}
		if(dX > 0 && flipped == 0){
			skeletonImage = skeletonImage.getFlippedCopy(true, false);
			flipped = 1;
		}
		this.setXPos(this.getXPos()+dX);
		this.setYPos(this.getYPos()+dY);
		
		ifAttackedByPlayer(unitPlayer);
		attackPlayer(unitPlayer);
		checkHealth(unitPlayer);
	}
	
	/**
	 * Draw unit on the map
	 */
	@Override
	public void drawUnit(float xaxis, float yaxis) {
		skeletonImage.draw(xaxis,yaxis);	
	}
	
	/**
	 * Getter to get the name of this unit
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
