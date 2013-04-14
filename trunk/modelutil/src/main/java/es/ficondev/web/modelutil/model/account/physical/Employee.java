package es.ficondev.web.modelutil.model.account.physical;

import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="accountId")
public class Employee extends PhysicalAccount {

	private static final long serialVersionUID = 7189527875985291497L;

	private Double salary;
	
	public Employee() {
	}

	public Employee(String username, String password, String salt, String email, Locale locale, TimeZone timeZone,
			String name, String direction, String phoneNumber,
			Double salary) {
		super(username, password, salt, email, locale, timeZone, name, direction, phoneNumber);
		
		this.salary = salary;
	}

	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
}
