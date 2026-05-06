package rs.ac.ni.pmf.rwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.OrderItemEntity;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long>
{
	// US-11 — stavke konkretne narudžbine
	List<OrderItemEntity> findByOrderId(Long orderId);
}
