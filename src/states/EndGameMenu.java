package states;
import java.awt.Graphics;

import eventHandling.MouseKeyManager;
import eventHandling.UIButton;
import eventHandling.UIManager;
import game.Game;
import images.Assets;

/**
 * 
 * Class that acts as the end game state after the game is finished
 *
 */
public class EndGameMenu extends State{
	
	private UIManager UI_manager;
	private MouseKeyManager MouseManager;
	private UIButton BackToMenu, ResetButton;
	//Variables for Resume and Back buttons
	private static final int startWidth = 300;
	private static final int startHeight = 50;
	
	/**
	 * Constructor the end game menu
	 * @param game - the game object
	 */
	public EndGameMenu(Game game) {
		super(game);
		UI_manager=new UIManager();
		MouseManager=game.getMouseManager();
		UI_manager.addObjs(new UIButton((this.game.getWidth() - startWidth)/2, this.game.getHeight()*2/6 - startHeight/2, startWidth, startHeight, Assets.Button_reset, "Reset Button"));
		UI_manager.addObjs(new UIButton((this.game.getWidth() - startWidth)/2, this.game.getHeight()*4/6 - startHeight/2, startWidth, startHeight, Assets.Button_to_menu, "Back Button"));
		MouseManager.setUI_manager(UI_manager);
		ResetButton=UI_manager.getObjs().get(0);
		BackToMenu=UI_manager.getObjs().get(1);
		
	}
	
	/**
	 * Updates the state of the buttons in this state every time the game updates
	 */
	@Override
	public void update() {
		
		if(ResetButton.isClicked()) {
			if(State.getState() instanceof SPGameState ) {
				this.game.setEnded(false);
				State.setState(new SPGameState(game));
			}
			else if (State.getState() instanceof MPGameState ){
				this.game.setEnded(false);
				State.setState(new MPGameState(game));
			}
			
			
			
		}
		
		if(BackToMenu.isClicked()) {
			this.game.setPause(false);
			//GameState.isPaused=false;
			State.setState(new MenuState(game));
			
		}
		
	}

	
	/**
	 * Renders every element on the menu
	 */
	@Override
	public void render(Graphics g) {
		UI_manager.render(g);
		
	}
}
