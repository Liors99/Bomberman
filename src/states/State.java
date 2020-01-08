package states;
import java.awt.Graphics;

import game.Game;

/**
 * 
 * Parent/base class for game state subclasses (e.g., main menu, settings, game)
 * Credit: CodeNMore video: https://www.youtube.com/watch?v=871zoXsYrbI&index=11&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 *
 */
public abstract class State {
	
	// STATE MANAGEMENT
	public static State current_state = null; //Initializes the state to nothing
	// GAME CLASS
	protected Game game;
	
	/**
	 * Setter method for state
	 * @param state - set to this state
	 */
	public static void setState(State state) {
		current_state = state; //Changes the current state of the game
	}
	
	/**
	 * Getter method for state
	 * @return current state
	 */
	public static State getState() {
		return current_state;
	}

	
	
	/**
	 * Constructor for State object
	 * @param game
	 */
	public State(Game game) {
		this.game = game;
	}
	
	// ABSTRACT STATE METHODS
	public abstract void update();
	public abstract void render(Graphics g);

}
