import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Draelic extends AggressiveUnit{
	
	/** Image of this unit */
	private Image draelicImage;
	/** Name of this unit */
	private String name = "Draelic";

	/** Instantiate the Draelic */
	public Draelic(String location, int initialXPos, int initialYPos, double damage, double health, double cdTime)
	throws SlickException{
		draelicImage  = new Image(location);
		chop = new Sound("assets/sounds/chop.ogg");
		die = new Sound("assets/sounds/pain.ogg");
		this.setXPos(initialXPos);
		this.setYPos(initialYPos);
		this.setDamage(damage);
		this.setHealth(health);
		this.setMaxHP(this.getHealth());
		this.setCdTime(cdTime);
		this.setCoolDown(this.getCdTime());
		this.setMonsterType(1);
	}
	
	/**
	 * Update to interact with player
	 */
	@Override
	public void update(Player unitPlayer, double dX, double dY) {
		// TODO Auto-generated method stub
		this.setXPos(this.getXPos()+dX);
		this.setYPos(this.getYPos()+dY);
		
		ifAttackedByPlayer(unitPlayer);
		attackPlayer(unitPlayer);
		checkHealth(unitPlayer);
	}
	
	/** Draw this unit on the map */
	@Override
	public void drawUnit(float xaxis, float yaxis) {
		draelicImage.draw(xaxis,yaxis,1.5f);
		
	}
	
	/** Getter to get the name of this unit */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
