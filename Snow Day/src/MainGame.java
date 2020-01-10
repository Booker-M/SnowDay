import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class MainGame extends JPanel {

	private static final long serialVersionUID = 1L;
	static JFrame window = new JFrame("Snow Day");
	private static Controller controller = new Controller();
	private static Graphics g;
	private static JPanel panel = new JPanel();
	public static JLabel text = new JLabel();
	private static JPanel textPanel = new JPanel();
	public static double gravity = -1;
	public static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private static Slope slope;
	private static Boarder boarder;
	private static Snow snow;
    static FrontTrees frontTrees;
    static BackTrees backTrees;
    static Mountains mountains;
    static Clouds clouds;
    static Powder powder;
    static boolean playing = false;
    static int highscore = 0;
	
	public static void main(String[] args) {
		newGame();
	}
	
	static void runGame() {
		playing = true;
		while(!(Boarder.crash && Controller.keysPressed > 0)) {
			slope.refresh();
			panel.updateUI();
			Boarder.move();
			if (Boarder.xpos - screensize.width/2 > highscore) {
				highscore = Boarder.xpos - screensize.width/2;
			}
        	if (Boarder.crash) {
        		text.setFont(new Font("Verdana",1,20));
        		if (highscore == Boarder.xpos - screensize.width/2) {
        			text.setText("You crashed! You set a record of " + (Boarder.xpos - screensize.width/2) + " feet. Press any key to restart.");
        		}
        		else {
            		text.setText("You crashed! You travelled " + (Boarder.xpos - screensize.width/2) + " feet. Your best is " + highscore + ". Press any key to restart.");
        		}
        	}
        	else if (Boarder.xpos < screensize.width/2 + 500) {
        		text.setFont(new Font("Verdana",1,50));
                text.setText("Snow Day");
        	}
        	else if (Boarder.xpos < screensize.width/2 + 3000) {
        		text.setFont(new Font("Verdana",1,25));
        		text.setText("Press W or Up to jump.");
        	}
        	else if (Boarder.xpos < screensize.width/2 + 6000) {
        		text.setFont(new Font("Verdana",1,25));
                text.setText("Press A and D or Left and Right to spin.");
        	}
        	else {
        		text.setFont(new Font("Verdana",1,25));
        		text.setText("" + (Boarder.xpos - screensize.width/2));
        	}
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		reset();
		runGame();
	}
	
	static boolean isPlaying() {
		return playing;
	}

	public static void newGame() {
        window.setSize(screensize.width, screensize.height);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(true);
		
		panel.setLayout(new OverlayLayout(panel));
		panel.setLocation(screensize.width/2, screensize.height/2);
		panel.setVisible(true);
		
        text.setVisible(true);
        
        textPanel.setBackground(new Color(172,216,230));
        textPanel.add(text);
		
		reset();
		
		panel.add(snow);
		panel.add(frontTrees);
		panel.add(boarder);
		panel.add(slope);
		panel.add(powder);
		panel.add(backTrees);
		panel.add(mountains);
		panel.add(clouds);
		panel.add(textPanel);
		
		window.addKeyListener(controller);
		window.add(panel);
        window.setVisible(true);
        
        runGame();
	}
	
	public static void reset() {
	    slope = new Slope(g);
        slope.setVisible(true);
        
        frontTrees = new FrontTrees(g);
        backTrees = new BackTrees(g);
        mountains = new Mountains(g);
        clouds = new Clouds(g);
        powder = new Powder(g);
        snow = new Snow(g);
        
        boarder = new Boarder(g);
        boarder.setVisible(true);
	}
	
}
