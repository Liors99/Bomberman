package states;
import java.awt.Graphics;

import eventHandling.MouseKeyManager;
import eventHandling.UIButton;
import eventHandling.UIManager;
import game.Game;
import images.Assets;

/**
 * The pause menu state of the game when the game is paused
 * @author Liors99
 *
 */
public class PauseMenu extends State{
	
	private UIManager UI_manager;
	private MouseKeyManager MouseManager;
	private UIButton BackToMenu, ResumeButton;
	//Variables for Resume and Back buttons
	private static final int startWidth = 300;
	private static final int startHeight = 50;
	
	/**
	 * A constructor the pause menu
	 * @param game - the game object
	 */
	public PauseMenu(Game game) {
		super(game);
		UI_manager=new UIManager();
		MouseManager=game.getMouseManager();
		UI_manager.addObjs(new UIButton((this.game.getWidth() - startWidth)/2, this.game.getHeight()*2/6 - startHeight/2, startWidth, startHeight, Assets.Button_resume, "Resume Button"));
		UI_manager.addObjs(new UIButton((this.game.getWidth() - startWidth)/2, this.game.getHeight()*4/6 - startHeight/2, startWidth, startHeight, Assets.Button_to_menu, "Back Button"));
		MouseManager.setUI_manager(UI_manager);
		ResumeButton=UI_manager.getObjs().get(0);
		BackToMenu=UI_manager.getObjs().get(1);
		
	}
	
	/**
	 * Updates all the buttons
	 */
	@Override
	public void update() {
		
		if(ResumeButton.isClicked()) {
			this.game.setPause(false);
			//GameState.isPaused=false;
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
