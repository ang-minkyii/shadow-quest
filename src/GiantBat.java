import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class GiantBat extends PassiveUnit {
	
	/** Image of Giant bat */
	public Image giantBatImage;
	/** Name of this monster */
	private String name = "Giant Bat";
	/** flag to flip the image of this unit only once when changing directions */
	public int flipped = 1;
	
	/**
	 * Instantiate the Giant Bat
	 * @param location Folder location of the image of this unit
	 * @param xPos The x location of this unit
	 * @param yPos The y location of this unit
	 * @param damage The damage of this unit
	 * @param health The hP of this unit
	 * @param cdTime THe attack cool down of this unit
	 * @throws SlickException
	 */
	public GiantBat(String location, int xPos, int yPos, double damage, double health, double cdTime)
	throws SlickException{
		giantBatImage = new Image(location);
       	die = new Sound("assets/sounds/pain.ogg");
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setDamage(damage);
		this.setHealth(health);
		this.setMaxHP(this.getHealth());
		this.setCdTime(cdTime);
		this.setMonsterType(0);
		this.setSpeed(0.15);
		this.setTime(1000);
		this.setMoveTime(0);
		this.setAttackedTime(this.getTime());
	}
	
	/**
	 * Update method to handle movement and actions of this unit
	 */
	public void update(Player unitPlayer, double dX, double dY) {
		
		//Flip the image of this unit when it walks in different directions
		if(dX < 0 && flipped == 1){
			giantBatImage = giantBatImage.getFlippedCopy(true, false);
			flipped = 0;
		}
		if(dX > 0 && flipped == 0){
			giantBatImage = giantBatImage.getFlippedCopy(true, false);
			flipped = 1;
		}
		attackedByPlayerAndRun(unitPlayer);
		movement(dX,dY);
		checkHealth(unitPlayer);
	}

	/**
	 * Draw this unit on the map
	 */
	public void drawUnit(float xaxis, float yaxis) {
		giantBatImage.draw(xaxis,yaxis);
		
	}

	/**
	 * Getter to get the name of this unit
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	
}
