package es.ficondev.web.modelutil.model.plate;

import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

public interface PlateDao extends GenericDao<Plate, Long> {

	List<Plate>	getAllPlates(int startIndex, int count);
	int			countAllPlates();

	Plate		findPlateByName(String name) throws InstanceNotFoundException;
	List<Plate> findPlatesByPrice(Double minPrice, Double maxPrice);
	List<Plate>	findPlatesByKCal(Integer minKCal, Integer maxKCal);
	List<Plate> findPlatesByEnergy(Integer minProtein, Integer maxProtein,
				Integer minFat, Integer maxFat, Integer minCarbohydrates,
				Integer maxCarbohydrates, Integer minAlcohol, Integer maxAlcohol);

}
