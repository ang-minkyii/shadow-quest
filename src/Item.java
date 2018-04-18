import java.util.Random;

public abstract class Item extends WorldObject{
	
	/** 1 if item has been picked up, 0 otherwise */
	private int taken;
	/** Abstract methods of all items */
	public abstract void applyEffect(Player unitPlayer);
	public abstract void drawItem(float xaxis, float yaxis);
	public abstract void update(Player unitPlayer);
	
	/**
	 * Method to detect if player is within specified range
	 * @param unitPlayer The player
	 * @param range The range specified
	 * @return true is player is within range, 0 otherwise
	 */
	public boolean withinRange(Player unitPlayer, int range){
		double xDistance = Math.abs(unitPlayer.getXPos() - this.getXPos());
		double yDistance = Math.abs(unitPlayer.getYPos() - this.getYPos());
		double totalDistance = Math.sqrt(Math.pow(xDistance,2) + Math.pow(yDistance,2));
		
		if(totalDistance <= range){
			return true;
		}
		return false;
	}
	
	/** Generate a random number between specified min and max
	 * @param min Minimum number to be generated
	 * @param max Maximum number to be generated
	 * @return A random number
	 */
	public int generateRand(int min, int max){
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/** To set if the item has been picked up
	 * @param status Status of the item
	 */
	public void setStatus(int status){
		taken = status;
	}
	
	/**
	 * Getter to get the status of the item
	 * @return Status of the item
	 */
	public int getStatus(){
		return taken;
	}
}
