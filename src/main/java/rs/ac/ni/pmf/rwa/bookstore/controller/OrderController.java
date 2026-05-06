package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderStatusUpdateDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.OrderSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController
{
	private final OrderService _orderService;

	@GetMapping("/api/v1/orders")
	public List<OrderSummaryDto> getAllOrders()
	{
		return _orderService.getAllOrders();
	}

	@GetMapping("/api/v1/orders/{id}")
	public OrderDto getOrderById(@PathVariable final Long id)
	{
		return _orderService.getOrderById(id);
	}

	@GetMapping("/api/v1/users/{userId}/orders")
	public List<OrderSummaryDto> getOrdersByUserId(@PathVariable final Long userId)
	{
		return _orderService.getOrdersByUserId(userId);
	}

	@PostMapping("/api/v1/orders")
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDto createOrder(@RequestBody @Valid final OrderRequestDto dto)
	{
		return _orderService.createOrder(dto);
	}

	@PatchMapping("/api/v1/orders/{id}/status")
	public OrderDto updateOrderStatus(@PathVariable final Long id,
	                                  @RequestBody @Valid final OrderStatusUpdateDto dto)
	{
		return _orderService.updateOrderStatus(id, dto);
	}
}
