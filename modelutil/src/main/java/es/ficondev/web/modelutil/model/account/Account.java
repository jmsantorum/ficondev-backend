package es.ficondev.web.modelutil.model.account;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import es.ficondev.web.modelutil.model.rol.Rol;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements Serializable
{
	private static final long serialVersionUID = 3200946771891986979L;

	private Long		accountId;

	private String		username;
	private String		password;
	private String		salt;
	private String		email;
	private Locale		locale;
	private TimeZone	timeZone;
	private Boolean		activated;
	private String		activationCode;

	private Set<Rol>	roles	= new HashSet<Rol>();

	private Long 		version;

	public Account() {
	}

	public Account(String username, String password, String salt, String email, Locale locale, TimeZone timeZone) { 
		this.username 		= username;
		this.password 		= password;
		this.salt			= salt;
		this.email 			= email;
		this.locale 		= locale;
		this.timeZone		= timeZone;
		this.activated		= false;
		this.activationCode	= String.valueOf(System.currentTimeMillis()) + email.hashCode();
	}

	@SequenceGenerator( // It only takes effect for
			name = "AccountIdGenerator", // databases providing identifier
			sequenceName = "AccountSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AccountIdGenerator")
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated)	{
		this.activated = activated;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	@ManyToMany(
			targetEntity=Rol.class,
			mappedBy = "accounts",
			cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}
			)
	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public void addRol(Rol rol) {
		roles.add(rol);
	}

	public void removeRol(Rol rol) {
		roles.remove(rol);
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
