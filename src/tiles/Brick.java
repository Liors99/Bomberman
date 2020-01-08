package tiles;

import images.Assets;

/**
 * 
 * Class that deals with the brick entity
 *
 */
public class Brick extends Tile{

	/**
	 * A constructor for the brick class
	 * @param id - the id for the brick
	 */
	public Brick(int id) {
		super(Assets.brick, id);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Checks if the brick is solid, in this case it is
	 */
	@Override
	public boolean isSolid()
	{
		return true;
	}
	
}
