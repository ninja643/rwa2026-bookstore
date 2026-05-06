package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.CartMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CartItemRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CartEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CartItemEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.BookRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.CartItemRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.CartRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService
{
	private final CartRepository _cartRepository;
	private final CartItemRepository _cartItemRepository;
	private final BookRepository _bookRepository;
	private final UserRepository _userRepository;
	private final CartMapper _cartMapper;

	public CartDto getCart(final Long userId)
	{
		final CartEntity cart = getOrCreateCart(userId);
		return _cartMapper.toDto(cart);
	}

	@Transactional
	public CartDto addItem(final Long userId, final CartItemRequestDto dto)
	{
		final CartEntity cart = getOrCreateCart(userId);

		final BookEntity book = _bookRepository.findById(dto.getBookId())
		                                       .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getBookId()));

		final Optional<CartItemEntity> existing = _cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId());

		if (existing.isPresent())
		{
			existing.get().setQuantity(dto.getQuantity());
			_cartItemRepository.save(existing.get());
		}
		else
		{
			final CartItemEntity item = CartItemEntity.builder()
			                                          .cart(cart)
			                                          .book(book)
			                                          .quantity(dto.getQuantity())
			                                          .build();
			_cartItemRepository.save(item);
		}

		return _cartMapper.toDto(_cartRepository.findById(cart.getId()).orElseThrow());
	}

	@Transactional
	public CartDto updateItem(final Long userId, final Long itemId, final CartItemRequestDto dto)
	{
		final CartEntity cart = findCartByUserId(userId);

		final CartItemEntity item = _cartItemRepository.findById(itemId)
		                                               .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

		if (!item.getCart().getId().equals(cart.getId()))
		{
			throw new ResourceNotFoundException("Cart item not found with id: " + itemId + " for user: " + userId);
		}

		final Integer quantity = dto.getQuantity();

		if (quantity == null || quantity == 0)
		{
			_cartItemRepository.deleteById(itemId);
		}
		else
		{
			item.setQuantity(quantity);
			_cartItemRepository.save(item);
		}

		return _cartMapper.toDto(_cartRepository.findById(cart.getId()).orElseThrow());
	}

	@Transactional
	public CartDto removeItem(final Long userId, final Long itemId)
	{
		final CartEntity cart = findCartByUserId(userId);

		final CartItemEntity item = _cartItemRepository.findById(itemId)
		                                               .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

		if (!item.getCart().getId().equals(cart.getId()))
		{
			throw new ResourceNotFoundException("Cart item not found with id: " + itemId + " for user: " + userId);
		}

		_cartItemRepository.deleteById(itemId);

		return _cartMapper.toDto(_cartRepository.findById(cart.getId()).orElseThrow());
	}

	@Transactional
	public void clearCart(final Long userId)
	{
		final CartEntity cart = findCartByUserId(userId);
		cart.getItems().clear();
		_cartRepository.save(cart);
	}

	CartEntity findCartByUserId(final Long userId)
	{
		return _cartRepository.findByUserId(userId)
		                      .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
	}

	private CartEntity getOrCreateCart(final Long userId)
	{
		return _cartRepository.findByUserId(userId).orElseGet(() -> {
			final UserEntity user = _userRepository.findById(userId)
			                                       .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

			return _cartRepository.save(CartEntity.builder().user(user).build());
		});
	}
}
