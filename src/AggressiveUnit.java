public abstract class AggressiveUnit extends Monster{
	
	/** Attack cool down time */
	private double coolDown;

	/**
	 * Method that reduces the HP of this unit if attacked by player
	 * @param unitPlayer The player who attacked this unit
	 */
	public void ifAttackedByPlayer(Player unitPlayer){
		if(this.getHealth()>0){
			if(withinRange(unitPlayer,50) == true){
				if(unitPlayer.getPlayerAttackStatus()==1){
					// Reduce health only if this unit is still alive
					this.setHealth(this.getHealth()-unitPlayer.getRandDamage());
					unitPlayer.resetCdTime();
				}
			}
		}
	}
	
	/**
	 * Update method to constantly update the movement of this unit.
	 * This unit just follows the player when the player is within range.
	 * @return an array containing movement in the x and y directions.
	 */
	public double[] movementUpdate(Player unitPlayer){
		//Only generate movement when unit is still alive
		if(this.getHealth()>0){
			//moveCalculate method is in the Monster class (abstract)
			this.move = this.moveCalculate(unitPlayer);
		}
		return this.move;
	}
	
	/**
	 * Attack player
	 * @param unitPlayer Unit to be attacked
	 */
	public void attackPlayer(Player unitPlayer){
		
		if(this.getHealth()>0){
			//cool down
			if(this.getCdTime() > 0){
				this.setCdTime(this.getCdTime()-0.5);
			}else{
				this.setCdTime(0);
			}
			//Attack only when cool down is 0 and player is within range
			if(this.getCdTime() == 0 && (withinRange(unitPlayer,35)==true)){
				unitPlayer.setHealth(unitPlayer.getHealth()-this.generateRand(0, (int)this.getDamage()));
				//reset cool down time after attack
				this.setCdTime(this.getCoolDown());
				//play sound when attack
				this.chop.play();
			}
		}
	}
	
	/**
	 * Getter to get the attack cool down time of this unit
	 * @return the attack cool down time
	 */
	public double getCoolDown(){
		return coolDown;
	}
	
	/**
	 * Setter to set the attack cool down time of this unit
	 * @param coolDown
	 */
	public void setCoolDown(double coolDown){
		this.coolDown = coolDown;
	}
}
