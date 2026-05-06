package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.DuplicateResourceException;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.ReviewMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.ReviewDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.ReviewRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.ReviewEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.BookRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.ReviewRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService
{
	private final ReviewRepository _reviewRepository;
	private final BookRepository _bookRepository;
	private final UserRepository _userRepository;
	private final ReviewMapper _reviewMapper;

	public List<ReviewDto> getReviewsByBookId(final Long bookId)
	{
		if (!_bookRepository.existsById(bookId))
		{
			throw new ResourceNotFoundException("Book not found with id: " + bookId);
		}

		return _reviewRepository.findByBookId(bookId).stream()
		                        .map(_reviewMapper::toDto)
		                        .toList();
	}

	public ReviewDto createReview(final Long bookId, final ReviewRequestDto dto)
	{
		if (_reviewRepository.existsByUserIdAndBookId(dto.getUserId(), bookId))
		{
			throw new DuplicateResourceException("User " + dto.getUserId() + " has already reviewed book " + bookId);
		}

		final BookEntity book = _bookRepository.findById(bookId)
		                                       .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

		final UserEntity user = _userRepository.findById(dto.getUserId())
		                                       .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

		final ReviewEntity entity = _reviewMapper.toEntity(dto);
		entity.setBook(book);
		entity.setUser(user);

		return _reviewMapper.toDto(_reviewRepository.save(entity));
	}

	public ReviewDto updateReview(final Long bookId, final Long reviewId, final ReviewRequestDto dto)
	{
		final ReviewEntity existing = _reviewRepository.findById(reviewId)
		                                               .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

		if (!existing.getBook().getId().equals(bookId))
		{
			throw new ResourceNotFoundException("Review not found with id: " + reviewId + " for book: " + bookId);
		}

		existing.setRating(dto.getRating());
		existing.setComment(dto.getComment());

		return _reviewMapper.toDto(_reviewRepository.save(existing));
	}

	public void deleteReview(final Long bookId, final Long reviewId)
	{
		final ReviewEntity existing = _reviewRepository.findById(reviewId)
		                                               .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

		if (!existing.getBook().getId().equals(bookId))
		{
			throw new ResourceNotFoundException("Review not found with id: " + reviewId + " for book: " + bookId);
		}

		_reviewRepository.deleteById(reviewId);
	}
}
