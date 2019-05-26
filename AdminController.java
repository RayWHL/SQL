package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class AdminController {
	//员工信息修改控件
	private ObservableList<AuditModify> AuditModifyData = FXCollections.observableArrayList();
	@FXML private TableView<AuditModify> AuditModifyTable;
	@FXML private TableColumn<AuditModify, String> UIDcolumn;
	@FXML private TableColumn<AuditModify, String> UNameColumn;
	@FXML private TableColumn<AuditModify, String> USexColumn;
	@FXML private TableColumn<AuditModify, String> UYearColumn;
	@FXML private TableColumn<AuditModify, String> UTelColumn;
	@FXML private TableColumn<AuditModify,Boolean> Choose;
	
	//@FXML private Button FirAgreeButton ;
	
	private String AdminID;
	
	
	
	@FXML public void initialize() {
		AdminID=LoginController.UserID;
		
		UIDcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UIDProperty()));
		UNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UNameProperty()));
		USexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().USexProperty()));
		UYearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UYearProperty()));
		UTelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().UTelProperty()));
		
		Choose.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().ChooseProperty()));

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
		
		
		ShowModifyInfo();
	}
	
	/*
	void FirAgreeModify(TableRow<AuditModify> row)
	{
		
		AuditModify getrow = row.getItem();
		System.out.println(getrow.UIDProperty());
		
		Connection con=null;
		try {
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//更新数据
			PreparedStatement ps = con.prepareStatement("UPDATE S_EMPLOYEE SET UNAME=?,UYEAR=?,USEX=?,UTEL=? WHERE UID=?");
			ps.setString(1, getrow.UNameProperty());
			ps.setString(2, getrow.UYearProperty());
			ps.setString(3, getrow.USexProperty());
			ps.setString(4, getrow.UTelProperty());
			ps.setString(5, getrow.UIDProperty());
			ps.executeUpdate();
			
			//删除modify表中数据
			ps = con.prepareStatement("DELETE FROM S_MODIFY WHERE UID=?");
			ps.setString(1, getrow.UIDProperty());
			ps.executeUpdate();
			
			//刷新表格
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
	*/
	
	
	
	void ShowModifyInfo()
	{
		AuditModifyData.clear();
		Connection con=null;
		try {
			//连接数据库
			con = ConnectSQL.connecToMySQL();
			if(con==null)
				return;
			//查询ID对应姓名
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
				AuditModifyData.add(new AuditModify(FirUID,FirUName,FirUSex,FirUYear,FirUTel,false));

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
	
	
	
}
