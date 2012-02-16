package clickbot;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * Bot for the Clickfast Flash game
 * 
 * @author Joseph Huynh
 * 
 */
public class ClickBot {

	private static boolean running = true;
	private static Point pt;
	static MainWindow mw;
	static ImageFind imgF;
	static Robot rob;

	/**
	 * Initializes windows and search class
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		rob = new Robot();
		mw = new MainWindow();
		imgF = new ImageFind();
	}

	/**
	 * Calibrates screen coordinates
	 */
	public static void calibrate() {
		imgF.setAnchors();
		mw.status("Ready");
	}

	/**
	 * Executes search and click routines
	 * 
	 * @throws Exception
	 */
	public static void run() throws Exception {
		running = true;
		mw.status("Running");
		while (running) {
			if ((pt = imgF.findImg()) != null) {
				moveClick(pt);
			}
			Thread.sleep(50);
		}
	}

	/**
	 * Stops bot from running
	 */
	public static void stop() {
		running = false;
		mw.status("Ready");
	}

	/**
	 * Moves mouse pointer to given point and clicks
	 * 
	 * @param a
	 *            Point to move to
	 */
	private static void moveClick(Point a) {
		rob.mouseMove(a.x, a.y);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

}
