package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>
{
	// US-15 — sve recenzije za datu knjigu
	List<ReviewEntity> findByBookId(Long bookId);

	// US-15 — provera da li korisnik već ima recenziju za ovu knjigu (unique constraint)
	Optional<ReviewEntity> findByUserIdAndBookId(Long userId, Long bookId);

	boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
