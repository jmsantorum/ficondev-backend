package es.ficondev.web.modelutil.model.account.physical.legal.provider;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.persistence.Version;

import es.ficondev.web.modelutil.model.ingredient.Ingredient;

@Entity
@AssociationOverrides({
	@AssociationOverride(name = "pk.provider", 
			joinColumns = @JoinColumn(name = "accountId")),
			@AssociationOverride(name = "pk.ingredient", 
			joinColumns = @JoinColumn(name = "ingredientId")) })
public class ProviderIngredient implements Serializable {

	private static final long serialVersionUID = 2534061402743268255L;

	private ProviderIngredientId	pk	= new ProviderIngredientId();

	private Integer					stock;
	private Double					price;

	private Long					version;

	public ProviderIngredient() {
	}

	@EmbeddedId
	public ProviderIngredientId getPk() {
		return pk;
	}

	public void setPk(ProviderIngredientId pk) {
		this.pk = pk;
	}

	@Transient
	public Provider getProvider() {
		return getPk().getProvider();
	}

	public void setProvider(Provider provider) {
		getPk().setProvider(provider);
	}

	@Transient
	public Ingredient getIngredient() {
		return getPk().getIngredient();
	}

	public void setIngredient(Ingredient ingredient) {
		getPk().setIngredient(ingredient);
	}

	public Integer getStock() {
		return stock;
	}
	
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ProviderIngredient that = (ProviderIngredient) o;

		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

}
