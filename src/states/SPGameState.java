package states;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import eventHandling.PauseListener;
import game.Game;
import game.Map;
import images.Assets;
import players.Enemy;
import players.Human;
import players.Player;

/**
 * THe single player component of the game, player versus AI
 * @author Lior Somin
 *
 */
public class SPGameState extends State{
	
	private Human human1;
	//private Player player2;
	private Enemy enemy1;
	private ArrayList<Player> players;
	private ArrayList<int[]> illegalSpawnLocs; 
	private Map map;
	private PauseMenu pause_menu;
	private PauseListener pauser;
	private HashSet<Player> all_dead_players;
	private EndGameMenu ender;
	private long gameEndTimer;
	private long timeBeforeGameEnd = 200;
	
	// Start tile, defined in terms of (row,col) for each player
	
	/**
	 * The constructor for the single player game state
	 * @param game - the game object
	 */
	public SPGameState(Game game) {
		super(game); // call constructor of State class
		// Instantiate players
		//************************************
		players = new ArrayList<Player>();
		human1 = new Human(game, Assets.player1, game.getTileSize()*17, game.getTileSize()*17);
		players.add(human1);
		human1.setKeyManager(game.getKeyManagerPlayer1());
		//************************************
		//Instantiate AI enemies - depends on map, so must come after map construction
		enemy1 = new Enemy(game, Assets.enemy1, game.getTileSize()*1, game.getTileSize()*1);
		players.add(enemy1);
		// Create ArrayList of illegal destruction block/brick spawning locations
		this.createIllegalSpawnLocs();
		// Create map, assign to game instance
		map = new Map(game, this.illegalSpawnLocs);	
		//************************************
		map.addPlayertoMap(human1);
		map.addPlayertoMap(enemy1);
		//************************************
		game.setMap(map);
		
		pauser = new PauseListener();
		pauser.setKeyManager(game.getManagerPauser());
		all_dead_players = new HashSet<Player>();
		
	}

	/**
	 * A method that updates all the components in this state
	 */
	@Override
	public void update() {
		
		if(!this.game.isEnded()) {
			if(pauser.getTrigger()) {
				pauser.setTrigger(false);
				pause_menu=new PauseMenu(game);
				this.game.setPause(true);
			}
			
			if (this.game.isPaused() == false) {
				for(Player p:players) {
					p.update();
					if(p.isDead) {
						all_dead_players.add(p);
					}
				}
				
				pauser.update();
				//map.printMap(players);  //optional map printer
			}
			else {
				pause_menu.update();
			}
		}
		
		else {
			ender.update();
		}
		
		
		if(all_dead_players.size() >= players.size() - 1) {
			gameEndTimer += this.game.getTimeSinceLast();
			if (gameEndTimer > timeBeforeGameEnd)
			{
				this.game.setEnded(true);
				ender = new EndGameMenu(game);
				
				all_dead_players.clear();
			}
		}	
	}
	

	/**
	 * A method that renders all the components in this state
	 */
	@Override
	public void render(Graphics g) {
		map.render(g);
		human1.render(g);
		enemy1.render(g);
		//player2.render(g);
		if(this.game.isPaused()) {
			pause_menu.render(g);
		}
		
		if(this.game.isEnded()) {
			ender.render(g);
		}
	}
	
	/**
	 * A method that creates the illegal spawns
	 */
	private void createIllegalSpawnLocs()
	{
		illegalSpawnLocs = new ArrayList<int[]>();
		//************************************
		for (Player player: this.players)
		{
			illegalSpawnLocs.add(new int[] {player.getCol(), player.getRow()});
			for (int[] loc: player.getExtendedCrossSectionPairs())
			{
				illegalSpawnLocs.add(loc);
			}
		}
	}
	
	
}
