package rs.ac.ni.pmf.rwa.bookstore.repository;

import rs.ac.ni.pmf.rwa.bookstore.model.Book;

import java.util.HashMap;
import java.util.Map;

public class BookRepository
{
	public static Map<Long, Book> BOOKS = new HashMap<>();

	static
	{
		BOOKS.put(1L, Book.builder()
		                  .id(1L)
		                  .isbn("9780134685991")
		                  .title("Effective Java")
		                  .author("Joshua Bloch")
		                  .publisher("Addison-Wesley")
		                  .publicationYear(2018)
		                  .genre("Programming")
		                  .pageCount(416)
		                  .price(49.99)
		                  .stockQuantity(10)
		                  .build());

		BOOKS.put(2L, Book.builder()
		                  .id(2L)
		                  .isbn("9781617294945")
		                  .title("Spring in Action")
		                  .author("Craig Walls")
		                  .publisher("Manning")
		                  .publicationYear(2018)
		                  .genre("Programming")
		                  .pageCount(520)
		                  .price(44.99)
		                  .stockQuantity(7)
		                  .build());
	}

	public static Long nextId()
	{
		return BOOKS.keySet()
		            .stream()
		            .max(Long::compareTo)
		            .map(id -> id + 1)
		            .orElse(1L);
	}
}
