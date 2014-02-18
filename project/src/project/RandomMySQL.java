package project;
/**
 * This is to product random temperature on one day into MySQL.
 * 
 * @author <a href="mailto:Y.Li38@student.liverpool.ac.uk">Li Yiming</a>
 * @version 2.0
 * 
 */
import java.sql.*;
import java.util.Random;
import java.text.DecimalFormat;
import java.util.Calendar;

public class RandomMySQL {

	private String path;

	private String user;

	private String password;

	private String table;

	public static void main(String args[]) {
		RandomMySQL R1 = new RandomMySQL("scanner1", "root","123456",
				"scanner1");
		R1.product();
		RandomMySQL R2 = new RandomMySQL("scanner2", "root","123456",
				"scanner2");
		R2.product();
	}

	public RandomMySQL(String path, String user, String password, String table) {
		this.path = path;
		this.user = user;
		this.password = password;
		this.table = table;
	}

	private float temperature() {
		Random R = new Random();
		if (R.nextFloat() < 0.95) {
			// 5--10
			return (R.nextFloat() * 5 + 5);
		} else if (R.nextFloat() < 0.025) {
			// 0--5
			return (R.nextFloat() * 5);
		} else {
			// 10--15
			return (R.nextFloat() * 10 + 5);
		}
	}

	public void product() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
					+ path, user, password);
		} catch (SQLException el) {
			el.printStackTrace();
		}

		try {
			String query = null;
			for (int i = 0; i < 24; i++) {
				String time = null;
				for (int j = 0; j < 60; j++) {

					DecimalFormat df = new DecimalFormat("##.0");
					String temp = df.format(temperature());
					float temperature = Float.parseFloat(temp);

					Statement state = con.createStatement();
					Calendar now = Calendar.getInstance();
					time = now.get(Calendar.YEAR) + "-"
							+ (now.get(Calendar.MONTH) + 1) + "-"
							+ now.get(Calendar.DAY_OF_MONTH) + " " + i + ":"
							+ j + ":00";
					query = "INSERT INTO " + table + " VALUES('" + time + "','"
							+ temperature + "')";
					state.executeUpdate(query);
					state.close();
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
