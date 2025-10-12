package dk.sb_rentacar_mvc.dto;

import java.util.List;

public class AdminDto {

	private List<ReservationDto> resDtoList;
	private List<CarDto> carDtoList;
	
	
	public AdminDto(List<ReservationDto> resDtoList, List<CarDto> carDtoList) {
		super();
		this.resDtoList = resDtoList;
		this.carDtoList = carDtoList;
	}

	
	public List<ReservationDto> getResDtoList() {
		return resDtoList;
	}

	public void setResDtoList(List<ReservationDto> resDtoList) {
		this.resDtoList = resDtoList;
	}

	public List<CarDto> getCarDtoList() {
		return carDtoList;
	}

	public void setCarDtoList(List<CarDto> carDtoList) {
		this.carDtoList = carDtoList;
	}
	
	
}
