package es.ficondev.web.modelutil.model.plate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import es.ficondev.web.modelutil.model.ingredient.Ingredient;

@Embeddable
public class PlateIngredientId implements Serializable {

	private static final long serialVersionUID = -787837918325098918L;

	private Plate		plate;
	private Ingredient	ingredient;
	
	@ManyToOne
	public Plate getPlate() {
		return plate;
	}
	
	public void setPlate(Plate plate) {
		this.plate = plate;
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
 
        PlateIngredientId that = (PlateIngredientId) o;
 
        if (plate != null ? !plate.equals(that.plate) : that.plate != null) {
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
        result = (plate != null ? plate.hashCode() : 0);
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        return result;
    }
}
