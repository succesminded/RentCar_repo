package dk.sb_rentacar_mvc.dto;

public class CarDto {
	
	private Integer id;
	private String type;
	private String plateNumber;
	private String color;
	private boolean active;
	private int fee;
	
	
	public CarDto(Integer id, String type, String plateNumber, String color, boolean active, int fee) {
		super();
		this.id = id;
		this.type = type;
		this.plateNumber = plateNumber;
		this.color = color;
		this.active = active;
		this.fee = fee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
