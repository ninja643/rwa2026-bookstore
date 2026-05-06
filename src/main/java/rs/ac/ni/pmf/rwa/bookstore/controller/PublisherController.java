package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.PublisherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController
{
	private final PublisherService _publisherService;

	@GetMapping
	public List<PublisherDto> getAllPublishers()
	{
		return _publisherService.getAllPublishers();
	}

	@GetMapping("/{id}")
	public PublisherDto getPublisherById(@PathVariable final Long id)
	{
		return _publisherService.getPublisherById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PublisherDto createPublisher(@RequestBody @Valid final PublisherRequestDto dto)
	{
		return _publisherService.createPublisher(dto);
	}

	@PutMapping("/{id}")
	public PublisherDto updatePublisher(@PathVariable final Long id,
	                                    @RequestBody @Valid final PublisherRequestDto dto)
	{
		return _publisherService.updatePublisher(id, dto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePublisher(@PathVariable final Long id)
	{
		_publisherService.deletePublisher(id);
	}
}
