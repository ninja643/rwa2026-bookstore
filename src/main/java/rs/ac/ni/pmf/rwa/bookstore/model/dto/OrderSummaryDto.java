package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderSummaryDto
{
	Long id;
	Long userId;
	OrderStatus status;
	BigDecimal totalPrice;
	LocalDateTime createdAt;
}
