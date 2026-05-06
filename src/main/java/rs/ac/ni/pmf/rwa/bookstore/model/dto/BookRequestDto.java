package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BookRequestDto
{
	String isbn;

	@NotBlank
	String title;

	List<Long> authorIds;
	Long publisherId;
	List<Long> categoryIds;
	Integer publicationYear;

	@NotNull
	@Positive
	BigDecimal price;

	@NotNull
	@Positive
	Integer stockQuantity;

	String description;
}
