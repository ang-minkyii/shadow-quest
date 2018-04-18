import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class PrinceAldric extends Villager {
	
	/** Image of this unit */
	private Image princeAldric;
	/** Name of this unit */
	private String name = "Prince Aldric";
	/** 1 if elixir is in hand, 0 otherwise */
	private int elixirInHand;
	
	/** Instantiate the Prince Aldric
	 * @param folder Directory to where the image can be found 
	 * @param xPos Location in the x direction
	 * @param yPos Location in the y direction
	 * @throws SlickException
	 */
	public PrinceAldric(String folder, double xPos, double yPos) 
	throws SlickException{
		princeAldric = new Image(folder);
		setXPos(xPos);
		setYPos(yPos);
		this.setHealth(this.generateRand(0, 100));
		this.setMaxHP(this.getHealth());
	}
	
	/**
	 * Draw this unit on the map
	 */
	public void drawUnit(float xaxis, float yaxis){
		princeAldric.draw(xaxis,yaxis);	
	}
	
	/**
	 * Interact and speak with the player if the player is within range
	 */
	public void playerWithinRange(Player unitPlayer){
		if(withinRange(unitPlayer,50)==true){
			speak(unitPlayer);
			interactWithPlayer(unitPlayer);
			setSpeakTime(Villager.speechLength);
		}else if(speakTimer() == false){
			setSpeak(null);
		}
	}
	
	/** Dialogue of this unit 
	 */
	 public void speak(Player unitPlayer){
		if(getElixir()==0){
			setSpeak("Please seek out the Elixir of Life to cure the king.");
		}else{
			setSpeak("The elixir! My father is cured! Thank you!");
		}
	}
	
	 /**
	  * Receive the elixir of life from the player
	  */
	public void interactWithPlayer(Player unitPlayer){
		if(unitPlayer.getElixir()==1){
			setElixir(1);
			unitPlayer.setElixir(0);
		}
	}
	
	/**
	 * Setter to set "elixirInHand" to 1 or 0
	 * @param elixir
	 */
	public void setElixir(int elixir){
		elixirInHand = elixir;
	}
	
	/** Getter to get the status of elixir of life
	 * @return "elixirInhand"
	 */
	public int getElixir(){
		return elixirInHand;
	}
	
	/**
	 * Getter to get the name of this unit
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Update this unit to detect if player is within range
	 */
	public void update(Player unitPlayer) {
		playerWithinRange(unitPlayer);
	}
}
