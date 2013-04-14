package es.ficondev.web.backend.web.pages.chef.plate;

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
	private Notification notification;

	void onValidateFromAddForm() {
		if (!addForm.isValid()) 
			return;

		try {
			ingredientService.addPlate(name, description, price);
		} catch (DuplicateInstanceException e) {
			addForm.recordError(messages.get("error-" + e.getKey() + "-exists"));
		}
	}

	Object onSuccessFromAddForm() {
		notification.setContent("Plate created correctly.");
		return notification;
	}
}
