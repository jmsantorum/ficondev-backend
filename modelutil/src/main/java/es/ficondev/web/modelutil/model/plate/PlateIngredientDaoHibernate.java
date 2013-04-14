package es.ficondev.web.modelutil.model.plate;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;

@Repository("plateIngredientDao")
public class PlateIngredientDaoHibernate extends GenericDaoHibernate<PlateIngredient, PlateIngredientId> implements PlateIngredientDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ingredient> getIngredientsByPlate(Long plateId) throws InstanceNotFoundException {
		return getSession().createQuery(
		   		"SELECT pi.pk.ingredient FROM PlateIngredient pi " +
		   		"WHERE pi.pk.plate.plateId = :plateId ").
		   		setParameter("plateId", plateId).
		   		list();
	}
	

}
