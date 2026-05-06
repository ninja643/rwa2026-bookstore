package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PublisherDto
{
	Long id;
	String name;
	String city;
	String country;
}
