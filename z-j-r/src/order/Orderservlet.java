package order;

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
 * Servlet implementation class Orderservlet
 */
@WebServlet("/Orderservlet")
public class Orderservlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Orderservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        //	连接数据库
		String userID = request.getParameter("id");
        Connection con = (Connection) CreateDB.getConnectionDB();
        //	操作数据库
        try {
        	 Statement statement = con.createStatement();
        	 HttpSession session = request.getSession();
        	 String UserP = (String) session.getAttribute("userPhone");
			 String sql = "select * from orderlist where orderID = " + userID;
		     pst = con.prepareStatement(sql);
		     rs = statement.executeQuery(sql);
             while(rs.next()){
            	 String ID = rs.getString("orderID");
            	 String Num = rs.getString("orderNum");
            	 String Name = rs.getString("order_Name");
            	 String Phone = rs.getString("orderPhone");
            	 String From = rs.getString("orderFrom");
            	 String To = rs.getString("orderTo");
            	 String Time = rs.getString("orderTime");
            	 String INF = rs.getString("order_INF");
            	 String Goods = rs.getString("orderGoods");
            	 String g = "";
            	 if(Goods.equals("小")){
            		 g = "3";
            	 } else if(Goods.equals("中")){
            		 g = "5";
            	 } else
            		 g = "10";
            	 response.setContentType("text/html;charset=UTF-8");
            	 response.setHeader("Cache-Control", "no-cache");
            	 response.setCharacterEncoding("UTF-8");
            	 PrintWriter pw = response.getWriter(); 
            	 String json = "{\"id\":\""+ ID +"\",\"num\":\""+ Num +"\",\"name\":\""+ Name + "\",\"phone\":\""+ Phone +
            			 "\",\"from\":\""+ From +"\",\"to\":\""+ To +"\",\"time\":\""+ Time +"\",\"information\":\""+ INF +
            			 "\",\"Goods\":\""+ g +"\",\"User\":\""+ UserP +"\"}";
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
        System.out.println("用户订单详情页");
        response.setContentType("text/plain");
        response.setContentType("application/json");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
