package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
	Optional<UserEntity> findByUsername(String username);

	List<UserEntity> findAllByUsernameContaining(String username);

	List<UserEntity> findAllByFirstNameStartingWithAndLastNameStartingWith(String firstName, String lastName);

	List<UserEntity> findAllByRoles_Name(String roleName);
}
