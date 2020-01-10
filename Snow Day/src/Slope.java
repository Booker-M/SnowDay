import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Slope extends GameComponent {
	
	private static final long serialVersionUID = 1L;

	public Slope(Graphics g) {
		super(g);
		lines.removeAll(lines);
		yValues.removeAll(yValues);
	}

	int minAngle = -90;
	int maxAngle = 45;
	int minLength = 750;
	int maxLength = 1250;
	
	static ArrayList<Line> lines = new ArrayList<Line>();
	static ArrayList<Integer> yValues = new ArrayList<Integer>();
	public static ArrayList<Integer[]> trees = new ArrayList<Integer[]>();
	
	private void addNewComponent() {
		int angle = ThreadLocalRandom.current().nextInt(minAngle, maxAngle + 1);
		if (angle > 0 ) {
			angle = ThreadLocalRandom.current().nextInt(minAngle, maxAngle + 1);
		}
    	if (lines.size() > 0) {
    		if (lastAngle() > 0 && !lastConcavity()) {
				angle = ThreadLocalRandom.current().nextInt(0, maxAngle + 1);
    		}
			if (lastAngle() < 0 && !lastConcavity()) {
	    		angle = ThreadLocalRandom.current().nextInt(minAngle, 1);
			}
    	}
    	int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
    	if (angle > 0) {
    		length = length/2;
    	}
		addLine(Math.toRadians(angle), length);
	}
	
    private void addLine(double angle, int length) {
    	if (lines.size() == 0) {
    		lines.add(new Line(0, 0, (int) (length*Math.cos(angle)), (int) (length*Math.sin(angle)), true));
    	}
    	else {
        	Line last = lines.get(lines.size() - 1);
    		int x2 = (int) (last.getX2() + length*Math.cos(angle));
    		int y2 = (int) (last.getY2() + length*Math.sin(angle));
    		boolean concave;
    		if(lastConcavity()) {
    			if(angle > 0 && lastAngle() < 0) {
    				concave = false;
    			}
    			else {
    				concave = false;
    			}
    		}
    		else {
    			if(angle < 0 && lastAngle() > 0) {
    				concave = false;
    			}
    			else {
    				concave = true;
    			}
    		}
    		lines.add(new Line(last.getX2(), last.getY2(), x2, y2, concave));
    	}
		addYValues();
	}
    
    public void addYValues() {
		int x1 = lines.get(lines.size() - 1).getX(), x2 = lines.get(lines.size() - 1).getX2();
		int y1 = lines.get(lines.size() - 1).getY(), y2 = lines.get(lines.size() - 1).getY2();
		for(double t = 0; t <= 1; t = t + 0.001) {
			int xcoord;
			int ycoord;
			if (lines.get(lines.size() - 1).getConcave()) {
				xcoord = (int) ((1 - t)*(1 - t)*x1 + 2*(1 - t)*t*x1 + t*t*x2);
				ycoord = (int) ((1 - t)*(1 - t)*y1 + 2*(1 - t)*t*y2 + t*t*y2);			
			}
			else {
				xcoord = (int) ((1 - t)*(1 - t)*x1 + 2*(1 - t)*t*x2 + t*t*x2);
				ycoord = (int) ((1 - t)*(1 - t)*y1 + 2*(1 - t)*t*y1 + t*t*y2);		
			}
			if(yValues.size() < xcoord) {
				yValues.add(ycoord);
				if ((int) (Math.random()*300) == 1) {
					trees.add(new Integer[]{xcoord, (int) (Math.random()*150 + 151), (int) (Math.random()*2)});
				}
			}
		}
    }
    	
    private int lastAngle() {
    	return lines.get(lines.size() - 1).getAngle();		
	}
    
    private boolean lastConcavity() {
    	return lines.get(lines.size() - 1).getConcave();		
	}

	@Override
	public void refresh() {
		while (yValues.size() < Boarder.xpos + MainGame.screensize.width/2) {
			addNewComponent();
		}
		while (lines.get(0).getX2() < Boarder.xpos - MainGame.screensize.width/2) {
			lines.remove(0);
		}
		if (trees.size() > 0) {
			if (trees.get(0)[0] < Boarder.xpos - MainGame.screensize.width/2) {
				trees.remove(0);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = -MainGame.screensize.width/2; i < MainGame.screensize.width/2; i++) {
			int x = Boarder.xpos + i;
			if (x >= yValues.size() || x < 0) {
				return;
			}
			g.setColor(Color.white);
			if (-yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2 + 100 > MainGame.screensize.height) {
				g.drawLine(x - Boarder.xpos + MainGame.screensize.width/2, -yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2, x - Boarder.xpos + MainGame.screensize.width/2, MainGame.screensize.height);
			}
			else {
				g.drawLine(x - Boarder.xpos + MainGame.screensize.width/2, -yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2, x - Boarder.xpos + MainGame.screensize.width/2, -yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2 + 100);
			}
			if (-yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2 + 101 <= MainGame.screensize.height) {
				g.setColor(Color.lightGray);
				g.drawLine(x - Boarder.xpos + MainGame.screensize.width/2, -yValues.get(x) + Boarder.ypos + MainGame.screensize.height/2 + 101, x - Boarder.xpos + MainGame.screensize.width/2, MainGame.screensize.height);
			}
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MainGame.screensize.width, MainGame.screensize.height);
    }

	public static int yValues(int xpos) {
		return yValues.get(xpos);
	}
    
}