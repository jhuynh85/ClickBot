package clickbot;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Class for finding a distinct image on the screen. Works by getting
 * coordinates for a top-left and a bottom-right anchor, then searching all the
 * pixels in between for the image.
 * 
 * @author Joseph Huynh
 */
public class ImageFind {
	private Point topLeft; // Coordinates of top-left anchor
	private Point botRight; // Coordinates of bottom-right anchor
	private BufferedImage tl;
	private BufferedImage br;
	private BufferedImage screen;
	private Robot bot;
	private Dimension screenSize;
	private Rectangle screenRectangle;

	/**
	 * Constructor when both anchor images are provided.
	 * 
	 * @param tl
	 *            Image of top-left anchor
	 * @param br
	 *            Image of bottom-right anchor
	 * @throws Exception
	 */
	ImageFind(BufferedImage t, BufferedImage b) throws Exception {
		bot = new Robot();
		tl = t;
		br = b;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenRectangle = new Rectangle(screenSize);
		// screen = bot.createScreenCapture(screenRectangle);

		// Save as PNG
		// ImageIO.write(screen, "png", new File("screencapture.png"));
		/*
		 * if (topLeft == null || botRight == null) { throw new
		 * Exception("Can't find anchors"); } else {
		 * System.out.println("TL anchor: (" + topLeft.getX() + "," +
		 * topLeft.getY() + ")"); System.out.println("BR anchor: (" +
		 * botRight.getX() + "," + botRight.getY() + ")"); }
		 */
	}

	/**
	 * Constructor when only the background image is provided
	 * 
	 * @param background
	 */
	ImageFind(BufferedImage background) {

	}

	/**
	 * Searches for the given image
	 * 
	 * @param img
	 *            Image to search for
	 * @return Coordinates of the image if it is found, null if not found
	 */
	private Point findImg(BufferedImage img, BufferedImage bigImage) {
		// look for all (x,y) positions where target appears in desktop
		int x0, y0, bigW, bigH, imgW, imgH;
		int maxRow = 0;
		int maxCol = 0;

		bigW = bigImage.getWidth();
		bigH = bigImage.getHeight();
		imgW = img.getWidth();
		imgH = img.getHeight();

		for (int y = 0; y < bigH - imgH; y++) {
			xSearch: for (int x = 0; x < bigW - imgW; x++) {
				if (Math.abs(bigImage.getRGB(x, y) - img.getRGB(0, 0)) < 10) {
					// System.out.println("Found possible match at (" + x + ","
					// + y + ")");
					x0 = x;
					y0 = y;
					for (int j = 0; j < imgH; j++) {
						for (int i = 0; i < imgW; i++) {
							if (Math.abs(img.getRGB(i, j)
									- bigImage.getRGB(x + i, y + j)) > 10) {
								continue xSearch;
							}
							if (j > maxRow) {
								maxRow = j;
								if (i > maxCol)
									maxCol = i;
							}
						}
					}
					return new Point(x0, y0);
				}
			}
		}
		System.out.println("Max number of matches is " + maxRow + " rows and "
				+ maxCol + " columns");
		return null;
	}

	/**
	 * Searches entire screen for given image
	 * 
	 * @param img
	 *            Image to search for
	 * @return True if and only if image is found on the screen
	 */
	public Point findImg(BufferedImage img) {
		screen = bot.createScreenCapture(screenRectangle);
		int target = 2424318;
		int TL;

		for (int i = 400; i < 1000; i += 5) {
			// Y traverse
			for (int j = 200; j < 700; j += 5) {
				TL = -(screen.getRGB(i, j));
				// Target found
				if (TL == target) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * Searches the screen for the given anchors. Used when the user may have
	 * moved the game window.
	 */
	boolean findAnchors() {
		screen = bot.createScreenCapture(screenRectangle);
		topLeft = findImg(tl, screen);
		botRight = findImg(br, screen);

		if (topLeft == null || botRight == null) {
			return false;
		}
		return true;
	}

	// computes the root mean squared error between a rectangular window in
	// bigImg and target.
	double imageDistance(BufferedImage bigImg, int bx, int by,
			BufferedImage target) {
		double dist = 0.0;
		for (int y = 0; y < target.getHeight(); y++) {
			for (int x = 0; x < target.getWidth(); x++) {
				// assume RGB images...
				for (int colorChannel = 0; colorChannel < 3; colorChannel++) {
					dist += Math.pow(target.getRGB(x, y) - bigImg.getRGB(x, y),
							2);
				}
			}
		}
		return Math.sqrt(dist) / target.getWidth() / target.getHeight();
	}

}
