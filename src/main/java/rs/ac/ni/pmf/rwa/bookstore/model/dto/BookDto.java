package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BookDto
{
	Long id;
	String isbn;
	String title;
	List<AuthorSummaryDto> authors;
	PublisherSummaryDto publisher;
	List<CategorySummaryDto> categories;
	Integer publicationYear;
	BigDecimal price;
	Integer stockQuantity;
	String description;
	List<BookImageDto> images;
}
