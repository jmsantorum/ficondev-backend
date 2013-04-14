package es.ficondev.web.modelutil.model.plate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Plate implements Serializable {

	private static final long serialVersionUID = 7520833127834912862L;

	private Long	plateId;

	private String	name;
	private String	description;
	private Double	price;

	private Set<PlateIngredient>	plateIngredients	= new HashSet<PlateIngredient>();

	private Long	version;

	public Plate() {
	}
	
	public Plate(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	@SequenceGenerator( // It only takes effect for
			name = "PlateIdGenerator", // databases providing identifier
			sequenceName = "PlateSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PlateIdGenerator")
	public Long getPlateId() {
		return plateId;
	}

	public void setPlateId(Long plateId) {
		this.plateId = plateId;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@OneToMany(
			mappedBy = "pk.plate",
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

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
