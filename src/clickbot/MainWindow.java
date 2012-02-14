package clickbot;

import java.awt.EventQueue;
import javax.imageio.ImageIO;
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
 * @author Joseph Huynh
 *
 */
public class MainWindow {

	private JFrame frmClickbotByJoseph;
	private BufferedImage im;
	private JLabel statLbl;

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
	 */
	public MainWindow() {
		initialize();
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
		
		statLbl = new JLabel("Status: No image loaded");
		statLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statLbl.setBounds(10, 11, 254, 14);
		frmClickbotByJoseph.getContentPane().add(statLbl);
		
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new StartListener());
		btnStart.setBounds(93, 38, 89, 23);
		frmClickbotByJoseph.getContentPane().add(btnStart);
	}
	
	/**
	 * Starts bot
	 */
	private void start(){
		
	}
	
	/**
	 * Event handler for 'Start' button
	 */
	private class StartListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
			start();
		}
	}

}
