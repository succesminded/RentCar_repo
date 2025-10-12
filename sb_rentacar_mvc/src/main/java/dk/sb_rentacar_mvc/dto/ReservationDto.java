package dk.sb_rentacar_mvc.dto;

import java.time.LocalDate;

public class ReservationDto {

	private CarDto carDto;
	private LocalDate startDate;
	private LocalDate endDate;
	private String userName;
	private String userAddress;
	private String userEmail;
	private String userPhone;
	
	
	public ReservationDto(CarDto carDto, LocalDate startDate, LocalDate endDate, String userName, String userAddress,
			String userEmail, String userPhone) {
		super();
		this.carDto = carDto;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userName = userName;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
	}


	public CarDto getCarDto() {
		return carDto;
	}

	public void setCarDto(CarDto carDto) {
		this.carDto = carDto;
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

	
}
