package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EmployeeController {
	@FXML private Label WelcomeLabel ;
	@FXML private Label SignInLabel ;
	@FXML private Button SignInButton ;
	
	//������Ϣ�ؼ�
	@FXML private TextField SecUserID;
	@FXML private TextField SecUserName;
	@FXML private TextField SecUserSex;
	@FXML private TextField SecUserYear;
	@FXML private TextField SecUserTel;
	//@FXML private Button SecModify ;
	
	//������Ϣ�ؼ�
	//@FXML private Label ThrFunSalary;
	@FXML private TextField ThrType;
	@FXML private TextField ThrRank;
	@FXML private TextField ThrSalary;
	@FXML private TextField ThrSub;
	
	//���ؼ�
	private ObservableList<SalaryPerMonth> SalaryMonthData = FXCollections.observableArrayList();
	@FXML private TableView<SalaryPerMonth> SalaryMonthTable;
	@FXML private TableColumn<SalaryPerMonth, String> YearColumn;
	@FXML private TableColumn<SalaryPerMonth, String> MonthColumn;
	@FXML private TableColumn<SalaryPerMonth, String> SalaryColumn;
	@FXML private TableColumn<SalaryPerMonth, String> SubColumn;
	@FXML private TableColumn<SalaryPerMonth, String> SumSalaryColumn;
	
	//ʱ���ѡ��ؼ�
	@FXML private DatePicker FourBeginTime;
	@FXML private DatePicker FourEndTime;
	
	//���ڼ�¼���ؼ�
	private ObservableList<EmployeeAttendance> EmployeeAttendanceData = FXCollections.observableArrayList();
	@FXML private TableView<EmployeeAttendance> EmployeeAttendanceTable;
	@FXML private TableColumn<EmployeeAttendance, String> BEGINTColumn;
	@FXML private TableColumn<EmployeeAttendance, String> ENDTColumn;
	@FXML private TableColumn<EmployeeAttendance, String> TYPEColumn;
	
	private Boolean SignIn;  //�����жϴ�����:ǩ����ǩ��
	private Boolean NormalWork;	//�Ƿ��������ϰࣨ����/������
	private String SignTime;	//����ǩ��ʱ��
	
	//Ա��δ�������Ϣ
	private String UserID;
	private String OldUserName;
	private String OldUserSex;
	private String OldUserTel;
	private String OldUserYear;
	
	
	@FXML public void initialize() {
		setWelcomeLabel();
		checkAttenTable();
		
		YearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().YearProperty()));
		MonthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().MonthProperty()));
		SalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SalaryProperty()));
		SubColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SubProperty()));
		SumSalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SumSalaryProperty()));
		SetSalary();
		
		BEGINTColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().BEGINTProperty()));
		ENDTColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ENDTProperty()));
		TYPEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().TYPEProperty()));
		//SetFourAttendance();
	}
	
	void setWelcomeLabel() {
		UserID=LoginController.UserID;
		
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ѯID��Ӧ����
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_EMPLOYEE WHERE UID=? ");
			ps.setString(1,UserID);
			ResultSet  rs = ps.executeQuery();
			rs.next();
			WelcomeLabel.setText(rs.getString("UNAME")+",��ӭʹ�ù��ʹ���ϵͳ��");
			
			//�û�������Ϣ
			OldUserName=rs.getString("UNAME");
			OldUserSex=rs.getString("USEX");
			OldUserTel=rs.getString("UTEL");
			OldUserYear=rs.getString("UYEAR");
			
			SecUserID.setText(UserID);
			//SecUserID.setDisable(true);
			SecUserName.setText(OldUserName);
			SecUserSex.setText(OldUserSex);
			SecUserYear.setText(OldUserYear);
			SecUserTel.setText(OldUserTel);
			
			//����������Ϣ
			ps = con.prepareStatement("SELECT TNAME,TRANK,TSALARY,TSUB FROM S_EMPLOYEE,S_TYPE WHERE S_EMPLOYEE.TID=S_TYPE.TID AND "
					+ "S_EMPLOYEE.UID=?");
			ps.setString(1,UserID);
			rs = ps.executeQuery();
			rs.next();
			//ThrFunSalary.setText("���֣�"+rs.getString("TNAME")+"  �ȼ���"+rs.getString("TRANK")+
			//		"  �������ʣ�"+rs.getString("TSALARY")+"Ԫ/ʱ    "+"�Ӱ������"+rs.getString("TSUB")+"Ԫ/ʱ");
			ThrType.setText(rs.getString("TNAME"));
			ThrRank.setText(rs.getString("TRANK"));
			ThrSalary.setText(rs.getString("TSALARY")+"Ԫ/ʱ");
			ThrSub.setText(rs.getString("TSUB")+"Ԫ/ʱ");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	void checkAttenTable() {
		//UserID=LoginController.UserID;
		SignIn=true;
		
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ȡ��ǰʱ��
			Date dNow = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dNow);
			//String currentYear = currentTime.substring(0,4);
			//String currentMonth = currentTime.substring(5,7);
			String currentDay = currentTime.substring(0,10);
			String currentHour = currentTime.substring(11,13);
			
			//����Ա�����ڱ��Ƿ���δ���ǩ��
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_ATTENDANCE WHERE SIGNOUT=0 AND UID=?");
			ps.setString(1, UserID);
			ResultSet  rs = ps.executeQuery();
			while(rs.next())
			{
				String getDay=rs.getString("BEGINT").substring(0,10);
				if(currentDay.equals(getDay))
				{
					SignIn=false;
					SignInLabel.setText("�����ϴδ�ʱ��Ϊ��\n"+rs.getString("BEGINT")+"\n���ͼ�����ǩ��");
					SignTime=rs.getString("BEGINT");
				}
				/*
				else
				{
					//ɾ��������¼
					String getEndTime=rs.getString("ENDT");	
				}*/
			}
			
			/*
			//����Ա���������Ƿ���δ���ǩ��
			ps = con.prepareStatement("SELECT * FROM S_SUBSIDY WHERE SIGNOUT=0 AND UID=?");
			ps.setString(1, UserID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String getDay=rs.getString("BEGINT").substring(0,10);
				if(currentDay.equals(getDay))
				{
					SignIn=false;
					SignInLabel.setText("�����ϴδ�ʱ��Ϊ��"+rs.getString("BEGINI")+"���ͼ�����ǩ��");
					SignTime=rs.getString("BEGINT");
				}
			}*/
			
			//����SignIn���ñ�ǩ
			if(SignIn)
			{
				//ǩ��ʱ���жϵ�ǰʱ���
				if(Integer.parseInt(currentHour)<17 || Integer.parseInt(currentHour)>=9)
				{
					NormalWork=true;
					SignInLabel.setText("����û��δ��ɵ�ǩ��,"+"���ͼ��ǩ��");
				}
				else 
				{
					NormalWork=false;
					SignInLabel.setText("�������°�ʱ�䣬������ǩ����");
				}
			}
			else
			{
				//ǩ��ʱ��ʱ���
				//�жϵ�ǰʱ���
				if(Integer.parseInt(currentHour)<18 || Integer.parseInt(currentHour)>=9)
					NormalWork=true;
				else NormalWork=false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	@FXML public void SignInClicked()
	{
		//��Ҫ����ǩ��/ǩ�ˣ�����/����
		System.out.println("there");
		
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			Date dNow = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dNow);
			String currentDay = currentTime.substring(0,10);
			//�����ϰ�ʱ��ǩ��
			if(NormalWork && SignIn) 
			{
				PreparedStatement ps = con.prepareStatement("INSERT INTO S_ATTENDANCE VALUES(?,?,?,?) ");
				ps.setString(1, UserID);
				ps.setString(2, currentTime);
				ps.setString(3, currentTime);
				ps.setBoolean(4, false);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "ǩ���ɹ���ʱ��Ϊ��"+currentTime);
			}
			//����ʱ��ǩ��18��ǰǩ��
			if(NormalWork&& !SignIn)
			{
				PreparedStatement ps = con.prepareStatement("UPDATE S_ATTENDANCE SET ENDT=?,SIGNOUT=? WHERE UID=? AND BEGINT=?");
				ps.setString(1, currentTime);
				ps.setBoolean(2, true);
				ps.setString(3, UserID);
				ps.setString(4, SignTime);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "ǩ�˳ɹ���ʱ��Ϊ��"+currentTime);
			}
			
			// *������ǩ��
			//�Ӱ�ʱ��ǩ��17���
			if(SignIn && !NormalWork)
			{
				JOptionPane.showMessageDialog(null,"�°��ˣ�����Ҫǩ���ˣ�");
				/*
				PreparedStatement ps = con.prepareStatement("INSERT INTO S_SUBSIDY VALUES(?,?,?,?) ");
				ps.setString(1, UserID);
				ps.setString(2, currentTime);
				ps.setString(3, currentTime);
				ps.setBoolean(4, false);
				ps.executeUpdate();
				*/
			}
			//�Ӱ�ǩ��
			if(!SignIn && !NormalWork)
			{
				//���ڱ�
				PreparedStatement ps = con.prepareStatement("UPDATE S_ATTENDANCE SET ENDT=?,SIGNOUT=? WHERE UID=? AND BEGINT=?");
				ps.setString(1, currentDay+" 17:00:00");
				ps.setBoolean(2, true);
				ps.setString(3, UserID);
				ps.setString(4, SignTime);
				ps.executeUpdate();
				//������
				ps = con.prepareStatement("INSERT INTO S_SUBSIDY VALUES(?,?,?,?) ");
				ps.setString(1, UserID);
				ps.setString(2, currentDay+" 17:00:00");
				ps.setString(3, currentTime);
				ps.setBoolean(4, true);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "ǩ�˳ɹ���ʱ��Ϊ��"+currentTime);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
		
		checkAttenTable();
	}
	
	
	//������Ϣ�޸İ�ť
	@FXML public void SecModifyClicked()
	{
		String NewUserName=SecUserName.getText();
		String NewUserSex=SecUserSex.getText();
		String NewUserYear=SecUserYear.getText();
		String NewUserTel=SecUserTel.getText();
		System.out.println(NewUserName+NewUserSex+NewUserYear);
		//�ж��Ƿ��и���
		if(NewUserName.equals(OldUserName) &&
				NewUserSex.equals(OldUserSex) &&
				NewUserYear.equals(OldUserYear) &&
				NewUserTel.equals(OldUserTel))
		{
			JOptionPane.showMessageDialog(null, "û���κθ��ģ�");
			return;
		}
		
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ѯID��Ӧ����
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_MODIFY WHERE UID=? ");
			ps.setString(1,UserID);
			ResultSet  rs = ps.executeQuery();
			
			if(rs.next())
			{
				//�Ѿ����ڴ������
				ps = con.prepareStatement("UPDATE S_MODIFY SET UNAME=?,UYEAR=?,USEX=?,UTEL=? WHERE UID=?");
				ps.setString(1,NewUserName);
				ps.setString(2, NewUserYear);
				ps.setString(3, NewUserSex);
				ps.setString(4, NewUserTel);
				ps.setString(5, UserID);
				
			}
			else
			{
				//û�д������
				ps.close();
				ps = con.prepareStatement("INSERT INTO S_MODIFY VALUES (?, ?, ?, ?, ?)");
				ps.setString(1, UserID);
			 	ps.setString(2, NewUserName);
			 	ps.setString(3, NewUserYear);
			 	ps.setString(4, NewUserSex);
			 	ps.setString(5, NewUserTel);
			}
			
			//�Ƿ�ִ�гɹ�
			if(ps.executeUpdate()==1)
				JOptionPane.showMessageDialog(null, "���ύ���ģ�");
			else
				JOptionPane.showMessageDialog(null, "���ĸ�ʽ����");

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
		
		
	}
	
	private void SetSalary()
	{
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ѯID��Ӧ����
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_SALARY WHERE UID=? ");
			ps.setString(1,UserID);
			ResultSet  rs = ps.executeQuery();
			String ThrYear=null;
			String ThrMonth=null;
			String ThrSalary=null;
			String ThrSub=null;
			String ThrSum=null;

			while(rs.next())
			{
				ThrYear=rs.getString("PYEAR").trim();
				ThrMonth=rs.getString("PMONTH").trim();
				ThrSalary=rs.getString("PFUND").trim();
				ThrSub=rs.getString("PFUND2").trim();
				ThrSum=rs.getString("PSUM").trim();
				SalaryMonthData.add(new SalaryPerMonth(ThrYear,ThrMonth,ThrSalary,ThrSub,ThrSum));

			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		SalaryMonthTable.setItems(SalaryMonthData);

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	@FXML private void SetFourAttendance()
	{
		if(FourBeginTime.getValue().equals("") || FourEndTime.getValue().equals(""))
		{
			JOptionPane.showMessageDialog(null, "��ѡ��ʱ��Σ�");
			return;
		}
		EmployeeAttendanceData.clear();
		String GetFourBeginTime=FourBeginTime.getValue().toString()+" 00:00:00";
		String GetFourEndTime=FourEndTime.getValue().toString()+" 23:59:59";
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ѯ�����ϰ�
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_ATTENDANCE WHERE UID=? AND "
					+ "BEGINT>=? AND "
					+ "ENDT<=?");
			ps.setString(1,UserID);
			ps.setString(2, GetFourBeginTime);
			ps.setString(3, GetFourEndTime);
			ResultSet  rs = ps.executeQuery();
			String BEGINT=null;
			String ENDT=null;
			String TYPE="�����ϰ�";
			
			while(rs.next())
			{
				BEGINT=rs.getString("BEGINT").trim();
				ENDT=rs.getString("ENDT").trim();
		
				EmployeeAttendanceData.add(new EmployeeAttendance(BEGINT,ENDT,TYPE));
			}
			//�Ӱ�
			ps = con.prepareStatement("SELECT * FROM S_SUBSIDY WHERE UID=? AND "
					+ "BEGINT>=? AND "
					+ "ENDT<=?");
			ps.setString(1,UserID);
			ps.setString(2, GetFourBeginTime);
			ps.setString(3, GetFourEndTime);
			rs = ps.executeQuery();
			TYPE="�Ӱ�";
			while(rs.next())
			{
				BEGINT=rs.getString("BEGINT").trim();
				ENDT=rs.getString("ENDT").trim();
				EmployeeAttendanceData.add(new EmployeeAttendance(BEGINT,ENDT,TYPE));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		EmployeeAttendanceTable.setItems(EmployeeAttendanceData);

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
}
