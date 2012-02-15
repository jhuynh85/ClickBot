package clickbot;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Font;

/**
 * Main screen
 * 
 * @author Joseph Huynh
 * 
 */
public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel statLbl;
	private JButton btnStart;
	private Thread t1, t2;

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public MainWindow() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws URISyntaxException
	 */
	private void initialize() throws URISyntaxException {
		final URI uri = new URI(
				"http://www.andkon.com/arcade/mousegames/clickfast/");

		setTitle("ClickBot by Joseph Huynh");
		setBounds(100, 100, 290, 145);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		statLbl = new JLabel("Status: Ready");
		statLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statLbl.setBounds(10, 11, 254, 14);
		getContentPane().add(statLbl);

		btnStart = new JButton("Start");
		btnStart.setEnabled(false);
		btnStart.addMouseListener(new StartListener());
		btnStart.setBounds(44, 36, 89, 23);
		getContentPane().add(btnStart);

		JButton btnCalibrate = new JButton("Calibrate");
		btnCalibrate.addMouseListener(new CalibrateListener());
		btnCalibrate.setBounds(143, 36, 89, 23);
		getContentPane().add(btnCalibrate);

		JButton b1 = new JButton("LINK TO GAME");
		b1.setFont(new Font("Verdana", Font.BOLD, 12));
		b1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				open(uri);
			}
		});
		b1.setBorderPainted(false);
		b1.setBackground(Color.WHITE);
		b1.setForeground(Color.BLUE);
		b1.setOpaque(false);
		b1.setToolTipText(uri.toString());
		b1.setBounds(67, 70, 140, 25);
		getContentPane().add(b1);
		setVisible(true);
	}

	/**
	 * Event handler for 'Start' button
	 */
	private class StartListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (btnStart.isEnabled()) {
				if (btnStart.getText().equals("Start")) {
					btnStart.setText("Stop");
					try {
						t1 = makeStart();
						if (t1 != null)
							t1.start();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (btnStart.getText().equals("Stop")) {
					btnStart.setText("Start");
					ClickBot.stop();
				} else
					try {
						throw new Exception("Shouldn't be here!");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}
	}

	/**
	 * Event handler for 'Calibrate'
	 * 
	 */
	private class CalibrateListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			t2 = makeCal();
			if (t2 != null)
				t2.start();
			btnStart.setEnabled(true);
		}
	}

	/**
	 * Method for updating the status text
	 * 
	 * @param str
	 *            Text to display
	 */
	public void status(String str) {
		statLbl.setText("Status: " + str);
	}

	/**
	 * Generates a new thread. Called whenever 'Start' button is pressed.
	 * 
	 * @return
	 */
	private Thread makeStart() {
		Thread t = null;
		try {
			t = new Thread(new Runnable() {
				public void run() {
					try {
						ClickBot.run();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return t;
	}

	/**
	 * Generates a new thread. Called whenever 'Calibrate' button is pressed.
	 * 
	 * @return
	 */
	private Thread makeCal() {
		Thread t = null;
		try {
			t = new Thread(new Runnable() {
				public void run() {
					try {
						ClickBot.calibrate();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return t;
	}

	/**
	 * Opens URL
	 * 
	 * @param uri
	 */
	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(uri);
			} catch (IOException e) {
				// TODO: error handling
			}
		} else {
			// TODO: error handling
		}
	}
}
