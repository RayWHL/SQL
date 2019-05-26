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
	@FXML private TextField UserName;  //�˺�����
	@FXML private PasswordField PassWord;	//��������
	
	public static String UserID;  //�����˺���Ϣ
	
	@FXML void on_LoginButton_clicked()
	{
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			//��ȡ��������
			String UName=UserName.getText();
			String PWord=PassWord.getText();
			
			//��ѯ�˺Ŷ�Ӧ�����Լ����������ж�Ա�����߹���Ա
			PreparedStatement ps = con.prepareStatement("SELECT PASSWORD, DID FROM S_EMPLOYEE,S_TYPE WHERE UID=? AND "
					+ "S_EMPLOYEE.TID=S_TYPE.TID ");
			ps.setString(1,UName);
			ResultSet  rs = ps.executeQuery();
			
			if(rs.next())
			{
				UserID=UName;
				String QPassword = rs.getString("PASSWORD").trim();
				String QTYPE = rs.getString("DID").trim();
				//�ж������Ƿ���ȷ
				if(PWord.equals(QPassword))
				{
					//�ж��Ƿ�Ϊ����Ա
					if(QTYPE.equals("000001"))
					{
						//�رձ�����
						Stage tempStage = (Stage) LoginButton.getScene().getWindow();
				        tempStage.close();
				        //��������Ա����
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml") );
				        AnchorPane root = new AnchorPane();
					    Scene myScene = new Scene(root);
				        try {
					        myScene.setRoot((Parent) loader.load());
					        myScene.getStylesheets().add(getClass().getResource("EmployeeCss.css").toExternalForm());
					        Stage newStage = new Stage();
					        newStage.setTitle("���ʹ���ϵͳ--����Ա");
					        newStage.setScene(myScene);
					        newStage.show();
				        }catch (Exception ex) {
				   	    ex.printStackTrace();
				        } 
					}
					else
					{
						//�رձ�����
						Stage tempStage = (Stage) LoginButton.getScene().getWindow();
				        tempStage.close();
						//����Ա������
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("Employee.fxml") );
				        AnchorPane root = new AnchorPane();
					    Scene myScene = new Scene(root);
				        try {
					        myScene.setRoot((Parent) loader.load());
					        myScene.getStylesheets().add(getClass().getResource("EmployeeCss.css").toExternalForm());
					        Stage newStage = new Stage();
					        newStage.setTitle("���ʹ���ϵͳ--Ա��");
					        newStage.setScene(myScene);
					        newStage.show();
				        }catch (Exception ex) {
				   	    ex.printStackTrace();
				        } 
					}
				}
				else
					JOptionPane.showMessageDialog(null, "�������");
			}
			else
				JOptionPane.showMessageDialog(null, "�˺Ŵ���");
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

