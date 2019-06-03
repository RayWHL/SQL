package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ForFourSalaryTable {
	private final StringProperty FourDID;
	private final StringProperty FourDNAME;
	private final StringProperty FourUID;
	private final StringProperty FourYEAR;
	private StringProperty FourMONTH;
	private StringProperty FourFUND;
	private final StringProperty FourSUB;
	private final StringProperty FourSUM;
	
	public ForFourSalaryTable(String FourDID,String FourDNAME,String FourUID,String FourYEAR, String FourMONTH, String FourFUND, String FourSUB, String FourSUM)
	{
		this.FourDID = new SimpleStringProperty(FourDID);
		this.FourDNAME = new SimpleStringProperty(FourDNAME);
		this.FourUID = new SimpleStringProperty(FourUID);
		this.FourYEAR = new SimpleStringProperty(FourYEAR);
		this.FourMONTH = new SimpleStringProperty(FourMONTH);
		this.FourFUND = new SimpleStringProperty(FourFUND);
		this.FourSUB = new SimpleStringProperty(FourSUB);
		this.FourSUM = new SimpleStringProperty(FourSUM);
	}
	
	public String FourDIDProperty() {
		return FourDID.get();
	}
	
	public String FourDNAMEProperty() {
		return FourDNAME.get();
	}
	
	public String FourUIDProperty() {
		return FourUID.get();
	}

	
	public String FourYEARProperty() {
		return FourYEAR.get();
	}

	public String FourMONTHProperty() {
		return FourMONTH.get();
	}

	public String FourFUNDProperty() {
		return FourFUND.get();
	}
	public String FourSUBProperty() {
		return FourSUB.get();
	}
	public String FourSUMProperty() {
		return FourSUM.get();
	}
}
