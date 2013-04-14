package es.ficondev.web.modelutil.model.account.physical.legal.provider;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import es.ficondev.web.modelutil.model.account.physical.legal.LegalAccount;

@Entity
@PrimaryKeyJoinColumn(name="accountId")
public class Provider extends LegalAccount {

	private static final long serialVersionUID = -6184707149577779697L;

	private Set<ProviderIngredient>	providerIngredients	= new HashSet<ProviderIngredient>();
	
	public Provider() {
	}

	public Provider(String username, String password, String salt, String email, Locale locale, TimeZone timeZone,
			String fullName, String direction, String phoneNumber, 
			String phoneNumber2, String fax, String contactName, String cif, String web) {
		super(username, password, salt, email, locale, timeZone, fullName, direction, phoneNumber, phoneNumber2, fax, contactName, cif, web);
	}

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "pk.provider",
			orphanRemoval = true		
			)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Set<ProviderIngredient> getProviderIngredients() {
		return providerIngredients;
	}

	public void setProviderIngredients(
			Set<ProviderIngredient> providerIngredients) {
		this.providerIngredients = providerIngredients;
	}

	public void addProviderIngredient(ProviderIngredient providerIngredient){
		this.providerIngredients.add(providerIngredient);
	}

	public void removeProviderIngredient(ProviderIngredient providerIngredient) {
		this.providerIngredients.remove(providerIngredient);
	}
}
