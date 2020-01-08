package eventHandling;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Class that handles mouse inputs from the user
 * @author Lior Somin
 *
 */
public class MouseKeyManager implements MouseListener, MouseMotionListener{
	
	private boolean left_pressed, right_pressed;
	private int mouse_x,mouse_y;
	private UIManager UI_manager;

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Changed the mouse coordinates according to the current position of the mouse on screen
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouse_x=e.getX();
		this.mouse_y=e.getY();
		if(UI_manager!=null) {
			UI_manager.MouseMove(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Checks if the left mouse button or right mouse button are being pressed.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			this.left_pressed=true;
		}
		else if(e.getButton()==MouseEvent.BUTTON3) {
			this.right_pressed=true;
		}
		
		
	}

	/**
	 * Checks if the left mouse button or right mouse button are being released.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(e.getButton()==MouseEvent.BUTTON1) {
			this.left_pressed=false;
		}
		else if(e.getButton()==MouseEvent.BUTTON3) {
			this.right_pressed=false;
		}
		
		if(UI_manager!=null) {
			UI_manager.MouseRelease(e);
		}
		
	}
	
	
	
	//GETTER AND SETTERS
	
	public boolean getLeftPress() {
		return this.left_pressed;
	}
	
	public boolean getRightPress() {
		return this.right_pressed;
	}
	
	public int getMouseX() {
		return this.mouse_x;
	}
	
	public int getMouseY() {
		return this.mouse_y;
	}
	
	public void setUI_manager(UIManager u) {
		this.UI_manager=u;
	}
	
	public UIManager getManager() {
		return this.UI_manager;
	}
	
}
