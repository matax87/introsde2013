package introsde.assignment3.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "HealthProfileType", propOrder = { "id", "created", "weight", "height", "steps", "calories" })
public class HealthProfile {

	private Long id;
	private Date created;
	private Double weight;
	private Double height;
	private Integer steps;
	private Integer calories;
	
	public HealthProfile() {
		super();
	}
	
	public HealthProfile(Long id, Date date, Double weight, Double height, Integer steps, Integer calories) {
		this();
		this.id = id;
		this.created = date;
		this.weight = weight;
		this.height = height;
		this.steps = steps;
		this.setCalories(calories);
	}
	
	@XmlElement(name = "hpId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@XmlElement(name = "date")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date date) {
		this.created = date;
	}

	@XmlElement
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@XmlElement
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@XmlElement
	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	@XmlElement
	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "HealthProfile [id=" + id + ", date=" + created + ", weight="
				+ weight + ", height=" + height + ", steps=" + steps
				+ ", calories=" + calories + "]";
	}
	
	
}
