package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.ni.pmf.rwa.bookstore.exception.InvalidOperationException;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.OrderMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.DiscountType;
import rs.ac.ni.pmf.rwa.bookstore.model.OrderStatus;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderStatusUpdateDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.*;
import rs.ac.ni.pmf.rwa.bookstore.repository.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService
{
	private final OrderRepository _orderRepository;
	private final UserRepository _userRepository;
	private final AddressRepository _addressRepository;
	private final BookRepository _bookRepository;
	private final CartService _cartService;
	private final CouponService _couponService;
	private final OrderMapper _orderMapper;

	public List<OrderSummaryDto> getAllOrders()
	{
		return _orderRepository.findAll().stream()
		                       .map(_orderMapper::toSummaryDto)
		                       .toList();
	}

	public OrderDto getOrderById(final Long id)
	{
		return _orderRepository.findById(id)
		                       .map(_orderMapper::toDto)
		                       .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
	}

	public List<OrderSummaryDto> getOrdersByUserId(final Long userId)
	{
		if (!_userRepository.existsById(userId))
		{
			throw new ResourceNotFoundException("User not found with id: " + userId);
		}

		return _orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
		                       .map(_orderMapper::toSummaryDto)
		                       .toList();
	}

	@Transactional
	public OrderDto createOrder(final OrderRequestDto dto)
	{
		final UserEntity user = _userRepository.findById(dto.getUserId())
		                                       .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

		final AddressEntity address = _addressRepository.findById(dto.getAddressId())
		                                                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + dto.getAddressId()));

		final CartEntity cart = _cartService.findCartByUserId(dto.getUserId());

		if (cart.getItems().isEmpty())
		{
			throw new InvalidOperationException("Cannot create order from an empty cart");
		}

		final List<OrderItemEntity> orderItems = new ArrayList<>();
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (final CartItemEntity cartItem : cart.getItems())
		{
			final BookEntity book = cartItem.getBook();
			final int quantity = cartItem.getQuantity();
			final BigDecimal priceAtPurchase = book.getPrice();

			book.setStockQuantity(book.getStockQuantity() - quantity);
			_bookRepository.save(book);

			final OrderItemEntity orderItem = OrderItemEntity.builder()
			                                                 .book(book)
			                                                 .quantity(quantity)
			                                                 .priceAtPurchase(priceAtPurchase)
			                                                 .build();
			orderItems.add(orderItem);

			totalPrice = totalPrice.add(priceAtPurchase.multiply(BigDecimal.valueOf(quantity)));
		}

		final List<CouponEntity> appliedCoupons = new ArrayList<>();

		if (dto.getCouponCodes() != null)
		{
			for (final String code : dto.getCouponCodes())
			{
				final CouponEntity coupon = _couponService.findAndAssertUsable(code);
				totalPrice = applyDiscount(totalPrice, coupon);
				coupon.setUsageCount(coupon.getUsageCount() + 1);
				appliedCoupons.add(coupon);
			}
		}

		final OrderEntity order = OrderEntity.builder()
		                                     .user(user)
		                                     .address(address)
		                                     .status(OrderStatus.PENDING)
		                                     .totalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP))
		                                     .build();

		final OrderEntity savedOrder = _orderRepository.save(order);

		for (final OrderItemEntity item : orderItems)
		{
			item.setOrder(savedOrder);
		}
		savedOrder.getItems().addAll(orderItems);
		savedOrder.getCoupons().addAll(appliedCoupons);

		cart.getItems().clear();

		return _orderMapper.toDto(_orderRepository.save(savedOrder));
	}

	@Transactional
	public OrderDto updateOrderStatus(final Long id, final OrderStatusUpdateDto dto)
	{
		final OrderEntity order = _orderRepository.findById(id)
		                                          .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

		if (order.getStatus() == OrderStatus.CANCELLED)
		{
			throw new InvalidOperationException("Cannot change status of a cancelled order");
		}

		if (dto.getStatus() == OrderStatus.CANCELLED)
		{
			for (final OrderItemEntity item : order.getItems())
			{
				final BookEntity book = item.getBook();
				book.setStockQuantity(book.getStockQuantity() + item.getQuantity());
				_bookRepository.save(book);
			}
		}

		order.setStatus(dto.getStatus());

		return _orderMapper.toDto(_orderRepository.save(order));
	}

	private BigDecimal applyDiscount(final BigDecimal totalPrice, final CouponEntity coupon)
	{
		if (coupon.getDiscountType() == DiscountType.PERCENTAGE)
		{
			final BigDecimal discount = totalPrice.multiply(coupon.getDiscountValue())
			                                      .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			return totalPrice.subtract(discount).max(BigDecimal.ZERO);
		}
		else
		{
			return totalPrice.subtract(coupon.getDiscountValue()).max(BigDecimal.ZERO);
		}
	}
}
