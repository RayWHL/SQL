package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ForThrAttendanceTable {
	private final StringProperty ThrDID;
	private final StringProperty ThrDNAME;
	private final StringProperty ThrUID;
	private StringProperty ThrTID;
	private StringProperty ThrBEGINTIME;
	private final StringProperty ThrENDTIME;
	
	public ForThrAttendanceTable(String ThrDID,String ThrDNAME,String ThrUID, String ThrTID, String ThrBEGINTIME, String ThrENDTIME)
	{
		this.ThrDID = new SimpleStringProperty(ThrDID);
		this.ThrDNAME = new SimpleStringProperty(ThrDNAME);
		this.ThrUID = new SimpleStringProperty(ThrUID);
		this.ThrTID = new SimpleStringProperty(ThrTID);
		this.ThrBEGINTIME = new SimpleStringProperty(ThrBEGINTIME);
		this.ThrENDTIME = new SimpleStringProperty(ThrENDTIME);
	}
	
	public String ThrDIDProperty() {
		return ThrDID.get();
	}
	
	public String ThrDNAMEProperty() {
		return ThrDNAME.get();
	}
	
	public String ThrUIDProperty() {
		return ThrUID.get();
	}

	public String ThrTIDProperty() {
		return ThrTID.get();
	}

	public String ThrBEGINTIMEProperty() {
		return ThrBEGINTIME.get();
	}
	public String ThrENDTIMEProperty() {
		return ThrENDTIME.get();
	}
}
