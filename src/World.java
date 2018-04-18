/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    /** Create a new World object. */
    public final int screenwidth;
    public final int screenheight;
    /** The offset value when drawing player on the x-axis. */
    public static final int xOffset = 36;
    /** The offset value when drawing player on the y-axis. */
    public static final int yOffset = 50;
    /** The total number of pixels on the map */
    public static final int totalPixels = 6912;
    /** The number of pixels in a 1x1 tile */
    public static final int pixelsPerTile = 72;
    /** Initial position of the player on the x-axis */
    public static final double initialXPos = 795.0;
    /** Initial position of the player on the y-axis */
    public static final double initialYPos = 684.0;
    /** The total number of tiles representable by the map */
    public static final int numOfTiles = totalPixels/pixelsPerTile;
    /** Delimiters used for parsing data */
	public static final String delim1 = ",";
	public static final String delim2 = " ";
	/** number of spaces between the monster type and their locations in the data.txt*/
	public static final int numOfSpaces = 7;
	
    /** Declare a new player */
	private Player unitPlayer;
	/** Declare a new Tiled map */
	private TiledMap map;
	/** Declare a new camera */
	private Camera cam;
	/** Declare a new blockArray */
	private BlockArray blockedTile;
	/** Declare every unit type */
	private PrinceAldric[] prince;
	private Elvira[] shaman;
	private Garth[] peasant;
	private Zombie[] zombie;
	private Bandit[] bandit;
	private Skeleton[] skeleton;
	private GiantBat[] giantBat;
	private Draelic[] draelic;
	private Jackfruit[] jackfruit;
	private AmuletOfVitality amulet;
	private SwordOfStrength sword;
	private TomeOfAgility tome;
	private ElixirOfLife elixir;
	
	/** Used for data parsing, incl. string tokenizer, string and file name */
	public Scanner scan;
	public String newUnit;
	public StringTokenizer tok, token;
	public File file;
	public static int count;
	
	/** Number of each units used for initialization */
	public int numOfZombie;
	public int numOfBandit;
	public int numOfGiantBat;
	public int numOfSkeleton;
	public int numOfDraelic;
	public int numOfPrince;
	public int numOfElvira;
	public int numOfGarth;
	
	/** Arraylist used to store an unknown number of units */
	public ArrayList<DataProcess> list;
	
	
	/**
	 * Create new objects for the world
	 * @param screenwidth  The screen width defined in RPG class.
	 * @param screenheight The screen height defined in RPG class.
	 * @throws SlickException
	 */
    public World(int screenwidth, int screenheight)
    throws SlickException
    {
    	//Initialize new objects in this World
    	map = new TiledMap("assets/map.tmx","assets"); 
    	unitPlayer = new Player("assets/units/player.png",initialXPos,initialYPos,26,100,600,0.20);
    	cam = new Camera(unitPlayer,screenwidth,RPG.screenheight-RPG.PANELHEIGHT,pixelsPerTile,totalPixels);
    	blockedTile = new BlockArray(numOfTiles,map,pixelsPerTile,totalPixels);
    	amulet = new AmuletOfVitality("assets/items/amulet.png",965,3563);
    	sword = new SwordOfStrength("assets/items/sword.png",4791,1263);
    	tome = new TomeOfAgility("assets/items/book.png",546,6707);
    	elixir = new ElixirOfLife("assets/items/elixir.png",1976,402);
    	list = new ArrayList<>();
    	//Read data
    	readData("assets/data/data.txt");
    	
    	//Generate units after data has been read
    	unitGenerator();
    	fruitGenerator(10);
    	
    	//Initialize screen width and screen height
    	this.screenwidth = screenwidth;
    	this.screenheight = screenheight;
    }
    
    /**
     * Read data from data.txt file given
     * @param location Folder location of the data.txt file
     */
    public void readData(String location){
    	//Variables to store the location data from data.txt and add them into the arraylist
    	double yLocation;
    	double xLocation;
    	file = new File(location);
		try {
			scan = new Scanner(file);
			
			//Start scanning every line of data.txt file
			while(scan.hasNextLine()){
				newUnit = scan.nextLine();
				
				//Tokenize the string without Space as a delimiter, mainly to collect the location in the y direction
				tok = new StringTokenizer(newUnit,delim1,true);
				while(tok.hasMoreTokens()==true){
					String s1 = tok.nextToken();
					tok.nextToken();
					yLocation = Double.parseDouble(tok.nextToken());
					
					//Tokenize the string the second time to separate data with spaces between them
					token = new StringTokenizer(s1,delim2,true);
					String name = token.nextToken();
					//Data given has about 7 spaces in each line 
					count = numOfSpaces;
					while(count > 0){
						token.nextToken();
						count -= 1;
					}
					//After 7 spaces, store the location in the x direction
					xLocation = Double.parseDouble(token.nextToken());
					
					//Count the number of each type of unit by their names
					unitCount(name);
					
					//Add data from each line to an arraylist to be processed later
					list.add(new DataProcess(name,xLocation,yLocation));
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    
    /** 
     * Count the number of each unit needed to be generated later 
     * @param name The name of each unit
     */
    public void unitCount(String name){
    	if(name.equals("Zombie")==true){
    		numOfZombie += 1;
    	}
    	if(name.equals("Skeleton")==true){
    		numOfSkeleton += 1;
    	}
    	if(name.equals("Bandit")==true){
    		numOfBandit += 1;
    	}
    	if(name.equals("GiantBat")==true){
    		numOfGiantBat += 1;
    	}
    	if(name.equals("Draelic")==true){
    		numOfDraelic+= 1;
    	} 
    	if(name.equals("PrinceAldric")==true){
    		numOfPrince += 1;
    	}
    	if(name.equals("Elvira")==true){
    		numOfElvira += 1;
    	}
    	if(name.equals("Garth")==true){
    		numOfGarth += 1;
    	}
    }
    
    /** 
     * Generate each type of unit on the map/in the World
     */
    public void unitGenerator()
    throws SlickException{
    	//variables used for incrementing each unit
    	int a=0,b=0,c=0,d=0;
    	int e=0,f=0,g=0,h=0;
    	
    	zombie = new Zombie[numOfZombie];
    	bandit = new Bandit[numOfBandit];
    	giantBat = new GiantBat[numOfGiantBat];
    	skeleton = new Skeleton[numOfSkeleton];
    	draelic = new Draelic[numOfDraelic];
    	prince = new PrinceAldric[numOfPrince];
    	shaman = new Elvira[numOfElvira];
    	peasant = new Garth[numOfGarth];
    	
    	//for-each construct to iterate through each unit stored in the arraylist
    	for(DataProcess p : list){
    		if(p.name.equals("Zombie")==true){
    			zombie[a] = new Zombie("assets/units/zombie.png",p.xPos,p.yPos,10,60,800);
    			a += 1;
    		}
    		if(p.name.equals("Bandit")==true){
    			bandit[b] = new Bandit("assets/units/bandit.png",p.xPos,p.yPos,8,40,200);
    			b += 1;
    		}
    		if(p.name.equals("Skeleton")==true){
    			skeleton[c] = new Skeleton("assets/units/skeleton.png",p.xPos,p.yPos,16,100,500);
    			c += 1;
    		}
    		if(p.name.equals("GiantBat")==true){
    			giantBat[d] = new GiantBat("assets/units/dreadBat.png",p.xPos,p.yPos,0,40,0);
    			d += 1;
    		}
    		if(p.name.equals("Draelic")==true){
    			draelic[e] = new Draelic("assets/units/necromancer.png",p.xPos,p.yPos,30,140,400);
    			e += 1;
    		}
    		if(p.name.equals("PrinceAldric")==true){
    			prince[f] = new PrinceAldric("assets/units/prince.png",p.xPos,p.yPos);
    			f += 1;
    		}
    		if(p.name.equals("Elvira")==true){
    			shaman[g] = new Elvira("assets/units/shaman.png",p.xPos,p.yPos);
    			g += 1;
    		}
    		if(p.name.equals("Garth")==true){
    			peasant[h] = new Garth("assets/units/peasant.png",p.xPos,p.yPos);
    			h += 1;
    		}
    	}
    }
    
    /** Generate jackfruit on the map with random locations
     * @param num The number of fruits to be genereted
     * @throws SlickException
     */
    public void fruitGenerator(int num)
    throws SlickException{
    	int locationArray[] = new int[2];
    	jackfruit = new Jackfruit[num];
    	
    	for(int i=0;i<jackfruit.length;i++){
    		//Randomly locate the jackfruits
    		locationArray = blockedTile.locationGenerator();
    		jackfruit[i] = new Jackfruit("assets/items/jackfruit.png",locationArray[0],locationArray[1]);
    	}
    }
    
    /**
     * Draw items on the map
     * @param items An array of items to be drawn
     */
    public void drawItem(Item[] items){
    	for(int i=0;i<items.length;i++){
    		items[i].drawItem((float)(items[i].getXPos()-cam.getCameraPixelsX()), (float)(items[i].getYPos()-cam.getCameraPixelsY()));
    	}
    }
    
    /**
     * Update items on the map
     * @param items An array of items to be updated
     */
    public void itemUpdate(Item[] items){
    	for(int i=0;i<items.length;i++){
    		items[i].update(unitPlayer);
    	}
    }
    
    /**
     * Draw every unit on the map
     * @param unit Unit type to be drawn
     */
    public void drawUnit(Unit[] unit){
    	for(int i=0;i<unit.length;i++){
    		if(unit[i].getHealth()>0){
    			unit[i].drawUnit(-(float)(cam.getCameraPixelsX()-unit[i].getXPos()+xOffset), -(float)(cam.getCameraPixelsY()-unit[i].getYPos()+yOffset));
    		}
        }
    }
    
    /**
     * Render the speech of each Villagers
     * @param g The current Slick graphics context
     * @param villager Type of villagers (Elvira, Garth, Prince Aldric)
     */
    public void renderSpeak(Graphics g, Villager[] villager){
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transparent
        Color GOLD = new Color(0.9f,0.9f,0.4f);
        
        String text;
        int text_x,text_y;
        int bar_x, bar_y;
        int bar_width, bar_height;
        
        for(int i=0;i<villager.length;i++){
        	villager[i].update(unitPlayer);
       		if(villager[i].getSpeak() != null){
       			text = villager[i].getSpeak();
       			bar_width = g.getFont().getWidth(text)+2*xOffset;
       			bar_height = 30;
       			text_x = (int) (villager[i].getXPos()-cam.getCameraPixelsX()-(bar_width/2)+20);
       			text_y = (int) (villager[i].getYPos()-cam.getCameraPixelsY()-yOffset);
       			bar_x = (int)(villager[i].getXPos()-cam.getCameraPixelsX()-(bar_width/2)-xOffset+20);
       			bar_y = (int)(villager[i].getYPos()-cam.getCameraPixelsY()-yOffset-30);
       			g.setColor(GOLD);
       			g.fillOval(bar_x-3, bar_y-3, bar_width+6, bar_height+6);
       			g.setColor(VALUE);
       			g.fillOval(bar_x, bar_y, bar_width, bar_height);
       			g.setColor(BAR_BG);
       			g.drawString(text, text_x, text_y-25);
       		}
       	}
   }

   /** Renders the player's status panel.
    * @param g The current Slick graphics context.
    */
   public void renderPanel(Graphics g)
   {
       // Panel colors
       Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
       Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
       Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
       Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.5f);      // Red, transp
       Color BLUE = new Color(0.1f,0.5f,0.5f,1.0f);		   // Blue
       Color RED  = new Color(0.8f, 0.0f, 0.0f, 1.0f);		// Red
       // Variables for layout
       String text;                // Text to display
       int text_x, text_y;         // Coordinates to draw text
       int expTextX, expTextY;
       int bar_x, bar_y;           // Coordinates to draw rectangles
       int bar_width, bar_height;  // Size of rectangle to draw
       int hp_bar_width;           // Size of red (HP) rectangle
       int inv_x, inv_y;           // Coordinates to draw inventory item
       int textX, textY;
       int newBarWidth, newBarHeight;	//Size of the run cool down bar 
       float runCoolDownPercent, experiencePercent;		//Remaining time available for run as a percentage
       float health_percent;     						// Player's health, as a percentage

       // Panel background image
       unitPlayer.panel.draw(0, RPG.screenheight - RPG.PANELHEIGHT);
       // Display the player's health
       text_x = 15;
       text_y = RPG.screenheight - RPG.PANELHEIGHT + 25;
       g.setColor(LABEL);
       g.drawString("Health:", text_x, text_y);
       text = String.valueOf((int)unitPlayer.getHealth()) + "/" + (int)unitPlayer.getMaxHP();      

       bar_x = 90;
       bar_y = RPG.screenheight - RPG.PANELHEIGHT + 20;
       bar_width = 90;
       bar_height = 30;
       health_percent = (float) (unitPlayer.getHealth()/unitPlayer.getMaxHP());                 
       hp_bar_width = (int) (bar_width * health_percent);
       text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
       g.setColor(BAR_BG);
       g.fillRect(bar_x, bar_y, bar_width, bar_height);
       g.setColor(BAR);
       g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
       g.setColor(VALUE);
       g.drawString(text, text_x, text_y);
       
       //Draw Instructions and run bar
       newBarWidth = 150;	
       newBarHeight = 10;
       textX = 15;
       expTextX = 13;
       expTextY = 33;
       textY = RPG.screenheight - RPG.PANELHEIGHT -65;
       runCoolDownPercent = (float)(unitPlayer.getRunTime()/Player.allowableRunningTime);
       experiencePercent = (float)(unitPlayer.getNumOfMonstersKilled()/unitPlayer.getLevelUpRequirement());
       g.setColor(LABEL);
       g.drawString("(A) Attack", textX, textY-5);
       g.drawString("(S) Run", textX, textY+15);
       g.drawString("(P) Pause music",RPG.screenwidth-160, textY+20);
       g.drawString("(R) Resume music", RPG.screenwidth-160, textY+40);
       g.setColor(VALUE);
       g.fillRect(textX-3,textY+37,newBarWidth+6,newBarHeight+12);
       g.fillRect(expTextX-3, expTextY-3, newBarWidth+6, newBarHeight+6);
       g.setColor(BAR_BG);
       g.fillRect(textX,textY+40,newBarWidth,newBarHeight+6);
       g.fillRect(expTextX, expTextY, newBarWidth, newBarHeight);
       g.setColor(BLUE);
       g.fillRect(expTextX,expTextY,experiencePercent*newBarWidth, newBarHeight);
       g.setColor(RED);
       g.fillRect(textX, textY+40,runCoolDownPercent*newBarWidth, newBarHeight+6);
       g.setColor(VALUE);
       g.drawString("RUN",textX+55,textY+40);
       g.setColor(LABEL);
       g.drawString("LEVEL : " + unitPlayer.getLevel(), 13, 10);
       
       // Display the player's damage and cool down
       text_x = 200;
       g.setColor(LABEL);
       g.drawString("Damage:", text_x, text_y);
       text_x += 80;
       text = String.valueOf((int)unitPlayer.getDamage());                    
       g.setColor(VALUE);
       g.drawString(text, text_x, text_y);
       text_x += 40;
       g.setColor(LABEL);
       g.drawString("Rate:", text_x, text_y);
       text_x += 55;
       text = String.valueOf((int)(unitPlayer.getCdTime()));                         
       g.setColor(VALUE);
       g.drawString(text, text_x, text_y);

       // Display the player's inventory
       g.setColor(LABEL);
       g.drawString("Items:", 420, text_y);
       bar_x = 490;
       bar_y = RPG.screenheight - RPG.PANELHEIGHT + 10;
       bar_width = 288;
       bar_height = bar_height + 20;
       g.setColor(BAR_BG);
       g.fillRect(bar_x, bar_y, bar_width, bar_height);

       inv_x = 490;
       inv_y = RPG.screenheight - RPG.PANELHEIGHT
           + ((RPG.PANELHEIGHT - 72) / 2);
       
       //Iterate through Player's for items and display them
       for(int i=0;i<unitPlayer.getInventory().length;i++)
       {
       		if(unitPlayer.getInventory()[i] != null){
       			g.drawImage(unitPlayer.getInventory()[i], inv_x, inv_y);
       			inv_x += 72;	//go to next box in the status panel
       		}
       }
   }
   
   /**
    * Render the health bar and name of all unit
    * @param g The current Slick graphics context
    * @param unit
    */
   public void renderStatus(Graphics g, Unit[] unit){
	   Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.5f);
	   Color VALUE = new Color(1.0f, 1.0f, 1.0f); 
	   Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);
   	
	   int text_x, text_y;
	   int bar_x, bar_y;
	   int bar_width, bar_height;
	   int hp_bar_width;
	   int barHeight = 20;
	   float health_percent;
   	
	   for(int i=0;i<unit.length;i++){
		   if(unit[i].getHealth()>0){
			   bar_width = g.getFont().getWidth(unit[i].getName())+10;
			   text_x = (int)(unit[i].getXPos()-cam.getCameraPixelsX()-(bar_width/2));
			   text_y = (int)(unit[i].getYPos()-cam.getCameraPixelsY()-yOffset);
			   bar_x = (int)(unit[i].getXPos()-cam.getCameraPixelsX()-(bar_width/2)-5);
			   bar_y = (int)(unit[i].getYPos()-cam.getCameraPixelsY()-yOffset);
			   bar_height = barHeight;
			   health_percent = (float)(unit[i].getHealth()/unit[i].getMaxHP());                 
			   hp_bar_width = (int) (bar_width * health_percent);
			   g.setColor(BAR_BG);
			   g.fillRect(bar_x, bar_y, bar_width, bar_height);
			   g.setColor(BAR);
			   g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
			   g.setColor(VALUE);
			   g.drawString(unit[i].getName(), text_x, text_y);
		   }
	   }
   }
   
   /**
    * Update all units
    * @param monster Monster type to be updated
    */
   public void unitUpdate(Monster[] monster){
   	
	   for(int i=0;i<monster.length;i++){
		   monster[i].move = monster[i].movementUpdate(unitPlayer);
		   //move if the movement is not blocked
		   if(blockedTile.update(monster[i], monster[i].move[0], monster[i].move[1])==true){
			   monster[i].update(unitPlayer,monster[i].move[0],monster[i].move[1]);
		   }else{
			   //slide along the wall if blocked
			   blockedTile.smoothenMovement(monster[i], monster[i].move[0], monster[i].move[1]);
		   }
	   }
   }
    
    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
  
    public void update(double dir_x, double dir_y, int delta, int attack, int run)
    throws SlickException
    {    
    	//Let blockedArray determines whether the player can move in desired directions 
    	if(blockedTile.update(unitPlayer,dir_x, dir_y) == true){
    		//Let player handle the movement of the player
    		unitPlayer.update(dir_x, dir_y,delta,attack,run);
    	}else{
    		//Slide along the wall
    		blockedTile.smoothenMovement(unitPlayer,dir_x,dir_y);
    	}
        //Allow camera to follow the player
    	cam.update(unitPlayer.getXPos(),unitPlayer.getYPos());
    	amulet.update(unitPlayer);
    	sword.update(unitPlayer);
    	tome.update(unitPlayer);
    	elixir.update(unitPlayer);
   
        //update Monsters
        unitUpdate(zombie);
        unitUpdate(skeleton);
        unitUpdate(bandit);
        unitUpdate(draelic); 
        unitUpdate(giantBat);
        itemUpdate(jackfruit);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {	
    	//Render the map based on the position of the camera and the player
    	map.render(cam.getRemainderPosX(),cam.getRemainderPosY(),cam.getCameraTileX(),cam.getCameraTileY(),(screenwidth/pixelsPerTile)+2,((screenheight)/pixelsPerTile)+2);

    	//Draw player on the screen such that its always in the center of the frame unless approaching boundaries
        unitPlayer.drawUnit((float)(unitPlayer.getXPos()-cam.getCameraPixelsX())-xOffset,(float)(unitPlayer.getYPos()-cam.getCameraPixelsY())-yOffset);
       
        //Draw units and items on the map 
        drawUnit(prince);
        drawUnit(shaman);
        drawUnit(peasant);
        drawUnit(zombie);
        drawUnit(skeleton);
        drawUnit(bandit);
        drawUnit(giantBat);
        drawUnit(draelic);
        drawItem(jackfruit);
        //Render units' health bars
        renderStatus(g,zombie);
        renderStatus(g,skeleton);
        renderStatus(g,bandit);
        renderStatus(g,giantBat);
        renderStatus(g,prince);
        renderStatus(g,shaman);
        renderStatus(g,peasant);
        renderStatus(g,draelic);
        
        //Render dialogue
        renderSpeak(g,shaman);
        renderSpeak(g,prince);
        renderSpeak(g,peasant);
        
        //draw unique items
        amulet.drawItem((float)(amulet.getXPos()-cam.getCameraPixelsX()-xOffset),(float)(amulet.getYPos()-cam.getCameraPixelsY()-yOffset));
        sword.drawItem((float)(sword.getXPos()-cam.getCameraPixelsX()-xOffset), (float)(sword.getYPos()-cam.getCameraPixelsY()-yOffset));
    	tome.drawItem((float)(tome.getXPos()-cam.getCameraPixelsX()-xOffset), (float)(tome.getYPos()-cam.getCameraPixelsY()-yOffset));
    	elixir.drawItem((float)(elixir.getXPos()-cam.getCameraPixelsX()-xOffset), (float)(elixir.getYPos()-cam.getCameraPixelsY()-yOffset));
    
    	renderPanel(g);
    	
        //g.drawString("(x,y) = (" + unitPlayer.getXPos() + ", " + unitPlayer.getYPos() + ")",100,10);
        
    }
}
