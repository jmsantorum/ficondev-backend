package es.ficondev.web.modelutil.model.ingredient;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

@Repository("ingredientDao")
public class IngredientDaoHibernate extends GenericDaoHibernate<Ingredient, Long> implements IngredientDao {

	@Override
	public Ingredient findIngredientByName(String name) throws InstanceNotFoundException {
		Ingredient ingredient = (Ingredient) getSession().createQuery(
				"SELECT i FROM Ingredient i WHERE i.name = :name")
				.setParameter("name", name)
				.uniqueResult();

		if (ingredient == null) 
			throw new InstanceNotFoundException(name, Ingredient.class.getName());

		return ingredient;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ingredient> getAllIngredients(int startIndex, int count) {
		return getSession().createQuery(
				"SELECT i FROM Ingredient i " +
				"ORDER BY i.name ASC").
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}
	
	@Override
	public int countAllIngredients() {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(i) FROM Ingredient i ").
				uniqueResult();

		return (int) count;
	}
	
}
