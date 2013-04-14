package es.ficondev.web.backend.web.pages.chef.ingredient;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;
import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;

@RequiresRoles("CHEF")
public class View {

	private Long ingredientId;
	
	public Long getIngredientId() {
		return ingredientId;
	}
	
	@Property
	private Ingredient ingredient;
	
	@Inject
	private IngredientService ingredientService;
	
	@InjectPage
	private ShowAll showAll;
	
	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}
	
	Object onActivate(Long id) {    	
       	ingredientId = id;
       	
       	try {
			ingredient = ingredientService.getIngredientById(ingredientId);
			return null;
		} catch (InstanceNotFoundException e) {

		}
       	
       	return showAll;
	}
	
	Long onPassivate() {
		return ingredientId;
	}
	
	Object onActionFromDelete() {
    	try {
    		ingredientService.removeIngredient(ingredientId);
    		return null;
		} catch (Exception e) {
			return showAll;
		}
    }
}
