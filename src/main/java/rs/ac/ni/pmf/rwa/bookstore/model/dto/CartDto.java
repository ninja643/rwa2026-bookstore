package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartDto
{
	Long id;
	List<CartItemDto> items;
	BigDecimal totalPrice;
}
