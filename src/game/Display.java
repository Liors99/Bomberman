package game;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * 
 * Class which handles display of game
 * Credit: CodeNMore video: https://www.youtube.com/watch?v=lf9awz6j88Q&index=2&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ
 *
 */
public class Display {
	
	//  Main game window is JFrame object (top-level container)
	private JFrame frame;
	// Graphics for game drawn to Canvas object
	private Canvas canvas;
	
	private String title;      // Window title
	private int width,height;  // Window width and height in pixels
	
	/**
	 * Constructor of our display class
	 * @param title - title of window
	 * @param width - width of title in pixels
	 * @param height - height of title in pixels
	 */
	public Display(String title, int width, int height) {
		this.title=title;
		this.width=width;
		this.height=height;
		create_display();
	}
	
	
	/**
	 * Method for initializing our JFrame and Canvas
	 */
	private void create_display() {
		frame= new JFrame(title);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // Window appears at center of screen
		frame.setVisible(true);
		
		canvas= new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		// Set max and min canvas size to make sure it stays at the specified height and width
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		// Set focusable flag to false; only JFrame has focus (not what is being drawn)
		canvas.setFocusable(false); 
		
		// Add Canvas to JFrame
		frame.add(canvas);
		// Resize our window so that entire Canvas is visible
		frame.pack();
	}
	
	
	/**
	 * Getter method to access canvas from other classes
	 * @return Game canvas
	 */
	public Canvas getCanvas() {
		return canvas;
	}
	
	
	/**
	 * Getter method to access JFrame from other classes
	 * @return JFrame of game
	 */
	public JFrame getFrame() {
		return frame;
	}
	
}
