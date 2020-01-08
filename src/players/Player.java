package players;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import game.*;
import bombs.*;
import images.*;

/**
 * Class for players; extends Entity class
 */
public abstract class Player extends Entity {

	protected final int ANIMATION_SPEED = 100; // every 100 ms, switch frames for animation
    protected Game game; 
	// Movement
	protected float speed;
	protected float xMove, yMove;
	protected String lastMove;
	// Animations
	protected Animation walkDown, walkUp, walkLeft, walkRight;
	// Bombs
	protected ArrayList<Bomb> bombs;
	// Bomb radius
	protected int blastRadius = 1;
	// Other flags
	public boolean isDead = false; // determines whether player is dead
	public boolean isDying = false;
	private long deathTimer = 0;
	private long deathDuration = 200;
	
	
	/**
	 * Constructor for Player object
	 * @param game
	 * @param x - x-position at which to initiate player
	 * @param y - x-position at which to initiate player
	 */
	public Player(Game game, ExtendedImage[][] frames, float x, float y) {
		super(x, y); 
		this.game = game; // to access KeyManager and inputs from game object
		this.speed = this.game.getTileSize()/12.0f; // speed for move, defined in terms of number of pixels
		this.xMove = 0;
		this.yMove = 0;
		this.lastMove = "down"; //default player looks forward/down on screen
		
		//Animations
		this.walkDown = new Animation (this.game, ANIMATION_SPEED, frames[0], true); 
		this.walkUp = new Animation (this.game, ANIMATION_SPEED, frames[1], true);
		this.walkLeft = new Animation (this.game, ANIMATION_SPEED, frames[2], true);
		this.walkRight = new Animation (this.game, ANIMATION_SPEED, frames[3], true);
		
		//Bomb queue
		bombs = new ArrayList<Bomb>();
	}

	@Override
	public void update() {
		
		// Walking animation
		this.walkDown.update();
		this.walkUp.update();
		this.walkLeft.update();
		this.walkRight.update();
		
		// Bombs
		for (Bomb bomb : this.bombs) {
			bomb.update();
		}			
	}
		
	
	@Override
	public void render(Graphics g) {
		// Player walking animation
		if(!isDying) {
			g.drawImage(this.getCurrentAnimationFrame(), (int)this.x, (int)this.y, Game.tileSize, Game.tileSize, 
				null);	
		}
		else if(!isDead)
		{
			deathTimer += this.game.getTimeSinceLast();
			g.drawImage(this.getCurrentAnimationFrame().turnRed(), (int)this.x, (int)this.y, Game.tileSize, Game.tileSize, 
					null);	
			if (deathTimer > deathDuration)
			{
				this.isDead = true;
			}
		}
		
		
		// Bomb animation(s)
		for (Bomb bomb : this.bombs) {
			bomb.render(g);
		}
	}
	
	
	
	
	//GETTERS AND SETTERS
	
	public abstract void deactivateBomb();

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Game getGame() {
		return game;
	}
	
	public int getBlastRadius() {
		return blastRadius;
	}

	public void setBlastRadius(int blastRadius) {
		this.blastRadius = blastRadius;
	}
	
	private ExtendedImage getCurrentAnimationFrame() {
		ExtendedImage frameToReturn; 
		if (this.xMove < 0) {
			frameToReturn = this.walkLeft.getCurrentFrame();
		} else if (this.xMove > 0) {
			frameToReturn = this.walkRight.getCurrentFrame();
		} else if (this.yMove < 0) {
			frameToReturn = this.walkUp.getCurrentFrame();
		} else if (this.yMove > 0) {
			frameToReturn = this.walkDown.getCurrentFrame();
		} else {
			if (this.lastMove.equals("left"))
				frameToReturn = this.walkLeft.getStillFrame();
			else if (this.lastMove.equals("right"))
				frameToReturn = this.walkRight.getStillFrame();
			else if (this.lastMove.equals("up"))
				frameToReturn = this.walkUp.getStillFrame();
			else
				frameToReturn = this.walkDown.getStillFrame();
		}
		return new ExtendedImage(frameToReturn);
	}
	
	public ArrayList<Bomb> getBombs(){
		return this.bombs;
	}
	
}
