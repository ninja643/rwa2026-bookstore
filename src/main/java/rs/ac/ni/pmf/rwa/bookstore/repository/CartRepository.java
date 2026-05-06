package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CartEntity;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long>
{
	// US-09, US-10 — dohvatanje korpe za datog korisnika
	Optional<CartEntity> findByUserId(Long userId);
}
