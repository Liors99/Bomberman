package game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import eventHandling.KeyManager;
import eventHandling.MouseKeyManager;
import images.Assets;
import states.State;
import states.MenuState;
import states.SPGameState;

/**
 * Main Game class
 * Implements runnable - allows for Game instance to be run on a thread (thread of execution
 * in a program)
 * Credit: CodeNmore video: https://www.youtube.com/watch?v=vFRuEgEdO9Q&index=4&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 */
public class Game implements Runnable{
	
	// Specify the number of tiles that will compose the map in the x- and y-directions
	private static final int numTilesX = 19; 
	private static final int numTilesY = 19; 
	// We want the tileSize to be available to all classes
	public static int tileSize; // the size of the tile (in pixels) appropriate to the user's screen resolution
	
	// Variables for game display window
	private Display display;
	public String title = "Bomberman";
	private int width, height;
	

	// Variables for game thread
	private boolean running = false;
	private Thread thread;
	
	// Variable for game canvas
	private BufferStrategy bs; //A way for the PC to draw things on the screen; 
	                           //it's like a " hidden screen" that prevents flickering
	private Graphics g;
	
	// Time-keeping variables
	private long lastTime = 0;
	private long timeSinceLast = 0;
	
	// Other variables
	public State game_state;
	public State menu_state;
	private KeyManager KeyManagerPlayer1;
	private KeyManager KeyManagerPlayer2;
	private KeyManager KeyManagerPauser;
	private Map map;
	private MouseKeyManager MouseManager;
	// Flag to indicate if game is paused
	private boolean isPaused = false;
	// Flag to indicate if game is ended
    private boolean isEnded = false;
    
	
	/**
	 * Constructor for our game class
	 * 
	 */
	public Game() {
		
		// Get the user's screen size so we can optimize the GUI's and its contents' dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenHeight = screenSize.getHeight(); // limiting screen dimension (screenHeight < screenWidth)
		// Set the tile size to the largest possible integer value given the screen's dimensions,
		// the number of tiles in the map, and the tile (sprite) size
		tileSize = ((int) (screenHeight / numTilesY) / Assets.spriteDim) * Assets.spriteDim;
		this.width = tileSize * numTilesX;
		this.height = tileSize * numTilesY;
		// Create new KeyManager instance
		KeyManagerPlayer1 = new KeyManager();
		KeyManagerPlayer2 = new KeyManager();
		KeyManagerPauser = new KeyManager();
		MouseManager = new MouseKeyManager();
	}
	
	/**
	 * Method for initialization of game object; creates display (JFrame container and Canvas
	 * for drawing), adds KeyListener to get user input from keyboard, loads Assets (images 
	 * used in graphics), and sets game state (e.g., playing mode, menu mode, etc.). 
	 */
	private void init() {
		// Create new Display instance in our game thread
		display=new Display(title, width, height);
		//  Get the JFrame of our display and add to it a KeyListener (allows us to listen to
		// keyboard for input)
		display.getFrame().addKeyListener(KeyManagerPlayer1);
		display.getFrame().addKeyListener(KeyManagerPlayer2);
		display.getFrame().addKeyListener(KeyManagerPauser);
		
		//Whichever window is active in the game, will implement the mouse events. 
		display.getCanvas().addMouseListener(MouseManager);
		display.getCanvas().addMouseMotionListener(MouseManager);
		// Initialize assets (i.e., load images to use in game graphics)
		Assets.init();
		// Initialize a game state (by passing this game object) and set current state 
		// to game state (i.e., playing mode)
		game_state = new SPGameState(this);
		menu_state=new MenuState(this);
		State.setState(menu_state);
	}
	
	  
	/**
	 * Method to start the game thread
	 * Synchronized
	 */
	public synchronized void start() {
		// Boolean to indicate game is running
		if (running==true) {return;} //if game is already running, return nothing
		running=true;
		// New thread instance to which we pass game object; means game instance runs on thread
		thread= new Thread(this);
		// Call the run method 
		thread.start();
	}
	
	
	/**
	 * Method to stop the game thread
	 * Synchronized
	 */
	public synchronized void stop() {
		if (running==false) {return;} // if game is running, return nothing
		
		// wait for game thread to complete
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Method for updating all game variables
	 */
	private void update() {
		int Pause=KeyEvent.VK_ESCAPE;
		KeyManagerPauser.update(Pause);
		
		int player1Up = KeyEvent.VK_W;
		int player1Down = KeyEvent.VK_S;
		int player1Left = KeyEvent.VK_A;
		int player1Right = KeyEvent.VK_D;
		int player1Bomb=KeyEvent.VK_SPACE;
		KeyManagerPlayer1.update(player1Up, player1Down, player1Left, player1Right,player1Bomb);
		
		int player2Up = KeyEvent.VK_UP;
		int player2Down = KeyEvent.VK_DOWN;
		int player2Left = KeyEvent.VK_LEFT;
		int player2Right = KeyEvent.VK_RIGHT;
		int player2Bomb=KeyEvent.VK_ENTER;
		KeyManagerPlayer2.update(player2Up, player2Down, player2Left, player2Right,player2Bomb);
		
		// If we have a current state, call tick method of current state
		if(State.getState()!=null) {
			State.getState().update();
		}
		
		// start keeping track of time
		if (this.isPaused)
			this.timeSinceLast = 0;
		else
		    this.timeSinceLast = System.currentTimeMillis() - this.lastTime;
		
		this.lastTime = System.currentTimeMillis();
	}
	
	
	/**
	 * Method for rendering graphics
	 */
	private void render() {
		// Current buffer strategy of our canvas
		// Buffer is a hidden, intermediate drawing of our graphics before final rendering to
		// prevent flickering as graphics elements are rendered
		bs = display.getCanvas().getBufferStrategy();
		// Create buffer strategy if not already defined
		if(bs==null) {
			display.getCanvas().createBufferStrategy(2); // set to 2 buffers
			return;
		}
		
		// Graphics object - draw finished (post-buffering) graphics to canvas 
		g = bs.getDrawGraphics(); 
		
		//clear entire screen between canvas renderings
		g.clearRect(0, 0, width, height);
		
		// Start drawing 
		// Start rendering graphics in the game if the state is not null
		if(State.getState()!=null) {
			State.getState().render(g);
		}
		
		//g.drawImage(Assets.grass,x,grass_ypos,Assets.grass.getWidth(null)*4,Assets.grass.getWidth(null)*4,null);
		
		// End drawing
		bs.show();
		// Dispose of buffer graphics and release system resources
		bs.dispose();
	}
	
	
	/**
	 * The main run method of the game
	 * Required since Game class implements Runnable
	 */
	public void run() {
		// Initialize our Game instance
		init();
		
		// Code to ensure we achieve desired frames-per-second (FPS) on any PC
		int fps = 60; // set frames per second; amount of times to call update() and render() per
		              // second
		double timePerFrame = 1000000000 / fps; // amount of time per frame
		                                        // measured in nanoseconds (1 s = 1000000000 ns)
		
		double delta = 0; // to keep track of whether it is time to render a new frame
		long currentTime;
		long lastTime = System.nanoTime(); //returns the current time of computer in ns
		
		// Game loop 
		while(running) {
			currentTime = System.nanoTime();
				
			delta += (currentTime - lastTime) / timePerFrame; 
			lastTime = currentTime; 
			
			// If delta >= 1, its time to render a new frame
			if (delta >= 1) {
				// Update game variables
				update();
				// Redraw all graphics elements
				render();
				delta--;
			}
		}
		// Stop game thread
		stop();	
	}
	
	//GETTERS AND SETTERS
	
	/**
	 * Getter method for isPaused flag
	 * @return True if the game is paused
	 */
	public boolean isPaused(){
		return this.isPaused;  
	}
	
	
	/**
	 * Setter method for the isPaused flag
	 * @param isPaused - boolean value for isPaused flag; pass 'true' if game is paused, 'false' is game is unpaused
	 */
	public void setPause(boolean isPaused)
	{
		this.isPaused = isPaused;
	}
	
	
	/**
	 * Getter method for isEnded flag
	 * @return True if the game is ended
	 */
	public boolean isEnded(){
		return this.isEnded;  
	}
	
	
	/**
	 * Setter method for the isPaused flag
	 * @param isEnded - boolean value for isEnded flag; pass 'true' if game is ended, 'false' is game is not ended
	 */
	public void setEnded(boolean isEnded)
	{
		this.isEnded = isEnded;
	}
	
	
	/** 
	 * Retrieve the time associated with the last iteration of the update loop
	 * @return the system time of the last update
	 */
	public long getLastTime() {
		return this.lastTime;
	}
	
	
	/** 
	 * Returns the time that has passed in the game since last update call
	 * @return time that has passed since last game update call
	 */
	public long getTimeSinceLast() {
		return this.timeSinceLast;
	}
	
	
	public Map getMap() {
		return map;
	}

	
	public void setMap(Map map) {
		this.map = map;
	}
	
	
	public int getWidth() {
		return width;
	}

	
	public int getHeight() {
		return height;
	}
	
	
	public int getTileSize() {
		return tileSize;
	}
	
	
	public int getNumTilesX() {
		return numTilesX;
	}
	
	
	public int getNumTilesY() {
		return numTilesY;
	}
	
	
	public State getState() {
		return State.getState();
	}
	
	
	/**
	 * Getter method to make KeyManager accessible to other classes
	 * @return KeyManager
	 */
	public KeyManager getKeyManagerPlayer1() {
		return KeyManagerPlayer1;
	}
	
	
	public KeyManager getKeyManagerPlayer2() {
		return KeyManagerPlayer2;
	}
	
	
	public MouseKeyManager getMouseManager() {
		return this.MouseManager;
	}
	
	public KeyManager getManagerPauser() {
		return this.KeyManagerPauser;
	}
		
	
}
