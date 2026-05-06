package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.PaymentMethod;
import rs.ac.ni.pmf.rwa.bookstore.model.PaymentStatus;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", length = 50)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private PaymentStatus status;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;
}
