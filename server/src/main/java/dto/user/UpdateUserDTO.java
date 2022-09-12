package dto.user;

import model.utility.Gender;

import java.time.LocalDate;

public class UpdateUserDTO {
	public String username;
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
