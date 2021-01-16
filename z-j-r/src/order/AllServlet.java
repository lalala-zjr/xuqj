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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AllServlet
 */
@WebServlet("/AllServlet")
public class AllServlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllServlet() {
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
	    JSONObject object = new JSONObject();  //创建Json对象
        try {
        	 JSONArray array = new JSONArray();
        	 Statement statement = con.createStatement();
			 String sql = "select * from orderlist where order_UserPhone = " + UserP;
			 pst = con.prepareStatement(sql);
		     rs = statement.executeQuery(sql);
		     int a = 0;
		     while(rs.next()){
		    	 a++;
		    	 JSONObject arrayElementOne = new JSONObject();
		    	 String Num = rs.getString("orderNum");
            	 String Name = rs.getString("order_Name");
            	 String Phone = rs.getString("orderPhone");
            	 String Goods = rs.getString("orderGoods");
            	 String g = "";
            	 if(Goods.equals("小")){
            		 g = "3";
            	 } else if(Goods.equals("中")){
            		 g = "5";
            	 } else
            		 g = "10";
            	 response.setCharacterEncoding("UTF-8");
            	 arrayElementOne.put("num", Num);
            	 arrayElementOne.put("name", Name);
            	 arrayElementOne.put("phone", Phone);
            	 arrayElementOne.put("g", g);
            	 array.add(arrayElementOne);
		     }
		     object.put("sum", a);
		     object.put("order", array);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			PrintWriter pw = response.getWriter();
			//	关闭数据库
			pw.print(object.toString());
            pw.flush(); 
       	    pw.close();
       	    CreateDB.release(con, pst, rs);
		}
        System.out.println("查询用户订单成功");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("text/html;charset=utf-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
