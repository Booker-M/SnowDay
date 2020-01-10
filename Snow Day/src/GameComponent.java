import java.awt.Graphics;
import javax.swing.JComponent;

public abstract class GameComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	static Graphics g;
	
	GameComponent(Graphics gr) {
		g = gr;
	}
	
	public abstract void refresh();

}
