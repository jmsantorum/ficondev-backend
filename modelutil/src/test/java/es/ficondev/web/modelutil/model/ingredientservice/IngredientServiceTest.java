package es.ficondev.web.modelutil.model.ingredientservice;

import static es.ficondev.web.modelutil.model.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static es.ficondev.web.modelutil.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.ingredient.Ingredient;
import es.ficondev.web.modelutil.model.plate.Plate;
import es.ficondev.web.modelutil.model.plate.PlateIngredient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class IngredientServiceTest {

	@Autowired
	private IngredientService ingredientService;
	
	@Test
	public void testAddGetIngredient() throws DuplicateInstanceException, InstanceNotFoundException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		Ingredient i1 = ingredientService.addIngredient("i1", "description", 40, 30, 20, 10, 165);
		Ingredient i2 = ingredientService.addIngredient("i2", "description", 30, 20, 10, 40, 625);
		
		assertEquals(ingredientService.getIngredientById(i0.getIngredientId()).getDescription(), "description");
		assertEquals(ingredientService.getIngredientById(i1.getIngredientId()).getProtein(), Integer.valueOf(40));
		assertEquals(ingredientService.getIngredientById(i2.getIngredientId()).getKcal(), Integer.valueOf(625));
	}
	
	@Test
	public void testRemoveIngredient() throws DuplicateInstanceException, InstanceNotFoundException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		
		ingredientService.removeIngredient(i0.getIngredientId());
		
		boolean fail = false;
		try {
			ingredientService.getIngredientById(i0.getIngredientId());
		} catch (InstanceNotFoundException e) {
			fail = true;
		}
		
		assertTrue(fail);
	}
	
	@Test
	public void testGetCountAllIngredients() throws DuplicateInstanceException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		Ingredient i1 = ingredientService.addIngredient("i1", "description", 40, 30, 20, 10, 165);
		Ingredient i2 = ingredientService.addIngredient("i2", "description", 30, 20, 10, 40, 625);
		
		List<Ingredient> is = ingredientService.getAllIngredients(0, 2);
		assertEquals(is.size(), 2);
		assertEquals(is.get(0).getIngredientId(), i0.getIngredientId());
		assertEquals(is.get(1).getIngredientId(), i1.getIngredientId());
		
		assertEquals(ingredientService.countAllIngredients(), 3);
	}
	
	@Test
	public void testSaveIngredient() throws DuplicateInstanceException, InstanceNotFoundException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		
		i0.setDescription("new_description");
		
		ingredientService.saveIngredient(i0);
		
		assertEquals(ingredientService.getIngredientById(i0.getIngredientId()).getDescription(), "new_description");
	}
	
	@Test
	public void testAddGetPlate() throws DuplicateInstanceException, InstanceNotFoundException {
		Plate p0 = ingredientService.addPlate("plate1", "description", 2.00);
		Plate p1 = ingredientService.addPlate("plate2", "description", 4.00);
		
		assertEquals(ingredientService.getPlateById(p0.getPlateId()).getDescription(), "description");
		assertEquals(ingredientService.getPlateById(p1.getPlateId()).getPrice(), Double.valueOf(4));
	}
	
	@Test
	public void testRemovePlate() throws DuplicateInstanceException, InstanceNotFoundException {
		Plate p0 = ingredientService.addPlate("plate1", "description", 2.00);

		ingredientService.removePlate(p0.getPlateId());
		
		boolean fail = false;
		try {
			ingredientService.getPlateById(p0.getPlateId());
		} catch (InstanceNotFoundException e) {
			fail = true;
		}
		
		assertTrue(fail);
	}
	
	@Test
	public void testGetCountAllPlates() throws DuplicateInstanceException {
		Plate p1 = ingredientService.addPlate("plate1", "description", 2.00);
		Plate p2 = ingredientService.addPlate("plate2", "description", 4.00);
		Plate p3 = ingredientService.addPlate("plate3", "description", 6.00);
		
		List<Plate> ps = ingredientService.getAllPlates(0, 2);
		assertEquals(ps.size(), 2);
		assertEquals(ps.get(0).getPlateId(), p1.getPlateId());
		assertEquals(ps.get(1).getPlateId(), p2.getPlateId());
		
		assertEquals(ingredientService.countAllPlates(), 3);
	}
	
	@Test
	public void testSavePlate() throws DuplicateInstanceException, InstanceNotFoundException {
		Plate p1 = ingredientService.addPlate("plate1", "description", 2.00);

		p1.setDescription("new_description");
		
		ingredientService.savePlate(p1);
		
		assertEquals(ingredientService.getPlateById(p1.getPlateId()).getDescription(), "new_description");
	}
	
	@Test
	public void testAddQuitIngredientToFromPlate() throws DuplicateInstanceException, InstanceNotFoundException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		Ingredient i1 = ingredientService.addIngredient("i1", "description", 40, 30, 20, 10, 165);
		Ingredient i2 = ingredientService.addIngredient("i2", "description", 30, 20, 10, 40, 625);
		
		Plate p1 = ingredientService.addPlate("plate1", "description", 2.00);
		
		List<Ingredient> is = ingredientService.getIngredientsByPlate(p1.getPlateId());
		assertEquals(is.size(), 0);
		
		ingredientService.addIngredientToPlate(p1.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i1.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i1.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i2.getIngredientId());
		
		p1 = ingredientService.getPlateById(p1.getPlateId());
		is = ingredientService.getIngredientsByPlate(p1.getPlateId());
		assertEquals(is.size(), 3);
		for(PlateIngredient pi : p1.getPlateIngredients()) {
			if (pi.getIngredient().getIngredientId() == i1.getIngredientId())
				assertEquals(pi.getQuantity(), Integer.valueOf(2));
			else
				assertEquals(pi.getQuantity(), Integer.valueOf(1));
		}
		
		assertEquals(is.get(0).getIngredientId(), i0.getIngredientId());
		assertEquals(is.get(1).getIngredientId(), i1.getIngredientId());
		assertEquals(is.get(2).getIngredientId(), i2.getIngredientId());
		
		ingredientService.quitIngredientFromPlate(p1.getPlateId(), i1.getIngredientId());
		
		is = ingredientService.getIngredientsByPlate(p1.getPlateId());
		assertEquals(is.size(), 3);
		for(PlateIngredient pi : p1.getPlateIngredients()) {
				assertEquals(pi.getQuantity(), Integer.valueOf(1));
		}
		assertEquals(is.get(0).getIngredientId(), i0.getIngredientId());
		assertEquals(is.get(1).getIngredientId(), i1.getIngredientId());
		assertEquals(is.get(2).getIngredientId(), i2.getIngredientId());
	}
	
	@Test
	public void testFindPlates() throws DuplicateInstanceException, InstanceNotFoundException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		Ingredient i1 = ingredientService.addIngredient("i1", "description", 40, 30, 20, 10, 165);
		Ingredient i2 = ingredientService.addIngredient("i2", "description", 30, 20, 10, 40, 625);
		
		Plate p1 = ingredientService.addPlate("plate1", "description", 2.00);
		Plate p2 = ingredientService.addPlate("plate2", "description", 4.00);
		Plate p3 = ingredientService.addPlate("plate3", "description", 6.00);
		Plate p4 = ingredientService.addPlate("plate4", "description", 8.00);
		Plate p5 = ingredientService.addPlate("plate5", "description", 10.00);
		
		ingredientService.addIngredientToPlate(p1.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i1.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i2.getIngredientId());
		
		ingredientService.addIngredientToPlate(p2.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p2.getPlateId(), i1.getIngredientId());
		
		ingredientService.addIngredientToPlate(p3.getPlateId(), i2.getIngredientId());
		
		ingredientService.addIngredientToPlate(p4.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p4.getPlateId(), i1.getIngredientId());
		
		ingredientService.addIngredientToPlate(p5.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p5.getPlateId(), i1.getIngredientId());
		
		List<Plate> mp1 = ingredientService.findPlatesByPrice(3D, 9D);
		assertEquals(3, mp1.size());
		
		List<Plate> mp2 = ingredientService.findPlatesByKCal(550, 650);
		assertEquals(1, mp2.size());
		
		List<Plate> mp3 = ingredientService.findPlatesByEnergy(0, 100, 0, 100, 0, 100, 0, 100);
		assertEquals(5, mp3.size());
	}
	
	@Test
	public void testGenerateMenu() throws DuplicateInstanceException, InstanceNotFoundException {
		Ingredient i0 = ingredientService.addIngredient("i0", "description", 15, 25, 20, 40, 525);
		Ingredient i1 = ingredientService.addIngredient("i1", "description", 40, 30, 20, 10, 165);
		Ingredient i2 = ingredientService.addIngredient("i2", "description", 30, 20, 10, 40, 625);
		Ingredient i3 = ingredientService.addIngredient("i3", "description", 20, 10, 40, 30, 105);
		Ingredient i4 = ingredientService.addIngredient("i4", "description", 10, 20, 30, 40, 125);
		Ingredient i5 = ingredientService.addIngredient("i5", "description", 10, 20, 30, 40, 15);
		Ingredient i6 = ingredientService.addIngredient("i6", "description", 10, 20, 30, 40, 115);
		Ingredient i7 = ingredientService.addIngredient("i7", "description", 10, 20, 30, 40, 25);
		Ingredient i8 = ingredientService.addIngredient("i8", "description", 10, 20, 30, 40, 122);
		Ingredient i9 = ingredientService.addIngredient("i9", "description", 10, 20, 30, 40, 195);
		
		Plate p1 = ingredientService.addPlate("plate1", "description", 2.00);
		Plate p2 = ingredientService.addPlate("plate2", "description", 4.00);
		Plate p3 = ingredientService.addPlate("plate3", "description", 6.00);
		Plate p4 = ingredientService.addPlate("plate4", "description", 8.00);
		Plate p5 = ingredientService.addPlate("plate5", "description", 10.00);
		
		ingredientService.addIngredientToPlate(p1.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i1.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i2.getIngredientId());
		ingredientService.addIngredientToPlate(p1.getPlateId(), i3.getIngredientId());
		
		ingredientService.addIngredientToPlate(p2.getPlateId(), i4.getIngredientId());
		ingredientService.addIngredientToPlate(p2.getPlateId(), i5.getIngredientId());
		ingredientService.addIngredientToPlate(p2.getPlateId(), i6.getIngredientId());
		ingredientService.addIngredientToPlate(p2.getPlateId(), i7.getIngredientId());
		ingredientService.addIngredientToPlate(p2.getPlateId(), i8.getIngredientId());
		
		ingredientService.addIngredientToPlate(p3.getPlateId(), i9.getIngredientId());
		ingredientService.addIngredientToPlate(p3.getPlateId(), i0.getIngredientId());
		ingredientService.addIngredientToPlate(p3.getPlateId(), i1.getIngredientId());
		
		ingredientService.addIngredientToPlate(p4.getPlateId(), i2.getIngredientId());
		ingredientService.addIngredientToPlate(p4.getPlateId(), i3.getIngredientId());
		ingredientService.addIngredientToPlate(p4.getPlateId(), i4.getIngredientId());
		ingredientService.addIngredientToPlate(p4.getPlateId(), i5.getIngredientId());
		ingredientService.addIngredientToPlate(p4.getPlateId(), i6.getIngredientId());
		ingredientService.addIngredientToPlate(p4.getPlateId(), i7.getIngredientId());
		
		ingredientService.addIngredientToPlate(p5.getPlateId(), i8.getIngredientId());
		ingredientService.addIngredientToPlate(p5.getPlateId(), i9.getIngredientId());
		ingredientService.addIngredientToPlate(p5.getPlateId(), i0.getIngredientId());
		
		List<List<Plate>> myMenus = ingredientService.generateMenu(0D, 50D, 0, 10000, 0, 100, 0, 100, 0, 100, 0, 100, 40);
		assertEquals(31, myMenus.size());
		
		List<List<Plate>> myMenus2 = ingredientService.generateMenu(0D, 10D, 0, 10000, 0, 100, 0, 100, 0, 100, 0, 100, 40);
		assertEquals(9, myMenus2.size());
	}

}
