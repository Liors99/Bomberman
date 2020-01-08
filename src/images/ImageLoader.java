package images;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * Class for loading images
 * Credit: CodeNMore video: https://www.youtube.com/watch?v=JTulErIM7Ec&index=7&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ 
 *
 */
public class ImageLoader {
	
	/**
	 * Method for loading images
	 * @param path - the location of the image 
	 * @return
	 */
	public static ExtendedImage loadImage(String path) {
		try {
			return new ExtendedImage(ImageIO.read(ImageLoader.class.getResource(path))); // load image
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1); // exit in case image is not loaded
		}
		
		return null; // in case image is not loaded
	}
}
