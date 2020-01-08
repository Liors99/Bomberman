package tiles;

import images.Assets;

/**
 * 
 * Class that deals with the block entity
 *
 */
public class Block extends Tile{

	/**
	 * A constructor for the block class
	 * @param id - the id of the block
	 */
	public Block(int id) {
		super(Assets.metal, id);
	}

	/**
	 * Checks if the block is solid, in this case it is
	 */
	@Override
	public boolean isSolid()
	{
		return true;
	}
	
}
