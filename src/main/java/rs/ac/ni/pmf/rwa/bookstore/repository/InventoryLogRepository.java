package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.InventoryLogEntity;

import java.util.List;

public interface InventoryLogRepository extends JpaRepository<InventoryLogEntity, Long>
{
	// US-16 — audit log promena zaliha za datu knjigu
	List<InventoryLogEntity> findByBookIdOrderByCreatedAtDesc(Long bookId);
}
