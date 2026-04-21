package rs.ac.ni.pmf.rwa.bookstore.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.ni.pmf.rwa.bookstore.model.Book;
import rs.ac.ni.pmf.rwa.bookstore.repository.BookRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceTest
{
	private final BookService bookService = new BookService();
	private Map<Long, Book> originalBooks;

	@BeforeEach
	void setUp()
	{
		originalBooks = new HashMap<>(BookRepository.BOOKS);
	}

	@AfterEach
	void tearDown()
	{
		BookRepository.BOOKS.clear();
		BookRepository.BOOKS.putAll(originalBooks);
	}

	@Test
	void getAllBooksReturnsAllBooks()
	{
		final int expectedSize = BookRepository.BOOKS.size();

		assertThat(bookService.getAllBooks()).hasSize(expectedSize);
	}

	@Test
	void getBookByIdReturnsBookWhenExists()
	{
		assertThat(bookService.getBookById(1L)).isPresent();
	}

	@Test
	void getBookByIdReturnsEmptyWhenNotExists()
	{
		assertThat(bookService.getBookById(999L)).isEmpty();
	}

	@Test
	void createBookCreatesBookWithGeneratedId()
	{
		final Book input = Book.builder()
		                       .isbn("9780000000001")
		                       .title("New Book")
		                       .author("New Author")
		                       .publisher("New Publisher")
		                       .publicationYear(2026)
		                       .genre("Test")
		                       .pageCount(250)
		                       .price(19.99)
		                       .stockQuantity(12)
		                       .build();

		final Book created = bookService.createBook(input);

		assertThat(created.getId()).isNotNull();
		assertThat(created.getTitle()).isEqualTo("New Book");
		assertThat(BookRepository.BOOKS.get(created.getId())).isEqualTo(created);
	}

	@Test
	void updateBookReturnsUpdatedBookWhenExists()
	{
		final Book update = Book.builder()
		                        .isbn("9780000000002")
		                        .title("Updated Book")
		                        .author("Updated Author")
		                        .publisher("Updated Publisher")
		                        .publicationYear(2025)
		                        .genre("Updated")
		                        .pageCount(300)
		                        .price(24.99)
		                        .stockQuantity(5)
		                        .build();

		final var result = bookService.updateBook(1L, update);

		assertThat(result).isPresent();
		assertThat(result.get().getTitle()).isEqualTo("Updated Book");
		assertThat(BookRepository.BOOKS.get(1L).getTitle()).isEqualTo("Updated Book");
	}

	@Test
	void updateBookReturnsEmptyWhenNotExists()
	{
		final Book update = Book.builder().title("Updated Book").build();

		assertThat(bookService.updateBook(999L, update)).isEmpty();
	}

	@Test
	void deleteBookReturnsTrueWhenExists()
	{
		assertThat(bookService.deleteBook(1L)).isTrue();
		assertThat(BookRepository.BOOKS).doesNotContainKey(1L);
	}

	@Test
	void deleteBookReturnsFalseWhenNotExists()
	{
		assertThat(bookService.deleteBook(999L)).isFalse();
	}
}
