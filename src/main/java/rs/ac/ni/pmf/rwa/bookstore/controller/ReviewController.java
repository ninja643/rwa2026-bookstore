package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.ReviewDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.ReviewRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books/{bookId}/reviews")
@RequiredArgsConstructor
public class ReviewController
{
	private final ReviewService _reviewService;

	@GetMapping
	public List<ReviewDto> getReviewsByBookId(@PathVariable final Long bookId)
	{
		return _reviewService.getReviewsByBookId(bookId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ReviewDto createReview(@PathVariable final Long bookId,
	                              @RequestBody @Valid final ReviewRequestDto dto)
	{
		return _reviewService.createReview(bookId, dto);
	}

	@PutMapping("/{reviewId}")
	public ReviewDto updateReview(@PathVariable final Long bookId,
	                              @PathVariable final Long reviewId,
	                              @RequestBody @Valid final ReviewRequestDto dto)
	{
		return _reviewService.updateReview(bookId, reviewId, dto);
	}

	@DeleteMapping("/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReview(@PathVariable final Long bookId,
	                         @PathVariable final Long reviewId)
	{
		_reviewService.deleteReview(bookId, reviewId);
	}
}
