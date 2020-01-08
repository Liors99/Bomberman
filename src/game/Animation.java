package game;

import images.ExtendedImage;

/**
 * 
 * Class that handles changing the frame of an object to create an animation
 *
 */
public class Animation {
	
	private Game game;
	private int speed, index; // speed to switch between frames
	private long timePassed;
	private ExtendedImage[] frames;
	private boolean isActive = false;
	
	
	/**
	 * Constructor for the animation class
	 * @param game - the game object
	 * @param speed - the speed of the animation
	 * @param frames - the number of frames in the animation
	 * @param isActive - a boolean to check whether the animation should proceed
	 */
	public Animation(Game game, int speed, ExtendedImage[] frames, boolean isActive) {
		this.game = game;
		this.speed = speed;
		this.frames = frames;
		this.isActive = isActive;
		index = 0; // since we update before render, and we want the first frame index to be one for walking (zero is at rest)
		
	}
	
	/**
	 * A method that updates the state of the animation and its variables every time the game is being updated.
	 */
	public void update() {
		// Set up the following if-statement because we don't want to log time as passed unless
		// the animation is active
		if (this.isActive)
		{
			timePassed += this.game.getTimeSinceLast();
			if (timePassed > speed) {
				index++;
				timePassed = 0;
				// To make sure we don't go out of bounds on our frames array index, 
				// go back to beginning
				if (index >= frames.length)
					index = 1; 
			}	
		}
	}
	
	// GETTERS & SETTERS 
	
	public ExtendedImage getCurrentFrame() {
		return frames[index];
	}
	
	public ExtendedImage getStillFrame() {
		return frames[0];
	}
	
	public void setActive() {
		this.isActive = true;
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}

}
