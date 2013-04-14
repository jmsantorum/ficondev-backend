package es.ficondev.web.modelutil.model.ingredient;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import es.ficondev.web.modelutil.model.account.physical.legal.provider.ProviderIngredient;
import es.ficondev.web.modelutil.model.plate.PlateIngredient;

@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1669022934514930845L;

	private Long	ingredientId;

	private String	name;
	private String	description;

	// Ingredients: http://www.bedca.net/bdpub/
	// Total energy distribution (Total: 100%)
	private Integer protein;
	private Integer fat;
	private Integer carbohydrates;
	private Integer alcohol;

	private Integer kcal;

	private Set<PlateIngredient>	plateIngredients	= new HashSet<PlateIngredient>();
	private Set<ProviderIngredient>	providerIngredients	= new HashSet<ProviderIngredient>();

	private Long	version;

	public Ingredient() {
	}

	public Ingredient(String name, String description, Integer protein, Integer fat, Integer carbohydrates, Integer alcohol, Integer kcal) {
		this.name = name;
		this.description = description;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrates = carbohydrates;
		this.alcohol = alcohol;
		this.kcal = kcal;
	}
	
	
	@SequenceGenerator( // It only takes effect for
			name = "IngredientIdGenerator", // databases providing identifier
			sequenceName = "IngredientSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "IngredientIdGenerator")
	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "pk.ingredient",
			orphanRemoval = true
			)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Set<PlateIngredient> getPlateIngredients() {
		return plateIngredients;
	}	

	public void setPlateIngredients(Set<PlateIngredient> plateIngredients) {
		this.plateIngredients = plateIngredients;
	}	

	public void addPlateIngredient(PlateIngredient plateIngredient){
		this.plateIngredients.add(plateIngredient);
	}

	public void removePlateIngredient(PlateIngredient plateIngredient) {
		this.plateIngredients.remove(plateIngredient);
	}

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "pk.ingredient",
			orphanRemoval = true
			)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Set<ProviderIngredient> getProviderIngredients() {
		return providerIngredients;
	}

	public void setProviderIngredients(
			Set<ProviderIngredient> providerIngredients) {
		this.providerIngredients = providerIngredients;
	}

	public void addProviderIngredient(ProviderIngredient providerIngredient){
		this.providerIngredients.add(providerIngredient);
	}

	public void removeProviderIngredient(ProviderIngredient providerIngredient) {
		this.providerIngredients.remove(providerIngredient);
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
