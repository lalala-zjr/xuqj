package order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import db.CreateDB;

/**
 * Servlet implementation class SendServlet
 */
@WebServlet("/SendServlet")
public class SendServlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String num = request.getParameter("num");
        String name = request.getParameter("name");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String phone = request.getParameter("phone");
        String good = request.getParameter("good");
        String time = request.getParameter("time");
        String information = request.getParameter("information");
        Date  date = new Date();
        Random rand = new Random();
        long data2 = rand.nextInt(999 - 100 + 1) + 100;
        long ID = data2 + date.getTime();
        HttpSession session = request.getSession();
        String UserP = (String) session.getAttribute("userPhone");
        Connection con = (Connection) CreateDB.getConnectionDB();
        //	操作数据库
        try {
        	 Statement statement = con.createStatement();
			 String sql = "INSERT INTO orderlist VALUES ("+"'"+ ID +"','"+ num +"','"+ name+"','"+ phone +"','"+ from 
					 +"','"+ to +"','"+ good +"','"+ time +"','"+ information + "','"+ UserP +"')";
	    	 pst = con.prepareStatement(sql);
		     statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//	关闭数据库
			CreateDB.release(con, pst, rs);
		}
        System.out.println("发布订单成功");
        response.setContentType("text/plain");
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter(); 
   	 	String json = "{\"id\":\"" + ID +"\"}";
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
