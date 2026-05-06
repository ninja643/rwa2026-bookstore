package rs.ac.ni.pmf.rwa.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.InventoryLogDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.InventoryLogRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.InventoryLogEntity;

@Component
@RequiredArgsConstructor
public class InventoryLogMapper
{
	private final BookMapper _bookMapper;

	public InventoryLogDto toDto(final InventoryLogEntity entity)
	{
		return InventoryLogDto.builder()
		                      .id(entity.getId())
		                      .book(_bookMapper.toSummaryDto(entity.getBook()))
		                      .changeAmount(entity.getChangeAmount())
		                      .reason(entity.getReason())
		                      .createdAt(entity.getCreatedAt())
		                      .build();
	}

	public InventoryLogEntity toEntity(final InventoryLogRequestDto dto)
	{
		return InventoryLogEntity.builder()
		                         .changeAmount(dto.getChangeAmount())
		                         .reason(dto.getReason())
		                         .build();
		// book se postavlja u servisu
	}
}
