package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategoryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CategoryRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController
{
	private final CategoryService _categoryService;

	@GetMapping
	public List<CategoryDto> getRootCategories()
	{
		return _categoryService.getRootCategories();
	}

	@GetMapping("/{id}")
	public CategoryDto getCategoryById(@PathVariable final Long id)
	{
		return _categoryService.getCategoryById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryDto createCategory(@RequestBody @Valid final CategoryRequestDto dto)
	{
		return _categoryService.createCategory(dto);
	}

	@PutMapping("/{id}")
	public CategoryDto updateCategory(@PathVariable final Long id,
	                                  @RequestBody @Valid final CategoryRequestDto dto)
	{
		return _categoryService.updateCategory(id, dto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategory(@PathVariable final Long id)
	{
		_categoryService.deleteCategory(id);
	}
}
