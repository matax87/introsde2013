package introsde.assignment2.rest.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "measure")
@XmlType(name = "MeasureType", propOrder = { "value", "created"	})
public class Measure {
	
	private Long mid;	
	private String value;
	private Date created;
	
	public Measure() {
		super();
	}

	public Measure(Long mid, String value, Date created) {
		this();
		this.mid = mid; 
		this.value = value;
		this.created = created;
	}

	@XmlAttribute
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	@XmlElement(required = true)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlElement
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
