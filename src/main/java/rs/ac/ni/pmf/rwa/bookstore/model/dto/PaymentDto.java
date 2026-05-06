package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.PaymentMethod;
import rs.ac.ni.pmf.rwa.bookstore.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentDto
{
	Long id;
	Long orderId;
	PaymentMethod paymentMethod;
	PaymentStatus status;
	LocalDateTime paymentDate;
	BigDecimal amount;
}
