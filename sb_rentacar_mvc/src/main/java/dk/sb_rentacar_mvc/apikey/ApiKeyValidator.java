package dk.sb_rentacar_mvc.apikey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyValidator {

	@Value("${apiKey1}")
	private String apikey1;
	@Value("${apiKey2}")
	private String apikey2;
	
	
	public ApiKeyValidator() {
		super();
	}
	
	public boolean validApiKey(String apiKey) {
		
		boolean result = false; 
		
		if(apiKey.equals(this.apikey1) || apiKey.equals(this.apikey2))
		{
			result = true;
		}
		
		return result;
	}
	
	
}
