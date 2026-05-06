package rs.ac.ni.pmf.rwa.bookstore.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserDto
{
	Long id;

	@NotBlank(message = "Username cannot be blank")
	String username;
	String firstName;
	String lastName;
	@Email(message = "Email is not valid")
	String email;

	@Pattern(regexp = "^$|.*\\S.*", message = "Must not be blank if provided")
	String phoneNumber;
}
