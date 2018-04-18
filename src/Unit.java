import java.util.Random;


public abstract class Unit extends WorldObject{
	
	/** Max health of unit */
	private double maxHP;
	/** Unit's current health points */
	private double unitHealth;
	/** Unit's current damage */
	private double unitDamage;
	/** Unit's current attack cool down time */
	private double cooldownTime;
	/** Unit's movement speed */
	private double speed;
	/**
	 * Method to draw unit 
	 * @param xaxis x location of unit to be drawn at
	 * @param yaxis y location of unit to be drawn at
	 */
	public abstract void drawUnit(float xaxis, float yaxis);
	
	/**
	 * Method to get the name of the unit
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * Setter to set the unit's current health
	 * @param unitHealth Health to be set
	 */
	public void setHealth(double unitHealth){
		this.unitHealth = unitHealth;
	}
	
	/**
	 * Setter to set the unit's max health point
	 * @param maxHP Max health points to be set
	 */
	public void setMaxHP(double maxHP){
		this.maxHP = maxHP;
	}
	
	/**
	 * Setter to set the unit's current damage
	 * @param unitDamage Damage to be set
	 */
	public void setDamage(double unitDamage){
		this.unitDamage = unitDamage;
	}
	
	/**
	 * Setter to set the unit's attack cool down time
	 * @param cooldownTime attack cool down time to be set
	 */
	public void setCdTime(double cooldownTime){
		this.cooldownTime = cooldownTime;
	}
	
	/**
	 * Getter to get the unit's max health points
	 * @return unit's max health points
	 */
	public double getMaxHP(){
		return maxHP;
	}
	
	/**
	 * Getter to get the unit's current health points
	 * @return the unit's current health points
	 */
	public double getHealth(){
		return unitHealth;
	}
	
	/**
	 * Getter method to get the unit's current damage
	 * @return
	 */
	public double getDamage(){
		return unitDamage;
	}
	
	/**
	 * Getter method to get the unit's current attack cool down time
	 * @return
	 */
	public double getCdTime(){
		return cooldownTime;
	}
	
	
	/**
	 * Method to set the speed of the unit
	 * @param speed
	 */
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	/** Getter to retrieve the speed of unit
	 * @return Speed of the unit's movement
	 */
	public double getSpeed(){
		return speed;
	}
	
	/**
	 * Getter method to retrieve random damage the unit would do
	 * @return A random number between 0 and the unit's MAX damage
	 */
	public double getRandDamage(){
		return generateRand(0,(int)unitDamage);
	}
	
	/** 
	 * A random number generator
	 * @param min minimum number to be generated 
	 * @param max maximum number to be generated
	 * @return
	 */
	public int generateRand(int min, int max){
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}