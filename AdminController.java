package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import com.mysql.jdbc.Connection;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class AdminController {
	//Ա����Ϣ�޸Ŀؼ�
	private ObservableList<AuditModify> AuditModifyData = FXCollections.observableArrayList();
	@FXML private TableView<AuditModify> AuditModifyTable;
	@FXML private TableColumn<AuditModify, String> UIDcolumn;
	@FXML private TableColumn<AuditModify, String> UNameColumn;
	@FXML private TableColumn<AuditModify, String> USexColumn;
	@FXML private TableColumn<AuditModify, String> UYearColumn;
	@FXML private TableColumn<AuditModify, String> UTelColumn;
	@FXML private TableColumn<AuditModify,CheckBox> checklist;
	
	//�������ʱ�
	private ObservableList<ForSecModifyTable> SecModifyData = FXCollections.observableArrayList();
	@FXML private TableView<ForSecModifyTable> SecModifyTable;
	@FXML private TableColumn<ForSecModifyTable, String> SecTIDColumn;
	@FXML private TableColumn<ForSecModifyTable, String> SecTNameColumn;
	@FXML private TableColumn<ForSecModifyTable, String> SecTRankColumn;
	@FXML private TableColumn<ForSecModifyTable, String> SecTSalaryColumn;
	@FXML private TableColumn<ForSecModifyTable, String> SecTSubColumn;
	@FXML private TableColumn<ForSecModifyTable, String> SecDNameColumn;
	@FXML private TableColumn<ForSecModifyTable,CheckBox> Secchecklist;
	
	//�����������ؼ�3
	@FXML private TextField ThrDepartmentID;
	@FXML private TextField ThrEmployeeID;
	@FXML private TextField ThrYear;
	@FXML private TextField ThrMonth;
	//������
	private AutoCompletionBinding<String> textAuto_ThrDepartmentID;
	private AutoCompletionBinding<String> textAuto_ThrEmployeeID;
	private ObservableList<ForThrAttendanceTable> ThrAttendanceData = FXCollections.observableArrayList();
	@FXML private TableView<ForThrAttendanceTable> ThrAttendanceTable;
	@FXML private TableColumn<ForThrAttendanceTable, String> ThrDIDColumn;
	@FXML private TableColumn<ForThrAttendanceTable, String> ThrDNAMEColumn;
	@FXML private TableColumn<ForThrAttendanceTable, String> ThrUIDColumn;
	@FXML private TableColumn<ForThrAttendanceTable, String> ThrTIDColumn;
	@FXML private TableColumn<ForThrAttendanceTable, String> ThrBEGINTIMEColumn;
	@FXML private TableColumn<ForThrAttendanceTable, String> ThrENDTIMEColumn;
	
	//���ʱ�4
	@FXML private TextField FourDepartmentID;
	@FXML private TextField FourEmployeeID;
	@FXML private TextField FourYear;
	@FXML private TextField FourMonth;
	//������
	private AutoCompletionBinding<String> textAuto_FourDepartmentID;
	private AutoCompletionBinding<String> textAuto_FourEmployeeID;
	private ObservableList<ForFourSalaryTable> FourSalaryData = FXCollections.observableArrayList();
	@FXML private TableView<ForFourSalaryTable> FourSalaryTable;
	@FXML private TableColumn<ForFourSalaryTable, String> FourDIDColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourDNAMEColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourUIDColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourYEARColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourMONTHColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourFUNDColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourSUBColumn;
	@FXML private TableColumn<ForFourSalaryTable, String> FourSUMColumn;
	
	//����ҳ��������Ա��
	@FXML private TextField FiveUID;
	@FXML private TextField FiveUNAME;
	@FXML private ChoiceBox FiveSEX;
	@FXML private TextField FiveYEAR;
	@FXML private TextField FiveDEPARTMENT;
	@FXML private TextField FiveTYPE;
	@FXML private TextField FiveTEL;
	@FXML private TextField FivePASSWORD;
	private AutoCompletionBinding<String> textAuto_FiveDEPARTMENT;
	private AutoCompletionBinding<String> textAuto_FiveTYPE;
	
	//����ҳ�����㹤�ʺ����ս�
	@FXML private TextField SixYEAR;
	@FXML private TextField SixMONTH;
	private ObservableList<ForSixCalculateTable> SixCalculateData = FXCollections.observableArrayList();
	@FXML private TableView<ForSixCalculateTable> SixCalculateTable;
	@FXML private TableColumn<ForSixCalculateTable, String> SixDIDColumn;
	@FXML private TableColumn<ForSixCalculateTable, String> SixDNAMEColumn;
	@FXML private TableColumn<ForSixCalculateTable, String> SixUIDColumn;
	@FXML private TableColumn<ForSixCalculateTable, String> SixUNAMEColumn;
	@FXML private TableColumn<ForSixCalculateTable, String> SixSalaryColumn;
	@FXML private TableColumn<ForSixCalculateTable, String> SixBONUSColumn;

	
	private String AdminID;
	
	
	
	@FXML public void initialize() {
		AdminID=LoginController.UserID;
		//��˱�
		UIDcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UIDProperty()));
		UNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UNameProperty()));
		USexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().USexProperty()));
		UYearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UYearProperty()));
		UTelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UTelProperty()));
		
		checklist.setCellValueFactory(cellData ->cellData.getValue().cb.getCheckBox());
		//�����˱�����
		ShowModifyInfo();
		
		//�������ʱ�
		SecTIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().TIDProperty()));
		SecTNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().TNameProperty()));
		SecTRankColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().TRankProperty()));
		SecTSalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().TSalaryProperty()));
		SecTSubColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().TSubProperty()));
		SecDNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().DNameProperty()));
		Secchecklist.setCellValueFactory(cellData ->cellData.getValue().cb1.getCheckBox());
		//���������
		ShowSecSalaryAndSub();
		
		//������Ϣ��3
		//��������Ϣ
		textAuto_ThrDepartmentID=TextFields.bindAutoCompletion(ThrDepartmentID, "");
		textAuto_ThrEmployeeID=TextFields.bindAutoCompletion(ThrEmployeeID, "");
		ThrDIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ThrDIDProperty()));
		ThrDNAMEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ThrDNAMEProperty()));
		ThrUIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ThrUIDProperty()));
		ThrTIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ThrTIDProperty()));
		ThrBEGINTIMEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ThrBEGINTIMEProperty()));
		ThrENDTIMEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ThrENDTIMEProperty()));
		
		
		//����ͳ�Ʊ�4
		textAuto_FourDepartmentID=TextFields.bindAutoCompletion(FourDepartmentID, "");
		textAuto_FourEmployeeID=TextFields.bindAutoCompletion(FourEmployeeID, "");
		FourDIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourDIDProperty()));
		FourUIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourUIDProperty()));
		FourDNAMEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourDNAMEProperty()));
		FourYEARColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourYEARProperty()));
		FourMONTHColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourMONTHProperty()));
		FourFUNDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourFUNDProperty()));
		FourSUBColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourSUBProperty()));
		FourSUMColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().FourSUMProperty()));
		
		//������Ա��5
		textAuto_FiveDEPARTMENT=TextFields.bindAutoCompletion(FiveDEPARTMENT, "");
		textAuto_FiveTYPE=TextFields.bindAutoCompletion(FiveTYPE, "");
		
		//�����6
		SixDIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SixDIDProperty()));
		SixDNAMEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SixDNAMEProperty()));
		SixUIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SixUIDProperty()));
		SixUNAMEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SixUNAMEProperty()));
		SixSalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SixSALARYProperty()));
		SixBONUSColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().SixBONUSProperty()));
		

		
		//SecModifyTable.setEditable(true);
		//SecTSalaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		//SecTSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		//AuditModifyTable.setEditable(true);
		//UIDcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
		//Choose.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().ChooseProperty()));

		//Choose.setCellValueFactory(cellData->cellData.getValue().ChooseProperty());
		//Choose.setCellFactory(new Callback<TableColumn<CustomVO,Boolean>, TableCell<CustomVO,Boolean>>() {

		/*
		AuditModifyTable.setRowFactory( tv -> {
			TableRow<AuditModify> row = new TableRow<AuditModify>();
			row.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
				FirAgreeModify(row);
				  }});
			return row ;
			});
		*/
		/*
		SecModifyTable.setRowFactory( tv -> {
			TableRow<ForSecModifyTable> row = new TableRow<ForSecModifyTable>();
			row.setOnKeyReleased(event -> {
				System.out.println(row.getItem().TSalaryProperty());
				//FirAgreeModify(row);
				  });
			return row ;
			});
		/*
			row.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
				//FirAgreeModify(row);
				  }});
			return row ;
			});*/
		
		
	}
	
	//ͨ�����
	@FXML void FirAgreeModify()
	{
		
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			ObservableList<AuditModify> list=AuditModifyTable.getItems();
	        for(AuditModify o:list)
	        {
	            if(o.cb.isSelected())
	            {
	                //System.out.println(o.name);
	                AuditModify getrow = o;
	    			System.out.println(getrow.UIDProperty());
	    			
	    			//��������
	    			PreparedStatement ps = con.prepareStatement("UPDATE S_EMPLOYEE SET UNAME=?,UYEAR=?,USEX=?,UTEL=? WHERE UID=?");
	    			ps.setString(1, getrow.UNameProperty());
	    			ps.setString(2, getrow.UYearProperty());
	    			ps.setString(3, getrow.USexProperty());
	    			ps.setString(4, getrow.UTelProperty());
	    			ps.setString(5, getrow.UIDProperty());
	    			ps.executeUpdate();
	    			
	    			//ɾ��modify��������
	    			ps = con.prepareStatement("DELETE FROM S_MODIFY WHERE UID=?");
	    			ps.setString(1, getrow.UIDProperty());
	    			ps.executeUpdate();
	            }
	        }
			
			//ˢ�±��
			ShowModifyInfo();

		}catch(Exception e) {
			e.printStackTrace();
		}
		

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
		
	}
	
	//�ܾ����
	@FXML void FirDisAgreeModify()
	{
		
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			ObservableList<AuditModify> list=AuditModifyTable.getItems();
	        for(AuditModify o:list)
	        {
	            if(o.cb.isSelected())
	            {
	                //System.out.println(o.name);
	                AuditModify getrow = o;
	    			System.out.println(getrow.UIDProperty());
	    			
	    			
	    			//ɾ��modify��������
	    			PreparedStatement ps = con.prepareStatement("DELETE FROM S_MODIFY WHERE UID=?");
	    			ps.setString(1, getrow.UIDProperty());
	    			ps.executeUpdate();
	            }
	        }
			
			//ˢ�±��
			ShowModifyInfo();

		}catch(Exception e) {
			e.printStackTrace();
		}
		

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
		
	}
	
	
	void ShowModifyInfo()
	{
		AuditModifyData.clear();
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ѯ
			PreparedStatement ps = con.prepareStatement("SELECT * FROM S_MODIFY");
			ResultSet  rs = ps.executeQuery();
			String FirUID=null;
			String FirUName=null;
			String FirUSex=null;
			String FirUYear=null;
			String FirUTel=null;

			while(rs.next())
			{
				FirUID=rs.getString("UID").trim();
				FirUName=rs.getString("UNAME").trim();
				FirUSex=rs.getString("USEX").trim();
				FirUYear=rs.getString("UYEAR").trim();
				FirUTel=rs.getString("UTEL").trim();
				AuditModifyData.add(new AuditModify(FirUID,FirUName,FirUSex,FirUYear,FirUTel));

			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		AuditModifyTable.setItems(AuditModifyData);

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	//��ʾ�������ʺͽ���
	void ShowSecSalaryAndSub()
	{
		SecModifyData.clear();
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//��ѯ�����Լ��������ʣ�����
			PreparedStatement ps = con.prepareStatement("SELECT TID,TNAME,TRANK,TSALARY,TSUB,DNAME FROM S_TYPE,S_DEPARTMENT WHERE S_TYPE.DID=S_DEPARTMENT.DID");
			ResultSet  rs = ps.executeQuery();
			String SecTID=null;
			String SecTName=null;
			String SecTRank=null;
			String SecTSalary=null;
			String SecTSub=null;
			String SecDname=null;

			while(rs.next())
			{
				SecTID=rs.getString("TID").trim();
				SecTName=rs.getString("TNAME").trim();
				SecTRank=rs.getString("TRANK").trim();
				SecTSalary=rs.getString("TSALARY").trim();
				SecTSub=rs.getString("TSUB").trim();
				SecDname=rs.getString("DNAME").trim();
				SecModifyData.add(new ForSecModifyTable(SecTID,SecTName,SecTRank,SecTSalary,SecTSub,SecDname));

			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		SecModifyTable.setItems(SecModifyData);

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	//�޸Ļ�������
	@FXML void SecModifySalary()
	{
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			ObservableList<ForSecModifyTable> list=SecModifyTable.getItems();
	        for(ForSecModifyTable o:list)
	        {
	            if(o.cb1.isSelected())
	            {
	                //System.out.println(o.name);
	            	ForSecModifyTable getrow = o;
	    			System.out.println(getrow.TIDProperty());
	    			String s=JOptionPane.showInputDialog("����"+getrow.TIDProperty()+"�Ļ�������:");
	    			if(!s.equals(""))
	    			{
		    			PreparedStatement ps = con.prepareStatement("UPDATE S_TYPE SET TSALARY=? WHERE TID=?");
		    			ps.setString(1, s);
		    			ps.setString(2, getrow.TIDProperty());
		    			if(ps.executeUpdate()!=0)
		    				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
		    			else JOptionPane.showMessageDialog(null, "�޸�ʧ��");
	    			}
	    			else JOptionPane.showMessageDialog(null, "������Ч");
	    			
	            }
	        }
			
			//ˢ�±��
			ShowSecSalaryAndSub();

		}catch(Exception e) {
			e.printStackTrace();
		}
		

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	//�޸Ľ���
	@FXML void SecModifySub()
	{
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			ObservableList<ForSecModifyTable> list=SecModifyTable.getItems();
	        for(ForSecModifyTable o:list)
	        {
	            if(o.cb1.isSelected())
	            {
	                //System.out.println(o.name);
	            	ForSecModifyTable getrow = o;
	    			System.out.println(getrow.TIDProperty());
	    			String s=JOptionPane.showInputDialog("����"+getrow.TIDProperty()+"�Ľ���:");
	    			if(!s.equals(""))
	    			{
		    			PreparedStatement ps = con.prepareStatement("UPDATE S_TYPE SET TSUB=? WHERE TID=?");
		    			ps.setString(1, s);
		    			ps.setString(2, getrow.TIDProperty());
		    			if(ps.executeUpdate()!=0)
		    				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
		    			else JOptionPane.showMessageDialog(null, "�޸�ʧ��");
	    			}
	    			else JOptionPane.showMessageDialog(null, "������Ч");
	    			
	            }
	        }
			
			//ˢ�±��
			ShowSecSalaryAndSub();

		}catch(Exception e) {
			e.printStackTrace();
		}
		

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	//�������
	@FXML public void ThrClickDepartment()
	{
		ThrDepartmentID.clear();
		ThrEmployeeID.clear();
		textAuto_ThrDepartmentID.dispose();
		textAuto_ThrEmployeeID.dispose();
		ThrEnterDepartment();
		ThrDepartmentID.setText(" ");
		ThrDepartmentID.end();
	}
	//������
	@FXML public void ThrEnterDepartment()
	{
		/*set up connection */
		Connection myCon = ConnectSQL.connecToMySQL();
		if(myCon == null)
			return;
		/*fetch data*/
		
		    PreparedStatement pStatement = null;
			ResultSet rs = null;
			LinkedList<String> searchResult = new LinkedList<>();
			
			try {
				pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_DEPARTMENT");
				rs = pStatement.executeQuery();
				while(rs.next())
				{
					String str1 = rs.getString("DID").trim();
					String str2 = rs.getString("DNAME").trim();
					String togetherStr = str1  + " " + str2;
					searchResult.add(togetherStr);
				}
				textAuto_ThrDepartmentID=TextFields.bindAutoCompletion(ThrDepartmentID, searchResult);
				//textAutoBingding.dispose();//����󶨹�ϵ
				//TextFields.bindAutoCompletion(keshiName, new LinkedList<>());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		 /*close the connection*/
		   try 
			{
			   myCon.close();
				
			} catch (SQLException e) {
			   e.printStackTrace();
			} 
	}
	
	//�������
	@FXML public void ThrClickEmployee()
	{
		
		//textAuto_ThrEmployeeID.dispose();
		ThrEnterEmployee();
		ThrEmployeeID.setText(" ");
		ThrEmployeeID.end();
	}
	//Ա�����������
	@FXML public void ThrEnterEmployee()
	{
		/*set up connection */
		Connection myCon = ConnectSQL.connecToMySQL();
		if(myCon == null)
			return;
		/*fetch data*/
		
		    PreparedStatement pStatement = null;
			ResultSet rs = null;
			LinkedList<String> searchResult = new LinkedList<>();
			
			try {
				pStatement=(PreparedStatement) myCon.prepareStatement("SELECT UID,UNAME FROM S_EMPLOYEE,S_TYPE "
						+ "WHERE S_TYPE.DID=? AND S_EMPLOYEE.TID=S_TYPE.TID");
				pStatement.setString(1, ThrDepartmentID.getText().toString().substring(0,6));
				rs = pStatement.executeQuery();
				while(rs.next())
				{
					String str1 = rs.getString("UID").trim();
					String str2 = rs.getString("UNAME").trim();
					String togetherStr = str1  + " " + str2;
					searchResult.add(togetherStr);
				}
				textAuto_ThrEmployeeID=TextFields.bindAutoCompletion(ThrEmployeeID, searchResult);
				//textAutoBingding.dispose();//����󶨹�ϵ
				//TextFields.bindAutoCompletion(keshiName, new LinkedList<>());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		 /*close the connection*/
		   try 
			{
			   myCon.close();
				
			} catch (SQLException e) {
			   e.printStackTrace();
			} 
	}
	
	//��ѯ������Ϣ
	@FXML public void ThrQueryAttendance()
	{
		ThrAttendanceData.clear();
		Connection con=null;
		try {
			//�������ݿ�
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			
			String ThrDepartInput;
			String ThrEmployeeInput;
			String ThrYearInput=ThrYear.getText();
			String ThrMonthInput=ThrMonth.getText();
			//��֤�·�Ϊ2λ�ַ���
			if(ThrMonthInput.length()==1)
				ThrMonthInput="0"+ThrMonthInput;
			
			PreparedStatement ps1,ps2;
			//��ȡ���������
			if(ThrDepartmentID.getText().equals("") )
			{
				JOptionPane.showMessageDialog(null, "������Ч");
				return;
			}
			ThrDepartInput=ThrDepartmentID.getText().substring(0,6);
			if(ThrEmployeeID.getText().equals(""))
			{
				ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID,S_DEPARTMENT.DNAME,S_EMPLOYEE.UID,S_TYPE.TID,S_ATTENDANCE.BEGINT,S_ATTENDANCE.ENDT "
						+ "FROM S_EMPLOYEE,S_ATTENDANCE,S_TYPE,S_DEPARTMENT "
						+ "WHERE S_EMPLOYEE.TID=S_TYPE.TID AND "
						+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
						+ "S_ATTENDANCE.UID=S_EMPLOYEE.UID AND "
						+ "S_ATTENDANCE.SIGNOUT=1 AND "
						+ "S_DEPARTMENT.DID=?");
				ps1.setString(1, ThrDepartInput);
				ps2 = con.prepareStatement("SELECT S_DEPARTMENT.DID,S_DEPARTMENT.DNAME,S_EMPLOYEE.UID,S_TYPE.TID,S_SUBSIDY.BEGINT,S_SUBSIDY.ENDT "
						+ "FROM S_EMPLOYEE,S_SUBSIDY,S_TYPE,S_DEPARTMENT "
						+ "WHERE S_EMPLOYEE.TID=S_TYPE.TID AND "
						+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
						+ "S_SUBSIDY.UID=S_EMPLOYEE.UID AND "
						+ "S_SUBSIDY.SIGNOUT=1 AND "
						+ "S_DEPARTMENT.DID=? ");
				ps2.setString(1, ThrDepartInput);
			}
			else
			{
				ThrEmployeeInput=ThrEmployeeID.getText().substring(0,6);
				ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID,S_DEPARTMENT.DNAME,S_EMPLOYEE.UID,S_TYPE.TID,S_ATTENDANCE.BEGINT,S_ATTENDANCE.ENDT "
						+ "FROM S_EMPLOYEE,S_ATTENDANCE,S_TYPE,S_DEPARTMENT "
						+ "WHERE S_EMPLOYEE.TID=S_TYPE.TID AND "
						+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
						+ "S_ATTENDANCE.UID=S_EMPLOYEE.UID AND "
						+ "S_ATTENDANCE.SIGNOUT=1 AND "
						+ "S_DEPARTMENT.DID=? AND "
						+ "S_EMPLOYEE.UID=?");
				ps1.setString(1, ThrDepartInput);
				ps1.setString(2, ThrEmployeeInput);
				ps2 = con.prepareStatement("SELECT S_DEPARTMENT.DID,S_DEPARTMENT.DNAME,S_EMPLOYEE.UID,S_TYPE.TID,S_SUBSIDY.BEGINT,S_SUBSIDY.ENDT "
						+ "FROM S_EMPLOYEE,S_SUBSIDY,S_TYPE,S_DEPARTMENT "
						+ "WHERE S_EMPLOYEE.TID=S_TYPE.TID AND "
						+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
						+ "S_SUBSIDY.UID=S_EMPLOYEE.UID AND "
						+ "S_SUBSIDY.SIGNOUT=1 AND "
						+ "S_DEPARTMENT.DID=? AND "
						+ "S_EMPLOYEE.UID=?");
				ps2.setString(1, ThrDepartInput);
				ps2.setString(2, ThrEmployeeInput);
			}
			
			//��ѯ
			ResultSet  rs1 = ps1.executeQuery();
			ResultSet  rs2 = ps2.executeQuery();
			String ThrDID=null;
			String ThrDNAME=null;
			String ThrUID=null;
			String ThrTID=null;
			String ThrBEGINTIME=null;
			String ThrENDTIME=null;

			while(rs1.next())
			{
				ThrDID=rs1.getString("DID").trim();
				ThrDNAME=rs1.getString("DNAME").trim();
				ThrUID=rs1.getString("UID").trim();
				ThrTID=rs1.getString("TID").trim();
				ThrBEGINTIME=rs1.getString("BEGINT").trim();
				ThrENDTIME=rs1.getString("ENDT").trim();
				//�������Ϊ��,�������ɸѡ
				if(ThrYearInput.equals(""))
					ThrAttendanceData.add(new ForThrAttendanceTable(ThrDID,ThrDNAME,ThrUID,ThrTID,ThrBEGINTIME,ThrENDTIME));
				else	//��ݲ�Ϊ��
				{
					//�·�Ϊ��,�������������ͬ����data
					if(ThrMonthInput.equals("") && ThrBEGINTIME.substring(0,4).equals(ThrYearInput))
						ThrAttendanceData.add(new ForThrAttendanceTable(ThrDID,ThrDNAME,ThrUID,ThrTID,ThrBEGINTIME,ThrENDTIME));
					//�·ݲ�Ϊ��,���¾�������һ��
					else if(!ThrMonthInput.equals("") && ThrBEGINTIME.substring(0,4).equals(ThrYearInput)&&ThrBEGINTIME.substring(5,7).equals(ThrMonthInput))
						ThrAttendanceData.add(new ForThrAttendanceTable(ThrDID,ThrDNAME,ThrUID,ThrTID,ThrBEGINTIME,ThrENDTIME));
				}
				

			}
			while(rs2.next())
			{
				ThrDID=rs2.getString("DID").trim();
				ThrDNAME=rs2.getString("DNAME").trim();
				ThrUID=rs2.getString("UID").trim();
				ThrTID=rs2.getString("TID").trim();
				ThrBEGINTIME=rs2.getString("BEGINT").trim();
				ThrENDTIME=rs2.getString("ENDT").trim();
				
				//�������Ϊ��,�������ɸѡ
				if(ThrYearInput.equals(""))
					ThrAttendanceData.add(new ForThrAttendanceTable(ThrDID,ThrDNAME,ThrUID,ThrTID,ThrBEGINTIME,ThrENDTIME));
				else	//��ݲ�Ϊ��
				{
					//�·�Ϊ��,�������������ͬ����data
					if(ThrMonthInput.equals("") && ThrBEGINTIME.substring(0,4).equals(ThrYearInput))
						ThrAttendanceData.add(new ForThrAttendanceTable(ThrDID,ThrDNAME,ThrUID,ThrTID,ThrBEGINTIME,ThrENDTIME));
					//�·ݲ�Ϊ��,���¾�������һ��
					else if(!ThrMonthInput.equals("") && ThrBEGINTIME.substring(0,4).equals(ThrYearInput)&&ThrBEGINTIME.substring(5,7).equals(ThrMonthInput))
						ThrAttendanceData.add(new ForThrAttendanceTable(ThrDID,ThrDNAME,ThrUID,ThrTID,ThrBEGINTIME,ThrENDTIME));
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ThrAttendanceTable.setItems(ThrAttendanceData);

		try {
		   con.close();
		} catch (SQLException e) {
		   e.printStackTrace();
		}  
	}
	
	//����ҳ ����ͳ�Ʊ�
	//�������
		@FXML public void FourClickDepartment()
		{
			FourDepartmentID.clear();
			FourEmployeeID.clear();
			textAuto_FourDepartmentID.dispose();
			textAuto_FourEmployeeID.dispose();
			FourEnterDepartment();
			FourDepartmentID.setText(" ");
			FourDepartmentID.end();
		}
		//������
		@FXML public void FourEnterDepartment()
		{
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			
			    PreparedStatement pStatement = null;
				ResultSet rs = null;
				LinkedList<String> searchResult = new LinkedList<>();
				
				try {
					pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_DEPARTMENT");
					rs = pStatement.executeQuery();
					while(rs.next())
					{
						String str1 = rs.getString("DID").trim();
						String str2 = rs.getString("DNAME").trim();
						String togetherStr = str1  + " " + str2;
						searchResult.add(togetherStr);
					}
					textAuto_FourDepartmentID=TextFields.bindAutoCompletion(FourDepartmentID, searchResult);
					//textAutoBingding.dispose();//����󶨹�ϵ
					//TextFields.bindAutoCompletion(keshiName, new LinkedList<>());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			 /*close the connection*/
			   try 
				{
				   myCon.close();
					
				} catch (SQLException e) {
				   e.printStackTrace();
				} 
		}
		
		//�������
		@FXML public void FourClickEmployee()
		{
			
			//textAuto_ThrEmployeeID.dispose();
			FourEnterEmployee();
			FourEmployeeID.setText(" ");
			FourEmployeeID.end();
		}
		//Ա�����������
		@FXML public void FourEnterEmployee()
		{
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			
			    PreparedStatement pStatement = null;
				ResultSet rs = null;
				LinkedList<String> searchResult = new LinkedList<>();
				
				try {
					pStatement=(PreparedStatement) myCon.prepareStatement("SELECT UID,UNAME FROM S_EMPLOYEE,S_TYPE "
							+ "WHERE S_TYPE.DID=? AND S_EMPLOYEE.TID=S_TYPE.TID");
					pStatement.setString(1, FourDepartmentID.getText().toString().substring(0,6));
					rs = pStatement.executeQuery();
					while(rs.next())
					{
						String str1 = rs.getString("UID").trim();
						String str2 = rs.getString("UNAME").trim();
						String togetherStr = str1  + " " + str2;
						searchResult.add(togetherStr);
					}
					textAuto_FourEmployeeID=TextFields.bindAutoCompletion(FourEmployeeID, searchResult);
					//textAutoBingding.dispose();//����󶨹�ϵ
					//TextFields.bindAutoCompletion(keshiName, new LinkedList<>());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			 /*close the connection*/
			   try 
				{
				   myCon.close();
					
				} catch (SQLException e) {
				   e.printStackTrace();
				} 
		}
		
		@FXML public void FourQuerySalary()
		{
			FourSalaryData.clear();
			Connection con=null;
			try {
				//�������ݿ�
				con = ConnectSQL.connecToMySQL();
				if(con==null)
					return;
				
				String FourDepartInput;
				String FourEmployeeInput;
				String FourYearInput=FourYear.getText();
				String FourMonthInput=FourMonth.getText();
				PreparedStatement ps1;
				//��ȡ���������
				if(FourDepartmentID.getText().equals("") )
				{
					JOptionPane.showMessageDialog(null, "������Ч");
					return;
				}
				FourDepartInput=FourDepartmentID.getText().substring(0,6);
				
				//���Ϊ��,��ɸѡʱ��
				if(FourYearInput.equals(""))
				{
					//Ա������Ϊ��
					if(FourEmployeeID.getText().equals("") || FourEmployeeID.getText().equals(" "))
					{
						ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID, S_DEPARTMENT.DNAME, S_SALARY.UID, S_SALARY.PYEAR,S_SALARY.PMONTH,S_SALARY.PFUND,S_SALARY.PFUND2,S_SALARY.PSUM "
								+ "FROM S_SALARY,S_EMPLOYEE,S_TYPE,S_DEPARTMENT "
								+ "WHERE S_SALARY.UID=S_EMPLOYEE.UID AND "
								+ "S_EMPLOYEE.TID=S_TYPE.TID AND "
								+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
								+ "S_DEPARTMENT.DID=?");
						ps1.setString(1, FourDepartInput);
						
					}
					//���벿�ź�Ա��
					else
					{
						FourEmployeeInput=FourEmployeeID.getText().substring(0,6);
						ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID, S_DEPARTMENT.DNAME, S_SALARY.UID, S_SALARY.PYEAR,S_SALARY.PMONTH,S_SALARY.PFUND,S_SALARY.PFUND2,S_SALARY.PSUM "
								+ "FROM S_SALARY,S_EMPLOYEE,S_TYPE,S_DEPARTMENT "
								+ "WHERE S_SALARY.UID=S_EMPLOYEE.UID AND "
								+ "S_EMPLOYEE.TID=S_TYPE.TID AND "
								+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
								+ "S_TYPE.DID=? AND "
								+ "S_EMPLOYEE.UID=?");
						ps1.setString(1, FourDepartInput);
						ps1.setString(2, FourEmployeeInput);
						
					}
				}
				//��ݲ�Ϊ��
				else
				{
					//��Ϊ��,ֻɸѡ���
					if(FourMonthInput.equals(""))
					{
						//Ա������Ϊ��
						if(FourEmployeeID.getText().equals("") || FourEmployeeID.getText().equals(" "))
						{
							ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID, S_DEPARTMENT.DNAME, S_SALARY.UID, S_SALARY.PYEAR,S_SALARY.PMONTH,S_SALARY.PFUND,S_SALARY.PFUND2,S_SALARY.PSUM "
									+ "FROM S_SALARY,S_EMPLOYEE,S_TYPE,S_DEPARTMENT "
									+ "WHERE S_SALARY.UID=S_EMPLOYEE.UID AND "
									+ "S_EMPLOYEE.TID=S_TYPE.TID AND "
									+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
									+ "S_DEPARTMENT.DID=? AND "
									+ "S_SALARY.PYEAR=?");
							ps1.setString(1, FourDepartInput);
							ps1.setString(2, FourYearInput);
						}
						//���벿�ź�Ա��
						else
						{
							FourEmployeeInput=FourEmployeeID.getText().substring(0,6);
							ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID, S_DEPARTMENT.DNAME, S_SALARY.UID, S_SALARY.PYEAR,S_SALARY.PMONTH,S_SALARY.PFUND,S_SALARY.PFUND2,S_SALARY.PSUM "
									+ "FROM S_SALARY,S_EMPLOYEE,S_TYPE,S_DEPARTMENT "
									+ "WHERE S_SALARY.UID=S_EMPLOYEE.UID AND "
									+ "S_EMPLOYEE.TID=S_TYPE.TID AND "
									+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
									+ "S_TYPE.DID=? AND "
									+ "S_EMPLOYEE.UID=? AND "
									+ "S_SALARY.PYEAR=?");
							ps1.setString(1, FourDepartInput);
							ps1.setString(2, FourEmployeeInput);
							ps1.setString(3, FourYearInput);
						}
					}
					else	//ɸѡ�����
					{
						//Ա������Ϊ��
						if(FourEmployeeID.getText().equals("") || FourEmployeeID.getText().equals(" "))
						{
							ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID, S_DEPARTMENT.DNAME, S_SALARY.UID, S_SALARY.PYEAR,S_SALARY.PMONTH,S_SALARY.PFUND,S_SALARY.PFUND2,S_SALARY.PSUM "
									+ "FROM S_SALARY,S_EMPLOYEE,S_TYPE,S_DEPARTMENT "
									+ "WHERE S_SALARY.UID=S_EMPLOYEE.UID AND "
									+ "S_EMPLOYEE.TID=S_TYPE.TID AND "
									+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
									+ "S_DEPARTMENT.DID=? AND "
									+ "S_SALARY.PYEAR=? AND "
									+ "S_SALARY.PMONTH=?");
							ps1.setString(1, FourDepartInput);
							ps1.setString(2, FourYearInput);
							ps1.setString(3, FourMonthInput);
						}
						//���벿�ź�Ա��
						else
						{
							FourEmployeeInput=FourEmployeeID.getText().substring(0,6);
							ps1 = con.prepareStatement("SELECT S_DEPARTMENT.DID, S_DEPARTMENT.DNAME, S_SALARY.UID, S_SALARY.PYEAR,S_SALARY.PMONTH,S_SALARY.PFUND,S_SALARY.PFUND2,S_SALARY.PSUM "
									+ "FROM S_SALARY,S_EMPLOYEE,S_TYPE,S_DEPARTMENT "
									+ "WHERE S_SALARY.UID=S_EMPLOYEE.UID AND "
									+ "S_EMPLOYEE.TID=S_TYPE.TID AND "
									+ "S_TYPE.DID=S_DEPARTMENT.DID AND "
									+ "S_TYPE.DID=? AND "
									+ "S_EMPLOYEE.UID=? AND "
									+ "S_SALARY.PYEAR=? AND "
									+ "S_SALARY.PMONTH=?");
							ps1.setString(1, FourDepartInput);
							ps1.setString(2, FourEmployeeInput);
							ps1.setString(3, FourYearInput);
							ps1.setString(4, FourMonthInput);
						}
					}
				}
				
				
				//��ѯ
				ResultSet  rs1 = ps1.executeQuery();
				String FourDID=null;
				String FourDNAME=null;
				String FourUID=null;
				String FourYEAR=null;
				String FourMONTH=null;
				String FourFUND=null;
				String FourFUND2=null;
				String FourSUM=null;

				while(rs1.next())
				{
					FourDID=rs1.getString("DID").trim();
					FourDNAME=rs1.getString("DNAME").trim();
					FourUID=rs1.getString("UID").trim();
					FourYEAR=rs1.getString("PYEAR").trim();
					FourMONTH=rs1.getString("PMONTH").trim();
					FourFUND=rs1.getString("PFUND").trim();
					FourFUND2=rs1.getString("PFUND2").trim();
					FourSUM=rs1.getString("PSUM").trim();
					FourSalaryData.add(new ForFourSalaryTable(FourDID,FourDNAME,FourUID,FourYEAR,FourMONTH,FourFUND,FourFUND2,FourSUM));

				}
				

			}catch(Exception e) {
				e.printStackTrace();
			}
			
			FourSalaryTable.setItems(FourSalaryData);

			try {
			   con.close();
			} catch (SQLException e) {
			   e.printStackTrace();
			}  
		}
		
		
		//����ҳ ������Ա��
		
		//�����ȡԱ�����
		@FXML public void FiveGetNewUID()
		{
			Connection con=null;
			try {
				//�������ݿ�
				con = ConnectSQL.connecToMySQL();
				if(con==null)
					return;
				
				PreparedStatement ps = con.prepareStatement("SELECT MAX(UID) FROM S_EMPLOYEE ");
				ResultSet  rs = ps.executeQuery();
				rs.next();
				String getUID=rs.getString("MAX(UID)");
				
				long LongUID=Long.parseLong(getUID);
				String StringUID=(LongUID+1)+"";
				//ת��Ϊ6Ϊ�ַ���
				while(StringUID.length()<6)
				{
					StringUID="0"+StringUID;
				}
				FiveUID.setText(StringUID);
				FiveUID.setDisable(true);
				FivePASSWORD.setText("1234");
				FivePASSWORD.setDisable(true);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
			   con.close();
			} catch (SQLException e) {
			   e.printStackTrace();
			}  
		}
		
		//���������б�
		//�������
		@FXML public void FiveClickDepartment()
		{
			FiveDEPARTMENT.clear();
			FiveTYPE.clear();
			textAuto_FiveDEPARTMENT.dispose();
			textAuto_FiveTYPE.dispose();
			FiveEnterDepartment();
			FiveDEPARTMENT.setText(" ");
			FiveDEPARTMENT.end();
		}
		//������
		private void FiveEnterDepartment()
		{
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			
			    PreparedStatement pStatement = null;
				ResultSet rs = null;
				LinkedList<String> searchResult = new LinkedList<>();
				
				try {
					pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_DEPARTMENT");
					rs = pStatement.executeQuery();
					while(rs.next())
					{
						String str1 = rs.getString("DID").trim();
						String str2 = rs.getString("DNAME").trim();
						String togetherStr = str1  + " " + str2;
						searchResult.add(togetherStr);
					}
					textAuto_FiveDEPARTMENT=TextFields.bindAutoCompletion(FiveDEPARTMENT, searchResult);
					//textAutoBingding.dispose();//����󶨹�ϵ
					//TextFields.bindAutoCompletion(keshiName, new LinkedList<>());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			 /*close the connection*/
			   try 
				{
				   myCon.close();
					
				} catch (SQLException e) {
				   e.printStackTrace();
				} 
		}
		
		//���������б�
		//�������
		@FXML public void FiveClickType()
		{
			
			//textAuto_ThrEmployeeID.dispose();
			FiveEnterType();
			FiveTYPE.setText(" ");
			FiveTYPE.end();
		}
		//Ա�����������
		private void FiveEnterType()
		{
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			
			    PreparedStatement pStatement = null;
				ResultSet rs = null;
				LinkedList<String> searchResult = new LinkedList<>();
				
				try {
					pStatement=(PreparedStatement) myCon.prepareStatement("SELECT TID,TNAME,TRANK FROM S_TYPE "
							+ "WHERE S_TYPE.DID=?");
					pStatement.setString(1, FiveDEPARTMENT.getText().toString().substring(0,6));
					rs = pStatement.executeQuery();
					while(rs.next())
					{
						String str1 = rs.getString("TID").trim();
						String str2 = rs.getString("TNAME").trim();
						String str3 = rs.getString("TRANK").trim() +"��";
								
						String togetherStr = str1  + " " + str2 + " " +str3;
						searchResult.add(togetherStr);
					}
					textAuto_FiveTYPE=TextFields.bindAutoCompletion(FiveTYPE, searchResult);
					//textAutoBingding.dispose();//����󶨹�ϵ
					//TextFields.bindAutoCompletion(keshiName, new LinkedList<>());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			 /*close the connection*/
			   try 
				{
				   myCon.close();
					
				} catch (SQLException e) {
				   e.printStackTrace();
				} 
		}
		
		//���
		@FXML public void FiveCleanUp()
		{
			FiveUID.clear();
			FiveUID.setDisable(false);
			FiveUNAME.clear();
			FiveYEAR.clear();
			FiveDEPARTMENT.clear();
			FiveTYPE.clear();
			FiveTEL.clear();
			FivePASSWORD.clear();
		}
		
		//ȷ��������Ա��
		@FXML public void FiveClickButton()
		{
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			
			PreparedStatement ps = null;
				
			try {
				ps=(PreparedStatement) myCon.prepareStatement("INSERT INTO S_EMPLOYEE VALUES(?,?,?,?,?,?,?)");
				ps.setString(1, FiveUID.getText().trim());
				ps.setString(2, FiveUNAME.getText().trim());
				ps.setString(3, FiveYEAR.getText().trim());
				ps.setString(4, FiveSEX.getValue().toString());
				ps.setString(5, FiveTYPE.getText().substring(0,6));
				ps.setString(6, FiveTEL.getText().trim());
				ps.setString(7, FivePASSWORD.getText().trim());
				if(ps.executeUpdate()!=0)
					JOptionPane.showMessageDialog(null, "����ɹ���");
				else
					JOptionPane.showMessageDialog(null, "����ʧ��");
				FiveCleanUp();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
			 /*close the connection*/
			try 
			{
				myCon.close();
					
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		
		//�����¹���
		@FXML public void SixClickSalary()
		{
			SixSalaryColumn.setText("�¹���");
			SixBONUSColumn.setVisible(false);
			SixCalculateData.clear();
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			
			String GetYear= SixYEAR.getText();
			String GetMonth= SixMONTH.getText();
			if(GetYear.length()!=4 || GetMonth.length()>2 || GetMonth.length()==0)
			{
				JOptionPane.showMessageDialog(null, "�������");
				return;
			}
			if(GetMonth.length()==1)
				GetMonth="0"+GetMonth;
			
			PreparedStatement ps1,ps2,ps3,ps4,ps5;
			ResultSet rs1,rs2,rs3,rs4;
			
			try {
				//����ѡ��UID
				ps1=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_EMPLOYEE");
				rs1=ps1.executeQuery();
				while(rs1.next())
				{
					int CalculateSalary1=0;
					int CalculateSalary2=0;
					int SalaryPerHour;
					int SubPerHour;
					//ȡ�ø�UID��Ӧ�Ļ������ʺͽ���ʱн
					ps3=(PreparedStatement) myCon.prepareStatement("SELECT TSALARY,TSUB FROM S_TYPE WHERE S_TYPE.TID=?");
					ps3.setString(1, rs1.getString("TID"));
					rs3=ps3.executeQuery();
					rs3.next();
					SalaryPerHour=Integer.parseInt(rs3.getString("TSALARY"));
					SubPerHour=Integer.parseInt(rs3.getString("TSUB"));
					
					//ѡ��ÿ��UID��ǩ��ʱ��
					ps2=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_ATTENDANCE WHERE UID=? AND SIGNOUT=1");
					ps2.setString(1, rs1.getString("UID"));
					rs2=ps2.executeQuery();
					while(rs2.next())
					{
						if(rs2.getString("BEGINT").substring(0,7).equals(GetYear+"-"+GetMonth) )
						{
							//���㹤��
							int Hours=Integer.parseInt(rs2.getString("ENDT").substring(11,13))-Integer.parseInt(rs2.getString("BEGINT").substring(11,13));
							CalculateSalary1+=Hours*SalaryPerHour;
						}
					}
					
					//�Ӱ���������������
					ps4=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_SUBSIDY WHERE UID=? AND SIGNOUT=1");
					ps4.setString(1, rs1.getString("UID"));
					rs4=ps4.executeQuery();
					while(rs4.next())
					{
						if(rs4.getString("BEGINT").substring(0,7).equals(GetYear+"-"+GetMonth) )
						{
							//���㹤��
							int Hours=Integer.parseInt(rs4.getString("ENDT").substring(11,13))-Integer.parseInt(rs4.getString("BEGINT").substring(11,13));
							CalculateSalary2+=Hours*SubPerHour;
						}
					}
					
					//��ʾ�����
					//ȡ����Ӧ������Ϣ
					ps3=(PreparedStatement) myCon.prepareStatement("SELECT S_DEPARTMENT.DID,DNAME FROM S_TYPE,S_DEPARTMENT"
							+ " WHERE S_TYPE.TID=? AND "
							+ " S_TYPE.DID=S_DEPARTMENT.DID");
					ps3.setString(1, rs1.getString("TID"));
					rs3=ps3.executeQuery();
					rs3.next();
					
					SixCalculateData.add(new ForSixCalculateTable(rs3.getString("DID"),rs3.getString("DNAME"),
							rs1.getString("UID"),rs1.getString("UNAME"),(CalculateSalary1+CalculateSalary2)+"",""));
					
					//�ж��Ƿ��Ѿ����ڸ��¹������
					ps3=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_SALARY WHERE UID=? AND PYEAR=? AND PMONTH=?");
					ps3.setString(1, rs1.getString("UID"));
					ps3.setString(2, GetYear);
					ps3.setString(3, GetMonth);
					rs3=ps3.executeQuery();
					//���ڸ��¹������
					if(rs3.next())
					{
						ps5=(PreparedStatement) myCon.prepareStatement("UPDATE S_SALARY SET PFUND=?,PFUND2=?,PSUM=? "
								+ "WHERE UID=? AND PYEAR=? AND PMONTH=? ");
						ps5.setString(1, CalculateSalary1+"");
						ps5.setString(2, CalculateSalary2+"");
						ps5.setString(3, (CalculateSalary1+CalculateSalary2)+"");
						ps5.setString(4, rs1.getString("UID"));
						ps5.setString(5, GetYear);
						ps5.setString(6, GetMonth);
					}
					else	//������
					{
						ps5=(PreparedStatement) myCon.prepareStatement("INSERT INTO S_SALARY VALUES(?,?,?,?,?,?)");
						ps5.setString(1, rs1.getString("UID"));
						ps5.setString(2, GetYear);
						ps5.setString(3, GetMonth);
						ps5.setString(4, CalculateSalary1+"");
						ps5.setString(5, CalculateSalary2+"");
						ps5.setString(6, (CalculateSalary1+CalculateSalary2)+"");
					}
					//���뵽���ݿ�
					
					ps5.executeUpdate();
					
				}
				
				SixCalculateTable.setItems(SixCalculateData);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
			 /*close the connection*/
			try 
			{
				myCon.close();
					
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		//�������ս�
		@FXML public void SixCalculateBonus()
		{
			/*set up connection */
			Connection myCon = ConnectSQL.connecToMySQL();
			if(myCon == null)
				return;
			/*fetch data*/
			SixSalaryColumn.setText("�깤��");
			SixBONUSColumn.setVisible(true);
			SixCalculateData.clear();
			
			String GetYear= SixYEAR.getText();
			
			PreparedStatement ps1,ps2,ps3;
			ResultSet rs1,rs2,rs3;
				
			try {
				//ȡ��ÿ��UID
				ps1=(PreparedStatement) myCon.prepareStatement("SELECT * FROM S_EMPLOYEE");
				rs1=ps1.executeQuery();
				while(rs1.next())
				{
					int Bonus=0;
					//����UID�����ս�
					ps2=(PreparedStatement) myCon.prepareStatement("SELECT PSUM FROM S_SALARY "
							+ "WHERE UID=? AND "
							+ "PYEAR=?");
					ps2.setString(1, rs1.getString("UID"));
					ps2.setString(2, GetYear);
					rs2=ps2.executeQuery();
					while(rs2.next())
					{
						Bonus+=Integer.parseInt(rs2.getString("PSUM"));
					}
					int temp=Bonus;
					Bonus=Bonus/12;
					
					//��ʾ�����
					ps3=(PreparedStatement) myCon.prepareStatement("SELECT S_DEPARTMENT.DID,DNAME FROM S_TYPE,S_DEPARTMENT"
							+ " WHERE S_TYPE.TID=? AND "
							+ " S_TYPE.DID=S_DEPARTMENT.DID");
					ps3.setString(1, rs1.getString("TID"));
					rs3=ps3.executeQuery();
					rs3.next();
					
					SixCalculateData.add(new ForSixCalculateTable(rs3.getString("DID"),rs3.getString("DNAME"),
							rs1.getString("UID"),rs1.getString("UNAME"),temp+"",Bonus+""));
					
				}
				
				SixCalculateTable.setItems(SixCalculateData);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
			 /*close the connection*/
			try 
			{
				myCon.close();
					
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
}
