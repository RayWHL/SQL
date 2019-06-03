package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ForSixCalculateTable {
	private final StringProperty SixDID;
	private final StringProperty SixDNAME;
	private final StringProperty SixUID;
	private final StringProperty SixUNAME;
	private StringProperty SixSALARY;
	private StringProperty SixBONUS;
	
	public ForSixCalculateTable(String SixDID,String SixDNAME,String SixUID,String SixUNAME, String SixSALARY, String SixBONUS)
	{
		this.SixDID = new SimpleStringProperty(SixDID);
		this.SixDNAME = new SimpleStringProperty(SixDNAME);
		this.SixUID = new SimpleStringProperty(SixUID);
		this.SixUNAME = new SimpleStringProperty(SixUNAME);
		this.SixSALARY = new SimpleStringProperty(SixSALARY);
		this.SixBONUS = new SimpleStringProperty(SixBONUS);

	}
	
	public String SixDIDProperty() {
		return SixDID.get();
	}
	
	public String SixDNAMEProperty() {
		return SixDNAME.get();
	}
	
	public String SixUIDProperty() {
		return SixUID.get();
	}

	
	public String SixUNAMEProperty() {
		return SixUNAME.get();
	}

	public String SixSALARYProperty() {
		return SixSALARY.get();
	}

	public String SixBONUSProperty() {
		return SixBONUS.get();
	}
	
}
