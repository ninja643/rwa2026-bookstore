package rs.ac.ni.pmf.rwa.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderItemDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.OrderEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper
{
	private final AddressMapper _addressMapper;
	private final OrderItemMapper _orderItemMapper;
	private final PaymentMapper _paymentMapper;
	private final CouponMapper _couponMapper;

	public OrderDto toDto(final OrderEntity entity)
	{
		final List<OrderItemDto> items = entity.getItems().stream()
		                                       .map(_orderItemMapper::toDto)
		                                       .toList();

		final List<CouponSummaryDto> coupons = entity.getCoupons().stream()
		                                             .map(_couponMapper::toSummaryDto)
		                                             .toList();

		return OrderDto.builder()
		               .id(entity.getId())
		               .userId(entity.getUser().getId())
		               .address(_addressMapper.toDto(entity.getAddress()))
		               .status(entity.getStatus())
		               .items(items)
		               .totalPrice(entity.getTotalPrice())
		               .payment(entity.getPayment() != null ? _paymentMapper.toDto(entity.getPayment()) : null)
		               .coupons(coupons)
		               .createdAt(entity.getCreatedAt())
		               .build();
	}

	public OrderSummaryDto toSummaryDto(final OrderEntity entity)
	{
		return OrderSummaryDto.builder()
		                      .id(entity.getId())
		                      .userId(entity.getUser().getId())
		                      .status(entity.getStatus())
		                      .totalPrice(entity.getTotalPrice())
		                      .createdAt(entity.getCreatedAt())
		                      .build();
	}
}
