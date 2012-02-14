package clickbot;

import java.awt.EventQueue;
import java.awt.Robot;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingConstants;

/**
 * Main screen
 * 
 * @author Joseph Huynh
 * 
 */
public class MainWindow {

	private JFrame frmClickbotByJoseph;
	private BufferedImage im;
	private BufferedImage tl;
	private BufferedImage br;
	private ImageFind imgF;
	private JLabel statLbl;
	private JButton btnStart;
	private Robot bot;
	private boolean running = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmClickbotByJoseph.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public MainWindow() throws Exception {
		initialize();
		load();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmClickbotByJoseph = new JFrame();
		frmClickbotByJoseph.setTitle("ClickBot by Joseph Huynh");
		frmClickbotByJoseph.setBounds(100, 100, 290, 110);
		frmClickbotByJoseph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClickbotByJoseph.getContentPane().setLayout(null);

		statLbl = new JLabel("Status: Ready");
		statLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statLbl.setBounds(10, 11, 254, 14);
		frmClickbotByJoseph.getContentPane().add(statLbl);

		btnStart = new JButton("Start");
		btnStart.addMouseListener(new StartListener());
		btnStart.setBounds(93, 38, 89, 23);
		frmClickbotByJoseph.getContentPane().add(btnStart);
	}

	/**
	 * Loads image files
	 * @throws Exception 
	 */
	private void load() throws Exception {
		try {
			im = ImageIO.read(new File("src/clickfast.png"));
			tl = ImageIO.read(new File("src/tl.png"));
			br = ImageIO.read(new File("src/br.png"));
			System.out.println("Files loaded successfully");
			System.out.println("tl: "+tl.getWidth()+"x"+tl.getHeight());
			System.out.println("br: "+br.getWidth()+"x"+br.getHeight());
			imgF = new ImageFind(tl, br);
		} catch (IOException e) {
			e.printStackTrace();
			status("Unable to load files");
		}
	}

	/**
	 * Starts bot
	 */
	private void start() {
		running = true;
		btnStart.setText("Stop");
	}

	/**
	 * Stops bot
	 */
	private void stop() {
		running = false;
		btnStart.setText("Start");
	}

	/**
	 * Event handler for 'Start' button
	 */
	private class StartListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (!running)
				start();
			else
				stop();
		}
	}

	/**
	 * Method for updating the status text
	 * 
	 * @param str
	 *            Text to display
	 */
	private void status(String str) {
		statLbl.setText("Status: " + str);
	}

}
