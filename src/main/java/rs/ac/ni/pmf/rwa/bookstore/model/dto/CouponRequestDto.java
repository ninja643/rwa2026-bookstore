package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CouponRequestDto
{
	@NotBlank
	String code;

	@NotNull
	@Positive
	BigDecimal discountValue;

	@NotNull
	DiscountType discountType;

	LocalDateTime validFrom;
	LocalDateTime validTo;
	Integer usageLimit;
}
