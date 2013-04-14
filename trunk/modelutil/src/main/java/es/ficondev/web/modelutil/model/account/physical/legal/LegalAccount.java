package es.ficondev.web.modelutil.model.account.physical.legal;

import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import es.ficondev.web.modelutil.model.account.physical.PhysicalAccount;


@Entity
@PrimaryKeyJoinColumn(name="accountId")
public abstract class LegalAccount extends PhysicalAccount {

	private static final long serialVersionUID = 4694204729588302177L;

	private String phoneNumber2;
	private String fax;
	private String contactName;
	private String cif;
	private String web;
	
	public LegalAccount() {
		super();
	}
	
	public LegalAccount(String username, String password, String salt, String email, Locale locale, TimeZone timeZone,
			String name, String direction, String phoneNumber, 
			String phoneNumber2, String fax, String contactName, String cif, String web) {
		super(username, password, salt, email, locale, timeZone, name, direction, phoneNumber);
		
		this.phoneNumber2 = phoneNumber2;
		this.fax = fax;
		this.contactName = contactName;
		this.cif = cif;
		this.web = web;
	}
	
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getCif() {
		return cif;
	}
	
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	public String getWeb() {
		return web;
	}
	
	public void setWeb(String web) {
		this.web = web;
	}
}
