package bombs;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import game.Animation;
import game.Entity;
import game.Game;
import images.Assets;
import players.Human;
import players.Player;

/**
 * 
 * Class that handles all the bomb events
 *
 */
public class Bomb extends Entity{
	
	private Game game;
	private Player player;
	
	// Variables for animations (bomb ticking and explosion)
	private Animation bombTicking;
	private Explosion explosion;
	private long timePassed = 0;
	private boolean bombActive = true;
	private static final int BOMB_SPEED = 500; // in ms per frame
	private static final long EXPLOSION_START_TIME = 3*BOMB_SPEED; // three frames in bomb ticking; when done, time to start explosion 
	public static final int EXPLOSION_SPEED = 100;  // in ms per frame
	private static final long BOMB_DURATION = 3*BOMB_SPEED + 4*EXPLOSION_SPEED;
	private int[] functionalBlastRadii; //functional blast radius in -,+ x,y directions (in that order)
	private ArrayList<int[]> hitBoxPairs = new ArrayList<int[]>(); //for tiles/pairs {col,row} within the bomb's hit box
	// Variables for powerups
	private ArrayList<int[]> powerUpSpawnLocs = new ArrayList<int[]>(); //all possible locations for a powerup to spawn
	
	/**
	 * The constructor for the bomb class
	 * @param player - receives the player object
	 */
	public Bomb(Player player)
	{
		super(player.getX(), player.getY());
		this.player = player;
		this.game = this.player.getGame();
		// Make the bombs centered on the tile in which they are placed
		this.setX(player.getCol() * Game.tileSize);
		this.setY(player.getRow() * Game.tileSize);
		
		// Must find functional blast radii for this explosion before building explosion animations
	    this.findFunctionalBlastRadii();
	    
		// Animations
		bombTicking = new Animation(this.game, BOMB_SPEED, Assets.bomb, true);
		this.explosion = new Explosion(this);
	}

	
	/**
	 * The method that updates the state of the bomb and its variables every time the game updates
	 */
	@Override
	public void update() {
		timePassed += this.game.getTimeSinceLast();
		if (bombActive)
		{
			if (timePassed < EXPLOSION_START_TIME) // still on the bomb ticking animation
			{
			    this.bombTicking.update();
			}
			else if (timePassed < BOMB_DURATION) // move onto explosion animation
			{
				this.explosion.setActive();
				this.explosion.update();
			}
			else // bomb is finished; spawn powerup(s) and reset player bomb variables
			{					
				for (int[] pair : this.hitBoxPairs) // where pair = {col, row} of tile
				{
					//System.out.println("CrossSection pair: " + Arrays.toString(pair));
					// need to have a try block in case the tile specified by the pair does not exist (is off the map)
					try
				    {
				    	    if (this.game.getMap().getTile(pair[0], pair[1]).getId() == 2) // destructible block tiles have id = 2
				    	    	{
				    	    	    this.game.getMap().setTile(pair[0], pair[1], 0); // set tiles id = 0 (grass tile)
				    	    	    if (this.player instanceof Human)
				    	    	        this.powerUpSpawnLocs.add(pair);
				    	    	}
				    }
				    catch (Exception e) {}
				}
				
				if (this.player instanceof Human)
				{
					//Then spawn powerup at one of the legal locations (i.e., where a destructible block previously existed)
					if (this.powerUpSpawnLocs.size() > 0) // if we have a possible spawning location
					    this.spawnPowerUp();
				}
				
				 //Reset bomb variables
				 this.bombActive = false; //bomb is no longer active
				 this.player.deactivateBomb();
			}
		}	
	}		

	
	
	/**
	 * The method that draws the bomb to the screen every time the game is being rendered
	 */
	@Override
	public void render(Graphics g) {
		if (timePassed < EXPLOSION_START_TIME)
			g.drawImage(this.bombTicking.getCurrentFrame(), (int)this.x, (int)this.y, 
					Game.tileSize, Game.tileSize, null);
		else if (timePassed < BOMB_DURATION)
			this.explosion.render(g);
	}

	
	// HELPER METHODS 
	/**
	 * This method spawns the powerups at random spots 
	 */
	 private void spawnPowerUp()
	 {
		 Random rnd = new Random(); // new random number generator
		 int rndPowerUp = rnd.nextInt(6); // chose from 1 of 3 powerups
		 int rndPair = rnd.nextInt(this.powerUpSpawnLocs.size()); // chose from 1 in 'n' spawning locations
		 int[] spawnLocation = this.powerUpSpawnLocs.get(rndPair);
		 if (rndPowerUp == 1)
			{
			    	Powerup speedUp = new Powerup(spawnLocation[0] * Game.tileSize, spawnLocation[1] * Game.tileSize, 
			    			"SpeedUp", 1, 2, Assets.speedUp);
			    	this.game.getMap().getPowerUps().add(speedUp);
			}
			else if (rndPowerUp == 2)
			{
			    	Powerup bombUp = new Powerup(spawnLocation[0] * Game.tileSize, spawnLocation[1] * Game.tileSize, 
			    			"bombUp", 2, 2, Assets.bombUp);
			    	this.game.getMap().getPowerUps().add(bombUp);
			}
			else if (rndPowerUp == 3)
			{
			 	Powerup blastUp = new Powerup(spawnLocation[0] * Game.tileSize, spawnLocation[1] * Game.tileSize, 
			 			"blastUp", 3, 2, Assets.blastUp);
			    	this.game.getMap().getPowerUps().add(blastUp);
			}
	 }
	 
	 
	 /**
	     * Returns functional blast radius for different directions: -x, +x, -y, +y, in that order
	     * If there is an indestructible block along the path, that will reduce the functional radius of the bomb
	     * along that direction.
	     * @return Functional blast radius along given direction. 
	     */
	    private void findFunctionalBlastRadii()
	    {
	    	    functionalBlastRadii = new int[] {0, 0, 0, 0};
	    	    
	    	    for (int c = 1; c <= this.player.getBlastRadius(); c++)
	    	    {
	    	    	    int col = this.getCol() - c;
	    	    	    int row = this.getRow();
	    	    	    boolean isGrass = (this.game.getMap().getTile(col, row).getId() == 0);
	    		    boolean isBrick = (this.game.getMap().getTile(col, row).getId() == 2);
	    	    	    if(isGrass || isBrick)
	    	    	    {
	    	    	    	    functionalBlastRadii[0]++;
	    	    	        int[] pair = new int[] {col,row};
	    	    	        this.hitBoxPairs.add(pair);
	    	    	    }
	    	    	    else
	    	    	    	    break;
	    	    	  //So that we don't extend beyond one brick, if brick, end of branch
	    	    	    if (isBrick)
	    	    	    	    break;
	    	    }
	    	    for (int c = 1; c <= this.player.getBlastRadius(); c++)
	    	    {
	    	    	    int col = this.getCol() + c;
	    	    	    int row = this.getRow();
	    	    	    boolean isGrass = (this.game.getMap().getTile(col, row).getId() == 0);
	    		    boolean isBrick = (this.game.getMap().getTile(col, row).getId() == 2);
	    	    	    if(isGrass || isBrick)
	    	    	    {
    	    	    	        functionalBlastRadii[1]++;
    	    	            int[] pair = new int[] {col,row};
    	    	            this.hitBoxPairs.add(pair);
    	    	        }
	    	    	    else
	    	    	    	    break;
	    	    	    if (isBrick)
    	    	    	    break;
	    	    }
	    	    for (int r = 1; r <= this.player.getBlastRadius(); r++)
	    	    {
	    	    	    int col = this.getCol();
	    	    	    int row = this.getRow() - r;
	    	    	    boolean isGrass = (this.game.getMap().getTile(col, row).getId() == 0);
	    		    boolean isBrick = (this.game.getMap().getTile(col, row).getId() == 2);
	    	    	    if(isGrass || isBrick)
	    	    	    {
    	    	    	        functionalBlastRadii[2]++;
    	    	            int[] pair = new int[] {col,row};
    	    	            this.hitBoxPairs.add(pair);
    	    	        }
	    	    	    else
	    	    	    	    break;
	    	    	    if (isBrick)
    	    	    	    break;
	    	    }
	    	    for (int r = 1; r <= this.player.getBlastRadius(); r++)
	    	    {
	    	    	    int col = this.getCol();
	    	    	    int row = this.getRow() + r;
	    	    	    boolean isGrass = (this.game.getMap().getTile(col, row).getId() == 0);
	    		    boolean isBrick = (this.game.getMap().getTile(col, row).getId() == 2);
	    	    	    if(isGrass || isBrick)
	    	    	    {
    	    	    	        functionalBlastRadii[3]++;
    	    	            int[] pair = new int[] {col,row};
    	    	            this.hitBoxPairs.add(pair);
    	    	        }
	    	    	    else
	    	    	    	    break;
	    	    	    if (isBrick)
    	    	    	        break;
	    	    }
	    }
	
	
	//GETTERS & SETTERS
	
	public Game getGame() {
		return this.game;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int[] getFunctionalBlastRadii() {
		return this.functionalBlastRadii;
	}
	
	public boolean getActive() {
		return this.bombActive;
	}
	
	
}
