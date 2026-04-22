package rs.ac.ni.pmf.rwa.bookstore.model;

import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
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
