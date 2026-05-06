package rs.ac.ni.pmf.rwa.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderItemDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.OrderItemEntity;

@Component
@RequiredArgsConstructor
public class OrderItemMapper
{
	private final BookMapper _bookMapper;

	public OrderItemDto toDto(final OrderItemEntity entity)
	{
		return OrderItemDto.builder()
		                   .id(entity.getId())
		                   .book(_bookMapper.toSummaryDto(entity.getBook()))
		                   .quantity(entity.getQuantity())
		                   .priceAtPurchase(entity.getPriceAtPurchase())
		                   .build();
	}
}
