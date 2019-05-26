package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeAttendance {
	private final StringProperty BEGINT;
	private final StringProperty ENDT;
	private final StringProperty TYPE;
	
	public EmployeeAttendance(String BEGINT,String ENDT,String TYPE)
	{
		this.BEGINT = new SimpleStringProperty(BEGINT);
		this.ENDT = new SimpleStringProperty(ENDT);
		this.TYPE = new SimpleStringProperty(TYPE);
		
	}
	
	public String BEGINTProperty() {
		return BEGINT.get();
	}
	
	public String ENDTProperty() {
		return ENDT.get();
	}
	
	public String TYPEProperty() {
		return TYPE.get();
	}
}
