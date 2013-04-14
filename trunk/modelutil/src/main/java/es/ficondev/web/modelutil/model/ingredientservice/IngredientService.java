package es.ficondev.web.modelutil.model.ingredientservice;

import java.util.List;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;
import es.ficondev.web.modelutil.model.plate.Plate;
import es.ficondev.web.modelutil.model.plate.PlateIngredient;

public interface IngredientService {

	/* Ingredients */
	Ingredient			getIngredientById(Long ingredientId) throws InstanceNotFoundException;
	Ingredient			addIngredient(String name, String description, Integer protein, Integer fat, Integer carbohydrates, Integer alcohol, Integer kcal) throws DuplicateInstanceException;
	void				removeIngredient(Long ingredientId) throws InstanceNotFoundException;
	List<Ingredient>	getAllIngredients(int startIndex, int count);
	int 				countAllIngredients();
	Ingredient			saveIngredient(Ingredient ingredient);

	/* Plates */
	Plate				getPlateById(Long plateId) throws InstanceNotFoundException;
	Plate				addPlate(String name, String description, Double price) throws DuplicateInstanceException;
	void				removePlate(Long plateId) throws InstanceNotFoundException;
	List<Plate>			getAllPlates(int startIndex, int count);
	int					countAllPlates();
	Plate				savePlate(Plate plate);
	
	/* Plates - Ingredients */
	PlateIngredient		addIngredientToPlate(Long plateId, Long ingredientId) throws InstanceNotFoundException;
	void				quitIngredientFromPlate(Long plateId, Long ingredientId) throws InstanceNotFoundException;
	List<Ingredient>	getIngredientsByPlate(Long plateId) throws InstanceNotFoundException;
	
	List<Plate>			findPlatesByPrice(Double minPrice, Double maxPrice);
	List<Plate>			findPlatesByKCal(Integer minKCal, Integer maxKCal);
	List<Plate>			findPlatesByEnergy(Integer minProtein, Integer maxProtein,
										   Integer minFat, Integer maxFat,
										   Integer minCarbohydrates, Integer maxCarbohydrates,
										   Integer minAlcohol, Integer maxAlcohol);
	
	List<List<Plate>>	generateMenu(Double minPrice, Double maxPrice, 
									 Integer minKCal, Integer maxKCal, 
									 Integer minProtein, Integer maxProtein,
									 Integer minFat, Integer maxFat,
									 Integer minCarbohydrates, Integer maxCarbohydrates,
									 Integer minAlcohol, Integer maxAlcohol,
									 Integer numMenus);
}
