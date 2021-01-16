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
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import db.CreateDB;

/**
 * Servlet implementation class RegistServlet
 */
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        Boolean flag = false;
        //	�������ݿ�
        Connection con = (Connection) CreateDB.getConnectionDB();
        //	�������ݿ�
        try {
        	 Statement statement = con.createStatement();
			 String sql = "select * from user where user_phone = " + phone;
		     pst = con.prepareStatement(sql);
		     rs = statement.executeQuery(sql);
             while(rs.next()){
            	 String userphone = rs.getString("user_phone");
            	 String userpassword = rs.getString("user_password");
            	 if(phone.equals(userphone) && password.equals(userpassword)){
                 	flag = true;
            	 }
             }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//	�ر����ݿ�
			CreateDB.release(con, pst, rs);
		}
        if(flag){
         	System.out.println("��¼�ɹ�");
         	//����session����
            HttpSession session = request.getSession();
            //���û����ݱ�����session�������
            session.setAttribute("userPhone", phone);
         	response.sendRedirect("../z-j-r/view/registOK.html");
         }else{
         	System.out.println("��¼ʧ��");
         	response.sendRedirect("../z-j-r/view/regist.html");
         }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
