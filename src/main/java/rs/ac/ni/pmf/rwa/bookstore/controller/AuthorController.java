package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController
{
	private final AuthorService _authorService;

	@GetMapping
	public List<AuthorDto> getAllAuthors()
	{
		return _authorService.getAllAuthors();
	}

	@GetMapping("/{id}")
	public AuthorDto getAuthorById(@PathVariable final Long id)
	{
		return _authorService.getAuthorById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AuthorDto createAuthor(@RequestBody @Valid final AuthorRequestDto dto)
	{
		return _authorService.createAuthor(dto);
	}

	@PutMapping("/{id}")
	public AuthorDto updateAuthor(@PathVariable final Long id,
	                              @RequestBody @Valid final AuthorRequestDto dto)
	{
		return _authorService.updateAuthor(id, dto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAuthor(@PathVariable final Long id)
	{
		_authorService.deleteAuthor(id);
	}
}
