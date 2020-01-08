package eventHandling;

/**
 * Class that triggers the pause menu in both game states
 * @author Lior Somin
 *
 */
public class PauseListener {
	private KeyManager keyManager;
	private boolean trigger_pause=false;
	
	/**
	 * Updates the state of the of the pause listener
	 */
	public void update() {
		getInput();
	}
	
	
	/**
	 * Gets input from the user and whether or not to trigger a pause
	 */
	private void getInput() {
		
		if(keyManager.pause) {
			if(trigger_pause==false) {
				trigger_pause=true;
			}
		}
	}
	
	//GETTES AND SETTERS
	
	public boolean getTrigger() {
		return this.trigger_pause;
	}
	
	public void setTrigger(boolean trigger) {
		this.trigger_pause=trigger;
	}
	
	public void setKeyManager(KeyManager keyManager)
	{
		this.keyManager = keyManager;
	}
}
