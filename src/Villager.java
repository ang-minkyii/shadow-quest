


public abstract class Villager extends Unit{
	
	/** Allowable time to speak */
	public final static double speechLength = 800;
	/** Dialogue */
	private String speak;
	/** Record the time this unit has spoken */
	private double speakTime;
	
	/** Abstract method which will be explained in each villager */
	public abstract void interactWithPlayer(Player unitPlayer);
	public abstract void speak(Player unitPlayer);
	public abstract void playerWithinRange(Player unitPlayer);
	public abstract void update(Player unitPlayer);
	
	/**
	 * To detect if player is within a specified range 
	 * @param unitPlayer The player
	 * @param range Specified by user the range of detection
	 * @return True if player is within the specified range
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
	
	/**
	 * Method to keep track of the dialogue display time
	 * @return True if dialogue is still allowed to be displayed
	 */
	public boolean speakTimer(){
		if(speakTime > 0){
			speakTime -= 0.5;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Setter to set the dialogue display time
	 * @param speakTime
	 */
	public void setSpeakTime(double speakTime){
		this.speakTime = speakTime;
	}
	
	/** Getter to retrieve the dialogue display time
	 * @return The dialogue display time
	 */
	public double getSpeakTime(){
		return speakTime;
	}
	
	/**
	 * Getter to get the dialogue text
	 * @return
	 */
	public String getSpeak(){
		return speak;
	}
	
	/**
	 * Set the dialogue text
	 * @param speak
	 */
	public void setSpeak(String speak){
		this.speak = speak;
	}
}
