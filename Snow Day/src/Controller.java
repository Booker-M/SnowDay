import java.awt.event.*;

// Controller
public class Controller implements KeyListener {

	public static boolean w, a, d;
	public static int keysPressed;
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
        	w = true;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            a = true;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            d = true;
        } 
        keysPressed++;
	}

	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
        	w = false;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            a = false;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            d = false;
        }
        if (keysPressed > 0) {
        	keysPressed = 0;
        }
	}

}
