package states;
import java.awt.Graphics;

import eventHandling.MouseKeyManager;
import eventHandling.UIButton;
import eventHandling.UIManager;
import game.Game;
import images.Assets;

/**
 * 
 * Class that acts as the menu state
 *
 */
public class MenuState extends State{
	
	private UIManager uiManager;
	private MouseKeyManager mouseManager;
	private UIButton Play_sp;
	private UIButton Play_mp;
	// Variables for Start button
	private static final int startWidth = 300;
	private static final int startHeight = 50;

	/**
	 * The constructor for the menu class
	 * @param game
	 */
	public MenuState(Game game) {
		super(game);
		uiManager=new UIManager();
		mouseManager=game.getMouseManager();
		// Add start button to UI manager
		uiManager.addObjs(new UIButton((this.game.getWidth() - startWidth)/2, (this.game.getHeight() - startHeight)/2+100,
				startWidth, startHeight, Assets.Button_Start_sp,"Play Button"));
		uiManager.addObjs(new UIButton((this.game.getWidth() - startWidth)/2, (this.game.getHeight() - startHeight)/2-100,
				startWidth, startHeight, Assets.Button_Start_mp,"Play Button"));
		mouseManager.setUI_manager(uiManager);
		Play_sp=uiManager.getObjs().get(0);
		Play_mp=uiManager.getObjs().get(1);	
	}

	
	/**
	 * Updates the state of every element on the menu
	 */
	@Override
	public void update() {
		if (Play_sp.isClicked()) {
			this.game.setEnded(false);
			State.setState(new SPGameState(this.game));
			
		}
		if(Play_mp.isClicked()) {
			this.game.setEnded(false);
			State.setState(new MPGameState(this.game));
		}
	}

	/**
	 * Renders every element on the menu
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.menu_bg,0,0,this.game.getWidth(), this.game.getHeight(),null);
		uiManager.render(g);
		
	}

}
