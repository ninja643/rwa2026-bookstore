package rs.ac.ni.pmf.rwa.bookstore.model;

import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class User
{
	Long id;
	String username;
	String firstName;
	String lastName;
	String email;
	String phoneNumber;
}
