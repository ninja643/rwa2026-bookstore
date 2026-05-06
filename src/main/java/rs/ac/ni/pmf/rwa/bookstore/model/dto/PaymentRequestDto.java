package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.PaymentMethod;

import java.math.BigDecimal;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestDto
{
	@NotNull
	PaymentMethod paymentMethod;

	@NotNull
	@Positive
	BigDecimal amount;
}
