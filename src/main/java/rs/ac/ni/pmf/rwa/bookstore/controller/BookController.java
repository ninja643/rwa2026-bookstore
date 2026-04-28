package rs.ac.ni.pmf.rwa.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.model.Book;
import rs.ac.ni.pmf.rwa.bookstore.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController
{
	private final BookService _bookService;

	@GetMapping
	public List<Book> getAllBooks()
	{
		return _bookService.getAllBooks();
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable final Long id)
	{
		return _bookService.getBookById(id)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody final Book book)
	{
		return _bookService.createBook(book);
	}

	@PutMapping("/{id}")
	public Book updateBook(@PathVariable final Long id, @RequestBody final Book book)
	{
		return _bookService.updateBook(id, book)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable final Long id)
	{
		if (!_bookService.deleteBook(id))
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
		}
	}
}
