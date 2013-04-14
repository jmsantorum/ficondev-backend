package es.ficondev.web.backend.web.pages.chef.plate;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;
import es.ficondev.web.modelutil.model.plate.Plate;

@RequiresRoles("CHEF")
public class Edit {
	@Property
	private Long plateId;
	
	@Property
	private String name;

	@Property
	private String description;

	@Property
	private Double price;

	@Inject
	private IngredientService ingredientService;

	@Component
	private Form addForm;

	@Component(id = "name")
	private TextField nameField;

	@Component(id = "description")
	private TextField descriptionField;

	@Component(id= "price")
	private TextField priceField;

	@Inject
	private Messages messages;

	@InjectPage
	private ShowAll showAll;

	@InjectPage
    private View view;
	
	Object onActivate(Long id) {    	
        try
        {
        	plateId = id;
        	
        	Plate plate = ingredientService.getPlateById(plateId);
        
        	name		= plate.getName();
        	description	= plate.getDescription();
        	price		= plate.getPrice();

        	return null;
        }
        catch (Exception e) {
        	
        }
        
        return showAll;
    }
	
    Long onPassivate() {
		return plateId;
	}
    
    Object onSuccess() {
    	try {
    		Plate plate = ingredientService.getPlateById(plateId);
    		
    		plate.setName(name);
    		plate.setDescription(description);
    		plate.setPrice(price);
			ingredientService.savePlate(plate);
		} catch (InstanceNotFoundException e) {

		} 

    	view.setPlateId(plateId);
    	
		return view;
    }
}
