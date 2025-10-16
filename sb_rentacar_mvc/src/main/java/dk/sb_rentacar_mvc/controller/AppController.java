package dk.sb_rentacar_mvc.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dk.sb_rentacar_mvc.apikey.ApiKeyValidator;
import dk.sb_rentacar_mvc.dto.AdminDto;
import dk.sb_rentacar_mvc.dto.CarDto;
import dk.sb_rentacar_mvc.dto.ErrorDto;
import dk.sb_rentacar_mvc.dto.ResDto;
import dk.sb_rentacar_mvc.service.AppService;

@Controller
public class AppController {

	private AppService service;
	private ApiKeyValidator apiKeyValidator;

	@Autowired
	public AppController(AppService service, ApiKeyValidator apiKeyValidator) {
		super();
		this.service = service;
		this.apiKeyValidator = apiKeyValidator;
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
	
	@GetMapping("/admin")
	public String loadAdminPage(
				Model model,
				@RequestParam("apiKey") String apiKey)
	{
		AdminDto adminDto = null;
		
		if(apiKeyValidator.validApiKey(apiKey))
		{
			adminDto = this.service.getAdminDto();
			model.addAttribute("adminDto", adminDto);
		}
		else
		{
			model.addAttribute("adminDto", adminDto);
		}
		
		return "admin.html";
	}
	
	@GetMapping("/admin/car/edit/start")
	public String getCarDataForModification(
				Model model,
				@RequestParam("apiKey") String apiKey,
				@RequestParam("carId") Integer carId)
	{
		CarDto carDto = null;
		
		if(apiKeyValidator.validApiKey(apiKey))
		{
			carDto = this.service.getCarDataForModification(carId);
			model.addAttribute("carDto", carDto);
		}
		else
		{
			model.addAttribute("carDto", carDto);
		}
		
		
		return "admin_editcar.html";
	}
	
	@PostMapping("/admin/car/edit/finish")
	public String saveCarDataModification(
				Model model,
				@RequestParam("apiKey") String apiKey,
				@RequestParam("newCar") int newCar,
				@RequestParam("carId") Integer carId,
				@RequestParam("type") String type,
				@RequestParam("plateNumber") String plateNumber,
				@RequestParam("color") String color,
				@RequestParam("active") boolean active,
				@RequestParam("fee") int fee,
				@RequestParam("image") MultipartFile image
				) throws IOException
	{
		AdminDto adminDto = null;
		
		byte[] imageToUpload = image.getBytes();
		
		if(apiKeyValidator.validApiKey(apiKey))
		{
			if(newCar == 0)
			{
				adminDto = this.service.saveCarDataModification(carId, type, plateNumber, color, active, fee, imageToUpload);
				model.addAttribute("adminDto", adminDto);
			}
			else
			{
				adminDto = this.service.saveCarDataModification(null, type, plateNumber, color, active, fee, imageToUpload);
				model.addAttribute("adminDto", adminDto);
			}
		}
		else
		{
			model.addAttribute("adminDto", adminDto);
		}
		
		
		return "admin.html";
	}
}
