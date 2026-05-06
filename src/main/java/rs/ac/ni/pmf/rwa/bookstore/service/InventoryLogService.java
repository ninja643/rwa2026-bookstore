package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.InventoryLogMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.InventoryLogDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.InventoryLogRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.InventoryLogEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.BookRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.InventoryLogRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryLogService
{
	private final InventoryLogRepository _inventoryLogRepository;
	private final BookRepository _bookRepository;
	private final InventoryLogMapper _inventoryLogMapper;

	public List<InventoryLogDto> getAllLogs()
	{
		return _inventoryLogRepository.findAll().stream()
		                              .map(_inventoryLogMapper::toDto)
		                              .toList();
	}

	public List<InventoryLogDto> getLogsByBookId(final Long bookId)
	{
		if (!_bookRepository.existsById(bookId))
		{
			throw new ResourceNotFoundException("Book not found with id: " + bookId);
		}

		return _inventoryLogRepository.findByBookIdOrderByCreatedAtDesc(bookId).stream()
		                              .map(_inventoryLogMapper::toDto)
		                              .toList();
	}

	public InventoryLogDto createLog(final InventoryLogRequestDto dto)
	{
		final BookEntity book = _bookRepository.findById(dto.getBookId())
		                                       .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getBookId()));

		final InventoryLogEntity entity = _inventoryLogMapper.toEntity(dto);
		entity.setBook(book);

		return _inventoryLogMapper.toDto(_inventoryLogRepository.save(entity));
	}
}
