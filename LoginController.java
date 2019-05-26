package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
	@FXML private Button LoginButton;
	@FXML private Button CleanButton;
	@FXML private TextField UserName;  //账号输入
	@FXML private PasswordField PassWord;	//密码输入
	
	public static String UserID;  //传递账号信息
	
	@FXML void on_LoginButton_clicked()
	{
		Connection con=null;
		try {
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			//获取输入内容
			String UName=UserName.getText();
			String PWord=PassWord.getText();
			
			//查询账号对应密码以及部门用于判断员工或者管理员
			PreparedStatement ps = con.prepareStatement("SELECT PASSWORD, DID FROM S_EMPLOYEE,S_TYPE WHERE UID=? AND "
					+ "S_EMPLOYEE.TID=S_TYPE.TID ");
			ps.setString(1,UName);
			ResultSet  rs = ps.executeQuery();
			
			if(rs.next())
			{
				UserID=UName;
				String QPassword = rs.getString("PASSWORD").trim();
				String QTYPE = rs.getString("DID").trim();
				//判断密码是否正确
				if(PWord.equals(QPassword))
				{
					//判断是否为管理员
					if(QTYPE.equals("000001"))
					{
						//关闭本界面
						Stage tempStage = (Stage) LoginButton.getScene().getWindow();
				        tempStage.close();
				        //启动管理员界面
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml") );
				        AnchorPane root = new AnchorPane();
					    Scene myScene = new Scene(root);
				        try {
					        myScene.setRoot((Parent) loader.load());
					        myScene.getStylesheets().add(getClass().getResource("EmployeeCss.css").toExternalForm());
					        Stage newStage = new Stage();
					        newStage.setTitle("工资管理系统--管理员");
					        newStage.setScene(myScene);
					        newStage.show();
				        }catch (Exception ex) {
				   	    ex.printStackTrace();
				        } 
					}
					else
					{
						//关闭本界面
						Stage tempStage = (Stage) LoginButton.getScene().getWindow();
				        tempStage.close();
						//启动员工界面
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("Employee.fxml") );
				        AnchorPane root = new AnchorPane();
					    Scene myScene = new Scene(root);
				        try {
					        myScene.setRoot((Parent) loader.load());
					        myScene.getStylesheets().add(getClass().getResource("EmployeeCss.css").toExternalForm());
					        Stage newStage = new Stage();
					        newStage.setTitle("工资管理系统--员工");
					        newStage.setScene(myScene);
					        newStage.show();
				        }catch (Exception ex) {
				   	    ex.printStackTrace();
				        } 
					}
				}
				else
					JOptionPane.showMessageDialog(null, "密码错误");
			}
			else
				JOptionPane.showMessageDialog(null, "账号错误");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try { 
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
		
	}
	
	@FXML void on_CleanButton_clicked()
	{
		UserName.clear();
		PassWord.clear();
	}
}

