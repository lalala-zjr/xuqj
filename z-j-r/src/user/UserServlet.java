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
 * Servlet implementation class UserServlet
 * 个人页面返回用户基本信息
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String UserP = (String) session.getAttribute("userPhone");
        Connection con = (Connection) CreateDB.getConnectionDB();
        //	操作数据库
        try {
        	 Statement statement = con.createStatement();
			 String sql = "select * from user where user_phone = " + UserP;
			 pst = con.prepareStatement(sql);
		     rs = statement.executeQuery(sql);
		     while(rs.next()){
		    	 String user = rs.getString("user_name");
		    	 String phone = rs.getString("user_phone");
		    	 response.setContentType("text/html;charset=UTF-8");
            	 response.setHeader("Cache-Control", "no-cache");
            	 response.setCharacterEncoding("UTF-8");
            	 PrintWriter pw = response.getWriter(); 
            	 String json = "{\"user\":\""+ user +"\",\"phone\":\""+ phone + "\"}";
                 pw.print(json); 
                 pw.flush(); 
            	 pw.close();
		     }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//	关闭数据库
			CreateDB.release(con, pst, rs);
		}
        System.out.println("个人用户信息返回成功");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
