package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
	Optional<UserEntity> findByUsername(String username);

	boolean existsByUsername(String username);
}
