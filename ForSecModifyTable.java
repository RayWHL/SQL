package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ForSecModifyTable {
	private final StringProperty TID;
	private final StringProperty TName;
	private final StringProperty TRank;
	private StringProperty TSalary;
	private StringProperty TSub;
	private final StringProperty DName;
	public checkbox cb1=new checkbox();
	
	public ForSecModifyTable(String TID,String TName,String TRank, String TSalary, String TSub, String DName)
	{
		this.TID = new SimpleStringProperty(TID);
		this.TName = new SimpleStringProperty(TName);
		this.TRank = new SimpleStringProperty(TRank);
		this.TSalary = new SimpleStringProperty(TSalary);
		this.TSub = new SimpleStringProperty(TSub);
		this.DName = new SimpleStringProperty(DName);
	}
	
	public String TIDProperty() {
		return TID.get();
	}
	
	public String TNameProperty() {
		return TName.get();
	}
	
	public String TRankProperty() {
		return TRank.get();
	}

	public String TSalaryProperty() {
		return TSalary.get();
	}

	public String TSubProperty() {
		return TSub.get();
	}
	public String DNameProperty() {
		return DName.get();
	}
}
