package es.ficondev.web.modelutil.model.account.physical.legal.provider;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import es.ficondev.web.modelutil.model.ingredient.Ingredient;

@Embeddable
public class ProviderIngredientId implements Serializable {

	private static final long serialVersionUID = -787837918325098918L;

	private Provider	provider;
	private Ingredient	ingredient;

	@ManyToOne
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@ManyToOne
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public boolean equals(Object o) 
	{
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ProviderIngredientId that = (ProviderIngredientId) o;

		if (provider != null ? !provider.equals(that.provider) : that.provider != null) {
			return false;
		}

		if (ingredient != null ? !ingredient.equals(that.ingredient) : that.ingredient != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() 
	{
		int result;
		result = (provider != null ? provider.hashCode() : 0);
		result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
		return result;
	}
}
