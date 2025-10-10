package dk.sb_rentacar_mvc.dto;

public class ErrorDto {

	private int code;

	public ErrorDto(int code) {
		super();
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
