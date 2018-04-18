import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Zombie extends AggressiveUnit{
	
	/** Image of this unit */
	private Image zombieImage;
	/** Name of this unit */
	private String name = "Zombie";
	/** flag to flip the image only once per change in direction */
	public int flipped = 1;
	
	/** Instantiate the zombie */
	public Zombie(String draelic, int xPos, int yPos, double damage, double health, double cdTime) 
	throws SlickException{
		zombieImage = new Image(draelic);
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
	 * Update this unit to flip image and also interact with the player
	 */
	@Override
	public void update(Player unitPlayer, double dX, double dY) {
		// TODO Auto-generated method stub
		if(dX < 0 && flipped == 1){
			zombieImage = zombieImage.getFlippedCopy(true, false);
			flipped = 0;
		}
		if(dX > 0 && flipped == 0){
			zombieImage = zombieImage.getFlippedCopy(true, false);
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
		zombieImage.draw(xaxis,yaxis);	
	}
	
	/** Getter to get the name of this unit 
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	
}
