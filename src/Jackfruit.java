import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
/**
 * Jackfruit class is to provide poisonous jackfruits on the map so when picked up, they gave the player an instant boost in HP but comes with a side effect
 * @author Andy
 */

public class Jackfruit extends Item{
	
	/** Image of this item */
	private Image jackfruit;
	/** Amount of HP to boost */
	public int freeHP = 10;
	/** Amount of HP to be added on to the player */
	public double addHP;
	/** Sound of eating and laughing */
	public Sound eat;
	public Sound laugh;
	/** Reduce Max HP */
	public double sideEffect;
	
	/**
	 *  Instantiate a jackfruit
	 * @param folder Directory to which the image can be found
	 * @param xPos Location in the x direction to place the jackfruit
	 * @param yPos Location in the y direction to place the jackfruit
	 * @throws SlickException
	 */
	public Jackfruit(String folder, int xPos, int yPos)
	throws SlickException{
		jackfruit = new Image(folder);
		eat = new Sound("assets/sounds/eat.ogg");
		laugh = new Sound("assets/sounds/laugh.ogg");
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setStatus(0);
		sideEffect = 2;
	}
	
	/**
	 * Add HP to player
	 */
	public void applyEffect(Player unitPlayer) {
		// TODO Auto-generated method stub
		if(this.getStatus() == 0){
			if(unitPlayer.getMaxHP()-unitPlayer.getHealth() > freeHP){
				unitPlayer.setHealth(unitPlayer.getHealth() + freeHP);
				eat.play();
			}else{
				addHP = unitPlayer.getMaxHP()-unitPlayer.getHealth();
				unitPlayer.setHealth(unitPlayer.getHealth()+addHP);
				eat.play();
			}
			//Decreases player's Max HP cause the player is poisoned
			unitPlayer.setHealth(unitPlayer.getHealth()-sideEffect);
			unitPlayer.setMaxHP(unitPlayer.getMaxHP()-sideEffect);
			laugh.play();
		}
	}
	
	/**
	 * Draw item on the map
	 */
	@Override
	public void drawItem(float xaxis, float yaxis) {
		// TODO Auto-generated method stub
		if(this.getStatus() == 0){
			jackfruit.draw(xaxis,yaxis);
		}
	}
	
	/**
	 * Update to detect player and to apply effect
	 */
	@Override
	public void update(Player unitPlayer) {
		// TODO Auto-generated method stub
		if(withinRange(unitPlayer,50) == true){
			if(unitPlayer.getHealth() < unitPlayer.getMaxHP()){
				applyEffect(unitPlayer);
				setStatus(1);
			}
		}
	}

}
