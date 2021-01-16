package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import db.CreateDB;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String UserP = (String) session.getAttribute("userPhone");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        Connection con = (Connection) CreateDB.getConnectionDB();
        try {
       	 	 Statement statement = con.createStatement();
       	 	 if(name != "" && password == ""){
       	 		 String sql = "UPDATE user SET user_name=\"" + name + "\"" + "WHERE user_phone=" + UserP;
       	 		 pst = con.prepareStatement(sql);
   		         statement.execute(sql);
       	 	 } else if(name == "" && password != ""){
       	 		 String sql = "UPDATE user SET user_password=\"" + password + "\"" + "WHERE user_phone=" + UserP;
       	 		 pst = con.prepareStatement(sql);
   		         statement.execute(sql);
       	 	 } else if(name != "" && password != ""){
       	 		 String sql = "UPDATE user SET user_name=\"" + name + "\", user_password=\"" + password + "\" WHERE user_phone=" + UserP;
       	 		 pst = con.prepareStatement(sql);
   		         statement.execute(sql);
       	 	 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//	关闭数据库
			CreateDB.release(con, pst, rs);
		}
        System.out.println("用户信息修改成功");
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter(); 
   	 	String json = "1";
        pw.print(json); 
        pw.flush(); 
   	 	pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
