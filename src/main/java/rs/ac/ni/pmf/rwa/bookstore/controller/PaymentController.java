package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PaymentDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PaymentRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.PaymentService;

@RestController
@RequiredArgsConstructor
public class PaymentController
{
	private final PaymentService _paymentService;

	@GetMapping("/api/v1/payments/{id}")
	public PaymentDto getPaymentById(@PathVariable final Long id)
	{
		return _paymentService.getPaymentById(id);
	}

	@GetMapping("/api/v1/orders/{orderId}/payment")
	public PaymentDto getPaymentByOrderId(@PathVariable final Long orderId)
	{
		return _paymentService.getPaymentByOrderId(orderId);
	}

	@PostMapping("/api/v1/orders/{orderId}/payment")
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentDto processPayment(@PathVariable final Long orderId,
	                                 @RequestBody @Valid final PaymentRequestDto dto)
	{
		return _paymentService.processPayment(orderId, dto);
	}
}
