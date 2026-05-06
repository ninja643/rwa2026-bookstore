package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.DiscountType;

import java.math.BigDecimal;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CouponSummaryDto
{
	String code;
	BigDecimal discountValue;
	DiscountType discountType;
}
