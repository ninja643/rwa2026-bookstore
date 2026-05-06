package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AuthorDto
{
	Long id;
	String firstName;
	String lastName;
	String biography;
}
