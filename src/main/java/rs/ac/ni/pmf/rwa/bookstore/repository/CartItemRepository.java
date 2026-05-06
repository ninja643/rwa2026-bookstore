package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CartItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long>
{
	// US-10 — sve stavke u korpi
	List<CartItemEntity> findByCartId(Long cartId);

	// US-09, US-10 — provera da li knjiga već postoji u korpi (unique constraint)
	Optional<CartItemEntity> findByCartIdAndBookId(Long cartId, Long bookId);
}
