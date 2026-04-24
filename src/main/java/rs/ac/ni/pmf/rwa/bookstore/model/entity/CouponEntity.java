package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 50)
	private String code;

	@Column(name = "discount_value", precision = 10, scale = 2)
	private BigDecimal discountValue;

	@Column(name = "discount_type", length = 20)
	private String discountType;

	@Column(name = "valid_from")
	private LocalDateTime validFrom;

	@Column(name = "valid_to")
	private LocalDateTime validTo;

	@ManyToMany(mappedBy = "coupons")
	@Builder.Default
	private Set<OrderEntity> orders = new HashSet<>();
}
