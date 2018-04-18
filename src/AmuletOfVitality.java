import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class AmuletOfVitality extends Item{
	
	/** Image of this item */
	private Image amuletImage;
	/** Increases the health of the player by 30% */
	private double healthRatio = 1.3;
	/** Health difference between before and after picking up this item */
	public double healthDifference;
	/** 20% reduce in health regeneration cool down time */
	public double regenRate = 0.8;
	
	/** Instantiate the Amulet of Vitality */
	public AmuletOfVitality(String folder, int xPos, int yPos) 
	throws SlickException{
		amuletImage = new Image(folder);
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setStatus(0);
	}

	/**
	 * Apply effect to the player
	 * Increases max HP and health regenerate rate
	 */
	public void applyEffect(Player unitPlayer) {
		if(this.getStatus() == 0){
			double increasedHealth = unitPlayer.getMaxHP()*healthRatio;
			healthDifference = increasedHealth - unitPlayer.getMaxHP();
			unitPlayer.setMaxHP(increasedHealth);
			unitPlayer.setHealth(unitPlayer.getHealth()+healthDifference);
			unitPlayer.setHealthRegen(regenRate);
			//Add items to player's inventory
			unitPlayer.addItemsToInventory(amuletImage);
			//Set such that player has this item in hand
			unitPlayer.setAmulet(1);
		}
	}

	/**
	 * Draw this item on the map
	 */
	public void drawItem(float xaxis, float yaxis) {
		if(this.getStatus() == 0){
			amuletImage.draw(xaxis,yaxis);
		}
		
	}
	
	/**
	 * Constantly update to detect player
	 */
	public void update(Player unitPlayer) {
		if(withinRange(unitPlayer,50)==true){
			applyEffect(unitPlayer);
			setStatus(1);
		}
	}
}
