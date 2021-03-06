package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuditModify {
	private final StringProperty UID;
	private final StringProperty UName;
	private final StringProperty USex;
	private final StringProperty UYear;
	private final StringProperty UTel;
	public checkbox cb=new checkbox();
	
	public AuditModify(String UID,String UName,String USex, String UYear, String UTel )
	{
		this.UID = new SimpleStringProperty(UID);
		this.UName = new SimpleStringProperty(UName);
		this.USex = new SimpleStringProperty(USex);
		this.UYear = new SimpleStringProperty(UYear);
		this.UTel = new SimpleStringProperty(UTel);
	}
	
	public String UIDProperty() {
		return UID.get();
	}
	
	public String UNameProperty() {
		return UName.get();
	}
	
	public String USexProperty() {
		return USex.get();
	}
	
	public String UYearProperty() {
		return UYear.get();
	}
	
	public String UTelProperty() {
		return UTel.get();
	}

}
