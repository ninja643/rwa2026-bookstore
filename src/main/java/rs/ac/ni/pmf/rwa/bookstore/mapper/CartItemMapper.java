package rs.ac.ni.pmf.rwa.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartItemDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CartItemEntity;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CartItemMapper
{
	private final BookMapper _bookMapper;

	public CartItemDto toDto(final CartItemEntity entity)
	{
		final BigDecimal subtotal = entity.getBook().getPrice()
		                                 .multiply(BigDecimal.valueOf(entity.getQuantity()));

		return CartItemDto.builder()
		                  .id(entity.getId())
		                  .book(_bookMapper.toSummaryDto(entity.getBook()))
		                  .quantity(entity.getQuantity())
		                  .subtotal(subtotal)
		                  .build();
	}
}
