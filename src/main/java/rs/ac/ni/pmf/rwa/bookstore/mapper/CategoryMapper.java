package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategoryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategoryRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategorySummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CategoryEntity;

import java.util.List;

@Component
public class CategoryMapper
{
	public CategoryDto toDto(final CategoryEntity entity)
	{
		final List<CategoryDto> subcategories = entity.getSubcategories().stream()
		                                              .map(this::toDto)
		                                              .toList();

		return CategoryDto.builder()
		                  .id(entity.getId())
		                  .name(entity.getName())
		                  .description(entity.getDescription())
		                  .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
		                  .subcategories(subcategories)
		                  .build();
	}

	public CategorySummaryDto toSummaryDto(final CategoryEntity entity)
	{
		return CategorySummaryDto.builder()
		                         .id(entity.getId())
		                         .name(entity.getName())
		                         .build();
	}

	public CategoryEntity toEntity(final CategoryRequestDto dto)
	{
		return CategoryEntity.builder()
		                     .name(dto.getName())
		                     .description(dto.getDescription())
		                     .build();
		// parent se postavlja u servisu na osnovu dto.getParentId()
	}
}
