package project;
/**
 * A dialog will display when there is something wrong.
 * i.e. Throw new exception, then show this dialog with errors.
 * 
 * @author <a href="mailto:Y.Li38@student.liverpool.ac.uk">Li Yiming</a>
 * @version 2.0
 * 
 */
// import Java Library
import java.awt.*;
import javax.swing.*;

public class IOExceptionGUI {

	/* ------------------ Fields ------------------------ */

	/**
	 * Get the screen size for own computer
	 */
	private final static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	/**
	 * Store the frame of client or server, which creates it
	 */
	private final JFrame frame;

	/**
	 * Store the error information
	 */
	private final String s;

	/* ------------------ Constructor ------------------------ */

	/**
	 * Constructs a new <code>IOException<\code> instance.
	 * 
	 * @param frame
	 * @param s
	 */
	public IOExceptionGUI(JFrame frame, String s) {
		this.frame = frame;
		this.s = s;
		// display the dialog
		exceptionGUI();
	}

	/* ------------------ Methods ------------------------ */

	/**
	 * Display the dialog with error information
	 */
	private void exceptionGUI() {
		// create components and set the attributes with them
		JDialog dialog = new JDialog(frame, "Error", true);
		JLabel label = new JLabel("Error: " + s);
		label.setForeground(Color.red);
		JPanel jpane1 = new JPanel();
		JPanel jpane = new JPanel();

		// set the layout the jpanel
		jpane1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpane1.add(label);

		jpane.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpane.add(jpane1);

		// add the jpanel into the dialog
		dialog.setContentPane(jpane);
		// make it display in the middle of screen
		dialog.setLocation((screenSize.width - 380) / 2,
				(screenSize.height - 90) / 2);
		// set the size
		dialog.setSize(380, 90);
		dialog.setVisible(true);
	}
}
