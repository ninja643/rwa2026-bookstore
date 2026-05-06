package rs.ac.ni.pmf.rwa.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartItemDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CartEntity;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper
{
	private final CartItemMapper _cartItemMapper;

	public CartDto toDto(final CartEntity entity)
	{
		final List<CartItemDto> items = entity.getItems().stream()
		                                      .map(_cartItemMapper::toDto)
		                                      .toList();

		final BigDecimal totalPrice = items.stream()
		                                   .map(CartItemDto::getSubtotal)
		                                   .reduce(BigDecimal.ZERO, BigDecimal::add);

		return CartDto.builder()
		              .id(entity.getId())
		              .items(items)
		              .totalPrice(totalPrice)
		              .build();
	}
}
