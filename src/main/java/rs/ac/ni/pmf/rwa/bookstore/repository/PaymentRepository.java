package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PaymentEntity;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>
{
	// US-13 — dohvatanje plaćanja za datu narudžbinu
	Optional<PaymentEntity> findByOrderId(Long orderId);
}
