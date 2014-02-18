package project;
/**
 * A server to control all the scanners and store all the ID and temperature into database.
 * There are three kinds of DB: Microsoft Server, MySQL and Access.
 * 
 * @author <a href="mailto:Y.Li38@student.liverpool.ac.uk">Li Yiming</a>
 * @version 2.0
 * 
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.sql.*;

public class Server {

	/* ------------------ Fields ------------------------ */
	private static ServerSocket ss;

	private final static int PORT = 10000;

	private static JFrame frame;

	private static boolean hasTurnOn;

	private static ConnectionCounter counter;

	private static Connection C;

	private static JTextArea textArea;

	private final static Integer lock = new Integer(0);

	private static JDialog dialogMicrosoftAccess;

	private static JDialog dialogMicrosoftSQLServer;
	
	private static JDialog dialogMySQL;

	private final static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	private static String path = null;

	public static String table = null;

	private static String user = null;

	private static String password = "";

	private static int choose = 0;

	private static JDialog dialogChooseScanner;

	private static JDialog dialogAboutProgram;

	public static HashMap<String, Socket> scanner = new HashMap<String, Socket>();

	public static HashMap<String, Socket> newScanner = new HashMap<String, Socket>();

	public static HashMap<String, Socket> startScanner = new HashMap<String, Socket>();

	public static JComboBox box;

	private static JDialog dialogSetTime;

	public static int time = 0;

	private static JButton buttonTurnOn;

	private static JButton buttonShutDown;

	/* ------------------ Methods ------------------------ */
	public static void main(String args[]) {
		serverGUI();
		dialogMicrosoftAccess();
		dialogMicrosoftSQLServer();
		dialogMySQL();
		dialogSetTime();
		dialogChooseScanner();
		dialogAboutProgram();

		while (true) {
			while (!hasTurnOn) {
			}

			synchronized (lock) {
				C = connection();
			}

			counter = new ConnectionCounter();

			if (C != null) {
				server();
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
		} else if(choose==3){
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException cnfe) {
				new IOExceptionGUI(frame, cnfe.getMessage());
			}
			try {
				con = DriverManager
						.getConnection(
								"jdbc:mysql://localhost:3306/"+path,
								user, password);
			} catch (SQLException el) {
				new IOExceptionGUI(frame, el.getMessage());
			}
		}else{
			
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
					Server.table = textFieldTable.getText().trim();
					Server.password = textFieldView.getText();
					choose = 1;
					dialogMicrosoftAccess.setVisible(false);
				} else {
					int f = fileChooser.showOpenDialog(frame);
					if (f == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						path = file.getPath();
					}
				}
				if (time != 0) {
					buttonTurnOn.setEnabled(true);
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
					Server.path = textFieldDatabase.getText().trim();
					Server.table = textFieldTable.getText().trim();
					Server.user = textFieldUser.getText().trim();
					Server.password = textFieldView.getText();
					choose = 2;
					dialogMicrosoftSQLServer.setVisible(false);
				}
				if (time != 0) {
					buttonTurnOn.setEnabled(true);
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

	private static void dialogMySQL(){

		// create components and set the attributes
		dialogMySQL = new JDialog(frame, "MySQL",
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
					Server.path = textFieldDatabase.getText().trim();
					Server.table = textFieldTable.getText().trim();
					Server.user = textFieldUser.getText().trim();
					Server.password = textFieldView.getText();
					choose = 3;
					dialogMySQL.setVisible(false);
				}
				if (time != 0) {
					buttonTurnOn.setEnabled(true);
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
	
	private static void dialogChooseScanner() {
		// create components and set the attributes
		dialogChooseScanner = new JDialog(frame, "Choose Scanner", true);
		final JButton add = new JButton("   Add  ");
		final JButton remove = new JButton("Remove");
		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		String[] items = { "******Scanner name******" };
		box = new JComboBox(items);
		JLabel labelView = new JLabel(
				"Please choose ones for connection from all scanners:");

		final JTextArea textArea1 = new JTextArea(
				"Scanner names added are as follows:", 5, 30);
		textArea1.setFont(new Font("Tahoma", Font.BOLD, 12));
		textArea1.setLineWrap(true);

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView3 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView1.add(labelView);
		jpaneView1.add(box);
		jpaneView1.add(add);
		jpaneView1.add(remove);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory.createTitledBorder(etched, "");
		jpaneView1.setBorder(border);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(new JScrollPane(textArea1));
		border = BorderFactory.createTitledBorder(etched, "Running scanners");
		jpaneView2.setBorder(border);

		jpaneView3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jpaneView3.add(buttonConfirmView);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView1);
		jpaneView.add(jpaneView2);
		jpaneView.add(jpaneView3);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					dialogChooseScanner.setVisible(false);
				} else if (buttonS == add) {
					if (!newScanner.containsKey((String) box.getSelectedItem())) {
						Socket temp = scanner.get((String) box
								.getSelectedItem());
						newScanner.put((String) box.getSelectedItem(), temp);
					}
				} else {
					if (newScanner.containsKey((String) box.getSelectedItem())) {
						newScanner.remove((String) box.getSelectedItem());
					}
				}
				Set<String> set = newScanner.keySet();
				int n = 0;
				textArea1.setText("Scanner names added are as follows:");
				for (String s : set) {
					textArea1.append("\n" + (++n) + "." + s);
				}
			}
		};
		add.addActionListener(buttonListenView);
		remove.addActionListener(buttonListenView);
		buttonConfirmView.addActionListener(buttonListenView);

		dialogChooseScanner.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 370) / 2;
		int y = (screenSize.height - 300) / 2;
		dialogChooseScanner.setLocation(x, y);
		// set the size
		dialogChooseScanner.setSize(370, 300);
	}

	private static void dialogSetTime() {
		// create components and set the attributes
		dialogSetTime = new JDialog(frame, "Set Time", true);

		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		String[] items = { "******Interal Time******", "1", "2", "3", "4", "5",
				"6", "10", "12", "15", "30", "60" };
		final JComboBox box1 = new JComboBox(items);
		JLabel labelView = new JLabel("Please set interal time to store:");

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView1.add(labelView);
		jpaneView1.add(box1);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory.createTitledBorder(etched, "");
		jpaneView1.setBorder(border);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpaneView2.add(buttonConfirmView);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView1);
		jpaneView.add(jpaneView2);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					Server.time = Integer.parseInt((String) box1
							.getSelectedItem());
					dialogSetTime.setVisible(false);
				}
				if (choose != 0) {
					buttonTurnOn.setEnabled(true);
				}
			}
		};
		buttonConfirmView.addActionListener(buttonListenView);

		dialogSetTime.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 370) / 2;
		int y = (screenSize.height - 150) / 2;
		dialogSetTime.setLocation(x, y);
		// set the size
		dialogSetTime.setSize(370, 150);
	}

	private static void dialogAboutProgram() {

		dialogAboutProgram = new JDialog(frame, "About Program", true);
		final JButton buttonConfirmView = new JButton("Confirm");
		buttonConfirmView.setToolTipText("Confirm and close.");
		JLabel label1 = new JLabel("Sample Application");
		JLabel label2 = new JLabel("Version: 1.0");
		JLabel label3 = new JLabel("Author: Li Yiming");
		JLabel label4 = new JLabel(
				"University: Liverpool, Artificial Intelligence");
		JLabel label5 = new JLabel("Email: Y.Li38@student.liv.ac.uk");

		// state the panels
		JPanel jpaneView1 = new JPanel();
		JPanel jpaneView2 = new JPanel();
		JPanel jpaneView3 = new JPanel();
		JPanel jpaneView4 = new JPanel();
		JPanel jpaneView5 = new JPanel();
		JPanel jpaneView6 = new JPanel();
		JPanel jpaneView = new JPanel();

		// layout and listener of dialogView

		jpaneView1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpaneView1.add(label1);

		jpaneView2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView2.add(label2);

		jpaneView3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView3.add(label3);

		jpaneView4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView4.add(label4);

		jpaneView5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpaneView5.add(label5);

		jpaneView6.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpaneView6.add(buttonConfirmView);

		jpaneView.setLayout(new BoxLayout(jpaneView, BoxLayout.Y_AXIS));
		jpaneView.add(jpaneView1);
		jpaneView.add(jpaneView2);
		jpaneView.add(jpaneView3);
		jpaneView.add(jpaneView4);
		jpaneView.add(jpaneView5);
		jpaneView.add(jpaneView6);

		ActionListener buttonListenView = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();
				if (buttonS == buttonConfirmView) {
					dialogAboutProgram.setVisible(false);
				}
			}
		};

		buttonConfirmView.addActionListener(buttonListenView);

		dialogAboutProgram.setContentPane(jpaneView);
		// set in the middle of screen
		int x = (screenSize.width - 270) / 2;
		int y = (screenSize.height - 250) / 2;
		dialogAboutProgram.setLocation(x, y);
		// set the size
		dialogAboutProgram.setSize(270, 250);
	}

	private static void serverGUI() {
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();

		frame = new JFrame("Server Management");

		buttonTurnOn = new JButton("Turn on");
		buttonTurnOn.setToolTipText("Turn on the server.");
		buttonTurnOn.setEnabled(false);
		buttonTurnOn.setPreferredSize(new Dimension(95, 35));

		buttonShutDown = new JButton("Shut down");
		buttonShutDown.setToolTipText("Shut down the server.");
		buttonShutDown.setEnabled(false);
		buttonShutDown.setPreferredSize(new Dimension(95, 35));

		JLabel labelWelcome = new JLabel("Welcome to server management!");
		labelWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));
		JLabel labelState = new JLabel("State of server:");

		textArea = new JTextArea("The state of server is close.", 5, 30);
		textArea.setFont(new Font("Tahoma", Font.BOLD, 12));
		textArea.setLineWrap(true);

		JPanel jpane1 = new JPanel();
		JPanel jpane2 = new JPanel();
		JPanel jpane3 = new JPanel();
		JPanel jpane4 = new JPanel();
		JPanel jpane = new JPanel();

		jpane1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpane1.add(labelWelcome);

		jpane2.setLayout(new GridLayout(2, 1));
		jpane2.add(buttonTurnOn);
		jpane2.add(buttonShutDown);

		jpane3.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpane3.add(new JScrollPane(textArea));
		jpane3.add(jpane2);

		jpane4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpane4.add(labelState);
		jpane4.add(jpane3);
		Border etched = BorderFactory.createEtchedBorder();
		Border border = BorderFactory.createTitledBorder(etched,
				"Server monitor");
		jpane4.setBorder(border);

		jpane.setLayout(new BoxLayout(jpane, BoxLayout.Y_AXIS));
		jpane.add(jpane1);
		jpane.add(jpane4);

		// set menus
		JMenuBar mBar = new JMenuBar();
		frame.setJMenuBar(mBar);

		JMenu chooseMenu = new JMenu("Choose");
		mBar.add(chooseMenu);
		JMenu chooseDatabase = new JMenu("Choose Database");
		chooseMenu.add(chooseDatabase);
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
		final JMenuItem chooseScanner = new JMenuItem("Choose Scanner");
		chooseMenu.add(chooseScanner);
		chooseMenu.addSeparator();
		final JMenuItem exit = new JMenuItem("Exit");
		chooseMenu.add(exit);

		JMenu setMenu = new JMenu("Set");
		mBar.add(setMenu);
		final JMenuItem setTime = new JMenuItem("Set Time");
		setMenu.add(setTime);
		JMenuItem setContents = new JMenuItem("Set Contents");
		setMenu.add(setContents);

		JMenu controlMenu = new JMenu("Control");
		mBar.add(controlMenu);
		final JMenuItem turnOn = new JMenuItem("Turn On");
		controlMenu.add(turnOn);
		final JMenuItem shutDown = new JMenuItem("Shut Down");
		controlMenu.add(shutDown);

		final JMenu helpMenu = new JMenu("Help");
		mBar.add(helpMenu);
		final JMenuItem helpContents = new JMenuItem("Help Contents");
		helpMenu.add(helpContents);
		final JMenuItem aboutProgram = new JMenuItem("About Program");
		helpMenu.add(aboutProgram);

		ActionListener menuListen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JMenuItem m = (JMenuItem) e.getSource();

				if (m == microsoftAccess) {
					dialogMicrosoftAccess.setVisible(true);
				} else if (m == microsoftSQLServer) {
					dialogMicrosoftSQLServer.setVisible(true);
				}else if(m==mySQL){
					dialogMySQL.setVisible(true);
				} else if (m == chooseScanner) {
					dialogChooseScanner.setVisible(true);
				} else if (m == exit) {
					System.exit(0);
				} else if (m == setTime) {
					dialogSetTime.setVisible(true);
				} else if (m == turnOn) {
					buttonTurnOn.setEnabled(false);
					buttonShutDown.setEnabled(true);

					hasTurnOn = true;
				} else if (m == shutDown) {
					buttonTurnOn.setEnabled(true);
					buttonShutDown.setEnabled(false);
					hasTurnOn = false;

					if (ss != null) {
						try {
							ss.close();
						} catch (IOException ioe) {
							new IOExceptionGUI(frame, ioe.getMessage());
						}
					}
				} else if (m == helpContents) {
					try {
						Desktop.getDesktop().open(new File("src/help/help.pdf"));
					} catch (IOException ioe) {
						new IOExceptionGUI(frame, ioe.getMessage());
					}
				} else if (m == aboutProgram) {
					dialogAboutProgram.setVisible(true);
				} else {

				}
			}
		};
		microsoftAccess.addActionListener(menuListen);
		microsoftSQLServer.addActionListener(menuListen);
		mySQL.addActionListener(menuListen);
		chooseScanner.addActionListener(menuListen);
		exit.addActionListener(menuListen);
		setTime.addActionListener(menuListen);
		turnOn.addActionListener(menuListen);
		shutDown.addActionListener(menuListen);
		helpContents.addActionListener(menuListen);
		aboutProgram.addActionListener(menuListen);

		ActionListener buttonListen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton buttonS = (JButton) e.getSource();

				if (buttonS == buttonTurnOn) {
					buttonTurnOn.setEnabled(false);
					buttonShutDown.setEnabled(true);

					hasTurnOn = true;

				} else {
					buttonTurnOn.setEnabled(true);
					buttonShutDown.setEnabled(false);
					hasTurnOn = false;

					if (ss != null) {
						try {
							ss.close();
						} catch (IOException ioe) {
							new IOExceptionGUI(frame, ioe.getMessage());
						}
					}
				}
			}
		};

		buttonTurnOn.addActionListener(buttonListen);
		buttonShutDown.addActionListener(buttonListen);

		frame.setContentPane(jpane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int x = (screenSize.width - 480) / 2;
		int y = (screenSize.height - 300) / 2;
		frame.setLocation(x, y);
		frame.setSize(480, 300);
		frame.setVisible(true);
	}

	private static void server() {
		Socket incoming = null;

		try {
			ss = new ServerSocket(PORT);

			textArea.setText("The state of server is open.\nCurrent scanners: "
					+ counter.getCurrentConnections() + "\nAll the scanners: "
					+ counter.getConnections());

			while (hasTurnOn) {
				try {
					ss.setSoTimeout(10000);
					incoming = ss.accept();
				} catch (Exception e) {
					incoming = null;
				}
				if (incoming != null) {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(incoming.getInputStream()));
					String n = in.readLine();
					if (scanner.containsKey(n)) {
						scanner.remove(n);
						box.removeItem(n);
					}
					scanner.put(n, incoming);
					box.addItem(n);
				}
				for (String s : newScanner.keySet()) {
					if (!startScanner.containsKey(s)) {
						startScanner.put(s, newScanner.get(s));
						Thread t = new Thread(new ServerHandler(
								newScanner.get(s), C, counter, s));
						t.start();
					}
				}
			}

		} catch (IOException ioe) {
			if (hasTurnOn) {
				new IOExceptionGUI(frame, ioe.getMessage());
			}
		} finally {
			if (ss != null) {
				try {
					ss.close();
					hasTurnOn = false;
				} catch (IOException ioe) {
					new IOExceptionGUI(frame, ioe.getMessage());
				}
			}

			if (incoming != null) {
				try {
					incoming.close();
				} catch (IOException ioe) {
					new IOExceptionGUI(frame, ioe.getMessage());
				}
			}

			if (C != null) {
				try {
					C.close();
				} catch (SQLException e) {
					new IOExceptionGUI(frame, e.getMessage());
				}
			}

			textArea.setText("The state of server is close.");
		}

	}

	public static JFrame getFrame() {
		return frame;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}
}
