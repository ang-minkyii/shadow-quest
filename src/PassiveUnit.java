public abstract class PassiveUnit extends Monster{
	
	/** Set to 1 allows this unit to run */
	private int run = 0;
	/** Movement cool down and run cool down time */
	private double time;
	/** 3 seconds movement changing time */
	private double movementTime;
	private double attackedCooldown;
	
	/**
	 * Method to reduce this unit's health if attacked by player
	 * @param unitPlayer The player
	 */
	public void attackedByPlayerAndRun(Player unitPlayer){
		if(withinRange(unitPlayer,50) == true){
			//If player has attacked
			if(unitPlayer.getPlayerAttackStatus()==1){
				//If this unit is still alive
				if(this.getHealth()>0){
					//Set to 1 so this unit starts running
					this.setRun(1);
					this.setAttackedTime(this.getTime());
					this.setHealth(this.getHealth()-unitPlayer.getRandDamage());
					//Reset the player's cool down 
					unitPlayer.resetCdTime();
				}
			}
		}
	}
	
	/**
	 * Update the movement of this unit based on the player's movement
	 * @return An array containing movement of this unit
	 */
    public double[] movementUpdate(Player unitPlayer){
    		
    		//Counting 3 seconds
			this.setMoveTime(this.getMoveTime()-0.5);
    		if(this.getHealth()>0){
    			if(this.getRun() == 1){
    				this.move = this.moveCalculate(unitPlayer);
    			}else if(this.getRun() != 1 && this.getMoveTime()<0){
    				//If not attacked by player 
    				//Generate random movement every 3 seconds
    				this.move[0] = this.generateRand(-1,1);
    				this.move[1] = this.generateRand(-1,1);
    				//Reset the movement cool down time back to 3 seconds
    				this.setMoveTime(this.getTime());
    			}
    		}
    		return this.move;
    }
    
	/**
	 * Handles x and y movement from the movement array
	 * @param dX Movement in the x direction
	 * @param dY Movement in the y direction
	 */
	public void movement(double dX, double dY){
		//If attacked by player, run
		if(this.getRun()==1){
			runFromPlayer(dX,dY);
		}else if(this.getRun()==0){
			//Move randomly
			this.setXPos(this.getXPos() + dX*this.getSpeed());
			this.setYPos(this.getYPos() + dY*this.getSpeed());
		}
	}
    
	/**
	 * Setter to set the unit to either run or not run
	 * @param run
	 */
	public void setRun(int run){
		this.run = run;
	}
	
	/**
	 * Getter to get the run status of the unit
	 * @return
	 */
	public int getRun(){
		return run;
	}
	
	/**
	 * Setter to set the run and movement cool down time
	 * @param time
	 */
	public void setTime(double time){
		this.time = time;
	}
	
	/** Getter to get the run and movement cool down time
	 * @return The run and movement cool down time
	 */
	public double getTime(){
		return time;
	}
	
	/**
	 * Setter to set the cool down time after being attacked
	 * @param time Cool down time
	 */
	public void setAttackedTime(double time){
		this.attackedCooldown = time;
	}
	
	/**
	 * Getter to retrieve the cool down time after being attacked
	 * @return The cool down time 
	 */
	public double getAttackedTime(){
		return attackedCooldown;
	}
	
	/**
	 * Setter to set the movement cool down time 
	 * @param time The movement cool down time to be set
	 */
	public void setMoveTime(double time){
		this.movementTime = time;
	}
	
	/**
	 * Getter to get the movement cool down time
	 * @return The movement cool down time
	 */
	public double getMoveTime(){
		return movementTime;
	}
	
	/**
	 * Method to handle the x and y movement to run away from player
	 * @param dX Movement in the x direction
	 * @param dY Movement in the y direction
	 */
	public void runFromPlayer(double dX, double dY){
		if(this.getAttackedTime() > 0 && this.getRun() == 1){
			// Counting down the 5 seconds 
			this.setAttackedTime(this.getAttackedTime()-0.25);
			this.setXPos(this.getXPos()+dX);
			this.setYPos(this.getYPos()+dY);
		}else{
			//After 5 seconds and player hasn't attacked, stop running
			this.setAttackedTime(this.getTime());
			this.setRun(0);
		}
	}
}
