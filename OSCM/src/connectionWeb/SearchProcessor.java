package connectionWeb;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * This Class handles the request for searching.
 * </p>
 * 
 * @author Yiming Li
 * @version 1.0
 * 
 */
public class SearchProcessor extends HttpServlet {

	private static final long serialVersionUID = -6653066473904929268L;

	/**
	 * Name of library, for distinguishing libraries. Could be "lib1", "lib2",
	 * "lib3".
	 */
	private String ID = null;

	/**
	 * The keyword for searching books.
	 */
	private String keyword = "";

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
		// Get user request
		HttpSession session = request.getSession();

		// Get library name
		String a = request.getParameter("select");
		if (a.equals("1")) {
			ID = "Package_ID";
		} else if (a.equals("2")) {
			ID = "Location_ID";
		} else if (a.equals("3")) {
			ID = "Company_ID";
		}

		// Get keyWord
		keyword = (String) (request.getParameter("KeyWord")).trim();

		// Show user's options
		session.setAttribute("ID", ID);
		session.setAttribute("keyword", keyword);

		// Show user's options
		response.sendRedirect("/OSCM/processor.jsp");
	}
}