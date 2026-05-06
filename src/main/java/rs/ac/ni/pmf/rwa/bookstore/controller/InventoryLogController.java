package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.InventoryLogDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.InventoryLogRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.InventoryLogService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory/logs")
@RequiredArgsConstructor
public class InventoryLogController
{
	private final InventoryLogService _inventoryLogService;

	@GetMapping
	public List<InventoryLogDto> getAllLogs()
	{
		return _inventoryLogService.getAllLogs();
	}

	@GetMapping("/book/{bookId}")
	public List<InventoryLogDto> getLogsByBookId(@PathVariable final Long bookId)
	{
		return _inventoryLogService.getLogsByBookId(bookId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryLogDto createLog(@RequestBody @Valid final InventoryLogRequestDto dto)
	{
		return _inventoryLogService.createLog(dto);
	}
}
