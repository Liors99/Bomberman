package eventHandling;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Class that handles button events on a single button
 * @author Lior Somin
 *
 */
public class UIButton{
	
	private  BufferedImage[] images;
	private float x,y;
	private int width,height;
	private Rectangle bounds;
	private String name;
	private boolean hover_state=false;
	private boolean clicked=false;
	
	/**
	 * Constructor for any button on the screen
	 * @param x - the x position of the button
	 * @param y - the y position of the button
	 * @param width - the width of the button
	 * @param height - the height of the button
	 * @param images - the images that associate with the button
	 */
	public UIButton(float x, float y, int width, int height, BufferedImage[] images, String name) {
		this.images=images;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.bounds=new Rectangle((int) x, (int) y, width,height);
		this.name=name;
		
	}
	
	/**
	 * Render method for the button, it draws the correct button on the screen depending on whether you hover over it or not.
	 * @param g- the graphics component
	 */
	public void render(Graphics g) {
		if(hover_state) {
			g.drawImage(images[1], (int) this.x, (int) this.y, null);
		}
		else {
			g.drawImage(images[0], (int) this.x, (int) this.y, null);
		}
	}

	
	/**
	 * A method that checks if the mouse is moving and tells whether you move over the button
	 * @param e
	 */
	public void MouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) {
			hover_state=true;
		}
		else {
			hover_state=false;
		}
	}
	
	/**
	 * A method that checks if you pressed on the button
	 * @param e
	 */
	public void MouseRelease(MouseEvent e) {
		if(hover_state) {
			clicked=true;
		}
	}
	
	
	//GETTERS AND SETTERS
	
	public boolean isClicked() {
		return clicked;
	}
	

	public boolean isHovered() {
		return hover_state;
	}
	
	public String getName() {
		return this.name;
	}

}
