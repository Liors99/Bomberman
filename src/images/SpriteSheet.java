package images;
import java.awt.image.BufferedImage;

/**
 * 
 * Class that loads in the spritesheet
 * Credit: CodeNMore video: https://www.youtube.com/watch?v=Vmpe6mht3qE&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ&index=8
 *
 */
public class SpriteSheet {

	private BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
	/**
	 * Method to crop sprite from sprite sheet
	 * @param x - x-coord of the top-left corner of sprite in pixels
	 * @param y - y-coord of the top-left corner of sprite in pixels
	 * @param width  - width of sprite in pixels
	 * @param height - height of sprite in pixels
	 * @return
	 */
	public BufferedImage crop(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
	}
}
