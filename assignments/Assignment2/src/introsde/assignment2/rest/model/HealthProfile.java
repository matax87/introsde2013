package introsde.assignment2.rest.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HealthProfileType", propOrder = { "weight", "height", "steps" })
public class HealthProfile {

	private Double weight;
	private Double height;
	private Integer steps;
	
	public HealthProfile() {
		super();
	}
	
	public HealthProfile(Double weight, Double height, Integer steps) {
		this();
		this.weight = weight;
		this.height = height;
		this.steps = steps;
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
}
