import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Garth extends Villager{
	
	/** Image of this unit*/
	private Image garth;
	/** Name of this unit */
	private String name = "Garth";
	
	/**
	 * Instantiate Garth
	 * @param folder Directory of the image 
	 * @param xPos Location in the x direction
	 * @param yPos Location in the y direction
	 * @throws SlickException
	 */
	public Garth(String folder, double xPos, double yPos)
	throws SlickException{
		garth = new Image(folder);
		setXPos(xPos);
		setYPos(yPos);
		this.setHealth(this.generateRand(0, 100));
		this.setMaxHP(this.getHealth());
	}
	
	/**
	 * Draw unit on the map
	 */
	public void drawUnit(float xaxis, float yaxis){
		garth.draw(xaxis,yaxis);	
	}
	
	/** Speak to player if the player is within range
	 */
	public void playerWithinRange(Player unitPlayer){
		if(withinRange(unitPlayer,50)==true){
			speak(unitPlayer);
			setSpeakTime(Villager.speechLength);
		}else if(speakTimer() == false){
			setSpeak(null);
		}
	}
	
	/** 
	 * Dialogue 
	 */
	public void speak(Player unitPlayer){

		if(unitPlayer.getAmulet()==0){
			setSpeak("Find the Amulet Of Vitality across the river to the West. Be careful, the jackfruits are poisonous!");
		}else if(unitPlayer.getTome()==0){
			setSpeak("Find the Tome Of Agility, in the Land Of Shadow.");
		}else if(unitPlayer.getSword()==0){
			setSpeak("Find the Sword Of Strength, cross the river and back on the east.");
		}else{
			setSpeak("You have found all the treasure I know of.");
		}	
	}
	
	/**
	 * No actions here 
	 */
	public void interactWithPlayer(Player unitPlayer){
	}
	
	/**
	 * Getter to retrieve the name of this unit
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	/**
	 * Update to detect the location of player 
	 */
	public void update(Player unitPlayer) {
		// TODO Auto-generated method stub
		playerWithinRange(unitPlayer);
	}
}
