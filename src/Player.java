import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Player extends Unit{
	
	/** Running time allowed to run */
	public final static double allowableRunningTime = 1000;
	/** Presence of an item */
	public final static int NOTPRESENT = 0;
	/** Improvements for each level up */
	public final static double INCREASEDHP = 1.05;
	public final static double REDUCEDCOOLDOWN = 0.97;
	public final static double INCREASEDDAMAGE = 1.08;
	public final static double INCREASEDHPREGEN = 0.97;
	/** Running speed of the player */
	private double runSpeed;
	/** Length of time left available to run, this decreases over time */
	private double runTime = allowableRunningTime;
	private double timer = 0;
	/** flag to allow player to face different directions */
	private int flipped = 1;
	/** Variable to determine if player is allowed to attack */
	private int attack;
	
	/** Variables to make Player's health regeneration function */
	private double coolDown;
	private double healthCoolDown;
	private double healthRegenerate;
	
	/** Health regeneration rate of the player */
	private double rate;
	/** Player's inventory */
	private Image[] inventory;
	/** Number to indicate the inventory slot, increases when an item is picked up */
	private int order = 0;
	
	/** To determine if these items have been picked up */
	private int gotElixir;
	private int gotAmulet;
	private int gotTome;
	private int gotSword;
	/** Name of this player */
	public String name = "Andy";
	
	/** Declaration of an image type object */
	public Image object;
	public Image panel;
	
	/** Sound of certain actions */
	public Sound getSlashed;
	public Sound gameover;
	public Sound pickUp;
	public Sound levelUp;
	
	/** level of the player */
	public int level;
	/** Number of monsters killed */
	public double monstersKilled;
	/** Number of monsters required to be killed to level up */
	public double levelUpRequirement;
	/** Increases the difficulty by increasing the level up requirement */
	public double increasedDifficulty;
	
	/**
	 * Method to draw a player in the World.java.
	 * @param xaxis The x location on the map.
	 * @param yaxis The y location on the map.
	 */
	public void drawUnit(float xaxis, float yaxis){
		if(this.getHealth()>0){
			object.draw(xaxis,yaxis);
		}else{
			gameover.play();
			object.draw((float)World.initialXPos,(float)World.initialYPos);
			this.setXPos(World.initialXPos);
			this.setYPos(World.initialYPos);
			this.setHealth(this.getMaxHP());
		}
	}
	
	/**
	 * Create a player.
	 * @param playerImage The desired image of the player.
	 * @throws SlickException
	 */
	public Player(String playerImage, double initialXPos, double initialYPos, int damage, int health, double cdTime, double regenRate)
	throws SlickException
	{
		object = new Image(playerImage);
		panel = new Image("assets/panel.png");
		getSlashed = new Sound("assets/sounds/slash.ogg");
		gameover = new Sound("assets/sounds/gameover.ogg");
		pickUp = new Sound("assets/sounds/pickup.ogg");
		levelUp = new Sound("assets/sounds/chipquest.ogg");
		inventory = new Image[4];
		setXPos(initialXPos);
		setYPos(initialYPos);
		this.setDamage(damage);
		this.setHealth(health);
		this.setMaxHP(health);
		this.setCdTime(cdTime);
		this.setRate(1);
		this.setSpeed(0.25);
		this.setLevel(1);
		this.setNumOfMonstersKilled(0);
		this.setElixir(NOTPRESENT);
		this.setTome(NOTPRESENT);
		this.setSword(NOTPRESENT);
		this.setAmulet(NOTPRESENT);
		healthRegenerate = regenRate;
		healthCoolDown = healthRegenerate;
		coolDown = this.getCdTime();
		levelUpRequirement = 3;
		increasedDifficulty = 2;
	}
	
	/**
	 *Attack monsters if the attack button is pressed
	 * @param permissionToAttack Attack variable passed from World and RPG
	 */
	public void attackMonster(int permissionToAttack){
		//Attack cool down
		if(coolDown > 0){
			coolDown = coolDown - 0.5;
		}else{
			coolDown = 0;
		}
		
		//Attack monster once cool down has decreased to zero
		//Attack if attack button is pressed
		if(coolDown == 0 && permissionToAttack == 1){
			attack = 1;
		}else{
			attack = 0;
		}
	}
	
	/**
	 * Health regeneration function, rate is about 3-4 seconds
	 */
	public void regenerateHealth(){
		//Cool down timer for health to regenerate
		if(healthCoolDown > 0){
			healthCoolDown -= 0.0001;
		}else if(this.getHealth() < this.getMaxHP()){
			this.setHealth(this.getHealth()+rate);
			healthCoolDown = healthRegenerate;
		}else if(this.getHealth() > this.getMaxHP()){
			this.setHealth(this.getMaxHP());
		}
	}
	/**
	 * Update the position of the player on the map.
	 * @param dir_x The player's movement in the x direction.
	 * @param dir_y The player's movement in the y direction.
	 * @param blockArray An array which has the tile property of the whole map.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void update(double dir_x, double dir_y, int delta, int permissionToAttack, int run){
		
		levelUpdate();
		//Face left if player is going left
		if(dir_x < 0 && flipped == 1){
			object = object.getFlippedCopy(true, false);
			flipped = 0;
		}
		//Face right if player is going right
		if(dir_x > 0 && flipped == 0){
			object = object.getFlippedCopy(true, false);
			flipped = 1;
		}
		//Return the run speed
		runSpeed = runSpeedCalculator(run,delta);
		
		//Player's movement
		setXPos(getXPos() + dir_x*runSpeed);
		setYPos(getYPos() + dir_y*runSpeed);
		
		//Health regeneration
		regenerateHealth();
		
		//Attack monster
		attackMonster(permissionToAttack);
	}
	
	/**
	 * Handles the player's level
	 * Improves damage, HP, cool down time and health regenerate rate
	 */
	public void levelUpdate(){
		if(getNumOfMonstersKilled() >= levelUpRequirement){
			levelUp.play();
			level += 1;
			setNumOfMonstersKilled(0);
			levelUpRequirement += increasedDifficulty;
			increasedDifficulty += 1;
			this.setMaxHP(this.getMaxHP()*INCREASEDHP);
			this.setDamage(this.getDamage()*INCREASEDDAMAGE);
			this.setCdTime(this.getCdTime()*REDUCEDCOOLDOWN);
			this.setHealthRegen(INCREASEDHPREGEN);
		}
	}
	
	/**
	 * Getter method to retrieve the requirement to level up in terms of number of monsters killed 
	 * @return Required number of monsters killed to level up
	 */
	public double getLevelUpRequirement(){
		return levelUpRequirement;
	}
	
	/**
	 * Calculate the speed of which the Player should run at 
	 * @param delta
	 * @return Return the speed the Player should run at depending on the cool down time and also pressed run button
	 */
	public double runSpeedCalculator(int run, int delta){
		
		//Decides the running speed
		if(runTime > 0 && run == 1){
			runTime -= 1;
			timer = runTime;
			delta = 2;
		}else if(runTime < allowableRunningTime){
			runTime += 0.2;
			delta = 1;
		}else{
			delta = 1;
		}
		return delta*this.getSpeed();
	}
	
	/**
	 * Add items to inventory
	 * @param item Items to be added to the inventory
	 */
	public void addItemsToInventory(Image item){
		inventory[order] = item;
		
		//increase only when an item has been added so the next time will go into the next slot
		order += 1;
		pickUp.play();
	}
	/**
	 * Getter method to get the Player's inventory
	 * @return
	 */
	public Image[] getInventory(){
		return inventory;
	}
	
	/**
	 * Setter to set the health regeneration time
	 * @param decreasedTimeRatio Player's new health regeneration rate
	 */
	public void setHealthRegen(double decreasedTimeRatio){
		healthRegenerate = healthRegenerate*decreasedTimeRatio;
	}
	
	/**
	 * Getter to get Player's health regeneration rate
	 * @return
	 */
	public double getHealthRegen(){
		return healthRegenerate;
	}
	
	/**
	 * Getter to determine if player wants to attack
	 * @return
	 */
	public int getPlayerAttackStatus(){
		return attack;
	}
	
	/**
	 * Getter to retrieve remaining running time
	 * @return Remaining running time left
	 */
	public double getRunTime(){
		return runTime;
	}
	
	/**
	 * Getter to get the time taken for the running bar to refill
	 * @return The time taken for the running bar to refill
	 */
	public double getTimer(){
		return timer;
	}
	
	/** Method to reset the attack cool down time */
	public void resetCdTime(){
		coolDown = this.getCdTime();
		getSlashed.play();
	}
	
	/**
	 * Method to indicate that the player has just picked up the Elixir of Life
	 * @param elixir
	 */
	public void setElixir(int elixir){
		gotElixir = elixir;
	}
	
	/**
	 * Getter to determine if the player has already got the elixir
	 * @return
	 */
	public int getElixir(){
		return gotElixir;
	}
	
	/**
	 * Method to indicate that the player has just picked up the Amulet of Vitality
	 * @param amulet
	 */
	public void setAmulet(int amulet){
		gotAmulet = amulet;
	}
	
	/**
	 * Getter to determine if the player has already got the amulet
	 * @return
	 */
	public int getAmulet(){
		return gotAmulet;
	}
	
	/** 
	 * Method to indicate that the player has just picked up the Tome of Agility
	 * @param tome
	 */
	public void setTome(int tome){
		gotTome = tome;
	}
	
	/**
	 * Getter to determine if the player has already got the tome
	 * @return
	 */
	public int getTome(){
		return gotTome;
	}
	
	/**
	 * Method to indicate that the player has already got the Sword of Strength
	 * @param sword
	 */
	public void setSword(int sword){
		gotSword = sword;
	}
	
	/**
	 * Getter to determine if the player has already got the sword
	 * @return
	 */
	public int getSword(){
		return gotSword;
	}
	
	/**
	 * Setter to set the rate at which the Player's health regenerates
	 * @param rate
	 */
	public void setRate(int rate){
		this.rate = rate;
	}
	
	/**
	 * Set the level of the player
	 * @param level Level of the player
	 */
	public void setLevel(int level){
		this.level = level;
	}
	
	/**
	 * Getter to retrieve the level of player
	 * @return The level of the player
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * Set the number of monsters killed
	 * @param num Number of monsters killed
	 */
	public void setNumOfMonstersKilled(double num){
		this.monstersKilled = num;
	}
	
	/**
	 * Getter to get the number of monsters killed
	 * @return The number of monsters killed
	 */
	public double getNumOfMonstersKilled(){
		return monstersKilled;
	}
	
	/**
	 * Retrieve the name of this unit
	 */
	public String getName() {
		return name;
	}
}
