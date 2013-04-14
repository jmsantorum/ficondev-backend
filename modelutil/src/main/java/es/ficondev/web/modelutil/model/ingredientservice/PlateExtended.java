package es.ficondev.web.modelutil.model.ingredientservice;

import es.ficondev.web.modelutil.model.plate.Plate;
import es.ficondev.web.modelutil.model.plate.PlateIngredient;

public class PlateExtended {
	private Double	price = 0D;
	private Integer protein = 0;
	private Integer fat = 0;
	private Integer carbohydrates = 0;
	private Integer alcohol = 0;
	private Integer kcal = 0;
	
	public PlateExtended(Plate plate) {
		price = plate.getPrice();
		for(PlateIngredient pi : plate.getPlateIngredients()) {
			protein 		+= pi.getQuantity() * pi.getIngredient().getProtein();
			fat 			+= pi.getQuantity() * pi.getIngredient().getFat();
			carbohydrates 	+= pi.getQuantity() * pi.getIngredient().getCarbohydrates();
			alcohol	 		+= pi.getQuantity() * pi.getIngredient().getAlcohol();
			kcal 			+= pi.getQuantity() * pi.getIngredient().getKcal();
		}
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getProtein() {
		return protein;
	}
	
	public void setProtein(Integer protein) {
		this.protein = protein;
	}
	
	public Integer getFat() {
		return fat;
	}
	
	public void setFat(Integer fat) {
		this.fat = fat;
	}
	
	public Integer getCarbohydrates() {
		return carbohydrates;
	}
	
	public void setCarbohydrates(Integer carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	
	public Integer getAlcohol() {
		return alcohol;
	}
	
	public void setAlcohol(Integer alcohol) {
		this.alcohol = alcohol;
	}
	
	public Integer getKcal() {
		return kcal;
	}
	
	public void setKcal(Integer kcal) {
		this.kcal = kcal;
	}
	
}
