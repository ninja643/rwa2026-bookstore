package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PublisherEntity;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<PublisherEntity, Long>
{
	Optional<PublisherEntity> findByNameIgnoreCase(String name);

	List<PublisherEntity> findByNameContainingIgnoreCase(String name);
}
