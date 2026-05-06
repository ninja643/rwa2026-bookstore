package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.OrderStatus;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
	// US-14 — istorija narudžbina korisnika, sortirano od najnovije
	List<OrderEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

	// US-14 — filtriranje po statusu (admin pregled)
	List<OrderEntity> findByStatus(OrderStatus status);
}
