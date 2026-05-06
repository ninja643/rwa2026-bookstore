package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderDto
{
	Long id;
	Long userId;
	AddressDto address;
	OrderStatus status;
	List<OrderItemDto> items;
	BigDecimal totalPrice;
	PaymentDto payment;
	List<CouponSummaryDto> coupons;
	LocalDateTime createdAt;
}
