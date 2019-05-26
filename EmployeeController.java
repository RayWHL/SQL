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
	
	//基本信息控件
	@FXML private TextField SecUserID;
	@FXML private TextField SecUserName;
	@FXML private TextField SecUserSex;
	@FXML private TextField SecUserYear;
	@FXML private TextField SecUserTel;
	//@FXML private Button SecModify ;
	
	//工资信息控件
	//@FXML private Label ThrFunSalary;
	@FXML private TextField ThrType;
	@FXML private TextField ThrRank;
	@FXML private TextField ThrSalary;
	@FXML private TextField ThrSub;
	
	//表格控件
	private ObservableList<SalaryPerMonth> SalaryMonthData = FXCollections.observableArrayList();
	@FXML private TableView<SalaryPerMonth> SalaryMonthTable;
	@FXML private TableColumn<SalaryPerMonth, String> YearColumn;
	@FXML private TableColumn<SalaryPerMonth, String> MonthColumn;
	@FXML private TableColumn<SalaryPerMonth, String> SalaryColumn;
	@FXML private TableColumn<SalaryPerMonth, String> SubColumn;
	@FXML private TableColumn<SalaryPerMonth, String> SumSalaryColumn;
	
	//时间段选择控件
	@FXML private DatePicker FourBeginTime;
	@FXML private DatePicker FourEndTime;
	
	//考勤记录表格控件
	private ObservableList<EmployeeAttendance> EmployeeAttendanceData = FXCollections.observableArrayList();
	@FXML private TableView<EmployeeAttendance> EmployeeAttendanceTable;
	@FXML private TableColumn<EmployeeAttendance, String> BEGINTColumn;
	@FXML private TableColumn<EmployeeAttendance, String> ENDTColumn;
	@FXML private TableColumn<EmployeeAttendance, String> TYPEColumn;
	
	private Boolean SignIn;  //用于判断打卡类型:签到、签退
	private Boolean NormalWork;	//是否是正常上班（考勤/津贴）
	private String SignTime;	//今天签到时间
	
	//员工未变更的信息
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
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//查询ID对应姓名
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_EMPLOYEE WHERE UID=? ");
			ps.setString(1,UserID);
			ResultSet  rs = ps.executeQuery();
			rs.next();
			WelcomeLabel.setText(rs.getString("UNAME")+",欢迎使用工资管理系统！");
			
			//用户基本信息
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
			
			//基本工资信息
			ps = con.prepareStatement("SELECT TNAME,TRANK,TSALARY,TSUB FROM S_EMPLOYEE,S_TYPE WHERE S_EMPLOYEE.TID=S_TYPE.TID AND "
					+ "S_EMPLOYEE.UID=?");
			ps.setString(1,UserID);
			rs = ps.executeQuery();
			rs.next();
			//ThrFunSalary.setText("工种："+rs.getString("TNAME")+"  等级："+rs.getString("TRANK")+
			//		"  基本工资："+rs.getString("TSALARY")+"元/时    "+"加班津贴："+rs.getString("TSUB")+"元/时");
			ThrType.setText(rs.getString("TNAME"));
			ThrRank.setText(rs.getString("TRANK"));
			ThrSalary.setText(rs.getString("TSALARY")+"元/时");
			ThrSub.setText(rs.getString("TSUB")+"元/时");
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
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//获取当前时间
			Date dNow = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dNow);
			//String currentYear = currentTime.substring(0,4);
			//String currentMonth = currentTime.substring(5,7);
			String currentDay = currentTime.substring(0,10);
			String currentHour = currentTime.substring(11,13);
			
			//检查该员工考勤表是否有未完成签到
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_ATTENDANCE WHERE SIGNOUT=0 AND UID=?");
			ps.setString(1, UserID);
			ResultSet  rs = ps.executeQuery();
			while(rs.next())
			{
				String getDay=rs.getString("BEGINT").substring(0,10);
				if(currentDay.equals(getDay))
				{
					SignIn=false;
					SignInLabel.setText("今天上次打卡时间为：\n"+rs.getString("BEGINT")+"\n点击图标完成签退");
					SignTime=rs.getString("BEGINT");
				}
				/*
				else
				{
					//删除该条记录
					String getEndTime=rs.getString("ENDT");	
				}*/
			}
			
			/*
			//检查该员工津贴表是否有未完成签到
			ps = con.prepareStatement("SELECT * FROM S_SUBSIDY WHERE SIGNOUT=0 AND UID=?");
			ps.setString(1, UserID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String getDay=rs.getString("BEGINT").substring(0,10);
				if(currentDay.equals(getDay))
				{
					SignIn=false;
					SignInLabel.setText("今天上次打卡时间为："+rs.getString("BEGINI")+"点击图标完成签退");
					SignTime=rs.getString("BEGINT");
				}
			}*/
			
			//根据SignIn设置标签
			if(SignIn)
			{
				//签到时，判断当前时间段
				if(Integer.parseInt(currentHour)<17 || Integer.parseInt(currentHour)>=9)
				{
					NormalWork=true;
					SignInLabel.setText("今天没有未完成的签到,"+"点击图标签到");
				}
				else 
				{
					NormalWork=false;
					SignInLabel.setText("现在是下班时间，不允许签到！");
				}
			}
			else
			{
				//签退时，时间段
				//判断当前时间段
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
		//需要区分签到/签退，考勤/津贴
		System.out.println("there");
		
		Connection con=null;
		try {
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			Date dNow = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dNow);
			String currentDay = currentTime.substring(0,10);
			//正常上班时间签到
			if(NormalWork && SignIn) 
			{
				PreparedStatement ps = con.prepareStatement("INSERT INTO S_ATTENDANCE VALUES(?,?,?,?) ");
				ps.setString(1, UserID);
				ps.setString(2, currentTime);
				ps.setString(3, currentTime);
				ps.setBoolean(4, false);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "签到成功！时间为："+currentTime);
			}
			//正常时间签退18点前签退
			if(NormalWork&& !SignIn)
			{
				PreparedStatement ps = con.prepareStatement("UPDATE S_ATTENDANCE SET ENDT=?,SIGNOUT=? WHERE UID=? AND BEGINT=?");
				ps.setString(1, currentTime);
				ps.setBoolean(2, true);
				ps.setString(3, UserID);
				ps.setString(4, SignTime);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "签退成功！时间为："+currentTime);
			}
			
			// *不允许签到
			//加班时间签到17点后
			if(SignIn && !NormalWork)
			{
				JOptionPane.showMessageDialog(null,"下班了，不需要签到了！");
				/*
				PreparedStatement ps = con.prepareStatement("INSERT INTO S_SUBSIDY VALUES(?,?,?,?) ");
				ps.setString(1, UserID);
				ps.setString(2, currentTime);
				ps.setString(3, currentTime);
				ps.setBoolean(4, false);
				ps.executeUpdate();
				*/
			}
			//加班签退
			if(!SignIn && !NormalWork)
			{
				//考勤表
				PreparedStatement ps = con.prepareStatement("UPDATE S_ATTENDANCE SET ENDT=?,SIGNOUT=? WHERE UID=? AND BEGINT=?");
				ps.setString(1, currentDay+" 17:00:00");
				ps.setBoolean(2, true);
				ps.setString(3, UserID);
				ps.setString(4, SignTime);
				ps.executeUpdate();
				//津贴表
				ps = con.prepareStatement("INSERT INTO S_SUBSIDY VALUES(?,?,?,?) ");
				ps.setString(1, UserID);
				ps.setString(2, currentDay+" 17:00:00");
				ps.setString(3, currentTime);
				ps.setBoolean(4, true);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "签退成功！时间为："+currentTime);
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
	
	
	//基本信息修改按钮
	@FXML public void SecModifyClicked()
	{
		String NewUserName=SecUserName.getText();
		String NewUserSex=SecUserSex.getText();
		String NewUserYear=SecUserYear.getText();
		String NewUserTel=SecUserTel.getText();
		System.out.println(NewUserName+NewUserSex+NewUserYear);
		//判断是否有更改
		if(NewUserName.equals(OldUserName) &&
				NewUserSex.equals(OldUserSex) &&
				NewUserYear.equals(OldUserYear) &&
				NewUserTel.equals(OldUserTel))
		{
			JOptionPane.showMessageDialog(null, "没有任何更改！");
			return;
		}
		
		Connection con=null;
		try {
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//查询ID对应姓名
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_MODIFY WHERE UID=? ");
			ps.setString(1,UserID);
			ResultSet  rs = ps.executeQuery();
			
			if(rs.next())
			{
				//已经存在待审核项
				ps = con.prepareStatement("UPDATE S_MODIFY SET UNAME=?,UYEAR=?,USEX=?,UTEL=? WHERE UID=?");
				ps.setString(1,NewUserName);
				ps.setString(2, NewUserYear);
				ps.setString(3, NewUserSex);
				ps.setString(4, NewUserTel);
				ps.setString(5, UserID);
				
			}
			else
			{
				//没有待审核项
				ps.close();
				ps = con.prepareStatement("INSERT INTO S_MODIFY VALUES (?, ?, ?, ?, ?)");
				ps.setString(1, UserID);
			 	ps.setString(2, NewUserName);
			 	ps.setString(3, NewUserYear);
			 	ps.setString(4, NewUserSex);
			 	ps.setString(5, NewUserTel);
			}
			
			//是否执行成功
			if(ps.executeUpdate()==1)
				JOptionPane.showMessageDialog(null, "已提交更改！");
			else
				JOptionPane.showMessageDialog(null, "更改格式错误！");

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
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//查询ID对应姓名
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
			JOptionPane.showMessageDialog(null, "请选择时间段！");
			return;
		}
		EmployeeAttendanceData.clear();
		String GetFourBeginTime=FourBeginTime.getValue().toString()+" 00:00:00";
		String GetFourEndTime=FourEndTime.getValue().toString()+" 23:59:59";
		Connection con=null;
		try {
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//查询正常上班
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_ATTENDANCE WHERE UID=? AND "
					+ "BEGINT>=? AND "
					+ "ENDT<=?");
			ps.setString(1,UserID);
			ps.setString(2, GetFourBeginTime);
			ps.setString(3, GetFourEndTime);
			ResultSet  rs = ps.executeQuery();
			String BEGINT=null;
			String ENDT=null;
			String TYPE="正常上班";
			
			while(rs.next())
			{
				BEGINT=rs.getString("BEGINT").trim();
				ENDT=rs.getString("ENDT").trim();
		
				EmployeeAttendanceData.add(new EmployeeAttendance(BEGINT,ENDT,TYPE));
			}
			//加班
			ps = con.prepareStatement("SELECT * FROM S_SUBSIDY WHERE UID=? AND "
					+ "BEGINT>=? AND "
					+ "ENDT<=?");
			ps.setString(1,UserID);
			ps.setString(2, GetFourBeginTime);
			ps.setString(3, GetFourEndTime);
			rs = ps.executeQuery();
			TYPE="加班";
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
