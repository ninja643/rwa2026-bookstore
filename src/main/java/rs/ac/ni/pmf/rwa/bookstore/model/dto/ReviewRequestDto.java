package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReviewRequestDto
{
	@NotNull
	Long userId;

	@NotNull
	@Min(1)
	@Max(5)
	Integer rating;

	String comment;
}
