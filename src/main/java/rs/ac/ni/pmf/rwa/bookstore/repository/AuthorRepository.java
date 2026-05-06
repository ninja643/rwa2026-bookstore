package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AuthorEntity;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long>
{
	// Admin pretraga autora po prezimenu
	List<AuthorEntity> findByLastNameContainingIgnoreCase(String lastName);
}
