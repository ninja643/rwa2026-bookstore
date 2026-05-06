package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AddressRequestDto
{
	@NotBlank
	String street;

	@NotBlank
	String city;

	String postalCode;

	@NotBlank
	String country;
}
