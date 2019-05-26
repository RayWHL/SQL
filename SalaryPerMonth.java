package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SalaryPerMonth {
	private final StringProperty Year;
	private final StringProperty Month;
	private final StringProperty Salary;
	private final StringProperty Sub;
	private final StringProperty SumSalary;
	
	public SalaryPerMonth(String Year,String Month,String Salary, String Sub, String SumSalary)
	{
		this.Year = new SimpleStringProperty(Year);
		this.Month = new SimpleStringProperty(Month);
		this.Salary = new SimpleStringProperty(Salary);
		this.Sub = new SimpleStringProperty(Sub);
		this.SumSalary = new SimpleStringProperty(SumSalary);
	}
	
	public String YearProperty() {
		return Year.get();
	}
	
	public String MonthProperty() {
		return Month.get();
	}
	
	public String SalaryProperty() {
		return Salary.get();
	}
	
	public String SubProperty() {
		return Sub.get();
	}
	
	public String SumSalaryProperty() {
		return SumSalary.get();
	}
}
