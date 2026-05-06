package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AddressEntity;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>
{
	// US-08 — sve adrese dostave datog korisnika
	List<AddressEntity> findByUserId(Long userId);
}
