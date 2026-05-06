package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartItemRequestDto
{
	@NotNull
	Long bookId;

	@NotNull
	@Min(1)
	Integer quantity;
}
