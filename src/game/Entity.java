package game;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Arrays;

/**
 * Base class for game entities (i.e., anything that is not a tile - items, players, etc.)
 */
public abstract class Entity {

	protected float x, y; // making x and y floats allows for smoother movement in game
	// protected - can only be accessed with its package
	protected int width, height; // width and height of entity 
	protected Rectangle limit; 
	// Entity cross-section - the tiles that fall within the entity's cross-section, given as {row,col} pairs
    protected int[][] crossSectionPairs;
    protected int[][] extendedCrossSectionPairs;
	
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = Game.tileSize;
		this.height = Game.tileSize;
		// Row and column of entity CANNOT be specified here as they need to be updated as x and y update;
		// use getter method instead when row and col are needed
		
		this.limit = new Rectangle(0, 0, this.width, this.height);
		// Cross-section of entity (includes +1 in each direction)
		// NOTE these pairs will always exist for an entity so long as it spawns within the game borders
		// Otherwise may not exist and must handle exceptions
		this.crossSectionPairs = new int[][]{
			{this.getCol() - 1, this.getRow()}, 
			{this.getCol() + 1, this.getRow()},
			{this.getCol(), this.getRow() + 1},
			{this.getCol(), this.getRow() - 1}
			}; 
		// For destructible block spawning (denotes illegal positions) and extended explosion animations	
	    this.extendedCrossSectionPairs = Arrays.copyOf(crossSectionPairs, 8);
	    extendedCrossSectionPairs[4] = new int[]{this.getCol() - 2, this.getRow()};
	    extendedCrossSectionPairs[5] = new int[]{this.getCol() + 2, this.getRow()};
	    extendedCrossSectionPairs[6] = new int[]{this.getCol(), this.getRow() + 2};
	    extendedCrossSectionPairs[7] = new int[]{this.getCol(), this.getRow() - 2};
	    
	}
	
	public float getX() {
		return this.x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Getter method for column of entity; optimized for bomb placement
	 * @return - column that entity is in (for bomb placement)
	 */
	public int getCol() {
		return (int) (this.x + this.width/2) / this.width;
	}
	
	/**
	 * Getter method for row of entity; optimized for bomb placement
	 * @return - row that entity is in (for bomb placement)
	 */
	public int getRow() {
		return (int) (this.y + this.width/2) / this.width;
	}
	
	/**
	 * Getter method for the {row,col} pairs associated with the entity's cross-section (+1 in each direction) 
	 * @return The 2d array associated with the entity's cross-section 
	 */
	public int[][] getCrossSectionPairs() {
		return this.crossSectionPairs;
	}
	
	/**
	 * Getter method for the {row,col} pairs associated with the entity's EXTENDED cross-section (+2 in each direction) 
	 * @return The 2d array associated with the entity's EXTENDED cross-section 
	 */
	public int[][] getExtendedCrossSectionPairs() {
		return this.extendedCrossSectionPairs;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	

	// ABSTRACT METHODS
	
	public abstract void update();
	public abstract void render(Graphics g);
}
