package images;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Essentially a wrapper class for BufferedImage to which we will add additional methods
 * @author Mariella
 *
 */
public class ExtendedImage extends BufferedImage{
	
    /**
     * Constructor which takes a BufferedImage as input and generates an ExtendedImage from it
     * @param image
     */
	public ExtendedImage(BufferedImage image) {
		//super(image.getColorModel(), image.getRaster(), image.getColorModel().isAlphaPremultiplied(), null);
		super(image.getColorModel(), image.copyData(image.getRaster().createCompatibleWritableRaster()), image.getColorModel().isAlphaPremultiplied(),
				null);
	}
	
	/**
	 * Constructor which creates a transparent image; basically takes the place of a 'null' object to prevent an exception from
	 * being thrown
	 */
	public ExtendedImage()
	{
		super(Assets.spriteDim, Assets.spriteDim, BufferedImage.TYPE_INT_ARGB);
		
	}
	
	/**
	 * Method to crop image
	 * @param x - x-coord of the top-left corner of subimage in pixels
	 * @param y - y-coord of the top-left corner of subimage in pixels
	 * @param width  - width of subimage in pixels
	 * @param height - height of subimage in pixels
	 * @return cropped image
	 */
	public ExtendedImage crop(int x, int y, int width, int height) {
		return new ExtendedImage(this.getSubimage(x, y, width, height));
	}
	
	/**
	 * Method to mirror image in horizontal (x-) direction
	 * @return x-mirrored image
	 */
	public ExtendedImage mirrorX() 
	{
		BufferedImage mirroredImage = new BufferedImage(this.getWidth(), this.getHeight(), this.getType());
		for (int y = 0; y < this.getHeight(); y++) 
		{
			for (int x = 0; x < this.getWidth(); x++) 
			{
				// x becomes mirrored (-1 because index starts at 0), y remains the same
				int x2 = this.getWidth() - x - 1;
				int y2 = y;
				Color pixelColor = new Color(this.getRGB(x,y), true);	
				mirroredImage.setRGB(x2, y2, pixelColor.getRGB());
			}
		}
		
		return new ExtendedImage(mirroredImage);
	}
	
	/**
	 * Method to mirror image in vertical (y-) direction
	 * @return y- mirroed image
	 */
	public ExtendedImage mirrorY() 
	{
		BufferedImage mirroredImage = new BufferedImage(this.getWidth(), this.getHeight(), this.getType());
		for (int y = 0; y < this.getHeight(); y++) 
		{
			for (int x = 0; x < this.getWidth(); x++) 
			{
				// y becomes mirrored (-1 because index for pixels starts at 0), x remains the same
				int x2 = x;
				int y2 = this.getHeight() - y - 1;
				Color pixelColor = new Color(this.getRGB(x,y), true);	
				mirroredImage.setRGB(x2, y2, pixelColor.getRGB());
			}
		}
		
		return new ExtendedImage(mirroredImage);
	}
	
	
	/**
	 * A method that rotates the image counterclockwise 90 degrees
	 * @return ExtendedImage- a rotated image
	 */
	public ExtendedImage rotateCCW()
	{
		BufferedImage rotatedImage = new BufferedImage(this.getWidth(), this.getHeight(), this.getType());
		for (int y = 0; y < this.getHeight(); y++) 
		{
			for (int x = 0; x < this.getWidth(); x++) 
			{
				int x2 = y;
				int y2 = this.getWidth() - x - 1;
				Color pixelColor = new Color(this.getRGB(x,y), true);	
				rotatedImage.setRGB(x2, y2, pixelColor.getRGB());
			}
		}
		return new ExtendedImage(rotatedImage);
	}
	
	/**
	 * A method that rotates the image clockwise 90 degrees
	 * @return ExtendedImage- a rotated image
	 */
	public ExtendedImage rotateCW()
	{
		BufferedImage rotatedImage = new BufferedImage(this.getWidth(), this.getHeight(), this.getType());
		for (int y = 0; y < this.getHeight(); y++) 
		{
			for (int x = 0; x < this.getWidth(); x++) 
			{
				int x2 = this.getHeight() - y - 1;
				int y2 = x;
				Color pixelColor = new Color(this.getRGB(x,y), true);	
				rotatedImage.setRGB(x2, y2, pixelColor.getRGB());
			}
		}
		return new ExtendedImage(rotatedImage);
	}
	
	/**
	 * A method that turns the current frame to red
	 * @return ExtendedImage - a red image
	 */
	public ExtendedImage turnRed()
	{
		ExtendedImage redImage = new ExtendedImage(this);
		for (int y = 0; y < this.getHeight(); y++) 
		{
			for (int x = 0; x < this.getWidth(); x++) 
			{
				if (!redImage.isTransparent(x, y))
			        redImage.setRGB(x, y, Color.RED.getRGB());		
			}
		}
		
		return redImage;
	}
	
	
	/**
	 * A method that turns the current frame to red
	 * @return ExtendedImage - a transparent image
	 */
	public ExtendedImage turnTransparent()
	{
		ExtendedImage transparent = new ExtendedImage(this);
		int c = new Color(0,0,0,1).getRGB();
		for (int y = 0; y < this.getHeight(); y++) 
		{
			for (int x = 0; x < this.getWidth(); x++) 
			{
			        transparent.setRGB(x,y,c);
			}
		}
		
		return transparent;
		
	}
	
	// HELPER METHODS
	
	/**
	 * Method to determine whether a pixel at (x,y) is transparent from its alpha value in the aRGB color model.
	 * The aRGB value is represented by a 32-bit unsigned integer. Bits 24 to 31 represent the alpha value.
	 * 
	 * Code attributed to chubbsondubs on Stack Overflow
	 * https://stackoverflow.com/questions/8978228/java-bufferedimage-how-to-know-if-a-pixel-is-transparent
	 * @param x - x-position of the pixel in the BufferedImage
	 * @param y - y-position of the pixel in the BufferedImage
	 * @return true is the pixel is transparent; false otherwise
	 */
	public boolean isTransparent(int x, int y)
	{
		// get the aRGB color value
		int pixelRGB = getRGB(x, y);
		if ((pixelRGB>>24) == 0x000000) //alpha value that signifies the pixel is transparent
			return true;
		else
			return false;
	}

}
