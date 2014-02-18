package connectionWeb;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MySQLConnection;

/**
 * <p>
 * This Class handles the request for searching.
 * </p>
 * 
 * @author Yiming Li
 * @version 1.0
 * 
 */
public class Parameter extends HttpServlet {

	private static final long serialVersionUID = -6653066473904929268L;

	/**
	 * The doPost method of the servlet.
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get library name
		String para = request.getParameter("paraInput").trim();

		try {
			float p = Float.parseFloat(para);
			float real_p = (1 / (1 + 1 / p));
			Connection con = MySQLConnection.connection();

			try {
				Statement state = con.createStatement();
				String query = "SELECT * FROM package_time;";
				ResultSet rs = state.executeQuery(query);

				while (rs.next()) {
					String remaining_time = (String) rs
							.getString("remaining_time");
					String package_ID = (String) rs.getString("package_ID");
					remaining_time = (int) (real_p * Integer
							.parseInt(remaining_time)) + "";

					try {
						Statement state1 = con.createStatement();
						String query1 = "UPDATE package_time SET remaining_time='"
								+ remaining_time
								+ "' WHERE package_ID='"
								+ package_ID + "';";

						state1.executeUpdate(query1);
						state1.close();

					} catch (Exception e) {
					}

				}
				state.close();
				con.close();
			} catch (SQLException e) {
			}
		} catch (Exception e) {
		}

		// Show user's options
		response.sendRedirect("/OSCM/processor.jsp");
	}
}