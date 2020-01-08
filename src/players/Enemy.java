package players;
import java.util.ArrayList;
import java.util.LinkedList;

import bfs.Agent;
import bfs.Maze;
import bfs.Vertex;
import bombs.Bomb;
import game.Game;
import images.ExtendedImage;

/**
 * 
 * Class that Handles the AI
 *
 */
public class Enemy extends Player 
{
	// Pathfinding
	private LinkedList<Vertex> path;
	private ArrayList<Vertex> previousMoves; //variable to store history of vertices that have been along path
	private Vertex lastMoveAdded = null;
	private Vertex currentTarget = null;
	private Vertex suspendedTarget = null;
	private Vertex endTarget = null;
	//For finding correct column and row
	int lastCol;
	int lastRow;
	//For bombs
	private boolean bombActive = false;
	private boolean humanTarget = true;
	
	public Enemy(Game game, ExtendedImage[][] frames, float x, float y) {
		super(game, frames, x, y);
		this.lastCol = (int) this.x / this.width;
		this.lastRow = (int) this.y / this.height;
		this.previousMoves = new ArrayList<Vertex>();
	}
	
	//PUBLIC METHODS

	@Override
	public void update() {
		
		if (!this.isDead)
		{
			super.update();
			
			// Find BFS path to target
			// Have we reached our current target?
			if (currentTarget != null)
			{
				//Add the vertex associated with the previous tile to the list of previous moves
				if (currentTarget.getParent() != null && currentTarget.getParent() != lastMoveAdded)
				{
				    previousMoves.add(currentTarget.getParent()); //add to the list of moves the parent of the current target vertex
				    this.lastMoveAdded = currentTarget.getParent();
				    /*for (Vertex move : previousMoves)
				    	    System.out.print(move);
				    System.out.println();*/
				}
				    	
				if (this.game.getMap().getTile(currentTarget.getCol(), currentTarget.getRow()).getId() == 2 && !this.bombActive)
				{
					this.activateBomb();
				}
				
				this.move(currentTarget);
				
			}
			// Do not search for next path/next target until current target is reached
			else 
			{
				if (this.humanTarget) //if the bomb isn't active
					this.endTarget = this.getHumanTarget(); //set the end target to the human player
	
				this.findPath();
				if (path.size() > 1)
				{
					this.currentTarget = this.path.get(1);
				}
				else
				{
					this.currentTarget = null;
					if (this.humanTarget)
						this.activateBomb();	
				}		
			}	    
		}
	}

		
	//HELPER METHODS
	/**
	 * Helper method to activate/place bomb
	 */
	private void activateBomb()
	{
		this.humanTarget = false;
		this.bombActive = true;
		Bomb bomb = new Bomb(this);
		this.bombs.add(bomb);
		this.currentTarget = previousMoves.get(previousMoves.size() - 2); //current target becomes last tile that we moved out of
		this.endTarget = this.getSafeTarget(bomb);
	}
	
	
	/**
	 * Method to find safe tile to which to return to get out of blast radius.
	 * This safe tile will become the temporary target in the BFS, until the bomb is finished.
	 * @return Vertex associated with safe tile
	 */
	private Vertex getSafeTarget(Bomb bomb)
	{
		int i = previousMoves.size() - 1; //start with last visited tile
		Vertex move = null;
		boolean inCrossSection = true;
		boolean withinRadius = true;
		while (withinRadius && inCrossSection && i >= 0)
		{
			move = previousMoves.get(i);
			inCrossSection = (bomb.getCol() == move.getCol()) || (bomb.getRow() == move.getRow());
			withinRadius = (Math.abs(bomb.getCol() - move.getCol()) < this.blastRadius + 1) &&
					(Math.abs(bomb.getRow() - move.getRow()) < this.blastRadius + 1);
			i--;
		}
		return move;	
	}
	
	/**
	 * Helper method to determine end-path target-vertex associated with human target
	 */
	private Vertex getHumanTarget()
	{
		Player targetPlayer = this.game.getMap().getPlayers().get(0); //human player is added before enemy, so take first in list
		int targetCol = (int) targetPlayer.getCol();
		int targetRow = (int) targetPlayer.getRow();
		// Set up current maze to solve, with player 1 as target
		return new Vertex(targetCol, targetRow);
	}
	
	/**
	 * Run BFS algorithm to find path to target
	 */
	private void findPath()
	{
		Vertex start = new Vertex(this.getLocalCol(), this.getLocalRow());
		Maze maze = new Maze(this.game.getMap().getTiles(), start);
		Agent agent = new Agent();
		Maze goal = new Maze(this.game.getMap().getTiles(), this.endTarget);
		path = agent.bfs(maze, goal);
		//System.out.println(path);
		//currentTarget = path.get(1);
		// If path exists, and if we haven't yet reached our destination
		
		/*if (path.size() > 1) 
		    move(path.get(1)); // get the next vertex because presently standing on the first
		else { // we have path.size() = 1, which is our current tile; we have reached our target, so stop moving
		    this.xMove = 0;
		    this.yMove = 0;
		}*/
	}
	
	
	/**
	 * Move enemy along its BFS path by one vertex
	 * @param move - the vertex to which the enemy is moving - int[]{col,row}
	 */
	private void move(Vertex move) {
		// Want to set speed to zero in case we have stopped moving
		/*this.xMove = 0;
		this.yMove = 0;*/
		// Determine direction of movement
		if (move.getCol() > this.getLocalCol()) {
			this.xMove = speed;
		    this.lastMove = "right";
		}		
		else if (move.getCol() < this.getLocalCol()) {
			this.xMove = -speed;
			this.lastMove = "left";
		}			
		else if (move.getRow() > this.getLocalRow()) {
			this.yMove = speed;
			this.lastMove = "down";
		}			
		else if (move.getRow() < this.getLocalRow()) {
			this.yMove = -speed;
			this.lastMove = "up";
		}
		else
		{
			this.currentTarget = null;
			this.xMove = 0;
			this.yMove = 0;
		}
				
		// Make move
		this.x += this.xMove;
		this.y += this.yMove;
		
	}
	
	/**
	 * Locally (within this class), we want the Enemy's col,row to be referenced
	 * to its top-left corner rather than its center (and recall this.x and this.y reference the top-left
	 * corner of the Entity)
	 */
	private int getLocalCol() {
		//return (int) this.getX() / this.getWidth();
		int col;
		//if coming from the left
		if (this.xMove > 0)
            col = (int) this.x / this.width;	
	    //if coming from the right
		else if (this.xMove < 0)	
			col = (int) (this.x + this.width) / this.width;
		//Store this as the last column location	
		else
			col = lastCol;
		//Update the last column location
		lastCol = col;
		return col;
	}
	
	
	/**
	 * Locally (within this class), we want the Enemy's col,row to be referenced
	 * to its top-left corner rather than the its center (and recall this.x and this.y reference the top-left
	 * corner of the Entity)
	 */
	private int getLocalRow() {
		//return (int) this.getY() / this.getHeight();
		int row;
		//if coming from above
		if (this.yMove > 0)
            row = (int) this.y  / this.height;	
	    //if coming from below
		else if (this.yMove < 0)	
			row = (int) (this.y + this.height) / this.height;
		//Store this as the last column location	
		else
			row = lastRow;
		//Update the last column location
		lastRow = row;
		return row;
	}
	
	//GETTERS AND PUBLIC METHODS
		
	public void deactivateBomb() {
		this.humanTarget = true;
		this.bombActive = false;
	}
	
}
