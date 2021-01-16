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
        	//	�������ݿ�
            Connection con = (Connection) CreateDB.getConnectionDB();
            //	�������ݿ�
            try {
            	 Statement statement = con.createStatement();
    			 String sql = "INSERT INTO user (user_name, user_phone, user_password) VALUES (" + "'�û�', " + " '" + phone + "'" + "," + " '" + password + "')";
    			 String sql2 = "select * from user where user_phone = " + phone;
    			 //	���ж����ݿ�����û����Ҫע����ֻ���
    			 pst = con.prepareStatement(sql2);
    		     rs = statement.executeQuery(sql2);
    		     while(rs.next()){
    		    	 String usedP = rs.getString("user_phone");
    		    	 if(phone.equals(usedP)){
    		    		 flag = false;
    		    	 }
    		     }
    		     //	�ٽ��в������
    		     if(flag){
    		    	 pst = con.prepareStatement(sql);
    		    	 statement.execute(sql);
    		     }
    		} catch (SQLException e) {
    			flag = false;
    			e.printStackTrace();
    		} finally {
    			//	�ر����ݿ�
    			CreateDB.release(con, pst, rs);
    		}
            if(flag){
             	System.out.println("ע��ɹ�");
             	response.sendRedirect("../z-j-r/view/loginOK.html");
             }else{
             	System.out.println("ע��ʧ��");
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
