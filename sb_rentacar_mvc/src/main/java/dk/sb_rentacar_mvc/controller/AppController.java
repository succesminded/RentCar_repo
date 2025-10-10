package dk.sb_rentacar_mvc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dk.sb_rentacar_mvc.dto.ErrorDto;
import dk.sb_rentacar_mvc.dto.ResDto;
import dk.sb_rentacar_mvc.service.AppService;

@Controller
public class AppController {

	private AppService service;

	@Autowired
	public AppController(AppService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/")
	public String getIndex(Model model)
	{
		
		return "cars.html";
	}
	
	@GetMapping("/cars")
	public String getAvailableCars(
			Model model,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate)
	{
		ResDto resDto = this.service.getAvailableCars(startDate, endDate);
		model.addAttribute("resDto", resDto);
		
		return "cars.html";
	}
	
	@PostMapping("/cars/startres")
	public String startReservation(
			Model model,
			@RequestParam("carId") Integer carId,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("endDate") LocalDate endDate
			)
	{
		
		ResDto resDto = this.service.chosenCarData(carId, startDate, endDate);
		model.addAttribute("resDto", resDto);
		
		return "carres.html";
	}
	
	@PostMapping("/car/finalizeres")
	public String saveReservation(
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
		model.addAttribute("errorDto", errorDto);
		
		return "end.html";
	}
	
}
