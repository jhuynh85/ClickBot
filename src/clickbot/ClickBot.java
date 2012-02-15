package clickbot;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ClickBot {

	private static boolean running = true;
	private static Point pt;
	static MainWindow mw;
	static ImageFind imgF;
	static BufferedImage im;
	static BufferedImage tl;
	static BufferedImage br;
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
		load();
		mw.status("Images loaded");
		imgF = new ImageFind(tl, br);
	}

	/**
	 * Loads image files
	 * 
	 * @throws Exception
	 */
	private static void load() throws Exception {
		try {
			im = ImageIO.read(new File("src/clickfast.png"));
			tl = ImageIO.read(new File("src/tl.png"));
			br = ImageIO.read(new File("src/br.png"));
			System.out.println("Files loaded successfully");
			System.out.println("tl: " + tl.getWidth() + "x" + tl.getHeight());
			System.out.println("br: " + br.getWidth() + "x" + br.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calibrates screen coordinates
	 */
	public static void calibrate() {
		mw.status("Searching for anchors");
		if (!imgF.findAnchors())
			mw.status("Can't find anchors");
	}

	/**
	 * Executes search and click routines
	 * 
	 * @throws Exception
	 */
	public static void run() throws Exception {
		running = true;
		while (running) {
			if ((pt = imgF.findImg(im)) != null) {
				moveClick(pt);
			}
			Thread.sleep(5);
		}
	}

	/**
	 * Stops bot from running
	 */
	public static void stop() {
		running = false;
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
