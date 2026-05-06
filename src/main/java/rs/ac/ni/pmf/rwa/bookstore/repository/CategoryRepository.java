package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>
{
	// US-03 — korene kategorije (bez roditelja) za prikaz stabla
	List<CategoryEntity> findByParentIsNull();

	// US-03 — podkategorije date kategorije
	List<CategoryEntity> findByParentId(Long parentId);

	Optional<CategoryEntity> findByNameIgnoreCase(String name);
}
