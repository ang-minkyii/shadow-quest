import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;


public class Elvira extends Villager{
	/** Image of elvira */
	private Image elvira;
	/** name of this unit */
	private String name = "elvira";
	/** Sound of healing */
	public Sound heal;
	/** flag to play the sound only once */
	public int playSound = 1;
	/**
	 * Instantiate Elvira
	 * @param folder Location of the image in the package
	 * @param xPos Location of elvira in the x direction
	 * @param yPos Location of elvira in the y direction
	 * @throws SlickException
	 */
	public Elvira(String folder, double xPos, double yPos)
	throws SlickException{
		elvira = new Image(folder);
		heal = new Sound("assets/sounds/recovery.ogg");
		setXPos(xPos);
		setYPos(yPos);
		//Randomly set the health of this unit as it is not important
		this.setHealth(this.generateRand(0, 100));
		this.setMaxHP(this.getHealth());
	}
	
	/**
	 * Draw unit on the map 
	 */
	public void drawUnit(float xaxis, float yaxis){
		elvira.draw(xaxis,yaxis);	
	}
	
	/**
	 * Detect if player is within range and interacts with it
	 */
	public void playerWithinRange(Player unitPlayer){
		if(withinRange(unitPlayer,50)==true){
			speak(unitPlayer);
			interactWithPlayer(unitPlayer);
			//Set speak time to be around 4 seconds
			setSpeakTime(Villager.speechLength);
		}else if(speakTimer() == false){
			//Set speak to null after 4 seconds
			setSpeak(null);
			playSound = 1;
		}
	}
	
	/**
	 * Speak to the player
	 */
	public void speak(Player unitPlayer){
		if(unitPlayer.getHealth()==unitPlayer.getMaxHP()){
			setSpeak("Return to me if you ever need healing. Think wisely before you eat the fruits!");
		}else{
			setSpeak("You're looking much healthier now.");
		}
	}
	
	/**
	 * Heals the player
	 */
	public void interactWithPlayer(Player unitPlayer){
		if(unitPlayer.getHealth() < unitPlayer.getMaxHP()){
			unitPlayer.setHealth(unitPlayer.getHealth()+0.02);
			if(playSound == 1){
				heal.play(1.0f,0.3f);
				playSound = 0;
			}
		}else{
			unitPlayer.setHealth(unitPlayer.getMaxHP());
		}
	}
	
	/**
	 * Return the name of this unit
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Update this unit to allow interactions
	 */
	public void update(Player unitPlayer) {
		playerWithinRange(unitPlayer);
	}
}
