package bombs;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashMap;

import eventHandling.KeyManager;
import game.Animation;
import game.Game;
import images.Assets;
import players.Human;
import players.Player;

/**
 * 
 * Class that handles the explosion component of the bomb, timing and animation
 *
 */
public class Explosion
{
	private float x;
	private float y;
	private Game game;
	private Player player;
	private Bomb bomb;
	private Animation explosionMain;
	private HashMap<int[], Animation> explosionBranches; // HashMap to store (col,row) and animation of explosion tile
	
	/**
	 * The constructor the Explosion class
	 * @param bomb - the bomb object
	 */
    public Explosion(Bomb bomb)
    {
    	    this.bomb = bomb;
    	    this.game = bomb.getGame();
    	    this.player = bomb.getPlayer();
    	    this.x = bomb.getX();
    	    this.y = bomb.getY();
    	    
    	    this.buildExplosionAnimation();
    }
    
    
    /**
     * The method that updates the state of the explosion and its variables everytime the game updates
     */
    public void update()
    {
    	    // Update central explosion animation
    	    this.explosionMain.update();
    	    // Update branches of explosion animation
    	    for (Animation branch : explosionBranches.values())
    	    	    branch.update();
    	    
    	    //Detect if a player has been hit by the bomb or explosion
    	    for(Player p: this.game.getMap().getPlayers())
    	    	    this.checkIfPlayerIsDead(p);
    }
    
    
    /**
     * The method that draws the explosion every time the render method is being called in game
     * @param g- the graphics component
     */
    public void render(Graphics g)
    {
    	    if (this.explosionMain.getIsActive())
    	    {
    	    	    g.drawImage(this.explosionMain.getCurrentFrame(), (int) this.x, (int) this.y, 
           	    		this.game.getTileSize(), this.game.getTileSize(), null);
    	    	    for (int[] key : explosionBranches.keySet())
    	    	    {
    	    	      	// if the explosion branch is to the right of the bomb
    	    	    	    if (key[0] > this.bomb.getCol()) 
    	    	    	       	g.drawImage(explosionBranches.get(key).getCurrentFrame().mirrorX(), key[0]*this.game.getTileSize(), 
    	    	    	       			key[1]*this.game.getTileSize(), this.game.getTileSize(), this.game.getTileSize(), null);
    	    	    	    // else if the explosion branch is below the bomb
    	    	    	    else if (key[1] > this.bomb.getRow())
    	    	    	      	g.drawImage(explosionBranches.get(key).getCurrentFrame().rotateCCW(), key[0]*this.game.getTileSize(),
    	    	    	      			key[1]*this.game.getTileSize(), this.game.getTileSize(), this.game.getTileSize(), null);
    	    	    	    // else if the explosion branch is above the bomb
    	    	    	    else if (key[1] < this.bomb.getRow())
    	    	    	      	g.drawImage(explosionBranches.get(key).getCurrentFrame().rotateCW(), key[0]*this.game.getTileSize(), 
    	    	    	      			key[1]*this.game.getTileSize(), this.game.getTileSize(), this.game.getTileSize(), null);
    	    	    	    // default for image is to the left - no image transformations required
    	    	    	    else
	    	    	        g.drawImage(explosionBranches.get(key).getCurrentFrame(), key[0]*this.game.getTileSize(), 
	    	    	        		key[1]*this.game.getTileSize(), this.game.getTileSize(), this.game.getTileSize(), null);
    	    	    }
    	    }      	    
    }
    
    
    //GETTERS & SETTERS
    
    
    public void setActive()
    {
    	    this.explosionMain.setActive();
    	    for (Animation branch : explosionBranches.values())
	    	    branch.setActive();
    	    //this.explosionBranch.setActive();
    }
    
    
    //HELPER METHODS
    
    
    /**
     * Helper method to check if a player has been struck and killed by the explosion
     */
    public void checkIfPlayerIsDead(Player p)
    {
		boolean isHitBomb = false; // if player is at the bomb center
	    boolean isHitExplosion = false; // if player is within explosion branch
	    // is hit explosion is the 4 outer explosions of the bomb + sign
		isHitBomb = (this.bomb.getRow() == p.getRow() && this.bomb.getCol() == p.getCol());
		for(int[] pair : this.explosionBranches.keySet())
		{						
	     	 if(pair[0] == p.getCol() && pair[1] == p.getRow())
		     	 isHitExplosion = true;   	    	
		}
				 
	    if(isHitBomb || isHitExplosion)
	    	{
	    	    p.isDying = true;
	    	    //***********************
	    	    if (p instanceof Human)
	    	    {
	    	    	    Human human = (Human) p;
	    	      	KeyManager disable = new KeyManager();
		    	    human.setKeyManager(disable);
	    	    }
	    	    //**************************
     	}
    }
    
    
    /**
     * Helper method to construct the animation tiles required for the explosion
     */
    private void buildExplosionAnimation()
    {
      	this.explosionMain = new Animation(this.game, Bomb.EXPLOSION_SPEED, Assets.explosion, false);
      	// Create HashMap to contain all branches of the explosion animation
	    this.explosionBranches = new HashMap<int[], Animation>();
	    // Check to see if the tiles in the cross-section of the bomb are occupied (i.e., = 1 or 2) or unoccupied (= 0, grass)
	    //Add animations within the row (along the x-direction)
	    // -x direction
	    for (int c = 1; c <= this.bomb.getFunctionalBlastRadii()[0]; c++)
	    	    this.addAnimationBranch(this.bomb.getCol() - c, this.bomb.getRow(), this.bomb.getFunctionalBlastRadii()[0]);
	    // +x direction
	    for (int c = 1; c <= this.bomb.getFunctionalBlastRadii()[1]; c++)
    	        this.addAnimationBranch(this.bomb.getCol() + c, this.bomb.getRow(), this.bomb.getFunctionalBlastRadii()[1]);
	    //Add animations within the column (along the y-direction)
	    // -y direction
	    for (int r = 1; r <= this.bomb.getFunctionalBlastRadii()[2]; r++)
	    	    this.addAnimationBranch(this.bomb.getCol(), this.bomb.getRow() - r, this.bomb.getFunctionalBlastRadii()[2]);
	    // +y direction
	    for (int r = 1; r <= this.bomb.getFunctionalBlastRadii()[3]; r++)
	    	    this.addAnimationBranch(this.bomb.getCol(), this.bomb.getRow() + r, this.bomb.getFunctionalBlastRadii()[3]);
    }
    
    /**
     * Helper method to add branch fragment of explosion animation
     * @param col - the column at which to add the animation
     * @param row - the row at which to add the animation
     */
    private void addAnimationBranch(int col, int row, int blastRadius)
    {
	    //Boolean flags to indicate if this tile exists (is on our map), if it is grass, and/or it is is brick
	    boolean isOnMap = ((row >= 0) && (row < this.game.getNumTilesY()) && (col >= 0) && (col < this.game.getNumTilesX()));
	    boolean isGrass = (this.game.getMap().getTile(col, row).getId() == 0);
	    boolean isBrick = (this.game.getMap().getTile(col, row).getId() == 2);
	    if (isOnMap && (isGrass || isBrick))
	    {
	       	int[] pair = new int[] {col, row};
	       	boolean atEndofBranch = ((Math.abs(this.bomb.getCol() - col) == blastRadius) || 
	       			(Math.abs(this.bomb.getRow() - row) == blastRadius));
	       	if (atEndofBranch)
	       		explosionBranches.put(pair, new Animation(this.game, Bomb.EXPLOSION_SPEED, Assets.explosCrossEnd, false));
	       	else
	       		explosionBranches.put(pair, new Animation(this.game, Bomb.EXPLOSION_SPEED, Assets.explosCrossMid, false));
	    }  	  
    }
    
    
    
}
