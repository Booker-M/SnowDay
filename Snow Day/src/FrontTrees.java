import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FrontTrees extends GameComponent {

	private static final long serialVersionUID = 1L;
	BufferedImage tree;
	
	FrontTrees(Graphics gr) {
		super(gr);
		try {
			tree = ImageIO.read(new File("Tree.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = -MainGame.screensize.width/2; i < MainGame.screensize.width/2; i++) {
			int x = Boarder.xpos + i;
			if (x >= Slope.yValues.size()) {
				return;
			}
			for(int j = 0; j < Slope.trees.size(); j++) {
				if (Slope.trees.get(j)[0] == x && Slope.trees.get(j)[2] == 0 && -Slope.yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2 - Slope.trees.get(j)[1] + 121 <= MainGame.screensize.height) {
					g.drawImage(tree, x - Boarder.xpos + MainGame.screensize.width/2 - 50, -Slope.yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2 - Slope.trees.get(j)[1] + 121, 60, Slope.trees.get(j)[1], null);
				}
			}
		}
	}

}
