import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class SwordOfStrength extends Item{

	/** Image of this item */
	private Image swordImage;
	/** Increases the damage of player by 50% after being picked up */
	public double damageRatio = 1.5;

	/** Instantiate the Sword of Strength */
	public SwordOfStrength(String folder, int xPos, int yPos) 
	throws SlickException{
		swordImage = new Image(folder);
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setStatus(0);
	}
	
	/**
	 * Apply effect to the player by increasing its damage
	 */
	public void applyEffect(Player unitPlayer) {
		//Only do this if item hasn't been picked up
		if(this.getStatus() == 0){
			unitPlayer.setDamage(unitPlayer.getDamage()*damageRatio);
			unitPlayer.addItemsToInventory(swordImage);
			unitPlayer.setSword(1);
		}
	}
	
	/**
	 * Draw item on the map
	 */
	public void drawItem(float xaxis, float yaxis) {
		if(this.getStatus() == 0){
			swordImage.draw(xaxis,yaxis);
		}
	}
	
	/**
	 * Update to detect if player is within range so effect can be applied
	 */
	public void update(Player unitPlayer) {
		if(withinRange(unitPlayer,50)==true){
			applyEffect(unitPlayer);
			setStatus(1);
		}
	}
}
