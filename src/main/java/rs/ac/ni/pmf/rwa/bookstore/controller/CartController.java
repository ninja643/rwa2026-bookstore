package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartItemRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.CartService;

@RestController
@RequestMapping("/api/v1/users/{userId}/cart")
@RequiredArgsConstructor
public class CartController
{
	private final CartService _cartService;

	@GetMapping
	public CartDto getCart(@PathVariable final Long userId)
	{
		return _cartService.getCart(userId);
	}

	@PostMapping("/items")
	@ResponseStatus(HttpStatus.CREATED)
	public CartDto addItem(@PathVariable final Long userId,
	                       @RequestBody @Valid final CartItemRequestDto dto)
	{
		return _cartService.addItem(userId, dto);
	}

	@PutMapping("/items/{itemId}")
	public CartDto updateItem(@PathVariable final Long userId,
	                          @PathVariable final Long itemId,
	                          @RequestBody @Valid final CartItemRequestDto dto)
	{
		return _cartService.updateItem(userId, itemId, dto);
	}

	@DeleteMapping("/items/{itemId}")
	public CartDto removeItem(@PathVariable final Long userId,
	                          @PathVariable final Long itemId)
	{
		return _cartService.removeItem(userId, itemId);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void clearCart(@PathVariable final Long userId)
	{
		_cartService.clearCart(userId);
	}
}
