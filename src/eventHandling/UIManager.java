package eventHandling;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class that handles all button events in a given state
 * @author Lior Somin
 *
 */
public class UIManager {
	
	//An arraylist containing all the UI objects on the current screen
	private ArrayList<UIButton> objs;
	
	public UIManager() {
		this.objs=new ArrayList<UIButton>();
	}
	
	/**
	 * Renders every button from the arraylist
	 * @param g - the graphics component
	 */
	public void render(Graphics g) {
		for(UIButton o: this.objs) {
			o.render(g);
		}
	}
	
	/**
	 * Checks if the mouse is being moved onto any button
	 * @param e - the mouseEvent object
	 */
	public void MouseMove(MouseEvent e) {
		for(UIButton o: this.objs) {
			o.MouseMove(e);
		}
	}
	
	/**
	 * Checks if the mouse is being released from any button
	 * @param e - the mouseEvent object
	 */
	public void MouseRelease(MouseEvent e) {
		for(UIButton o: this.objs) {
			o.MouseRelease(e);
		}
	}
	
	/**
	 * A method that adds buttons to the arraylist
	 * @param o - the button being added
	 */
	public void addObjs(UIButton o) {
		this.objs.add(o);
	}
	
	/**
	 * Returns the arraylist containing all buttons
	 * @return - returns the arraylist
	 */
	public ArrayList<UIButton> getObjs(){
		return this.objs;
	}
}
