import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Clouds extends GameComponent {

	private static final long serialVersionUID = 1L;

	public static ArrayList<Integer[]> clouds = new ArrayList<Integer[]>();

	BufferedImage cloud;
	
	Clouds(Graphics gr) {
		super(gr);
		try {
			cloud = ImageIO.read(new File("Cloud.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (clouds.size() == 0) {
			clouds.add(new Integer[]{(int) 0, (int) (Math.random()*4 + 1)});
		}
		while (clouds.size() == 0 || clouds.get(clouds.size() - 1)[0] + cloud.getWidth()/clouds.get(clouds.size() - 1)[1] < MainGame.screensize.width) {
			clouds.add(new Integer[]{(int) clouds.get(clouds.size() - 1)[0] + cloud.getWidth()/clouds.get(clouds.size() - 1)[1]*2/3, (int) (Math.random()*4 + 1)});
		}
		if (clouds.size() > 0) {
			while (clouds.get(0)[0] + cloud.getWidth()/clouds.get(0)[1] < -1000) {
				clouds.remove(0);
			}
		}
		for (int i = 0; i < clouds.size(); i++) {
			g.drawImage(cloud, clouds.get(i)[0], 0, cloud.getWidth()/clouds.get(i)[1], cloud.getHeight()/clouds.get(i)[1], null);
				clouds.get(i)[0] -= (int) Boarder.xvel/(2*clouds.get(i)[1]);
		}
	}
		
}
