package es.ficondev.web.modelutil.model.plate;

import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;

public interface PlateIngredientDao extends GenericDao<PlateIngredient, PlateIngredientId> {
	
	List<Ingredient>	getIngredientsByPlate(Long plateId) throws InstanceNotFoundException;

}
