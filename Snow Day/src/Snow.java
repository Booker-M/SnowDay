import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snow extends GameComponent {

	private static final long serialVersionUID = 1L;
	public static ArrayList<Integer[]> snow = new ArrayList<Integer[]>();
	
	Snow(Graphics gr) {
		super(gr);
		while (snow.size() < 50) {
			snow.add(new Integer[]{(int) (Math.random()*MainGame.screensize.width), (int) (Math.random()*MainGame.screensize.height)});
		}
	}

	@Override
	public void refresh() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		for (int i = 0; i < snow.size(); i++) {
			g.fillOval(snow.get(i)[0], snow.get(i)[1], 5, 5);
			snow.get(i)[0] -= (int) (Boarder.xvel + 1);	
			if (snow.get(i)[0] > MainGame.screensize.width) {
				snow.get(i)[0] -= MainGame.screensize.width;
			}
			else if (snow.get(i)[0] < 0) {
				snow.get(i)[0] += MainGame.screensize.width;
			}
			snow.get(i)[1] += (int) (Boarder.yvel + 1);	
			if (snow.get(i)[1] > MainGame.screensize.height) {
				snow.get(i)[1] -= MainGame.screensize.height;
			}
			else if (snow.get(i)[1] < 0) {
				snow.get(i)[1] += MainGame.screensize.height;
			}
		}
	}
	
}
