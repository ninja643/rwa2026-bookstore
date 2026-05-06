package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryRequestDto
{
	@NotBlank
	String name;

	String description;
	Long parentId;
}
