package es.ficondev.web.backend.web.pages.chef.ingredient;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;

@RequiresRoles("CHEF")
public class Add {

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
    
    @Inject
    private IngredientService ingredientService;

    @Component
    private Form addForm;

    @Component(id = "name")
    private TextField nameField;

    @Component(id = "description")
    private TextField descriptionField;

	@Component(id= "protein")
	private TextField proteinField;
	
	@Component(id= "fat")
	private TextField fatField;
	
	@Component(id= "carbohydrates")
	private TextField carbohydratesField;
	
	@Component(id= "alcohol")
	private TextField alcoholField;
	
	@Component(id= "kcal")
	private TextField kcalField;
	
	@Inject
	private Messages messages;
	
    @InjectPage
    private Notification notification;
    
    void onValidateFromAddForm() {
        if (!addForm.isValid()) 
            return;
        
       	if (protein + fat + carbohydrates + alcohol != 100) {
            addForm.recordError(proteinField, messages.get("energy-error"));
            addForm.recordError(fatField, messages.get("energy-error"));
            addForm.recordError(carbohydratesField, messages.get("energy-error"));
            addForm.recordError(alcoholField, messages.get("energy-error"));
        } else {
            try {
            	ingredientService.addIngredient(name, description, protein, fat, carbohydrates, alcohol, kcal);
            } catch (DuplicateInstanceException e) {
            	addForm.recordError(messages.get("error-" + e.getKey() + "-exists"));
            }
        }
    }

    Object onSuccessFromAddForm() {
    	notification.setContent("Ingredient created correctly.");
        return notification;
    }
	
}
