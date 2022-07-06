package dto.auth;

public class LoginResponseDTO {
	long expirationTime;
	String token;
	
	public LoginResponseDTO(){}
	public LoginResponseDTO(String token, long expirationTime){
		this.expirationTime = expirationTime;
		this.token = token;
	}
}
