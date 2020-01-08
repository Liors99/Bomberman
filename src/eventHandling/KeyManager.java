package eventHandling;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class that handles key inputs from the user
 * @author Lior Somin
 * Implements KeyListener - allows the program to listen for key events
 *
 */
public class KeyManager implements KeyListener{
	
	private boolean[] keys;	
	public boolean up,down,right,left,bomb,pause;
	
	/**
	 * Constructor for the keyManager
	 */
	public KeyManager() {
		keys = new boolean[256]; // boolean array for key codes
	}
	
	/**
	 * Updates the state of the keys pressed
	 * @param keyEventUp - Keycode for the going up
	 * @param keyEventDown - Keycode for the going down
	 * @param keyEventLeft - Keycode for the going left
	 * @param keyEventRight - Keycode for the going right
	 * @param KeyEventBomb - Keycode for placing the bomb
	 */
	public void update(int keyEventUp, int keyEventDown, int keyEventLeft, int keyEventRight, int KeyEventBomb) {
		up = keys[keyEventUp]; // KeyEvent gets code of key
		down = keys[keyEventDown];
		left = keys[keyEventLeft];
		right = keys[keyEventRight];
		bomb=keys[KeyEventBomb];
	}
	
	/**
	 * Overloaded method for updating the state of the pause key
	 * @param keyEventPause
	 */
	public void update(int keyEventPause) {
		pause=keys[keyEventPause];
	}

	/**
	 * Makes the key in the array true if being pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()]=true; //sets the key code of the key being pressed to true
	}

	/**
	 * Makes the key in the array false if being released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()]=false; //sets the key code of the key not being pressed to false
		
	}

	/**
	 * Must be implemented because of the interface, but not used in this case
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
}
