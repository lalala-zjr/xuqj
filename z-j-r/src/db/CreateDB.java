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
        //驱动程序名
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/xyqj";
        //MySQL配置时的用户名
        user = "root";
        //MySQL配置时的密码
        password = "mysql";
	}
	
	public static Connection getConnectionDB() {
		//声明Connection对象
        Connection con = null;
		try {
            //加载驱动程序
            Class.forName(driver);            
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
            	System.out.println("连接数据库！！！");
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
		System.out.println("关闭数据库！");
	}
	
}