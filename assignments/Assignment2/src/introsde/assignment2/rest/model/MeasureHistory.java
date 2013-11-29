package introsde.assignment2.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "measure-history")
@XmlType(name = "MeasureHistoryType")
public class MeasureHistory {
	
	private List<Measure> measureList;
	
	public MeasureHistory() {
    	super();
    }

	public MeasureHistory(List<Measure> measureList) {
		this();
		this.measureList = measureList;
	}
	
	@XmlElement(name = "measure")
	public List<Measure> getMeasureList() {
		if (measureList == null) {
			measureList = new ArrayList<Measure>();
        }
        return measureList;
	}
	
	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}
}
