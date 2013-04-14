package es.ficondev.web.modelutil.model.plate;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;

@Repository("plateDao")
public class PlateDaoHibernate  extends GenericDaoHibernate<Plate, Long> implements PlateDao {

	@Override
	public Plate findPlateByName(String name) throws InstanceNotFoundException {
		Plate plate = (Plate) getSession().createQuery(
				"SELECT p FROM Plate p WHERE p.name = :name")
				.setParameter("name", name)
				.uniqueResult();

		if (plate == null) 
			throw new InstanceNotFoundException(name, Ingredient.class.getName());

		return plate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Plate> getAllPlates(int startIndex, int count) {
		return getSession().createQuery(
				"SELECT p FROM Plate p " +
				"ORDER BY p.name ASC").
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}

	@Override
	public int countAllPlates() {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(p) FROM Plate p ").
				uniqueResult();

		return (int) count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plate> findPlatesByPrice(Double minPrice, Double maxPrice) {
		return getSession().createQuery(
		   		"SELECT p FROM Plate p " +
		   		"WHERE p.price >= :minPrice AND p.price <= :maxPrice ").
		   		setParameter("minPrice", minPrice).
		   		setParameter("maxPrice", maxPrice).
		   		list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plate> findPlatesByKCal(Integer minKCal, Integer maxKCal) {
		return getSession().createQuery(
		   		"SELECT p FROM Plate p " +
		   		"WHERE ( SELECT SUM(pi.pk.ingredient.kcal * pi.quantity) FROM PlateIngredient pi" +
		   		"        WHERE pi in elements(p.plateIngredients) ) BETWEEN :minKCal AND :maxKCal ").
		   		setParameter("minKCal", (long)minKCal).
		   		setParameter("maxKCal", (long)maxKCal).
		   		list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plate> findPlatesByEnergy(Integer minProtein,
			Integer maxProtein, Integer minFat, Integer maxFat,
			Integer minCarbohydrates, Integer maxCarbohydrates,
			Integer minAlcohol, Integer maxAlcohol) {
		
		return getSession().createQuery(
		   		"SELECT p FROM Plate p " +
		   		"WHERE (( SELECT SUM(pi.pk.ingredient.protein * pi.quantity) FROM PlateIngredient pi" +
		   		"        WHERE pi in elements(p.plateIngredients) ) BETWEEN :minProtein AND :maxProtein) AND " +
		   	    "      (( SELECT SUM(pi.pk.ingredient.fat * pi.quantity) FROM PlateIngredient pi" +
				"        WHERE pi in elements(p.plateIngredients) ) BETWEEN :minFat AND :maxFat) AND " +
				"      (( SELECT SUM(pi.pk.ingredient.carbohydrates * pi.quantity) FROM PlateIngredient pi" +
		  		"        WHERE pi in elements(p.plateIngredients) ) BETWEEN :minCarbohydrates AND :maxCarbohydrates) AND " +
		  		"      (( SELECT SUM(pi.pk.ingredient.alcohol * pi.quantity) FROM PlateIngredient pi" +
		 		"        WHERE pi in elements(p.plateIngredients) ) BETWEEN :minAlcohol AND :maxAlcohol) ").
		   		setParameter("minProtein", (long)minProtein).
		   		setParameter("maxProtein", (long)maxProtein).
		   		setParameter("minFat", (long)minFat).
		   		setParameter("maxFat", (long)maxFat).
		   		setParameter("minCarbohydrates", (long)minCarbohydrates).
		   		setParameter("maxCarbohydrates", (long)maxCarbohydrates).
		   		setParameter("minAlcohol", (long)minAlcohol).
		   		setParameter("maxAlcohol", (long)maxAlcohol).
		   		list();
		
	}

}
