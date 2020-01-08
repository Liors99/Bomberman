package bombs;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Entity;
import images.ExtendedImage;

/**
 * 
 * Class that handles powerups spawning and rendering 
 *
 */
public class Powerup extends Entity
{
	
	private String name;
	private int type; // 1 - speedup, 2 - TBD, 3 - TBD
	private int value; //rate of powerups
	private ExtendedImage img;
	
	/**
	 * The constructor of the PowerUp
	 * @param x - x position 
	 * @param y - y position
	 * @param name - name of powerup
	 * @param type - type of powerup
	 * @param value - value for the powerup
	 * @param img - the image of the powerup
	 */
	public Powerup(float x, float y, String name, int type, int value, ExtendedImage img) {
		super(x, y);
		this.img = img;
		this.name = name;
		this.type = type;
		this.value = value;
	}


	@Override
	public void update() {	
	}

	/**
	 * A method that renders the powerup
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(this.img, (int)this.x, (int)this.y, 
				this.width, this.height, null);	
	}
	
	
	//GETTERS & SETTERS
	public void setImage(ExtendedImage img) {
		this.img = img;
	}
	
	public int getType() {
		return this.type;
	}
}

