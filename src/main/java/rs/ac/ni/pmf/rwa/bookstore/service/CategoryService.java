package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.CategoryMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategoryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategoryRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CategoryEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
	private final CategoryRepository _categoryRepository;
	private final CategoryMapper _categoryMapper;

	public List<CategoryDto> getRootCategories()
	{
		return _categoryRepository.findByParentIsNull().stream()
		                          .map(_categoryMapper::toDto)
		                          .toList();
	}

	public CategoryDto getCategoryById(final Long id)
	{
		return _categoryRepository.findById(id)
		                          .map(_categoryMapper::toDto)
		                          .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
	}

	public CategoryDto createCategory(final CategoryRequestDto dto)
	{
		final CategoryEntity entity = _categoryMapper.toEntity(dto);

		if (dto.getParentId() != null)
		{
			final CategoryEntity parent = _categoryRepository.findById(dto.getParentId())
			                                                 .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + dto.getParentId()));
			entity.setParent(parent);
		}

		return _categoryMapper.toDto(_categoryRepository.save(entity));
	}

	public CategoryDto updateCategory(final Long id, final CategoryRequestDto dto)
	{
		final CategoryEntity existing = _categoryRepository.findById(id)
		                                                   .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

		existing.setName(dto.getName());
		existing.setDescription(dto.getDescription());

		if (dto.getParentId() != null)
		{
			final CategoryEntity parent = _categoryRepository.findById(dto.getParentId())
			                                                 .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + dto.getParentId()));
			existing.setParent(parent);
		}
		else
		{
			existing.setParent(null);
		}

		return _categoryMapper.toDto(_categoryRepository.save(existing));
	}

	public void deleteCategory(final Long id)
	{
		if (!_categoryRepository.existsById(id))
		{
			throw new ResourceNotFoundException("Category not found with id: " + id);
		}

		_categoryRepository.deleteById(id);
	}
}
