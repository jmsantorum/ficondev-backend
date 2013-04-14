package es.ficondev.web.modelutil.model.rol;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import es.ficondev.web.modelutil.model.account.Account;

@Entity
public class Rol implements Serializable {
	private static final long serialVersionUID = -4747052299994825744L;

	private Long 			rolId;

	private Set<Account> 	accounts;
	private RolType 		rolType;

	private Long 			version;

	public Rol() { };

	public Rol(RolType rolType) {
		this.accounts 		= new HashSet<Account>();
		this.rolType 	= rolType;
	}

	@SequenceGenerator( // It only takes effect for
			name = "RolIdGenerator", // databases providing identifier
			sequenceName = "RolSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "RolIdGenerator")
	public Long getRolId() {
		return rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	@ManyToMany(
			targetEntity=Account.class
			)
	@JoinTable(
			name="RolAccount",
			joinColumns = @JoinColumn( name="rolId" ),
			inverseJoinColumns = @JoinColumn( name="accountId" )
			)
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Account account) 
	{
		this.accounts.add(account);
	}

	public void removeAccount(Account account) {
		this.accounts.remove(account);
	}

	@Enumerated(EnumType.STRING)
	public RolType getRolType() {
		return rolType;
	}

	public void setRolType(RolType rolType) {
		this.rolType = rolType;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
