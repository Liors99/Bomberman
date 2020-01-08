package tiles;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;

/**
 * 
 * Parent class that deals with any type of tile in the game.
 *
 */
public class Tile 
{
	// static stuff here
	public static Tile[] tiles = new Tile[256]; // Array to store possible/legal values for tile ID; if tile ID is not in 
	                                                // this array, it is not legal
	public static Tile grassTile = new GrassTile(0);
	public static Tile metalTile = new Block(1);
	public static Tile brickTile = new Brick(2);
	protected BufferedImage texture;
	protected final int id;
	
	/**
	 * A constructor for the Tile 
	 * @param texture - the image of the tile
	 * @param id - the id of the tile
	 */
	public Tile(BufferedImage texture, int id)
	{
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	public void update()
	{
		
	}
	
	/**
	 * A method that draws the tile
	 * @param g - the graphics component
	 * @param x - x location
	 * @param y - y location
	 */
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(texture, x, y, Game.tileSize, Game.tileSize, null); 
	}
	
	
	//GETTERS AND SETTERS
	
	public int getId()
	{
		return id;
	}
	
	public boolean isSolid()
	{
		return false;
	}
	

}