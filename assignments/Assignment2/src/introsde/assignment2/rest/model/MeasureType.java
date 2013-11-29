package introsde.assignment2.rest.model;

public class MeasureType {
	
	private Long measureTypeId;
	private String name;
	private String type;
	
	public MeasureType() {
		super();
	}

	public MeasureType(Long measureTypeId, String name, String type) {
		this();
		this.measureTypeId = measureTypeId;
		this.name = name;
		this.type = type;
	}

	public Long getMeasureTypeId() {
		return measureTypeId;
	}

	public void setMeasureTypeId(Long measureTypeId) {
		this.measureTypeId = measureTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
