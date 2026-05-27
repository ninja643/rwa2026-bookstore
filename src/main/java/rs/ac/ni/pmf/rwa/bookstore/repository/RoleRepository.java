package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>
{
}
