package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class InventoryLogRequestDto
{
	@NotNull
	Long bookId;

	@NotNull
	Integer changeAmount;

	String reason;
}
