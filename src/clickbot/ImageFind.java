package clickbot;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * Class for finding a distinct image on the screen.
 * 
 * @author Joseph Huynh
 */
public class ImageFind {
	private static int target = 2424318; // RGB value of target
	private Point topLeft; // Coordinates of top-left anchor
	private Point botRight; // Coordinates of bottom-right anchor
	private BufferedImage screen;
	private Robot bot;
	private Dimension screenSize;
	private Rectangle screenRectangle;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	ImageFind() throws Exception {
		bot = new Robot();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenRectangle = new Rectangle(screenSize);
	}

	/**
	 * Searches within anchor points for a pixel with the target color
	 * 
	 * @return Point with coordinates of pixel with matching color
	 */
	public Point findImg() {
		screen = bot.createScreenCapture(screenRectangle);
		int rgb;
		for (int i = topLeft.x; i < botRight.x; i += 15) {
			for (int j = topLeft.y; j < botRight.y; j += 15) {
				rgb = -(screen.getRGB(i, j));
				// Target found
				if (rgb == target) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * Sets the anchor points (boundaries of flash game)
	 */
	public void setAnchors() {
		// Anchor points for 1440x900 screen: (455, 300) (980, 660)
		topLeft = new Point((int) (screenSize.width / 3.2),
				screenSize.height / 3);
		botRight = new Point((int) (screenSize.width / 1.47),
				(int) (screenSize.height / 1.36));
		System.out.println("TL:(" + topLeft.x + "," + topLeft.y + ") BR:("
				+ botRight.x + "," + botRight.y + ")");
	}
}
