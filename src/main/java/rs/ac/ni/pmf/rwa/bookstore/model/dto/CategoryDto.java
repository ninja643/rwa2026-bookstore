package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryDto
{
	Long id;
	String name;
	String description;
	Long parentId;
	List<CategoryDto> subcategories;
}
