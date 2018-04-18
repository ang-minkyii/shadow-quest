import org.newdawn.slick.Sound;

public abstract class Monster extends Unit{
	
	/** variables needed to compute the algorithm given in the specs*/
	private double distX;
	private double distY;
	private double distTotal;
	private double amount;
	private double dX;
	private double dY;
	/** Type of monster, either passive or aggressive */
	/** Needed because the moveCalculate method returns movement in different directions depending on type of monsters */
	private int monsterType;
	/** An array to store the movement of this unit */
	public double move[] = new double[2];
	/** To set this unit to dead or alive */
	private int dead = 0;
	/** Sounds for attack and death */
	public Sound chop;
	public Sound die;
	
	/**
	 * Calculate movement of monster based on the algorithm given in the spec sheet
	 * @param unitPlayer Player
	 * @return An array containing movement of monster in the x and y directions
	 */
	public double[] moveCalculate(Player unitPlayer){
		//Algorithm given
		double moveArray[] = new double[2];
		amount = 0.15;
		setSpeed(amount);
		distX = (unitPlayer.getXPos()) - this.getXPos();
		distY = (unitPlayer.getYPos()) - this.getYPos();
		distTotal = Math.sqrt(Math.pow(distX,2) + Math.pow(distY,2));
		
		//Move-able x and y directions 
		dX = (distX/distTotal)*amount;
		dY = (distY/distTotal)*amount;
		
		if(withinRange(unitPlayer,150) == true){
			//Checking if monster is passive or aggressive
			if(this.monsterType == 0){
				//run away from player because this monster type is passive
				moveArray[0] = -dX;
				moveArray[1] = -dY;
			}else if(withinRange(unitPlayer,20)==false){  //Stops right before the player 
				//chase player because this monster type is aggressive
				moveArray[0] = dX;
				moveArray[1] = dY;
			}
		}
		return moveArray;
	}
	
	/**
	 * Check and set health of dead unit so the die sound will play 
	 */
	public void checkHealth(Player unitPlayer){
		//only play the die sound when health is below 0
		if(this.getHealth() <= 0 && this.getStatus() == 0){
			die.play();
			unitPlayer.setNumOfMonstersKilled(unitPlayer.getNumOfMonstersKilled()+1);
			this.setDead(1);
		}
	}
	
	/** To categorize different type of monster */
	public void setMonsterType(int type){
		monsterType = type;
	}
	
	/** Update to detect and interact with player */
	public abstract void update(Player unitPlayer, double dX, double dY);
	
	/** Update movement based on player's movement */
	public abstract double[] movementUpdate(Player unitPlayer);
	
	/** Set the monster status to dead so the "die" sound can play */
	public void setDead(int deadOrNot){
		dead = deadOrNot;
	}
	
	/**
	 * Getter to get the status of this monster, either dead or alive 
	 * @return The status of this monster. dead or alive
	 */
	public int getStatus(){
		return dead;
	}
	
	public double getAmount(){
		return amount;
	}
	
	/**
	 * To check if the player is within the specified range
	 * @param unitPlayer The player
	 * @param range Range specified
	 * @return True if player is within specified range, false if opposite
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
}
