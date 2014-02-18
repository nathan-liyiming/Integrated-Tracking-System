package project;
/**
 * This is to handle the server and store the information into database use SQL.
 * 
 * @author <a href="mailto:Y.Li38@student.liverpool.ac.uk">Li Yiming</a>
 * @version 2.0
 * 
 */
import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ServerHandler implements Runnable {
	private final Socket client;

	private final Connection connection;

	private final ConnectionCounter counter;

	private final String name;

	public ServerHandler(Socket s, Connection C, ConnectionCounter c, String n) {
		client = s;
		connection = C;
		counter = c;
		name = n;
	}

	public void run() {
		counter.addConnection();
		Server.getTextArea().setText(display());

		BufferedReader in = null;
		PrintWriter out = null;

		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream()));
			boolean doing = true;
			out.println("Start");
			out.flush();
			while (doing) {
				if (!Server.newScanner.containsKey(name)) {
					out.println("Stop");
					out.flush();
					break;
				}
				String line = in.readLine();
				if (line == null) {
					doing = false;
				} else {
					if (line.equals("End")) {
						doing = false;
					} else {
						String list[]=line.split("and");
						loadIn(list[0],list[1]);
					}
				}

				Server.getTextArea().setText(display());
			}

		} catch (IOException ioe) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					new IOExceptionGUI(Server.getFrame(), ioe.getMessage());
				}
			}
			if (out != null) {
				out.close();
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException ioe) {
					new IOExceptionGUI(Server.getFrame(), ioe.getMessage());
				}
			}
			counter.endConnection();
			Server.getTextArea().setText(display());
			try {
				connection.close();
			} catch (SQLException e) {
				new IOExceptionGUI(Server.getFrame(), e.getMessage());
			}

			if (Server.newScanner.containsKey(name)) {
				Server.scanner.remove(name);
				Server.newScanner.remove(name);
				Server.startScanner.remove(name);
				Server.box.removeItem(name);
			} else {
				Server.startScanner.remove(name);
			}
		}
	}

	private void loadIn(String ID,String T) {
		try {
			Statement state = connection.createStatement();
			Calendar now = Calendar.getInstance();
			String time = now.get(Calendar.YEAR)
					+ "-"
					+ (now.get(Calendar.MONTH) + 1)
					+ "-"
					+ now.get(Calendar.DAY_OF_MONTH)
					+ " "
					+ now.get(Calendar.HOUR_OF_DAY)
					+ ":"
					+ (int) (Math.round(now.get(Calendar.MINUTE)
							/ (double) Server.time)) * Server.time + ":00";
			DecimalFormat df = new DecimalFormat("##.0");
			String temp = df.format(Double.parseDouble(T));
			float temperature = Float.parseFloat(temp);
			String query = "INSERT INTO " + Server.table + " VALUES('" + ID
					+ "','" + time + "','" + name + "','" + temperature + "')";
			state.executeUpdate(query);
			state.close();
		} catch (SQLException e) {
			new IOExceptionGUI(Server.getFrame(), e.getMessage());
		}
	}

	private String display() {
		return "The state of server is open.\nCurrent scanners: "
				+ counter.getCurrentConnections() + "\nAll the scanners: "
				+ counter.getConnections();
	}
}
