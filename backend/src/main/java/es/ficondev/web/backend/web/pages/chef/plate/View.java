package es.ficondev.web.backend.web.pages.chef.plate;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.backend.web.pages.chef.ingredient.ShowAll;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredientservice.IngredientService;
import es.ficondev.web.modelutil.model.plate.Plate;

public class View {
	private Long plateId;

	public Long getIngredientId() {
		return plateId;
	}

	@Property
	private Plate plate;

	@Inject
	private IngredientService ingredientService;

	@InjectPage
	private ShowAll showAll;

	public void setPlateId(Long ingredientId) {
		this.plateId = ingredientId;
	}

	Object onActivate(Long id) {    	
		plateId = id;

		try {
			plate = ingredientService.getPlateById(plateId);
			return null;
		} catch (InstanceNotFoundException e) {

		}

		return showAll;
	}

	Long onPassivate() {
		return plateId;
	}

	Object onActionFromDelete() {
		try {
			ingredientService.removePlate(plateId);
			return null;
		} catch (Exception e) {
			return showAll;
		}
	}
}
