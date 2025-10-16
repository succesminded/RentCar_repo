package dk.sb_rentacar_mvc.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.sb_rentacar_mvc.db.CarRepository;
import dk.sb_rentacar_mvc.db.ReservationRepository;
import dk.sb_rentacar_mvc.dto.AdminDto;
import dk.sb_rentacar_mvc.dto.CarDto;
import dk.sb_rentacar_mvc.dto.ErrorDto;
import dk.sb_rentacar_mvc.dto.ResDto;
import dk.sb_rentacar_mvc.dto.ReservationDto;
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
		
		/** bérlés kezdő és végdátuma közötti napok számának kiszámítása */
		long daysBetween = getDaysBetween(startDate, endDate);
		
		if(daysBetween >= 1)
		{
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
											car.getFee(),
											car.getImage()
											);
				carDtoList.add(carDto);
			}
			
			resDto = new ResDto(carDtoList, startDate, endDate, daysBetween);
		}
		else
		{
			resDto = new ResDto(carDtoList, startDate, endDate, daysBetween);
		}
		
		
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
										car.getFee(),
										car.getImage()
										);
			carDtoList.add(carDto);
		}
		
		/** bérlés kezdő és végdátuma közötti napok számának kiszámítása */
		long daysBetween = getDaysBetween(startDate, endDate);
		
		resDto = new ResDto(carDtoList, startDate, endDate, daysBetween);
		
		return resDto;
	}


	private long getDaysBetween(LocalDate startDate, LocalDate endDate) {
		
		long result = 0;

	    result = ChronoUnit.DAYS.between(startDate, endDate) + 1; //+1, mert a kezdő-és végdátumot is egy-egy napnak számoljuk
		
		return result;
	}


	public ErrorDto saveReservation(Integer carId, 
									LocalDate startDate, 
									LocalDate endDate,
									String userName, 
									String userAddress, 
									String userEmail,
									String userPhone) {
		
		ErrorDto errorDto = new ErrorDto(0);
		
		/** lekérjük a megadott intervallumban a foglalt autók listáját */
		List<Integer> resCarList = this.resRepository.getCarIdReseverdInUserDateRange(startDate, endDate);
		/** lekérjük a carREpo-ból, hogy az adott autó bérelhető-e (létezik-e)*/
		Integer carActive = this.carRepository.getActiveByCarId(carId);
		
		if(carActive != null)
		{
			/** ellenőrizzük, hogy a kapott carId benne van-e a foglalt autók listájában vagy nem foglalható */
			boolean carNotAvailable = resCarList.contains(carId) || carActive == 0;
						
			//ha nincs benne a listában, akkor mentés - code=1
			if(carNotAvailable == false)
			{	try
				{
					Reservation res = new Reservation(null, userName, userAddress, userEmail, userPhone, startDate, endDate, carId);
					this.resRepository.save(res);
					errorDto.setCode(1);
				}
				catch(Exception e)
				{
					//resRepo mentési hiba esetén - code=0
					errorDto.setCode(0);
				}
			}
			//ha benne van a listában, akkor nincs mentés - code=0
			else
			{
				errorDto.setCode(0);
			}
		}
		
		return errorDto;
	}


	public AdminDto getAdminDto() {
		
		AdminDto adminDto = null;
		List<CarDto> carDtoList = new ArrayList<>();
		List<ReservationDto> resDtoList = new ArrayList<>();
		
		/** Autók adatainak (minden autó) lekérése carRepo-ból */
		Iterable<Car> carList = this.carRepository.findAll();
		
		/** carDtoList feltöltése autókkal (carDto-kal) */
		for(Car car : carList)
		{
			CarDto carDto = new CarDto(
										car.getId(),
										car.getType(),
										car.getPlateNumber(),
										car.getColor(),
										car.isActive(),
										car.getFee(),
										car.getImage()
										);
			carDtoList.add(carDto);
		}
		
		/** Bérlések adatainak lekérése resRepo-ból */
		Iterable<Reservation> resList = this.resRepository.findAll();
		
		/** ReservationDtoList feltöltése carDto-val és foglalási adatokkal */
		CarDto carDto = null;
		
		for (Reservation res : resList)
		{
			Optional<Car> carOpt = this.carRepository.findById(res.getCarId());
			if(carOpt.isEmpty() == false)
			{
				Car car = carOpt.get();
				carDto = new CarDto(
									car.getId(),
									car.getType(),
									car.getPlateNumber(),
									car.getColor(),
									car.isActive(),
									car.getFee(),
									car.getImage()
									);
			}
			
			ReservationDto resDto = new ReservationDto(
														carDto,
														res.getStartDate(),
														res.getEndDate(),
														res.getUserName(),
														res.getUserAddress(),
														res.getUserEmail(),
														res.getUserPhone());
			resDtoList.add(resDto);
		}
		
		adminDto = new AdminDto(
								resDtoList,
								carDtoList);
		
		return adminDto;
	}


	public CarDto getCarDataForModification(Integer carId) {

		CarDto carDto = null;
		
		Optional<Car> carOpt = this.carRepository.findById(carId);
		if(carOpt.isEmpty() == false)
		{
			Car car = carOpt.get();
			carDto = new CarDto(
								car.getId(),
								car.getType(),
								car.getPlateNumber(),
								car.getColor(),
								car.isActive(),
								car.getFee(),
								car.getImage()
								);
		}
		
		return carDto;
	}


	public AdminDto saveCarDataModification(Integer carId, 
											String type, 
											String plateNumber, 
											String color,
											boolean active, 
											int fee,
											byte[] image) {
		
		AdminDto adminDto = null;
		Car car = null;
		Car carRepoData = null;
		
		/** ha az adott autó active státuszát akarja deaktiválni, akkor carId alapján ellenőrizni kell, 
		 * hogy az autó szerepel-e aktuális dátum szerint folyamatban lévő foglalásban. 
		 * Amennyiben igen, nem szabad végrehajtani a módosítást */
		
		if(carId == null)
		{
			car = new Car(
							carId,
							type,
							plateNumber,
							color,
							active,
							fee,
							image
							);
		}
		else
		{
			Optional<Car> carOpt = this.carRepository.findById(carId);
			
			if(carOpt.isEmpty() == false)
			{
				carRepoData = carOpt.get();
			}
	
			/** ha deaktiválni akarja és a carRepo-ban nincs deaktiválva*/
			if(active == false && active != carRepoData.isActive())
			{
				List<Integer> underReservationPeriod = this.resRepository.isCarInReservationPeriod(carId);
				/** ha nincs olyan foglalás a resRepo-ban, aminek időszakába a mai nap dátuma beleesik, akkor mentés*/
				
				if(underReservationPeriod.size() == 0)
				{
					car = new Car(
									carId,
									type,
									plateNumber,
									color,
									active,
									fee,
									image);
				}
				/** ha van olyan foglalás a resRepo-ban, aminek időszakába a mai nap dátuma beleesik, akkor mentés, DE active mező nem módosul*/
				else
				{
					car = new Car(
									carId,
									type,
									plateNumber,
									color,
									carRepoData.isActive(),
									fee,
									image
									);
				}
				
			}
			/** ha nem akarja deaktiválni*/
			else
			{
				car = new Car(
								carId,
								type,
								plateNumber,
								color,
								active,
								fee,
								image
								);
			}
		}
		
		/** MENTÉS */
		this.carRepository.save(car);
		
		adminDto = getAdminDto();
		
		return adminDto;
	}

}
