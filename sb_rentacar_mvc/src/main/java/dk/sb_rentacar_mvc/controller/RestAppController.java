package dk.sb_rentacar_mvc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.sb_rentacar_mvc.dto.ErrorDto;
import dk.sb_rentacar_mvc.dto.ResDto;
import dk.sb_rentacar_mvc.service.AppService;

@RestController
public class RestAppController {

	private AppService service;

	@Autowired
	public RestAppController(AppService service) {
		super();
		this.service = service;
	}
	
	
	@GetMapping("/availablecars")
	public ResDto getAvailableCars(
			Model model,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate)
	{
		ResDto resDto = this.service.getAvailableCars(startDate, endDate);
		
		return resDto;
	}
	
	
	/** ellenőrizni kellene, hogy az adott carId tényleg szabad-e a megadott időszakban
	 * és a megadott időszak helyes-e */
	@PostMapping("/reservation")
	public ErrorDto saveReservation(
			Model model,
			@RequestParam("carId") Integer carId,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate,
			@RequestParam("userName") String userName,
			@RequestParam("userAddress") String userAddress,
			@RequestParam("userEmail") String userEmail,
			@RequestParam("userPhone") String userPhone
			)
	{
		
		ErrorDto errorDto = this.service.saveReservation(	carId, 
															startDate, 
															endDate, 
															userName, 
															userAddress, 
															userEmail,
															userPhone);			
		
		return errorDto;
	}
}
