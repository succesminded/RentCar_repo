package dk.sb_rentacar_mvc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="cars")
public class Car {

	@Id
	@Column("id")
	private Integer id;
	
	@Column("type")
	private String type;
	
	@Column("plate_number")
	private String plateNumber;
	
	@Column("color")
	private String color;
	
	@Column("active")
	private boolean active;
	
	@Column("fee")
	private int fee;

	public Car(Integer id, String type, String plateNumber, String color, boolean active, int fee) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
	
	
}
