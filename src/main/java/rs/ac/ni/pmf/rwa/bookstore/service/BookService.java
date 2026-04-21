package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.model.Book;
import rs.ac.ni.pmf.rwa.bookstore.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService
{
	public List<Book> getAllBooks()
	{
		return BookRepository.BOOKS.values().stream().toList();
	}

	public Optional<Book> getBookById(final Long id)
	{
		return Optional.ofNullable(BookRepository.BOOKS.get(id));
	}

	public Book createBook(final Book book)
	{
		final Long id = BookRepository.nextId();
		final Book createdBook = Book.builder()
		                             .id(id)
		                             .isbn(book.getIsbn())
		                             .title(book.getTitle())
		                             .author(book.getAuthor())
		                             .publisher(book.getPublisher())
		                             .publicationYear(book.getPublicationYear())
		                             .genre(book.getGenre())
		                             .pageCount(book.getPageCount())
		                             .price(book.getPrice())
		                             .stockQuantity(book.getStockQuantity())
		                             .build();

		BookRepository.BOOKS.put(id, createdBook);
		return createdBook;
	}

	public Optional<Book> updateBook(final Long id, final Book book)
	{
		if (!BookRepository.BOOKS.containsKey(id))
		{
			return Optional.empty();
		}

		final Book updatedBook = Book.builder()
		                             .id(id)
		                             .isbn(book.getIsbn())
		                             .title(book.getTitle())
		                             .author(book.getAuthor())
		                             .publisher(book.getPublisher())
		                             .publicationYear(book.getPublicationYear())
		                             .genre(book.getGenre())
		                             .pageCount(book.getPageCount())
		                             .price(book.getPrice())
		                             .stockQuantity(book.getStockQuantity())
		                             .build();

		BookRepository.BOOKS.put(id, updatedBook);
		return Optional.of(updatedBook);
	}

	public boolean deleteBook(final Long id)
	{
		return BookRepository.BOOKS.remove(id) != null;
	}
}
