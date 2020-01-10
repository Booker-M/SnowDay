import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Boarder extends GameComponent {

	private static final long serialVersionUID = 1L;
	public static int xpos;
	public static int ypos;
	public static double xvel;
	public static double yvel;
	public static double vel;
	public static double spin;
	public static int direction;
	public static boolean landed;
	public static int slowmo;
	public static boolean crash;
	
	public Boarder(Graphics g) {
		super(g);
		xpos = MainGame.screensize.width/2;
		ypos = 0;
		xvel = 0;
		yvel = 0;
		spin = 0;
		direction = 0;
		landed = false;
		slowmo = 1;
		crash = false;
	}
	
	public static void newGame() {
	    xpos = MainGame.screensize.width/2;
        ypos = 0;
        xvel = 0;
        yvel = 0;
        spin = 0;
        direction = 0;
        landed = false;
        slowmo = 1;
        crash = false;
	}

	public static void move() {
		if (onGround()) {
			if (slowmo > 1) {
				slowmo = 1;
			}
			slide();
			if (onGround() && !crash) {
				if (Controller.w == true) {
					jump();
				}
			}
//			System.out.println("Slide: " + xpos + ", " + ypos + ", " + direction + ", " + vel);
		}
		else {
			fall();
			if (!onGround()) {
				if (landed == true) {
					landed = false;
				}
//				System.out.println("Fall: " + xpos + ", " + ypos + ", " + direction + ", " + vel);
			}
		}
		xpos+= xvel/slowmo;
		if ((int) (ypos + yvel/slowmo) <= Slope.yValues((int) (xpos))) {
			ypos = Slope.yValues((int) (xpos)) - 5;
		}
		else {
			ypos+= yvel/slowmo;
		}
		if ((int) vel == 0 && !crash) {
			push();
		}
	}

	private static void jump() {
		xvel -= Math.sin(Math.toRadians(direction))*25;
		yvel += Math.cos(Math.toRadians(direction))*25;
		vel = Math.sqrt(xvel*xvel + yvel*yvel);
	}
	
	private static void push() {
		xvel += Math.cos(Math.toRadians(direction))*10;
		yvel += Math.sin(Math.toRadians(direction))*10;
		vel = Math.sqrt(xvel*xvel + yvel*yvel);
	}

	private static void limitVelocity() {
		vel = 50;
		if (onGround()) {
			xvel = Math.cos(Math.toRadians(direction))*vel;
			yvel = Math.sin(Math.toRadians(direction))*vel;
		}
		else {
			xvel = Math.cos(Math.atan2(yvel, xvel))*vel;
			yvel = Math.sin(Math.atan2(yvel, xvel))*vel;
		}
		
	}

	static boolean onGround() {
		if(Slope.yValues.size() > 0 && ypos > Slope.yValues(xpos)) {
			return false;
		}
		return true;
	}

	public static void fall() {
		int dir;
		if (Controller.a == true && !crash) {
			spin+= 0.5;
		}
		if (Controller.d == true && !crash) {
			spin-=0.5;
		}
		if (ypos < Slope.yValues(xpos) + 500 && Math.abs(spin) > 2 && yvel < 0) {
			if (ypos > Slope.yValues(xpos) + 50) {
				if (slowmo == 1) {
					slowmo = (int) (Math.abs(spin)/2 + 15);
					spin = spin/slowmo;
				}
				else {
					slowmo = (int) (Math.abs(spin)/2 + 15);
				}
			}
		}
		else if (slowmo > 1) {
			spin = spin*slowmo;
			slowmo = 1;
		}
		while (spin < -180) {
			spin += 360;
		}
		while (spin > 180) {
			spin -= 360;
		}
		while (spin < -15) {
			spin++;
		}
		while (spin > 15) {
			spin--;
		}
		dir = (int) (direction + spin);
		while (dir < -180) {
			dir += 360;
		}
		while (dir > 180) {
			dir -= 360;
		}
		direction = dir;
		yvel+= MainGame.gravity/slowmo;
		vel = Math.sqrt(xvel*xvel + yvel*yvel);
		if (vel > 50) {
			limitVelocity();
		}
	}
	
	public static void slide() {
		int dir;
		if ((int) xvel > 0) {
			dir = (int) (Math.toDegrees(Math.atan2((Slope.yValues((int) (xpos + xvel)) - Slope.yValues(xpos)), (int) xvel)));
		}
		else {
			dir = (int) (Math.toDegrees(Math.atan2((Slope.yValues(xpos + 1) - Slope.yValues(xpos - 1)), 3)));
		}
		while (dir < -180) {
			dir += 360;
		}
		while (dir > 180) {
			dir -= 360;
		}
		while (dir > 90) {
			dir--;
		}
		while (dir < -90) {
			dir++;
		}
		if (Math.abs(direction - dir) > 90 && Math.abs(direction - dir) < 180) {
			if (!landed && !crash) {
				crash = true;
//				MainGame.endGame();
			}
			else {
				dir = direction;
			}
		}
		else {
			spin = dir - direction;
			while (spin < -180) {
				spin += 360;
			}
			while (spin > 180) {
				spin -= 360;
			}
			while (spin < -15) {
				spin++;
			}
			while (spin > 15) {
				spin--;
			}
		}
		direction = dir;
		if (!landed) {
			xvel = Math.abs(Math.cos(Math.toRadians(direction)))*xvel;
			yvel = Math.abs(Math.sin(Math.toRadians(direction)))*yvel;
			landed = true;
		}
		else {
			if (crash) {
				xvel = Math.cos(Math.toRadians(direction))*vel*0.92;
				yvel = Math.sin(Math.toRadians(direction))*(vel*0.92 - MainGame.gravity);
			}
			else {
				xvel = Math.cos(Math.toRadians(direction))*vel*0.99;
				yvel = Math.sin(Math.toRadians(direction))*(vel*0.99 - MainGame.gravity);
			}
		}
		vel = Math.sqrt(xvel*xvel + yvel*yvel);
//		while ((int) (ypos + yvel) < Slope.yValues((int) (xpos + xvel)) && ypos >= Slope.yValues((int) (xpos)) && (int) vel > 0) {
//			vel--;
//			xvel = Math.cos(Math.toRadians(direction))*vel;
//			yvel = Math.sin(Math.toRadians(direction))*vel;
//		}
	}

	@Override
	public void refresh() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage boarder = null;
		try {
			String image = new String();
			if (!onGround()) {
				image = "BoarderSpin.png";
			}
			else if (crash) {
				image = "BoarderCrash.png";
			}
			else if( Math.abs(vel) < 2) {
				image = "BoarderPush.png";
			}
			else {
				image = "Boarder.png";
			}
			boarder = ImageIO.read(new File(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		AffineTransform tx = AffineTransform.getRotateInstance(-Math.toRadians(direction), boarder.getWidth()/2, boarder.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		if (onGround()) {
			g.drawImage(op.filter(boarder, null), (int) (MainGame.screensize.width/2 - boarder.getWidth()/2 - Math.sin(Math.toRadians(direction))*(boarder.getHeight()/2 - 10)), (int) (MainGame.screensize.height/2 - boarder.getHeight() + 5), null);
//			g.drawImage(op.filter(boarder, null), (int) (MainGame.screensize.width/2 - Math.cos(Math.toRadians(direction))*boarder.getWidth()/2 - Math.sin(Math.toRadians(direction))*boarder.getHeight()), (int) (MainGame.screensize.height/2 - Math.sin(Math.toRadians(direction))*boarder.getWidth()/2 - Math.cos(Math.toRadians(direction))*boarder.getHeight()), null);
		}
		else {
			g.drawImage(op.filter(boarder, null), (int) (MainGame.screensize.width/2 - boarder.getWidth()/2), (int) (MainGame.screensize.height/2 - boarder.getHeight()), null);
		}
	}

}
