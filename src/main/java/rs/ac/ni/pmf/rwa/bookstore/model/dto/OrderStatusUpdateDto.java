package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.OrderStatus;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderStatusUpdateDto
{
	@NotNull
	OrderStatus status;
}
