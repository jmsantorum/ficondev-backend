package es.ficondev.web.modelutil.model.ingredient;

import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

public interface IngredientDao  extends GenericDao<Ingredient, Long> {
	
	Ingredient findIngredientByName(String name) throws InstanceNotFoundException;

	List<Ingredient>	getAllIngredients(int startIndex, int count);
	int					countAllIngredients();
	
	
}
