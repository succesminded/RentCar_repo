package dk.sb_rentacar_mvc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.sb_rentacar_mvc.db.CarRepository;
import dk.sb_rentacar_mvc.db.ReservationRepository;
import dk.sb_rentacar_mvc.dto.CarDto;
import dk.sb_rentacar_mvc.dto.ErrorDto;
import dk.sb_rentacar_mvc.dto.ResDto;
import dk.sb_rentacar_mvc.model.Car;
import dk.sb_rentacar_mvc.model.Reservation;

@Service
public class AppService {

	private CarRepository carRepository;
	private ReservationRepository resRepository;
	
	@Autowired
	public AppService(CarRepository carRepository, ReservationRepository resRepository) {
		super();
		this.carRepository = carRepository;
		this.resRepository = resRepository;
	}


	public ResDto getAvailableCars(LocalDate startDate, LocalDate endDate) {

		ResDto resDto = null;

		List<CarDto> carDtoList = new ArrayList<>();
		List<Car> carList = new ArrayList<>();
		List<Integer> resCarList = new ArrayList<>();
		
		/** AKTÍV AUTÓK LEKÉRDEZÉSE CARREPO-ból */
		carList = this.carRepository.getActiveCars();
		
		/** FOGLALÁSOK LEKÉRDEZÉSE RESREPO-ból */
		resCarList = this.resRepository.getCarIdReseverdInUserDateRange(startDate, endDate);
		
		/** resCarList-ben lévő carId-k törlése carList-ből */
		List<Car> removeCarList = new ArrayList<>();
		
		for(Car car : carList)
		{
			if(resCarList.contains(car.getId()))
			{
				removeCarList.add(car);
			}
		}
		
		carList.removeAll(removeCarList);
		
		/** carDtoList feltöltése a maradék carList elemekkel */
		for(Car car : carList)
		{
			CarDto carDto = new CarDto(
										car.getId(),
										car.getType(),
										car.getPlateNumber(),
										car.getColor(),
										car.isActive(),
										car.getFee());
			carDtoList.add(carDto);
		}
			
		resDto = new ResDto(carDtoList, startDate, endDate);
		
		return resDto;
	}


	public ResDto chosenCarData(Integer carId, LocalDate startDate, LocalDate endDate) {
		
		ResDto resDto = null;

		List<CarDto> carDtoList = new ArrayList<>();
		
		Optional<Car> carOpt = this.carRepository.findById(carId);
		if(carOpt.isEmpty() == false)
		{
			Car car = carOpt.get();
			CarDto carDto = new CarDto(
										car.getId(),
										car.getType(),
										car.getPlateNumber(),
										car.getColor(),
										car.isActive(),
										car.getFee());
			carDtoList.add(carDto);
		}
		
		resDto = new ResDto(carDtoList, startDate, endDate);
		
		return resDto;
	}


	public ErrorDto saveReservation(Integer carId, 
									LocalDate startDate, 
									LocalDate endDate,
									String userName, 
									String userAddress, 
									String userEmail,
									String userPhone) {
		
		ErrorDto errorDto = new ErrorDto(1);
		
		try
		{
			Reservation res = new Reservation(null, userName, userAddress, userEmail, userPhone, startDate, endDate, carId);
			this.resRepository.save(res);
		}
		catch(Exception e)
		{
			errorDto.setCode(0);
		}
		
		return errorDto;
	}

}
