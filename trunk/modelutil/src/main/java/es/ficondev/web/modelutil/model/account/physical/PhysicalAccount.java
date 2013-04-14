package es.ficondev.web.modelutil.model.account.physical;

import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import es.ficondev.web.modelutil.model.account.Account;


@Entity
@PrimaryKeyJoinColumn(name="accountId")
public abstract class PhysicalAccount extends Account {

	private static final long serialVersionUID = 2504813926283778384L;

	private String name;
	private String direction;
	private String phoneNumber;

	public PhysicalAccount() {
		super();
	}

	public PhysicalAccount(String username, String password, String salt, String email, Locale locale, TimeZone timeZone,
			String name, String direction, String phoneNumber) {
		super(username, password, salt, email, locale, timeZone);

		this.name = name;
		this.direction = direction;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
