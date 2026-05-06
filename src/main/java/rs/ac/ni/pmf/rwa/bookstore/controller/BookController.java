package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.BookDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.BookRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.BookSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController
{
	private final BookService _bookService;

	@GetMapping
	public List<BookSummaryDto> getAllBooks()
	{
		return _bookService.getAllBooks();
	}

	@GetMapping("/{id}")
	public BookDto getBookById(@PathVariable final Long id)
	{
		return _bookService.getBookById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDto createBook(@RequestBody @Valid final BookRequestDto dto)
	{
		return _bookService.createBook(dto);
	}

	@PutMapping("/{id}")
	public BookDto updateBook(@PathVariable final Long id,
	                          @RequestBody @Valid final BookRequestDto dto)
	{
		return _bookService.updateBook(id, dto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable final Long id)
	{
		_bookService.deleteBook(id);
	}
}
