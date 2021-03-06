public class Line {
	
	private int x;
	private int y;
	private int x2;
	private int y2;
	private boolean concave;
	
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getX2() {
		return x2;
	}


	public void setX2(int x2) {
		this.x2 = x2;
	}


	public int getY2() {
		return y2;
	}


	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public boolean getConcave() {
		return concave;
	}
	
	public void setConcave(boolean concave) {
		this.concave = concave;
	}


	public Line(int x, int y, int x2, int y2, boolean concave) {
		super();
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.concave = concave;
	}
	
	public int getAngle() {
		int dX = x2 - x;
		int dY = y2 - y;
		double rad = Math.atan2(dY, dX);
		return (int) Math.toDegrees(rad);
	}
	
}
