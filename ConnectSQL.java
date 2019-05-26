package application;

import java.sql.DriverManager;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

public class ConnectSQL {
	public static Connection connecToMySQL()
	{
		Connection myCon = null;
		try {
			
			myCon =(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Salary?characterEncoding=utf-8","root","412723");
			if(myCon != null)
			{
				System.out.println("Successfully connected!");
			}
			return myCon;
		}catch(Exception e)
		{ 
			//System.out.print("not connect to the database!");
			JOptionPane.showMessageDialog(null, "无法连接数据库！");
		}
		return myCon;
	}
}
