package es.ficondev.web.backend.web.pages.chef.plate;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;
import es.ficondev.web.modelutil.model.plate.Plate;

@RequiresRoles("CHEF")
public class ShowAll {

	@Inject
    private IngredientService ingredientService;
	
	@Property
	private List<Plate> plates;
	
	@Property
	private Plate plate;
	
	@InjectPage
	private ShowAll showAll;
	
	void onActivate() {
		plates = ingredientService.getAllPlates(0, ingredientService.countAllPlates());
	}
	
	Object onActionFromDelete(long plateId) {
    	try {
    		ingredientService.removePlate(plateId);
    		return null;
		} catch (Exception e) {
			return showAll;
		}
    }
	
}
