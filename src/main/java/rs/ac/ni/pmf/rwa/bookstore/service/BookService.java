package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.BookMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.BookDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.BookRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.BookSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.*;
import rs.ac.ni.pmf.rwa.bookstore.repository.AuthorRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.BookRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.CategoryRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.PublisherRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.specification.BookSpecifications;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService
{
	private final BookRepository _bookRepository;
	private final AuthorRepository _authorRepository;
	private final PublisherRepository _publisherRepository;
	private final CategoryRepository _categoryRepository;
	private final BookMapper _bookMapper;

	public List<BookSummaryDto> getAllBooks()
	{
		return _bookRepository.findAll().stream()
		                      .map(_bookMapper::toSummaryDto)
		                      .toList();
	}

	public BookDto getBookById(final Long id)
	{
		return _bookRepository.findById(id)
		                      .map(_bookMapper::toDto)
		                      .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
	}

	public BookDto createBook(final BookRequestDto dto)
	{
		final BookEntity entity = _bookMapper.toEntity(dto);

		resolveRelations(entity, dto);

		return _bookMapper.toDto(_bookRepository.save(entity));
	}

	public BookDto updateBook(final Long id, final BookRequestDto dto)
	{
		final BookEntity existing = _bookRepository.findById(id)
		                                           .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

		existing.setIsbn(dto.getIsbn());
		existing.setTitle(dto.getTitle());
		existing.setPublicationYear(dto.getPublicationYear());
		existing.setPrice(dto.getPrice());
		existing.setStockQuantity(dto.getStockQuantity());
		existing.setDescription(dto.getDescription());

		resolveRelations(existing, dto);

		return _bookMapper.toDto(_bookRepository.save(existing));
	}

	public void deleteBook(final Long id)
	{
		if (!_bookRepository.existsById(id))
		{
			throw new ResourceNotFoundException("Book not found with id: " + id);
		}

		_bookRepository.deleteById(id);
	}

	private void resolveRelations(final BookEntity entity, final BookRequestDto dto)
	{
		if (dto.getPublisherId() != null)
		{
			final PublisherEntity publisher = _publisherRepository.findById(dto.getPublisherId())
			                                                      .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + dto.getPublisherId()));
			entity.setPublisher(publisher);
		}

		if (dto.getAuthorIds() != null)
		{
			final Set<AuthorEntity> authors = new HashSet<>();
			for (final Long authorId : dto.getAuthorIds())
			{
				final AuthorEntity author = _authorRepository.findById(authorId)
				                                             .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));
				authors.add(author);
			}
			entity.setAuthors(authors);
		}

		if (dto.getCategoryIds() != null)
		{
			final Set<CategoryEntity> categories = new HashSet<>();
			for (final Long categoryId : dto.getCategoryIds())
			{
				final CategoryEntity category = _categoryRepository.findById(categoryId)
				                                                   .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
				categories.add(category);
			}
			entity.setCategories(categories);
		}
	}

	public List<BookSummaryDto> searchBooks(final String title, final String author)
	{
		final Pageable pageable = PageRequest.of(
				0,
				20,
				Sort.by(BookEntity_.TITLE).ascending());

		return _bookRepository
				.findAll(BookSpecifications.search(title, author), pageable)
				.stream()
				.map(_bookMapper::toSummaryDto)
				.collect(Collectors.toList());
	}
}
