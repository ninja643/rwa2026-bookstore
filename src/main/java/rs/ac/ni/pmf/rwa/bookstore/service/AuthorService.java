package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.AuthorMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AuthorEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService
{
	private final AuthorRepository _authorRepository;
	private final AuthorMapper _authorMapper;

	public List<AuthorDto> getAllAuthors()
	{
		return _authorRepository.findAll().stream()
		                        .map(_authorMapper::toDto)
		                        .toList();
	}

	public AuthorDto getAuthorById(final Long id)
	{
		return _authorRepository.findById(id)
		                        .map(_authorMapper::toDto)
		                        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
	}

	public AuthorDto createAuthor(final AuthorRequestDto dto)
	{
		final AuthorEntity saved = _authorRepository.save(_authorMapper.toEntity(dto));
		return _authorMapper.toDto(saved);
	}

	public AuthorDto updateAuthor(final Long id, final AuthorRequestDto dto)
	{
		final AuthorEntity existing = _authorRepository.findById(id)
		                                               .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

		existing.setFirstName(dto.getFirstName());
		existing.setLastName(dto.getLastName());
		existing.setBiography(dto.getBiography());

		return _authorMapper.toDto(_authorRepository.save(existing));
	}

	public void deleteAuthor(final Long id)
	{
		if (!_authorRepository.existsById(id))
		{
			throw new ResourceNotFoundException("Author not found with id: " + id);
		}

		_authorRepository.deleteById(id);
	}
}
