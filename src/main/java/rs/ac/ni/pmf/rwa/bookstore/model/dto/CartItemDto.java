package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartItemDto
{
	Long id;
	BookSummaryDto book;
	Integer quantity;
	BigDecimal subtotal;
}
