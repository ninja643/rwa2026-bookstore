package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@Column(name = "payment_method", length = 50)
	private String paymentMethod;

	@Column(length = 50)
	private String status;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Column(precision = 10, scale = 2)
	private BigDecimal amount;
}
