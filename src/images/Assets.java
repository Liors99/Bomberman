package images;
import java.awt.image.BufferedImage;

/**
 * Class for loading in all of our assets (images, sounds, etc.).
 * Eliminates repeat loading and cropping of images in game loop.
 * Credit: Sprite sheet used for this game is available for free use from 
 * https://opengameart.org/content/bomb-party-the-complete-set.
 * Credit: CodeNmore video : https://www.youtube.com/watch?v=gEPZJYJ4Hf4&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ&index=9
 */
public class Assets {
	
	// Dimension of sprite tiles in pixels
	public static final int spriteDim = 16; 
	
	// Variables that only need to be accessed in this class
	private static ExtendedImage sheet;
	private static ExtendedImage spriteSheet;
	
	// ExtendedImage objects that we want available to other classes
	public static ExtendedImage grass;  
	public static ExtendedImage metal;
	public static ExtendedImage brick;
	public static ExtendedImage speedUp;
	public static ExtendedImage bombUp;
	public static ExtendedImage blastUp;
	
	// ExtendedImage Arrays
	public static ExtendedImage[][] player1;
	public static ExtendedImage[][] player2;
	public static ExtendedImage[][] enemy1;
	
	public static ExtendedImage[] bomb;
	public static ExtendedImage[] explosion;
	public static ExtendedImage[] explosCrossEnd;
	public static ExtendedImage[] explosCrossMid;
	
	public static BufferedImage[] Button_Start_mp;
	public static BufferedImage[] Button_Start_sp;
	public static BufferedImage menu_bg;
	public static BufferedImage[] Button_resume;
	public static BufferedImage[] Button_to_menu;
	public static BufferedImage[] Button_reset;
	
	
	/**
	 * Method called Upon game instantiation to load ("initiate") images used for game graphics;
	 * creates ExtendedImage arrays to be used in graphics and/or animations
	 */
	public static void init() {		
		// Load sprite sheet
		sheet = ImageLoader.loadImage("/newspritesheet.png"); 
		spriteSheet = new ExtendedImage(sheet);
		speedUp = ImageLoader.loadImage("/speedup.png");
		bombUp = ImageLoader.loadImage("/bombup.png");
		blastUp = ImageLoader.loadImage("/blastUp.png");
		
		// Create ExtendedImage arrays for player animations
		//initPlayer1();
		//initPlayer2();
		player1 = initPlayer(spriteDim*4);
		player2 = initPlayer(spriteDim*2);
		enemy1 = initPlayer(spriteDim*1);
		initBomb();
		initExplosion();
		initExplosCrossEnd(); // default orientation is pointing west
		initExplosCrossMid(); // default orientation is pointing west
		
        // Create ExtendedImage objects for map textures
		grass = spriteSheet.crop(spriteDim, 0, spriteDim, spriteDim);
		metal = spriteSheet.crop(10*spriteDim, 4*spriteDim, spriteDim, spriteDim);
		brick = spriteSheet.crop(9*spriteDim, 0, spriteDim, spriteDim);
		
		//Loading Menu elements
		Button_Start_mp= new BufferedImage[2];
		Button_Start_mp[0]=ImageLoader.loadImage("/Menu_elements/Button_play_mult_0.png");
		Button_Start_mp[1]=ImageLoader.loadImage("/Menu_elements/Button_play_mult_1.png");
		
		Button_Start_sp= new BufferedImage[2];
		Button_Start_sp[0]=ImageLoader.loadImage("/Menu_elements/Button_play_AI_0.png");
		Button_Start_sp[1]=ImageLoader.loadImage("/Menu_elements/Button_play_AI_1.png");
		
		menu_bg=ImageLoader.loadImage("/Menu_elements/Background.png");
		
		Button_resume=new BufferedImage[2];
		Button_resume[0]=ImageLoader.loadImage("/Pause_elements/button_resume_0.png");
		Button_resume[1]=ImageLoader.loadImage("/Pause_elements/button_resume_1.png");
		
		Button_to_menu=new BufferedImage[2];
		Button_to_menu[0]=ImageLoader.loadImage("/Pause_elements/button_back_to_menu_0.png");
		Button_to_menu[1]=ImageLoader.loadImage("/Pause_elements/button_back_to_menu_1.png");
		
		Button_reset=new BufferedImage[2];
		Button_reset[0]=ImageLoader.loadImage("/End_elements/button_play_again_0.png");
		Button_reset[1]=ImageLoader.loadImage("/End_elements/button_play_again_1.png");

	}
		
	/**
	 * A method that loads the images for the players
	 * @param row - the row of the player in the spritesheet
	 * @return - an extendedImage object is returned
	 */
	private static ExtendedImage[][] initPlayer(int row) {
		// ExtendedImage arrays for Up, Down, Left, and Right motions
		ExtendedImage[] playerDown = new ExtendedImage[3];
		ExtendedImage[] playerUp = new ExtendedImage[3];
		ExtendedImage[] playerLeft = new ExtendedImage[3];
		ExtendedImage[] playerRight = new ExtendedImage[3];
		ExtendedImage[] playerLeftTransform = new ExtendedImage[3];
		
		// Crop images from larger sprite sheet	
		playerDown[0] = spriteSheet.crop(spriteDim*1, row, spriteDim, spriteDim);
		playerDown[1] = spriteSheet.crop(spriteDim*2, row, spriteDim, spriteDim);
		playerDown[2] = spriteSheet.crop(spriteDim*3, row, spriteDim, spriteDim);
		playerUp[0] = spriteSheet.crop(0, row, spriteDim, spriteDim);
		playerUp[1] = spriteSheet.crop(spriteDim*8, row, spriteDim, spriteDim);
		playerUp[2] = spriteSheet.crop(spriteDim*9, row, spriteDim, spriteDim);
		playerRight[0] = spriteSheet.crop(spriteDim*4, row, spriteDim, spriteDim);
		playerRight[1] = spriteSheet.crop(spriteDim*6, row, spriteDim, spriteDim);
		playerRight[2] = spriteSheet.crop(spriteDim*7, row, spriteDim, spriteDim);
		
		// To get Left-facing sprites, must transform images through mirroring
		// TO-DO: Kind of cumbersome - should ImageForTransform extend ExtendedImage?
		playerLeftTransform[0] = new ExtendedImage(playerRight[0]);
		playerLeftTransform[1] = new ExtendedImage(playerRight[1]);
		playerLeftTransform[2] = new ExtendedImage(playerRight[2]);
		playerLeft[0] = playerLeftTransform[0].mirrorX();
		playerLeft[1] = playerLeftTransform[1].mirrorX();
		playerLeft[2] = playerLeftTransform[2].mirrorX();
		
		// Put it all together in a 2D array
		ExtendedImage[][] playerArray = new ExtendedImage[][] 
				{playerDown, playerUp, playerLeft, playerRight};
		
		return playerArray;
	}
	
	/**
	 * A method that loads the image for the bomb
	 */
	private static void initBomb()
	{
		// Create ExtendedImage array for bomb ticking animation
		bomb = new ExtendedImage[3]; 
		// Crop images from sprite sheet and add to animation array
		bomb[0] = spriteSheet.crop(spriteDim*4,spriteDim*5,spriteDim,spriteDim);
		bomb[1] = spriteSheet.crop(spriteDim*6,spriteDim*5,spriteDim,spriteDim);
		bomb[2] = spriteSheet.crop(spriteDim*8,spriteDim*5,spriteDim,spriteDim);
	}
	
	/**
	 * A method that loads the images for the explosion
	 */
	private static void initExplosion()
	{
		// Create ExtendedImage array for explosion animation
		explosion = new ExtendedImage[4]; 
		// Crop images from sprite sheet and add to animation array
		explosion[0] = spriteSheet.crop(spriteDim*2, spriteDim*5, spriteDim, spriteDim);
		explosion[1] = spriteSheet.crop(spriteDim*14, spriteDim*5, spriteDim, spriteDim);
		explosion[2] = spriteSheet.crop(spriteDim*14, spriteDim*4, spriteDim, spriteDim);
		explosion[3] = spriteSheet.crop(spriteDim*14, spriteDim*3, spriteDim, spriteDim);
	}
	
	/**
	 * A method that loads the images for the explosion's cross ends
	 */
	private static void initExplosCrossEnd()
	{
		// Create ExtendedImage array for explosion animation
		explosCrossEnd = new ExtendedImage[5]; 
		// Crop images from sprite sheet and add to animation array
		explosCrossEnd[0] = spriteSheet.crop(spriteDim*0,spriteDim*5,spriteDim,spriteDim);
		explosCrossEnd[1] = new ExtendedImage();
		explosCrossEnd[2] = new ExtendedImage();
		explosCrossEnd[3] = new ExtendedImage();
	}
	
	/**
	 * A method that loads the images for the explosion's middle cross section
	 */
	private static void initExplosCrossMid()
	{
		// Create ExtendedImage array for explosion animation
		explosCrossMid = new ExtendedImage[5]; 
		// Crop images from sprite sheet and add to animation array
		explosCrossMid[0] = spriteSheet.crop(spriteDim*1,spriteDim*5,spriteDim,spriteDim);
		explosCrossMid[1] = new ExtendedImage();
		explosCrossMid[2] = new ExtendedImage();
		explosCrossMid[3] = new ExtendedImage();
	}
	
	
}
