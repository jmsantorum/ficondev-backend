package es.ficondev.web.modelutil.model.ingredientservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;
import es.ficondev.web.modelutil.model.ingredient.IngredientDao;
import es.ficondev.web.modelutil.model.plate.Plate;
import es.ficondev.web.modelutil.model.plate.PlateDao;
import es.ficondev.web.modelutil.model.plate.PlateIngredient;
import es.ficondev.web.modelutil.model.plate.PlateIngredientDao;
import es.ficondev.web.modelutil.model.plate.PlateIngredientId;

@Service("ingredientService")
@Transactional
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientDao ingredientDao;
	
	@Autowired
	private PlateDao plateDao;
	
	@Autowired
	private PlateIngredientDao plateIngredientDao;

	@Override
	public Ingredient getIngredientById(Long ingredientId) throws InstanceNotFoundException {
		return ingredientDao.find(ingredientId);
	}

	@Override
	public Ingredient addIngredient(String name, String description, Integer protein, Integer fat, Integer carbohydrates, Integer alcohol, Integer kcal) throws DuplicateInstanceException {
		try {
			ingredientDao.findIngredientByName(name);
			throw new DuplicateInstanceException("ingredient", Ingredient.class.getName());
		} catch (InstanceNotFoundException e) {

		}

		Ingredient ingredient = new Ingredient(name, description, protein, fat, carbohydrates, alcohol, kcal);

		ingredientDao.save(ingredient);

		return ingredient;
	}

	@Override
	public void removeIngredient(Long ingredientId) throws InstanceNotFoundException {
		ingredientDao.remove(ingredientId);
	}

	@Override
	@Transactional (readOnly = true)
	public List<Ingredient> getAllIngredients(int startIndex, int count) {
		return ingredientDao.getAllIngredients(startIndex, count);
	}

	@Override
	public int countAllIngredients() {
		return ingredientDao.countAllIngredients();
	}

	@Override
	public Ingredient saveIngredient(Ingredient ingredient) {
		ingredientDao.save(ingredient);
		return ingredient;
	}

	@Override
	public Plate getPlateById(Long plateId) throws InstanceNotFoundException {
		return plateDao.find(plateId);
	}
	
	@Override
	public Plate addPlate(String name, String description, Double price) throws DuplicateInstanceException {
		try {
			plateDao.findPlateByName(name);
			throw new DuplicateInstanceException("plate", Ingredient.class.getName());
		} catch (InstanceNotFoundException e) {

		}

		Plate plate = new Plate(name, description, price);

		plateDao.save(plate);

		return plate;
	}
	
	@Override
	public void removePlate(Long plateId) throws InstanceNotFoundException {
		plateDao.remove(plateId);
	}
	
	@Override
	public List<Plate> getAllPlates(int startIndex, int count) {
		return plateDao.getAllPlates(startIndex, count);
	}

	@Override
	public int countAllPlates() {
		return plateDao.countAllPlates();
	}
	
	@Override
	public Plate savePlate(Plate plate) {
		plateDao.save(plate);
		return plate;
	}

	@Override
	public PlateIngredient addIngredientToPlate(Long plateId, Long ingredientId) throws InstanceNotFoundException {
		Plate plate = plateDao.find(plateId);
		Ingredient ingredient = ingredientDao.find(ingredientId);
		
		PlateIngredientId plateIngredientId = new PlateIngredientId();
		plateIngredientId.setPlate(plate);
		plateIngredientId.setIngredient(ingredient);
		
		PlateIngredient plateIngredient;
		
		try {
			plateIngredient = plateIngredientDao.find(plateIngredientId);
			plateIngredient.setQuantity(plateIngredient.getQuantity()+1);
		} catch (InstanceNotFoundException e) {
			plateIngredient = new PlateIngredient();
			
			plateIngredient.setPlate(plate);
			plateIngredient.setIngredient(ingredient);
			plateIngredient.setQuantity(1);
		}
		
		plateIngredientDao.save(plateIngredient);
		
		return plateIngredient;
	}
	
	@Override
	public void quitIngredientFromPlate(Long plateId, Long ingredientId) throws InstanceNotFoundException {
		Plate plate = plateDao.find(plateId);
		Ingredient ingredient = ingredientDao.find(ingredientId);
		
		PlateIngredientId plateIngredientId = new PlateIngredientId();
		plateIngredientId.setPlate(plate);
		plateIngredientId.setIngredient(ingredient);
		
		PlateIngredient plateIngredient = plateIngredientDao.find(plateIngredientId);
		plateIngredient.setQuantity(Math.max(0, plateIngredient.getQuantity()-1));
		
		plateIngredientDao.save(plateIngredient);
	}
	
	@Override
	public List<Ingredient> getIngredientsByPlate(Long plateId) throws InstanceNotFoundException {
		return plateIngredientDao.getIngredientsByPlate(plateId);
	}
	
	@Override
	public List<Plate> findPlatesByPrice(Double minPrice, Double maxPrice) {
		return plateDao.findPlatesByPrice(minPrice, maxPrice);
	}
	
	@Override
	public List<Plate> findPlatesByKCal(Integer minKCal, Integer maxKCal) {
		return plateDao.findPlatesByKCal(minKCal, maxKCal);
	}
	
	@Override
	public List<Plate> findPlatesByEnergy(Integer minProtein,
			Integer maxProtein, Integer minFat, Integer maxFat,
			Integer minCarbohydrates, Integer maxCarbohydrates,
			Integer minAlcohol, Integer maxAlcohol) {

		return plateDao.findPlatesByEnergy(minProtein, maxProtein, 
				minFat, maxFat, minCarbohydrates, maxCarbohydrates,
				minAlcohol, maxAlcohol);
	}
	
	@Override
	public List<List<Plate>> generateMenu(Double minPrice, Double maxPrice,
			Integer minKCal, Integer maxKCal, Integer minProtein,
			Integer maxProtein, Integer minFat, Integer maxFat,
			Integer minCarbohydrates, Integer maxCarbohydrates,
			Integer minAlcohol, Integer maxAlcohol, Integer numMenus) {
		
		List<List<Plate>> menus = new ArrayList<List<Plate>>();
		
		List<Plate> plates = plateDao.getAllPlates(0, plateDao.countAllPlates());
		
		// Delete plates over maxValues
		Map<Plate,PlateExtended> extendedPlates = new HashMap<Plate,PlateExtended>();
		
		for(Plate plate : plates) {
			PlateExtended pe = new PlateExtended(plate);
			if ( (pe.getPrice() <= maxPrice) && (pe.getKcal() <= maxKCal) && (pe.getProtein() <= maxProtein) && 
				 (pe.getFat() <= maxFat) && (pe.getCarbohydrates() <= maxCarbohydrates) && (pe.getAlcohol() <= maxAlcohol) )
			{
				extendedPlates.put(plate, pe);
			} else {
				plates.remove(plate);				
			}
		}
		
		// Shuffle plates
		Collections.shuffle(plates, new Random(System.nanoTime()));
		
		Combination combination = new Combination(plates);
		
		List<List<Plate>> plateCombinations = combination.Ar();
				
		int price, kcal, protein, fat, carbohydrates, alcohol;
		
		for(int i=0; i<plateCombinations.size(); i++) {
			price = kcal = protein = fat = carbohydrates = alcohol = 0;
			// Calculate menu values
			for(Plate plate : plateCombinations.get(i)) {
				PlateExtended pe = extendedPlates.get(plate);
				price 			+= pe.getPrice();
				kcal 			+= pe.getKcal();
				protein 		+= pe.getProtein();
				fat 			+= pe.getFat();
				carbohydrates 	+= pe.getCarbohydrates();
				alcohol 		+= pe.getAlcohol();
			}
			
			//Check if menu is valid
			if ( (minPrice <= price && price <= maxPrice) &&
				 (minKCal <= kcal && kcal <= maxKCal) &&
				 (minProtein <= protein && protein <= maxProtein) &&
				 (minFat <= fat && fat <= maxFat) &&
			     (minCarbohydrates <= carbohydrates && carbohydrates <= maxCarbohydrates) &&
			     (minAlcohol <= alcohol && alcohol <= maxAlcohol) ) {
				menus.add(plateCombinations.get(i));
				if (menus.size() == numMenus) break;
			}
		}
		
		return menus;
	}
	

	
}
