package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CouponDto
{
	Long id;
	String code;
	BigDecimal discountValue;
	DiscountType discountType;
	LocalDateTime validFrom;
	LocalDateTime validTo;
	Integer usageLimit;
	Integer usageCount;
	Boolean isActive;
}
