package dto.user;

import java.time.LocalDate;

import model.utility.Gender;

public class UpdateUserDTO {
	public String username;
	public String password;
	public String firstname;
	public String lastname;
	public String dateOfBirth;
	public Gender gender;
	
	public UpdateUserDTO() {
		
	}
	
	public LocalDate getParsedDateOfBirth() {
		return LocalDate.parse(dateOfBirth);
	}
}
