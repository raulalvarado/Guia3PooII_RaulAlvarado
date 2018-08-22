package music;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "Chinook";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "";
	
	public Connection conn() throws ClassNotFoundException, SQLException,InstantiationException,IllegalAccessException{
		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(url+dbName, userName,password);
		return conn;
	}
}
