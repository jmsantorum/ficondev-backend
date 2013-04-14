package es.ficondev.web.backend.web.pages.chef.ingredient;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;
import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;

@RequiresRoles("CHEF")
public class Edit {

	@Property
	private Long ingredientId;
	
	@Property
    private String name;
	
	@Property
    private String description;

    @Property
    private Integer protein;

    @Property
	private Integer fat;
   
    @Property
	private Integer carbohydrates;
    
    @Property
	private Integer alcohol;

    @Property
	private Integer kcal;
	
	@InjectPage
	private ShowAll showAll;
	
	@Inject
	private IngredientService ingredientService;
	
	@InjectPage
    private View view;
	
	Object onActivate(Long id) {    	
        try
        {
        	ingredientId = id;
        	
        	Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
        
        	name		= ingredient.getName();
        	description	= ingredient.getDescription();
        	protein		= ingredient.getProtein();
        	fat			= ingredient.getFat();
        	carbohydrates	= ingredient.getCarbohydrates();
        	alcohol			= ingredient.getAlcohol();
        	kcal			= ingredient.getKcal();

        	return null;
        }
        catch (Exception e)
        {
        	
        }
        
        return showAll;
    }
	
    Long onPassivate() {
		return ingredientId;
	}
    
    Object onSuccess() {
    	try {
    		Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
    		
    		ingredient.setName(name);
    		ingredient.setDescription(description);
    		ingredient.setProtein(protein);
    		ingredient.setFat(fat);
    		ingredient.setCarbohydrates(carbohydrates);
    		ingredient.setAlcohol(alcohol);
    		ingredient.setKcal(kcal);
			ingredientService.saveIngredient(ingredient);
		} catch (InstanceNotFoundException e) {

		} 

    	view.setIngredientId(ingredientId);
    	
		return view;
    }
}
