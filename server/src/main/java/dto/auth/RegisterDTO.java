package dto.auth;

import model.utility.Gender;

import java.time.LocalDate;

public class RegisterDTO {
	public String username;
	public String password;
	public String firstName;
	public String lastName;
	public String dateOfBirth;
	public String gender;
	
	public RegisterDTO() {}
	
	public Gender getResolveGender() {
		if(gender.equalsIgnoreCase("male")) {
			return Gender.MALE;
		}
		
		return Gender.FEMALE;
	}
	
	public LocalDate getParsedDateOfBirth() {
		return LocalDate.parse(dateOfBirth);
	}
}
