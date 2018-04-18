import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ElixirOfLife extends Item {
	
	/** Image for elixir */
	private Image elixirImage;
	
	/** Instantiate the elixir of life */
	public ElixirOfLife(String folder, int xPos, int yPos) 
	throws SlickException{
		elixirImage = new Image(folder);
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setStatus(0);
	}
	
	/**
	 * Add items to player's inventory
	 */
	@Override
	public void applyEffect(Player unitPlayer) {
		// TODO Auto-generated method stub
		if(this.getStatus()==0){
			unitPlayer.addItemsToInventory(elixirImage);
			unitPlayer.setElixir(1);
		}
	}

	/**
	 * Draw item on map
	 */
	public void drawItem(float xaxis, float yaxis) {
		// TODO Auto-generated method stub
		if(this.getStatus() == 0){
			elixirImage.draw(xaxis,yaxis);
		}
	}

	/**
	 * Update to detect player and apply effect on player
	 */
	public void update(Player unitPlayer) {
		// TODO Auto-generated method stub
		if(withinRange(unitPlayer,50)==true){
			applyEffect(unitPlayer);
			setStatus(1);
		}
	}
}
