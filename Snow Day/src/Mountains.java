import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Mountains extends GameComponent {
	
	private static final long serialVersionUID = 1L;

	public static ArrayList<Integer[]> mountains = new ArrayList<Integer[]>();

	BufferedImage mountain;
	
	Mountains(Graphics gr) {
		super(gr);
		try {
			mountain = ImageIO.read(new File("Mountain.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mountains.size() == 0) {
			mountains.add(new Integer[]{(int) 0, (int) (Math.random()*4 + 1)});
		}
		while (mountains.size() == 0 || mountains.get(mountains.size() - 1)[0] + mountain.getWidth()/mountains.get(mountains.size() - 1)[1] < MainGame.screensize.width) {
			mountains.add(new Integer[]{(int) mountains.get(mountains.size() - 1)[0] + mountain.getWidth()/mountains.get(mountains.size() - 1)[1]*2/3, (int) (Math.random()*4 + 1)});
		}
		if (mountains.size() > 0) {
			while (mountains.get(0)[0] + mountain.getWidth()/mountains.get(0)[1] < -1000) {
				mountains.remove(0);
			}
		}
		for (int i = 0; i < mountains.size(); i++) {
			g.drawImage(mountain, mountains.get(i)[0], MainGame.screensize.height - mountain.getHeight()/mountains.get(i)[1], mountain.getWidth()/mountains.get(i)[1], mountain.getHeight()/mountains.get(i)[1], null);
				mountains.get(i)[0] -= (int) Boarder.xvel/9;
		}
	}
		
}
