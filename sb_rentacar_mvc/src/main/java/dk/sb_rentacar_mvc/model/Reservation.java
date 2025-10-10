package dk.sb_rentacar_mvc.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="reservations")
public class Reservation {

	@Id
	@Column("id")
	private Integer id;
	
	@Column("user_name")
	private String userName;
	
	@Column("user_address")
	private String userAddress;
	
	@Column("user_email")
	private String userEmail;
	
	@Column("user_phone")
	private String userPhone;
	
	@Column("start_date")
	private LocalDate startDate;
	
	@Column("end_date")
	private LocalDate endDate;
	
	@Column("car_id")
	private Integer carId;

	public Reservation(Integer id, String userName, String userAddress, String userEmail, String userPhone,
			LocalDate startDate, LocalDate endDate, Integer carId) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.startDate = startDate;
		this.endDate = endDate;
		this.carId = carId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	
	
}
