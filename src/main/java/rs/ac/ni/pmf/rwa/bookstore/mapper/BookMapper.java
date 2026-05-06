package rs.ac.ni.pmf.rwa.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.*;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookImageEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper
{
	private final AuthorMapper _authorMapper;
	private final PublisherMapper _publisherMapper;
	private final CategoryMapper _categoryMapper;

	public BookDto toDto(final BookEntity entity)
	{
		final List<AuthorSummaryDto> authors = entity.getAuthors().stream()
		                                             .map(_authorMapper::toSummaryDto)
		                                             .toList();

		final List<CategorySummaryDto> categories = entity.getCategories().stream()
		                                                  .map(_categoryMapper::toSummaryDto)
		                                                  .toList();

		final List<BookImageDto> images = entity.getImages().stream()
		                                        .map(this::toImageDto)
		                                        .toList();

		return BookDto.builder()
		              .id(entity.getId())
		              .isbn(entity.getIsbn())
		              .title(entity.getTitle())
		              .authors(authors)
		              .publisher(entity.getPublisher() != null ? _publisherMapper.toSummaryDto(entity.getPublisher()) : null)
		              .categories(categories)
		              .publicationYear(entity.getPublicationYear())
		              .price(entity.getPrice())
		              .stockQuantity(entity.getStockQuantity())
		              .description(entity.getDescription())
		              .images(images)
		              .build();
	}

	public BookSummaryDto toSummaryDto(final BookEntity entity)
	{
		final List<AuthorSummaryDto> authors = entity.getAuthors().stream()
		                                             .map(_authorMapper::toSummaryDto)
		                                             .toList();

		final String primaryImageUrl = entity.getImages().stream()
		                                     .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
		                                     .map(BookImageEntity::getUrl)
		                                     .findFirst()
		                                     .orElse(null);

		return BookSummaryDto.builder()
		                     .id(entity.getId())
		                     .isbn(entity.getIsbn())
		                     .title(entity.getTitle())
		                     .authors(authors)
		                     .price(entity.getPrice())
		                     .stockQuantity(entity.getStockQuantity())
		                     .primaryImageUrl(primaryImageUrl)
		                     .build();
	}

	public BookEntity toEntity(final BookRequestDto dto)
	{
		return BookEntity.builder()
		                 .isbn(dto.getIsbn())
		                 .title(dto.getTitle())
		                 .publicationYear(dto.getPublicationYear())
		                 .price(dto.getPrice())
		                 .stockQuantity(dto.getStockQuantity())
		                 .description(dto.getDescription())
		                 .build();
		// publisher, authors, categories se postavljaju u servisu
	}

	private BookImageDto toImageDto(final BookImageEntity entity)
	{
		return BookImageDto.builder()
		                   .id(entity.getId())
		                   .url(entity.getUrl())
		                   .isPrimary(entity.getIsPrimary())
		                   .build();
	}
}
