package es.ficondev.web.backend.web.pages.chef.ingredient;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.model.ingredient.Ingredient;
import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;

@RequiresRoles("CHEF")
public class ShowAll {

	@Inject
    private IngredientService ingredientService;
	
	@Property
	private List<Ingredient> ingredients;
	
	@Property
	private Ingredient ingredient;
	
	@InjectPage
	private ShowAll showAll;
	
	void onActivate() {
		ingredients = ingredientService.getAllIngredients(0, ingredientService.countAllIngredients());
	}
	
	Object onActionFromDelete(long ingredientId) {
    	try {
    		ingredientService.removeIngredient(ingredientId);
    		return null;
		} catch (Exception e) {
			return showAll;
		}
    }
}
