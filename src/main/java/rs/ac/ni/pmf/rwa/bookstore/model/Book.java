package rs.ac.ni.pmf.rwa.bookstore.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Book
{
	Long id;
	String isbn;
	String title;
	String author;
	String publisher;
	Integer publicationYear;
	String genre;
	Integer pageCount;
	Double price;
	Integer stockQuantity;
}
