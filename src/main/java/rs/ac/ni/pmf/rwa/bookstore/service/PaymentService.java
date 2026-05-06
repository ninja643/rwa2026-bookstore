package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.ni.pmf.rwa.bookstore.exception.InvalidOperationException;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.PaymentMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.OrderStatus;
import rs.ac.ni.pmf.rwa.bookstore.model.PaymentStatus;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PaymentDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PaymentRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.OrderEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PaymentEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.OrderRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.PaymentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService
{
	private final PaymentRepository _paymentRepository;
	private final OrderRepository _orderRepository;
	private final PaymentMapper _paymentMapper;

	public PaymentDto getPaymentById(final Long id)
	{
		return _paymentRepository.findById(id)
		                         .map(_paymentMapper::toDto)
		                         .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
	}

	public PaymentDto getPaymentByOrderId(final Long orderId)
	{
		if (!_orderRepository.existsById(orderId))
		{
			throw new ResourceNotFoundException("Order not found with id: " + orderId);
		}

		return _paymentRepository.findByOrderId(orderId)
		                         .map(_paymentMapper::toDto)
		                         .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order: " + orderId));
	}

	@Transactional
	public PaymentDto processPayment(final Long orderId, final PaymentRequestDto dto)
	{
		final OrderEntity order = _orderRepository.findById(orderId)
		                                          .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

		if (order.getStatus() == OrderStatus.CANCELLED)
		{
			throw new InvalidOperationException("Cannot process payment for a cancelled order");
		}

		if (_paymentRepository.findByOrderId(orderId).isPresent())
		{
			throw new InvalidOperationException("Payment already exists for order: " + orderId);
		}

		final PaymentEntity payment = _paymentMapper.toEntity(dto);
		payment.setOrder(order);
		payment.setStatus(PaymentStatus.COMPLETED);
		payment.setPaymentDate(LocalDateTime.now());

		final PaymentEntity saved = _paymentRepository.save(payment);

		order.setStatus(OrderStatus.CONFIRMED);
		_orderRepository.save(order);

		return _paymentMapper.toDto(saved);
	}
}
