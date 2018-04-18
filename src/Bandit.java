import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Bandit extends AggressiveUnit {
	
	/** Image of this unit */
	private Image banditImage;
	/** Name of this unit */
	private String name = "Bandit";
	/** flag to make the image flips only once per change in direction */
	public int flipped = 1;
	
	/** Instantiate the bandit  */
	public Bandit(String location, int xPos, int yPos, double damage, double health, double cdTime) 
	throws SlickException{
		banditImage = new Image(location);
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
	 * Update the unit to interact with the player
	 */
	public void update(Player unitPlayer, double dX, double dY) {
		// TODO Auto-generated method stub
		if(dX < 0 && flipped == 1){
			banditImage = banditImage.getFlippedCopy(true, false);
			flipped = 0;
		}
		if(dX > 0 && flipped == 0){
			banditImage = banditImage.getFlippedCopy(true, false);
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
	public void drawUnit(float xaxis, float yaxis) {
		banditImage.draw(xaxis,yaxis);
	}

	/**
	 * Getter to retrieve the name of this unit
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	
}
