package project;
/**
 * Use for scanner to connect with server, then controlled by server.
 * It will get the information of ID, time and temperature.
 * 
 * @author <a href="mailto:Y.Li38@student.liverpool.ac.uk">Li Yiming</a>
 * @version 2.0
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;

import java.sql.*;
import java.util.Calendar;

public class Client {
	
	/* ------------------ Fields ------------------------ */
	private static Socket s;

	private static BufferedReader in;

	private static PrintWriter out;

	private static JFrame frame;

	private static boolean hasStart;

	private static JDialog dialogName;

	private static JDialog dialogConnection;

	private static String name = null;

	private static String IP = null;

	private static Connection C = null;

	private static JDialog dialogMicrosoftAccess;

	private static JDialog dialogMicrosoftSQLServer;

	private static JDialog dialogMySQL;

	private static String path = null;

	public static String table = null;

	private static String user = null;

	private static String password = "";

	private static int choose = 0;

	private static JButton buttonStart;

	private final static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	/* ------------------ Methods ------------------------ */
	
	public static void main(String args[]) {
		GUI();
		dialogName();
		dialogMicrosoftAccess();
		dialogMicrosoftSQLServer();
		dialogMySQL();
		dialogConnection();
		while (true) {
			while (!hasStart) {
				try {
					if (in != null) {
						if (in.ready()) {
							if (in.readLine().equals("Start")) {
								hasStart = true;
							}
						}
					}
				} catch (IOException ioe) {
					// new IOExceptionGUI(frame, ioe.getMessage());
				}
			}
			readAndSend();

			try {
				if (in.ready()) {
					if (in.readLine().equals("Stop")) {
						hasStart = false;
					}
				}
			} catch (IOException ioe) {
				// new IOExceptionGUI(frame, ioe.getMessage());
			}
		}
	}

	public static Connection connection() {
		Connection con = null;
		if (choose == 1) {
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			} catch (ClassNotFoundException cnfe) {
				new IOExceptionGUI(frame, cnfe.getMessage());
			}
			try {
				con = DriverManager
						.getConnection("jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="
								+ path + ";pwd=" + password);
			} catch (SQLException el) {
				new IOExceptionGUI(frame, el.getMessage());
			}
		} else if (choose == 2) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException cnfe) {
				new IOExceptionGUI(frame, cnfe.getMessage());
			}

			try {
				con = DriverManager.getConnection(
						"jdbc:sqlserver://localhost:1433;DatabaseName=" + path,
						user, password);
			} catch (SQLException el) {
				System.out.println(el.getLocalizedMessage());
			}
		} else if (choose == 3) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException cnfe) {
				new IOExceptionGUI(frame, cnfe.getMessage());
			}
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/" + path, user, password);
			} catch (SQLException el) {
				new IOExceptionGUI(frame, el.getMessage());
			}
		} else {

		}

		return con;
	}

	public static void dialogMicrosoftAccess() {

		// create components and set the attributes
		dialogMicrosoftAccess = new JDialog(frame, "Microsoft Access", true);
		final JButton buttonConfirmView = new JButton("Confirm");
		final JButton buttonChoose = new JButton("Choose");
		buttonConfirmView.setToolTipText("Confirm and close.");
		final JPasswordField textFieldView = new JPasswordField(30);
		final JTextField textFieldTable = new JTextField(30);
		textFieldView.setEchoChar('*');
		JLabel labelChoose = new JLabel(
				"Please choose the path name for Access:");
		JLabel labelTable = new JLabel(
				"Please input the table name for Access:");
		JLabel labelView = new JLabel(
				"Please input the password(if no password, only ignore it):");
		final JFileChooser fileChooser = new JFileChooser();

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView3 = new JPanel();
		JPanel jpaneView4 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView1.add(labelChoose);
		jpaneView1.add(buttonChoose);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory.createTitledBorder(etched, "");
		jpaneView1.setBorder(border);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(labelTable);
		jpaneView2.add(textFieldTable);
		jpaneView2.setBorder(border);

		jpaneView3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView3.add(labelView);
		jpaneView3.add(textFieldView);
		jpaneView3.setBorder(border);

		jpaneView4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jpaneView4.add(buttonConfirmView);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView1);
		jpaneView.add(jpaneView2);
		jpaneView.add(jpaneView3);
		jpaneView.add(jpaneView4);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					Client.table = textFieldTable.getText().trim();
					Client.password = textFieldView.getText();
					choose = 1;
					dialogMicrosoftAccess.setVisible(false);
				} else {
					int f = fileChooser.showOpenDialog(frame);
					if (f == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						path = file.getPath();
					}
				}
			}
		};

		buttonChoose.addActionListener(buttonListenView);
		buttonConfirmView.addActionListener(buttonListenView);

		dialogMicrosoftAccess.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 360) / 2;
		int y = (screenSize.height - 280) / 2;
		dialogMicrosoftAccess.setLocation(x, y);
		// set the size
		dialogMicrosoftAccess.setSize(360, 280);
	}

	public static void dialogMicrosoftSQLServer() {

		// create components and set the attributes
		dialogMicrosoftSQLServer = new JDialog(frame, "Microsoft SQL Server",
				true);
		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		final JTextField textFieldDatabase = new JTextField(30);
		final JTextField textFieldTable = new JTextField(30);
		final JTextField textFieldUser = new JTextField(30);
		JLabel labelDatabase = new JLabel("Please input the database name:");
		JLabel labelTable = new JLabel("Please input the table name:");
		JLabel labelUser = new JLabel("Please input the user name:");
		JLabel labelPassword = new JLabel("Please input the password:");
		final JPasswordField textFieldView = new JPasswordField(30);
		textFieldView.setEchoChar('*');

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView3 = new JPanel();
		JPanel jpaneView4 = new JPanel();
		JPanel jpaneView5 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView1.add(labelDatabase);
		jpaneView1.add(textFieldDatabase);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory.createTitledBorder(etched, "");
		jpaneView1.setBorder(border);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(labelTable);
		jpaneView2.add(textFieldTable);
		jpaneView2.setBorder(border);

		jpaneView3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView3.add(labelUser);
		jpaneView3.add(textFieldUser);
		jpaneView3.setBorder(border);

		jpaneView4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView4.add(labelPassword);
		jpaneView4.add(textFieldView);
		jpaneView4.setBorder(border);

		jpaneView5.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jpaneView5.add(buttonConfirmView);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView1);
		jpaneView.add(jpaneView2);
		jpaneView.add(jpaneView3);
		jpaneView.add(jpaneView4);
		jpaneView.add(jpaneView5);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					Client.path = textFieldDatabase.getText().trim();
					Client.table = textFieldTable.getText().trim();
					Client.user = textFieldUser.getText().trim();
					Client.password = textFieldView.getText();
					choose = 2;
					dialogMicrosoftSQLServer.setVisible(false);
				}
			}
		};

		buttonConfirmView.addActionListener(buttonListenView);

		dialogMicrosoftSQLServer.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 360) / 2;
		int y = (screenSize.height - 330) / 2;
		dialogMicrosoftSQLServer.setLocation(x, y);
		// set the size
		dialogMicrosoftSQLServer.setSize(360, 330);
	}

	private static void dialogMySQL() {

		// create components and set the attributes
		dialogMySQL = new JDialog(frame, "MySQL", true);
		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		final JTextField textFieldDatabase = new JTextField(30);
		final JTextField textFieldTable = new JTextField(30);
		final JTextField textFieldUser = new JTextField(30);
		JLabel labelDatabase = new JLabel("Please input the database name:");
		JLabel labelTable = new JLabel("Please input the table name:");
		JLabel labelUser = new JLabel("Please input the user name:");
		JLabel labelPassword = new JLabel("Please input the password:");
		final JPasswordField textFieldView = new JPasswordField(30);
		textFieldView.setEchoChar('*');

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView3 = new JPanel();
		JPanel jpaneView4 = new JPanel();
		JPanel jpaneView5 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView1.add(labelDatabase);
		jpaneView1.add(textFieldDatabase);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory.createTitledBorder(etched, "");
		jpaneView1.setBorder(border);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(labelTable);
		jpaneView2.add(textFieldTable);
		jpaneView2.setBorder(border);

		jpaneView3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView3.add(labelUser);
		jpaneView3.add(textFieldUser);
		jpaneView3.setBorder(border);

		jpaneView4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView4.add(labelPassword);
		jpaneView4.add(textFieldView);
		jpaneView4.setBorder(border);

		jpaneView5.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jpaneView5.add(buttonConfirmView);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView1);
		jpaneView.add(jpaneView2);
		jpaneView.add(jpaneView3);
		jpaneView.add(jpaneView4);
		jpaneView.add(jpaneView5);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					Client.path = textFieldDatabase.getText().trim();
					Client.table = textFieldTable.getText().trim();
					Client.user = textFieldUser.getText().trim();
					Client.password = textFieldView.getText();
					choose = 3;
					dialogMySQL.setVisible(false);
				}
			}
		};

		buttonConfirmView.addActionListener(buttonListenView);

		dialogMySQL.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 360) / 2;
		int y = (screenSize.height - 330) / 2;
		dialogMySQL.setLocation(x, y);
		// set the size
		dialogMySQL.setSize(360, 330);
	}

	public static void GUI() {
		frame = new JFrame("Control");

		buttonStart = new JButton("Connect");
		buttonStart.setToolTipText("Connect to the computer");
		buttonStart.setPreferredSize(new Dimension(120, 36));
		buttonStart.setEnabled(false);

		final JButton buttonStop = new JButton("Disconnect");
		buttonStop.setToolTipText("Disconnect to the computer");
		buttonStop.setPreferredSize(new Dimension(120, 36));
		buttonStop.setEnabled(false);

		JLabel label = new JLabel("Scan and read data");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));

		JPanel jpane1 = new JPanel();
		JPanel jpane2 = new JPanel();
		JPanel jpane = new JPanel();

		jpane1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpane1.add(label);

		jpane2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpane2.add(buttonStart);
		jpane2.add(buttonStop);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory
				.createTitledBorder(etched, "Click button");
		jpane2.setBorder(border);

		jpane.setLayout(new BoxLayout(jpane, BoxLayout.Y_AXIS));
		jpane.add(jpane1);
		jpane.add(jpane2);

		frame.setContentPane(jpane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ------------------------------------------------------------------
		JMenuBar mBar = new JMenuBar();
		frame.setJMenuBar(mBar);

		JMenu setMenu = new JMenu("Set");
		mBar.add(setMenu);

		final JMenuItem setName = new JMenuItem("Set Name");
		setMenu.add(setName);

		final JMenuItem setConnection = new JMenuItem("Set Connection");
		setMenu.add(setConnection);

		JMenu chooseDatabase = new JMenu("Temperature Database");
		setMenu.add(chooseDatabase);

		setMenu.addSeparator();
		JMenuItem exit = new JMenuItem("Exit");
		setMenu.add(exit);
		// subSet------------------------------------------------------------
		final JMenuItem microsoftAccess = new JMenuItem("Microsoft Access");
		chooseDatabase.add(microsoftAccess);
		final JMenuItem microsoftSQLServer = new JMenuItem(
				"Microsoft SQL Server");
		chooseDatabase.add(microsoftSQLServer);
		final JMenuItem mySQL = new JMenuItem("MySQL");
		chooseDatabase.add(mySQL);
		JMenuItem oracle = new JMenuItem("Oracle");
		chooseDatabase.add(oracle);
		chooseDatabase.addSeparator();
		JMenuItem others = new JMenuItem("Others");
		chooseDatabase.add(others);
		// ------------------------------------------------------------------
		ActionListener menuListen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JMenuItem m = (JMenuItem) e.getSource();

				if (m == setName) {
					dialogName.setVisible(true);
				} else if (m == setConnection) {
					dialogConnection.setVisible(true);
				} else if (m == microsoftAccess) {
					dialogMicrosoftAccess.setVisible(true);
				} else if (m == microsoftSQLServer) {
					dialogMicrosoftSQLServer.setVisible(true);
				} else if (m == mySQL) {
					dialogMySQL.setVisible(true);
				} else {
					if (s != null) {
						out.println("End");
						out.flush();
						try {
							s.close();
						} catch (IOException ioe) {
							new IOExceptionGUI(frame, ioe.getMessage());
						}
					}

					if (in != null) {
						try {
							in.close();
						} catch (IOException ioe) {
							// new IOExceptionGUI(frame, ioe.getMessage());
						}
					}

					if (out != null) {
						out.close();
					}
					System.exit(0);
				}
			}
		};
		setName.addActionListener(menuListen);
		setConnection.addActionListener(menuListen);
		exit.addActionListener(menuListen);
		microsoftAccess.addActionListener(menuListen);
		microsoftSQLServer.addActionListener(menuListen);
		mySQL.addActionListener(menuListen);

		ActionListener buttonListen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();

				if (buttonS == buttonStart) {
					try {
						s = new Socket(IP, 10000);

						try {
							in = new BufferedReader(new InputStreamReader(
									s.getInputStream()));
							out = new PrintWriter(new OutputStreamWriter(
									s.getOutputStream()));
						} catch (IOException ioe) {
							new IOExceptionGUI(frame, ioe.getMessage());
						}

						buttonStop.setEnabled(true);
						buttonStart.setEnabled(false);

						out.println(name);
						out.flush();

					} catch (IOException ioe) {
						new IOExceptionGUI(frame, ioe.getMessage());
					}
				} else {
					hasStart = false;
					buttonStart.setEnabled(true);
					buttonStop.setEnabled(false);
					if (s != null) {
						out.println("End");
						out.flush();
						try {
							s.close();
						} catch (IOException ioe) {
							new IOExceptionGUI(frame, ioe.getMessage());
						}
					}

					if (in != null) {
						try {
							in.close();
						} catch (IOException ioe) {
							// new IOExceptionGUI(frame, ioe.getMessage());
						}
					}

					if (out != null) {
						out.close();
					}
				}
			}
		};
		buttonStart.addActionListener(buttonListen);
		buttonStop.addActionListener(buttonListen);

		int x = (screenSize.width - 350) / 2;
		int y = (screenSize.height - 200) / 2;
		frame.setLocation(x, y);
		// set the size of frame
		frame.setSize(350, 200);
		frame.setVisible(true);
	}

	public static void dialogName() {
		// create components and set the attributes
		dialogName = new JDialog(frame, "Set Name", true);
		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		final JTextField textFieldView = new JTextField(30);
		JLabel labelView = new JLabel("Please input the name for this scanner:");

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpaneView1.add(textFieldView);
		jpaneView1.add(buttonConfirmView);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(labelView);
		jpaneView2.add(jpaneView1);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView2);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					Client.name = textFieldView.getText().trim();
					dialogName.setVisible(false);
					if (Client.IP != null) {
						buttonStart.setEnabled(true);
					}
				}
			}
		};

		buttonConfirmView.addActionListener(buttonListenView);

		dialogName.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 457) / 2;
		int y = (screenSize.height - 120) / 2;
		dialogName.setLocation(x, y);
		// set the size
		dialogName.setSize(457, 120);
	}

	public static void dialogConnection() {
		// create components and set the attributes
		dialogConnection = new JDialog(frame, "Set Connection", true);
		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		final JTextField textFieldView = new JTextField(30);
		JLabel labelView = new JLabel(
				"Please input IP for connection(eg:127.0.0.1):");

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpaneView1.add(textFieldView);
		jpaneView1.add(buttonConfirmView);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(labelView);
		jpaneView2.add(jpaneView1);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView2);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					Client.IP = textFieldView.getText().trim();
					dialogConnection.setVisible(false);
					if (Client.name != null) {
						buttonStart.setEnabled(true);
					}
				}
			}
		};

		buttonConfirmView.addActionListener(buttonListenView);

		dialogConnection.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 457) / 2;
		int y = (screenSize.height - 120) / 2;
		dialogConnection.setLocation(x, y);
		// set the size
		dialogConnection.setSize(457, 120);
	}

	public static void readAndSend() {
		File f = new File(".\\");

		String[] name = f.list();
		File fl = null;

		for (int i = 0; i < name.length; i++) {
			if (name[i].length() <= 4) {
				// ignore
			} else if (name[i]
					.substring(name[i].length() - 4, name[i].length()).equals(
							".txt")) {
				fl = new File(name[i]);
				send(fl);
			}
		}
	}

	public static void send(File f) {
		C = connection();
		String result = null;
		Statement state = null;
		ResultSet rs = null;
		Calendar now = Calendar.getInstance();
		String time = now.get(Calendar.YEAR) + "-"
				+ (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DAY_OF_MONTH) + " "
				+ now.get(Calendar.HOUR_OF_DAY) + ":"
				+ now.get(Calendar.MINUTE) + ":00";
		try {
			state = C.createStatement();
			String query = "SELECT temperature FROM " + Client.table
					+ " WHERE time='" + time + "'";
			rs = state.executeQuery(query);
			while (rs.next()) {
				result = rs.getString("temperature");
			}
			state.close();
		} catch (SQLException e) {
			new IOExceptionGUI(frame, e.getMessage());
		}

		if (C != null) {
			try {
				C.close();
			} catch (SQLException e) {
				new IOExceptionGUI(frame, e.getMessage());
			}
		}

		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
		} catch (FileNotFoundException fnfe) {
			new IOExceptionGUI(frame, fnfe.getMessage());
		}

		String line;
		try {
			while ((line = inFile.readLine()) != null) {
				String list[] = line.split(" ");
				out.println(list[1].trim() + "and" + result);
				out.flush();
			}
		} catch (IOException ioe) {

			new IOExceptionGUI(frame, ioe.getMessage());
		} finally {
			try {
				inFile.close();
				f.delete();
			} catch (IOException ioe) {
				new IOExceptionGUI(frame, ioe.getMessage());
			}
		}
	}
}
