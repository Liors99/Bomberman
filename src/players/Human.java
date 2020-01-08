package players;
import java.util.ArrayList;
import java.util.LinkedList;

import bombs.Bomb;
import bombs.Powerup;
import eventHandling.KeyManager;
import game.Game;
import images.Assets;
import images.ExtendedImage;

/**
 * Class for controllable players; extends Player class
 */
public class Human extends Player {

    private KeyManager keyManager;
    // Bombs
    private int numAllowedBombs = 1;
	private int numActiveBombs = 0;
	// Other flags
	public static boolean trigger_pause = false;
	private boolean trigger_bomb = true;
	
	/**
	 * Constructor for Player object
	 * @param game
	 * @param x - x-position at which to initiate player
	 * @param y - x-position at which to initiate player
	 */
	public Human(Game game, ExtendedImage[][] frames, float x, float y) {
		super(game, frames, x, y); 
		// Setting collision Boundaries
		// Want to convert from double to integer variable-type
		// First wrap double values as Double objects to access the wrapper class's methods
		Double wrapperX = new Double(0.16*Game.tileSize); 
		Double wrapperY = new Double(0.30*Game.tileSize); 
		Double wrapperWidth = new Double(0.75*Game.tileSize);
		Double wrapperHeight = new Double(0.70*Game.tileSize);
		// Return the integer value of Double using the method intValue()
		this.limit.x = wrapperX.intValue();
		this.limit.y = wrapperY.intValue();
		this.limit.width = wrapperWidth.intValue();
		this.limit.height = wrapperHeight.intValue();
	}
	
	/**
	 * A method that moves the player either vertically or horizontally
	 */
	public void move() {
		movementX();
		movementY();
	}
	
	
	/**
	 * A method that allows the player to move to the horizontally 
	 */
	public void movementX()
	{
		if(xMove > 0)//moving right
		{
			int tx = (int) (x + xMove + limit.x + limit.width)/ Game.tileSize;
			if(!collisionWithBlocks(tx, (int) (y + limit.y)/ Game.tileSize) &&
					!collisionWithBlocks(tx, (int)(y + limit.y + limit.height)/Game.tileSize)) {
				x += xMove; 
			}else {
				x = tx* Game.tileSize - limit.x - limit.width -1;
			}
		}else if(xMove < 0)//moving left
		{
				int tx = (int) (x+xMove +limit.x)/ Game.tileSize;
				if(!collisionWithBlocks(tx, (int) (y +limit.y)/ Game.tileSize) &&
						!collisionWithBlocks(tx, (int)(y + limit.y +limit.height)/Game.tileSize)) {
					x += xMove; 
				}else {
					x =tx * Game.tileSize + Game.tileSize -limit.x;
				}
			}
	}
				
	/**
	 * A method that allows the player to move to the vertically 
	 */
	public void movementY()
	{
		if(yMove < 0) //up
		{
			int ty = (int) (y + yMove +limit.y) / Game.tileSize;
			
			if (!collisionWithBlocks((int) (x +limit.x) / Game.tileSize,ty) &&
					(!collisionWithBlocks((int) (x +limit.x + limit.width) / Game.tileSize,ty))){
				y += yMove;
			}else {
				y =ty* Game.tileSize + Game.tileSize -limit.y;
			}
		}else if(yMove > 0)//down
		{
			int ty = (int) (y + yMove +limit.y +limit.height) / Game.tileSize;
			
			if (!collisionWithBlocks((int) (x +limit.x) / Game.tileSize,ty) &&
					(!collisionWithBlocks((int) (x +limit.x + limit.width) / Game.tileSize,ty))){
				y += yMove;
			}else {
				y = ty*Game.tileSize -limit.y -limit.height-1;
			}
		}
	}
	
	
	/**
	 * A method that detects whether the player is colliding with blocks
	 * @param x - the player' x position
	 * @param y - the player's y position
	 * @return Boolean- True if being collided with a solid block and false if not
	 */
	protected boolean collisionWithBlocks(int x, int y)
	{
		ArrayList<Powerup> powerArray = game.getMap().getPowerUps();
		for (int i = 0; i < powerArray.size(); i ++)
		{	
			
			int powerUpX = (int) (powerArray.get(i).getX())/ Game.tileSize;
			int powerUpY = (int) (powerArray.get(i).getY())/ Game.tileSize;
			if (x == powerUpX && y == powerUpY )
			{
				powerArray.get(i).setImage(Assets.grass);
				if (powerArray.get(i).getType() == 1)
				{
					if (this.speed < 7)
					{
						this.speed = this.speed + 1;
					}
				}
				else if (powerArray.get(i).getType() == 2)
				{
					if(this.numAllowedBombs < 4)
					{
						this.numAllowedBombs = this.numAllowedBombs + 1;
						// recode bombs? cuz booleans only consider 1 bomb

						// while num of bombs < 2 being placed by player set player.allowedtoplacebomb to true
					}
				}
				else if (powerArray.get(i).getType() ==  3)
				{
					if (this.blastRadius < 4)
					{
						this.blastRadius = this.blastRadius + 1;
						// when blast radius increases fix animation and blocks collision
						// need for loop to destroy blocks up to the blastRadius
					}
				}
				powerArray.remove(i);
			}
		}
		
		return game.getMap().getTile(x,y).isSolid();
	}
	
	/**
	 * A method that gets the input from the user
	 */
	private void getInput() {
		this.xMove = 0;
		this.yMove = 0;
		
		if (keyManager.up) {
			this.yMove = -speed;
		    this.lastMove = "up";
		}
		if (keyManager.down) {
			this.yMove = speed;
		    this.lastMove = "down";
		}
		if (keyManager.left) {
			this.xMove = -speed;
		    this.lastMove = "left";
		}	
		if (keyManager.right) {
			this.xMove = speed;
		    this.lastMove = "right";
		}
		if(keyManager.bomb) {
			if(trigger_bomb) {
				if(this.numActiveBombs < this.numAllowedBombs) {
					this.bombs.add(new Bomb(this));
					this.numActiveBombs++;
				}
				
				trigger_bomb=false;
			}
		}
		else {
			trigger_bomb=true;
		}
		if(keyManager.pause) {
			if(trigger_pause==false) {
				trigger_pause=true;
			}
		}
			
	}


	/**
	 * Updates the player and its variables every time the game is being updated
	 */
	@Override
	public void update() {
		
		super.update();
		
		// Movement
		getInput();
		move();	
	}
		

	//GETTERS AND SETTERS
	
	
	public void deactivateBomb() {
		this.numActiveBombs--;
	}
	
	public void setKeyManager(KeyManager keyManager)
	{
		this.keyManager = keyManager;
	}
	
	public int getNumAllowedBombs() {
		return numAllowedBombs;
	}


	public void setNumAllowedBombs(int numBombs) {
		this.numAllowedBombs = numBombs;
	}
	
}
