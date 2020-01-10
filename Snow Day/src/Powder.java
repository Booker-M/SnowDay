import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Powder extends GameComponent {

	private static final long serialVersionUID = 1L;
	public static ArrayList<Integer[]> powder = new ArrayList<Integer[]>();
	
	Powder(Graphics gr) {
		super(gr);
	}

	@Override
	public void refresh() {
		if (Boarder.onGround() && powder.size() < 10 && (int) Boarder.vel != 0) {
			powder.add(new Integer[]{Boarder.xpos - 20, -Boarder.ypos - 10, (int) (Math.random()*5 - 2.5 - Boarder.xvel/3), (int) (-Math.random()*7 + (Math.random()*2 + 1)*Boarder.yvel)});
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		refresh();
		g.setColor(Color.white);
		for (int i = 0; i < powder.size(); i++) {
			powder.get(i)[0] += (int) (powder.get(i)[2]);
			powder.get(i)[1] += (int) (powder.get(i)[3]);
			powder.get(i)[3]++;
			if (powder.get(i)[0] < 0 || Slope.yValues.size() <= powder.get(i)[0] || powder.get(i)[1] > -Slope.yValues(powder.get(i)[0])) {
				powder.remove(i);
			}
			else {
				g.fillOval(powder.get(i)[0] - Boarder.xpos + MainGame.screensize.width/2, powder.get(i)[1] + Boarder.ypos + MainGame.screensize.height/2, 5, 5);
			}
		}

	}

}
