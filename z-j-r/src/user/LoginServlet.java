package user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import db.CreateDB;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
	
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String pass = request.getParameter("pass");
        Boolean flag = true;
        System.out.println(phone);
        System.out.println(password);
        System.out.println(pass);
        
        if(pass.equals(password) && pass.length() > 5 && pass.length() < 13){
        	//	response.sendRedirect("./index.html");
        	//	连接数据库
            Connection con = (Connection) CreateDB.getConnectionDB();
            //	操作数据库
            try {
            	 Statement statement = con.createStatement();
    			 String sql = "INSERT INTO user (user_name, user_phone, user_password) VALUES (" + "'用户', " + " '" + phone + "'" + "," + " '" + password + "')";
    			 String sql2 = "select * from user where user_phone = " + phone;
    			 //	先判断数据库中有没有需要注册的手机号
    			 pst = con.prepareStatement(sql2);
    		     rs = statement.executeQuery(sql2);
    		     while(rs.next()){
    		    	 String usedP = rs.getString("user_phone");
    		    	 if(phone.equals(usedP)){
    		    		 flag = false;
    		    	 }
    		     }
    		     //	再进行插入操作
    		     if(flag){
    		    	 pst = con.prepareStatement(sql);
    		    	 statement.execute(sql);
    		     }
    		} catch (SQLException e) {
    			flag = false;
    			e.printStackTrace();
    		} finally {
    			//	关闭数据库
    			CreateDB.release(con, pst, rs);
    		}
            if(flag){
             	System.out.println("注册成功");
             	response.sendRedirect("../z-j-r/view/loginOK.html");
             }else{
             	System.out.println("注册失败");
             	response.sendRedirect("../z-j-r/view/login.html");
             }
        } else {
        	response.sendRedirect("../z-j-r/view/login.html");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
