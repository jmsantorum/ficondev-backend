package es.ficondev.web.modelutil.model.plate;

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
	@AssociationOverride(name = "pk.plate", 
		joinColumns = @JoinColumn(name = "plateId")),
	@AssociationOverride(name = "pk.ingredient", 
		joinColumns = @JoinColumn(name = "ingredientId")) })
public class PlateIngredient implements Serializable {

	private static final long serialVersionUID = 2534061402743268255L;

	private PlateIngredientId	pk	= new PlateIngredientId();
	
	private Integer				quantity;
	
	private Long				version;
	
	public PlateIngredient() {
	}

	@EmbeddedId
	public PlateIngredientId getPk() {
		return pk;
	}
	
	public void setPk(PlateIngredientId pk) {
		this.pk = pk;
	}
	
	@Transient
	public Plate getPlate() {
		return getPk().getPlate();
	}
 
	public void setPlate(Plate plate) {
		getPk().setPlate(plate);
	}
 
	@Transient
	public Ingredient getIngredient() {
		return getPk().getIngredient();
	}
 
	public void setIngredient(Ingredient ingredient) {
		getPk().setIngredient(ingredient);
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
 
		PlateIngredient that = (PlateIngredient) o;
 
		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) {
			return false;
		}
 
		return true;
	}
	
	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
	
}
