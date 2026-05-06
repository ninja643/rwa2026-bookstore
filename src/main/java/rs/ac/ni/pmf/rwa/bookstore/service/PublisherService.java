package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.PublisherMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PublisherEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.PublisherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService
{
	private final PublisherRepository _publisherRepository;
	private final PublisherMapper _publisherMapper;

	public List<PublisherDto> getAllPublishers()
	{
		return _publisherRepository.findAll().stream()
		                           .map(_publisherMapper::toDto)
		                           .toList();
	}

	public PublisherDto getPublisherById(final Long id)
	{
		return _publisherRepository.findById(id)
		                           .map(_publisherMapper::toDto)
		                           .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));
	}

	public PublisherDto createPublisher(final PublisherRequestDto dto)
	{
		final PublisherEntity saved = _publisherRepository.save(_publisherMapper.toEntity(dto));
		return _publisherMapper.toDto(saved);
	}

	public PublisherDto updatePublisher(final Long id, final PublisherRequestDto dto)
	{
		final PublisherEntity existing = _publisherRepository.findById(id)
		                                                     .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));

		existing.setName(dto.getName());
		existing.setCity(dto.getCity());
		existing.setCountry(dto.getCountry());

		return _publisherMapper.toDto(_publisherRepository.save(existing));
	}

	public void deletePublisher(final Long id)
	{
		if (!_publisherRepository.existsById(id))
		{
			throw new ResourceNotFoundException("Publisher not found with id: " + id);
		}

		_publisherRepository.deleteById(id);
	}
}
