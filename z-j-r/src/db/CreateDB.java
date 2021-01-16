package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class CreateDB {
	private static String driver;
    private static String url;
    private static String user;
    private static String password;
	public static void main(String[] args){
	}
	static{
        //����������
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/xyqj";
        //MySQL����ʱ���û���
        user = "root";
        //MySQL����ʱ������
        password = "mysql";
	}
	
	public static Connection getConnectionDB() {
		//����Connection����
        Connection con = null;
		try {
            //������������
            Class.forName(driver);            
            //1.getConnection()����������MySQL���ݿ⣡��
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
            	System.out.println("�������ݿ⣡����");
        } catch (Exception e) {
            e.printStackTrace();
        }
		return con;
	}
	public static void release(Connection con,PreparedStatement pstmt, ResultSet rs){
		if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		System.out.println("�ر����ݿ⣡");
	}
	
}