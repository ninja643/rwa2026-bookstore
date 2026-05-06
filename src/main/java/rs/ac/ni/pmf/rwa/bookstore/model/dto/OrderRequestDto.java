package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderRequestDto
{
	@NotNull
	Long userId;

	@NotNull
	Long addressId;

	List<String> couponCodes;
}
