package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.UserDB;
import utils.Utils;
import beans.User;

public class ModifyPassword extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ModifyPassword() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		UserDB db = new UserDB();
		Utils utils = new Utils();
		int id = Integer.parseInt(request.getParameter("id"));
		String old_password = request.getParameter("old_password");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		String path = request.getContextPath();
		User user = db.get(db.get_by_id(id).getEmail(), utils.crypt_password(old_password));
		if (user == null)
		{
			request.getSession().setAttribute("msg","原密码错误");
			response.sendRedirect(path+"/templates/usercenter.jsp");
			return;
		}

		if (!utils.check_password(password1, password2)){
			request.getSession().setAttribute("msg","两次密码不一致");
			response.sendRedirect(path+"/templates/usercenter.jsp");
			return;
		}


		db.modify_passwd(id, utils.crypt_password(password2));
		request.getSession().setAttribute("msg","密码修改成功");
		response.sendRedirect(path+"/templates/usercenter.jsp");
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
