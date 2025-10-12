package dk.sb_rentacar_mvc.dto;

import java.time.LocalDate;
import java.util.List;

public class ResDto {

	private List<CarDto> carDtoList;
	private LocalDate startDate;
	private LocalDate endDate;
	private long daysBetween;
	private int dateProblem; //0 - rendben, 1 - startDate < today, 2 - startDate > endDate
	
	
	public ResDto(List<CarDto> carDtoList, LocalDate startDate, LocalDate endDate, long daysBetween,int dateProblem) {
		super();
		this.carDtoList = carDtoList;
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysBetween = daysBetween;
		this.dateProblem = dateProblem;
	}


	public List<CarDto> getCarDtoList() {
		return carDtoList;
	}

	public void setCarDtoList(List<CarDto> carDtoList) {
		this.carDtoList = carDtoList;
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
	
	public long getDaysBetween() {
		return daysBetween;
	}

	public void setDaysBetween(long daysBetween) {
		this.daysBetween = daysBetween;
	}

	public int getDateProblem() {
		return dateProblem;
	}

	public void setDateProblem(int dateProblem) {
		this.dateProblem = dateProblem;
	}
	
}
