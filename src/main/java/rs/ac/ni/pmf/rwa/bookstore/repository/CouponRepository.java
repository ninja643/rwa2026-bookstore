package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CouponEntity;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<CouponEntity, Long>
{
	// US-12 — lookup kupona po kodu pri unosu
	Optional<CouponEntity> findByCode(String code);

	// Admin pregled aktivnih kupona
	List<CouponEntity> findByIsActiveTrue();
}
